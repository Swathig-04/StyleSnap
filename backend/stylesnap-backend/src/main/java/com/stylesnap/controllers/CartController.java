package com.stylesnap.controllers;

import com.stylesnap.models.Cart;
import com.stylesnap.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody Cart newCartItem, HttpSession session) {
        Object userIdObj = session.getAttribute("userId");
        if (userIdObj == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login to add items to cart.");
        }
        int userId = (int) userIdObj;
        newCartItem.setUserId(userId);
        try {
            Optional<Cart> existingCartOpt = cartRepository.findByUserIdAndProductIdAndSize(
                    userId, newCartItem.getProductId(), newCartItem.getSize()
            );

            if (existingCartOpt.isPresent()) {
                Cart existingCart = existingCartOpt.get();
                int updatedQty = existingCart.getQuantity() + newCartItem.getQuantity();
                existingCart.setQuantity(updatedQty);
                cartRepository.save(existingCart);
            } else {
                newCartItem.setAddedAt(new Date());
                cartRepository.save(newCartItem);
            }
            return ResponseEntity.ok("Added/Updated cart item");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    @GetMapping("/view")
    public ResponseEntity<List<Cart>> viewCart(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Cart> cartItems = cartRepository.findByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<String> deleteCartItem(@PathVariable int cartId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");
        }

        Cart item = cartRepository.findById(cartId).orElse(null);
        if (item == null || item.getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }

        try {
            cartRepository.deleteById(cartId);
            return ResponseEntity.ok("Item removed.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove item.");
        }
    }
    @PutMapping("/update/{cartId}")
    public ResponseEntity<?> updateQuantity(@PathVariable int cartId, @RequestBody Cart updatedCart, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");
        }

        Cart cartItem = cartRepository.findById(cartId).orElse(null);
        if (cartItem == null || cartItem.getUserId() != userId) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }

        if (updatedCart.getQuantity() < 1) {
            return ResponseEntity.badRequest().body("Quantity must be at least 1.");
        }

        cartItem.setQuantity(updatedCart.getQuantity());
        cartRepository.save(cartItem);
        return ResponseEntity.ok("Quantity updated.");
    }
    @GetMapping("/count")
public ResponseEntity<Integer> getCartCount(HttpSession session) {
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        return ResponseEntity.ok(0); 
    }

    List<Cart> cartItems = cartRepository.findByUserId(userId);
    int totalQuantity = cartItems.stream().mapToInt(Cart::getQuantity).sum();
    return ResponseEntity.ok(totalQuantity);
}

}
