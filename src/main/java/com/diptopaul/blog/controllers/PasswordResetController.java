package com.diptopaul.blog.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diptopaul.blog.entities.PasswordResetToken;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.ApiResponse;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.EmailService;
import com.diptopaul.blog.services.PasswordResetTokenService;
import com.diptopaul.blog.services.UserService;
import com.diptopaul.blog.validationgroup.PasswordResetValidation;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@RestController
@RequestMapping("/api/v1/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenService resetService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private UserService userService;

    private final String resetPasswordUrlApiEnd = "/api/v1/password/resetPassword";

	private ModelMapper modelMapper;
    
    @PostMapping("/resetRequest")
    public ResponseEntity<String> requestPasswordReset(HttpServletRequest request, @RequestParam(value = "username", required = true) String email) {
        UserDto userDto = this.userService.getUserByEmail(email);// Find the user by email using UserRepository or UserService
        if (userDto != null) {
            PasswordResetTokenDto tokenDto = resetService.createToken(userDto);
            emailService.sendPasswordResetEmail(userDto, tokenDto, this.getBaseUrl(request) + resetPasswordUrlApiEnd, request.getLocale());
            return ResponseEntity.ok("Password reset email sent successfully!");
        }
        return ResponseEntity.badRequest().body("User not found with the provided email");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @RequestBody Map<String, String> requestBody) {
        if (this.resetService.validateToken(token)) {
        	String newPassword = requestBody.get("password");
        	
        	//create temporary userDto and set the new password, this tempo userDto will be used for validation check
        	UserDto userDto = new UserDto();
        	userDto.setPassword(newPassword);
            
            // Validate the UserDto based on the validation annotations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserDto>> violations = validator.validateProperty(userDto, "password", PasswordResetValidation.class);

            if (!violations.isEmpty()) {
            	ConstraintViolation<UserDto> firstViolation = violations.iterator().next();//take the first violation, there might be many
                return ResponseEntity.badRequest().body(new ApiResponse(firstViolation.getMessage(), "password", false));
            }
            
            // If validation succeeds, update the user's password
            this.resetService.resetPassword(token, userDto);
            return ResponseEntity.ok(new ApiResponse("Password reset successfully!", true));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Invalid or expired token.", false));
    }

    //error prone
//    @PostMapping("/resetPassword")
//    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @Validated(PasswordResetValidation.class) @RequestBody UserDto userDto) {
//    	/***** Instead of Validationg on UserDto, Alternative could be create a new Entity for Password and validate on that Entity (for now it's not neeeded) *****/
//    	if (this.resetService.validateToken(token)) {
//	        // If validation succeeds, update the user's password
//	        this.resetService.resetPassword(token, userDto);
//	        return ResponseEntity.ok(new ApiResponse("Password reset successfully!", true));
//	    }
//	    return ResponseEntity.badRequest().body(new ApiResponse("Invalid or expired token.", false));
//    }
	
    //an helper method
	private String getBaseUrl(HttpServletRequest request) {
	    String scheme = request.getScheme();
	    String serverName = request.getServerName();
	    int serverPort = request.getServerPort();
	    String contextPath = request.getContextPath();

	    StringBuilder baseUrl = new StringBuilder();
	    baseUrl.append(scheme).append("://").append(serverName);

	    // Append port number if it's not the default port (80 for HTTP, 443 for HTTPS)
	    if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
	        baseUrl.append(":").append(serverPort);
	    }

	    baseUrl.append(contextPath);

	    return baseUrl.toString();
	}

}
