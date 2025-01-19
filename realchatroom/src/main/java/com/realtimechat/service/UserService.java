package com.realtimechat.service;

import com.realtimechat.model.User;
import com.realtimechat.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Method to register a new user
    public User registerUser(String email, String password, String displayName, String securityQuestion1, String securityQuestion2) {
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("User with email " + email + " already exists");
        }
        
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(email, encodedPassword, displayName, securityQuestion1, securityQuestion2);
        return userRepository.save(user);
    }

    // Method to find a user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // JPA save() can handle updates on existing entities
    public User updateUser(User user) {
        return userRepository.save(user);  
    }
    
    // Verify the security answers for the user
    public boolean verifySecurityAnswers(String email, String securityAnswer1, String securityAnswer2) {
    	 User userOptional = userRepository.findByEmail(email);
         System.out.println("email: "+email+", securityAnswer1"+securityAnswer1+", securityAnswer2"+securityAnswer2);
         if (userOptional != null) {
             User user = userOptional;
             return user.getSecurityQuestion1().equals(securityAnswer1) && 
                    user.getSecurityQuestion2().equals(securityAnswer2);
         }
         return false;
    }
    
    // Method to update the user's password
    public void updatePassword(String email, String newPassword) {
        User userOptional = userRepository.findByEmail(email);
        
        if (userOptional != null) {
            User user = userOptional;
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }
}
