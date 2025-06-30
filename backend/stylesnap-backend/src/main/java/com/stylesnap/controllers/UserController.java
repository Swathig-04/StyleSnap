package com.stylesnap.controllers;
import com.stylesnap.models.User;
import com.stylesnap.models.LoginRequest;
import com.stylesnap.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
        String email = request.getEmail().trim();
        String password = request.getPassword().trim();

        System.out.println("Trying login for email: " + email + ", password: " + password);

        User user = userRepo.findByEmailAndPasswordHash(email, password);
        if (user != null) {
            System.out.println("Login success: " + user.getEmail());
            session.setAttribute("userId", user.getUserId());
            return ResponseEntity.ok("Login successful");
        } else {
            System.out.println("Login failed for: " + email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); 
        return ResponseEntity.ok("Logged out successfully");
    }
    @GetMapping("/status")
public ResponseEntity<?> checkStatus(HttpSession session) {
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }

    Optional<User> user = userRepo.findById(userId);
    if (user.isPresent()) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", user.get().getUserId());
        map.put("email", user.get().getEmail());
        map.put("username", user.get().getName()); 
        return ResponseEntity.ok(map);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
}

    

@PostMapping("/signup")
public ResponseEntity<String> signup(@RequestBody User user) {
    if (user.getEmail() == null || user.getPasswordHash() == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required");
    }

    if (userRepo.findByEmail(user.getEmail()) != null) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
    }

    userRepo.save(user);
    return ResponseEntity.ok("Signup successful");
}
}