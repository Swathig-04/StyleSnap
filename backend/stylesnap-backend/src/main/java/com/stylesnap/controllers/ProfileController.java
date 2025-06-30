package com.stylesnap.controllers;

import com.stylesnap.models.User;
import com.stylesnap.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
//@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")


public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getProfile(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Please log in to view profile.");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}
