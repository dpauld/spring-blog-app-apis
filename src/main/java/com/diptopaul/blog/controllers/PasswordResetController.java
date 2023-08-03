package com.diptopaul.blog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diptopaul.blog.entities.PasswordResetToken;
import com.diptopaul.blog.entities.User;
import com.diptopaul.blog.payloads.PasswordResetTokenDto;
import com.diptopaul.blog.payloads.UserDto;
import com.diptopaul.blog.repositories.UserRepo;
import com.diptopaul.blog.services.EmailService;
import com.diptopaul.blog.services.PasswordResetTokenService;
import com.diptopaul.blog.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

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
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestBody Map<String, String> requestBody) {
        if (this.resetService.validateToken(token)) {
            this.resetService.resetPassword(token, requestBody.get("newPassword"));
            return ResponseEntity.ok("Password reset successfully!");
        }
        return ResponseEntity.badRequest().body("Invalid or expired token");
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
