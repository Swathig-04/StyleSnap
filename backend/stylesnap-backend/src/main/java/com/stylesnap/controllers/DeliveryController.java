package com.stylesnap.controllers;

import com.stylesnap.models.DeliveryDetail;
import com.stylesnap.repositories.DeliveryDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.Date;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryDetailRepository deliveryRepo;
    @PostMapping("/save")
public ResponseEntity<String> saveAddress(@RequestBody DeliveryDetail delivery, HttpSession session) {
    Integer userId = (Integer) session.getAttribute("userId");
    System.out.println("📦 Incoming Delivery JSON: " + delivery);
    System.out.println("🔑 User ID from session: " + userId);

    if (userId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
    }

    if (deliveryRepo.findByUserId(userId) != null) {
        return ResponseEntity.badRequest().body("Address already exists");
    }

    delivery.setUserId(userId);
    delivery.setAddedAt(new Date());

    try {
        deliveryRepo.save(delivery);
        return ResponseEntity.ok("Address saved successfully");
    } catch (Exception e) {
        e.printStackTrace();  
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving address");
    }
}
    @GetMapping("/get")
    public ResponseEntity<DeliveryDetail> getAddress(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        DeliveryDetail detail = deliveryRepo.findByUserId(userId);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(detail);
    }
}
