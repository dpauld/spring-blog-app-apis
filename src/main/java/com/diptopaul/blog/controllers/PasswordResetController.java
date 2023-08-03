package com.diptopaul.blog.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        	String newPassword = requestBody.get("newPassword");
        	
        	/***** Complete newPassword validation code, Alternative could be create a new Entity for Password and validate on that Entity (for now it's not neeeded) *****/
        	//If token validation succeeds, perform the validation of the new password
        	PasswordResetTokenDto tokenDto = this.resetService.getPasswordResetTokenByToken(token);
        	UserDto userDto =  tokenDto.getUserDto();
        	userDto.setPassword(newPassword);
        	// Update the user's password, this way we can use the userDto and its validation rules to perform validation on the newPassword
            userDto.setPassword(newPassword);
            
            System.out.println("Nameeeeeeeeeee: " + userDto.getName());
            // Validate the UserDto based on the validation annotations
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<UserDto>> violations = validator.validateProperty(userDto, "password");

            if (!violations.isEmpty()) {
            	ConstraintViolation<UserDto> firstViolation = violations.iterator().next();
                //List<String> errors = Collections.singletonList(firstViolation.getMessage());
                return ResponseEntity.badRequest().body(new ApiResponse(firstViolation.getMessage(), "password", false));
                
//                StringBuilder errorMessages = new StringBuilder();
//                for (ConstraintViolation<UserDto> violation : violations) {
//                    errorMessages.append(violation.getMessage()).append("\n");
//                }
//                return ResponseEntity.badRequest().body(errorMessages.toString());
            }
            /**** *****/
            
            // If validation succeeds, update the user's password
            this.resetService.resetPassword(token, newPassword);
            return ResponseEntity.ok(new ApiResponse("Password reset successfully!", true));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Invalid or expired token.", false));
    }

	
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
