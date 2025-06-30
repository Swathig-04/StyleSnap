package com.stylesnap.controllers;

import com.stylesnap.models.ContactMessage;
import com.stylesnap.repositories.ContactRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping
    public ResponseEntity<String> submitContact(@RequestBody ContactMessage contactMessage, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            contactMessage.setUserId(userId);
        } else {
            contactMessage.setUserId(null); 
        }
        
        contactRepository.save(contactMessage);
        return ResponseEntity.ok("Message submitted successfully");
    }

    @GetMapping
    public List<ContactMessage> getAllMessages() {
        return contactRepository.findAll();
    }
}
