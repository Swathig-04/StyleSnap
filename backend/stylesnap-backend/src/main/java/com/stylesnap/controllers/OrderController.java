package com.stylesnap.controllers;

import com.stylesnap.models.Cart;
import com.stylesnap.models.Transaction;
import com.stylesnap.models.TransactionDetail;
import com.stylesnap.models.Catalog;
import com.stylesnap.repositories.CartRepository;
import com.stylesnap.repositories.TransactionRepository;
import com.stylesnap.repositories.TransactionDetailRepository;
import com.stylesnap.repositories.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private TransactionDetailRepository detailRepo;

    @Autowired
    private CatalogRepository catalogRepo;

    @PostMapping("/place")
    public ResponseEntity<String> placeOrder(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login required");
        }

        List<Cart> cartItems = cartRepo.findByUserId(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.badRequest().body("Cart is empty");
        }

        double totalCost = 0;
        StringBuilder orderDetailsBuilder = new StringBuilder();

        for (Cart item : cartItems) {
            Catalog product = catalogRepo.findById(item.getProductId()).orElse(null);
            if (product == null) continue;

            double price = product.getPrice();
            totalCost += price * item.getQuantity();

            orderDetailsBuilder.append("ProductID: ").append(item.getProductId())
                    .append(", Qty: ").append(item.getQuantity())
                    .append(", ₹").append(price)
                    .append(" | ");
        }

        double discount = 0;
        double platformFee = 20;
        double finalPrice = totalCost - discount + platformFee;

        Transaction txn = new Transaction();
        txn.setUserId(userId);
        txn.setTotalCost(totalCost);
        txn.setDiscount(discount);
        txn.setFinalPrice(finalPrice);
        txn.setOrderDetails(orderDetailsBuilder.toString());
        txn.setCreatedAt(LocalDateTime.now());

        txn = transactionRepo.save(txn);

        for (Cart item : cartItems) {
            Catalog product = catalogRepo.findById(item.getProductId()).orElse(null);
            if (product == null) continue;

            TransactionDetail detail = new TransactionDetail();
            detail.setTransactionId(txn.getTransactionId());
            detail.setProductId(item.getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setPriceAtPurchase(product.getPrice());

            detailRepo.save(detail);
        }

        // ✅ Now cart is cleared only after placing order
        cartRepo.deleteAll(cartItems);

        return ResponseEntity.ok("Order placed successfully!");
    }

    @GetMapping("/preview")
    public ResponseEntity<Map<String, Object>> getOrderPreview(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Cart> cartItems = cartRepo.findByUserId(userId);
        if (cartItems == null || cartItems.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        double totalCost = 0;
        for (Cart item : cartItems) {
            Catalog product = catalogRepo.findById(item.getProductId()).orElse(null);
            if (product != null) {
                totalCost += product.getPrice() * item.getQuantity();
            }
        }

        double discount = 0;
        double platformFee = 20;
        double finalPrice = totalCost - discount + platformFee;

        Map<String, Object> response = new HashMap<>();
        response.put("totalCost", totalCost);
        response.put("discount", discount);
        response.put("platformFee", platformFee);
        response.put("finalPrice", finalPrice);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/myorders")
    public ResponseEntity<List<Transaction>> getOrderHistory(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Transaction> orders = transactionRepo.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/latest")
    public ResponseEntity<Map<String, String>> getLatestOrder(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<Transaction> transactions = transactionRepo.findByUserId(userId);
        if (transactions == null || transactions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Transaction latest = transactions.stream()
                .max((t1, t2) -> t1.getCreatedAt().compareTo(t2.getCreatedAt()))
                .orElse(null);

        if (latest == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, String> response = new HashMap<>();
        response.put("orderId", "STY" + latest.getTransactionId());
        response.put("totalCost", String.format("%.2f", latest.getFinalPrice()));
        response.put("paymentMode", "UPI");
        response.put("estimatedDelivery", "Jul 3 – Jul 6");

        return ResponseEntity.ok(response);
    }
}
