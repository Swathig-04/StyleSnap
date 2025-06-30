package com.stylesnap.repositories;

import com.stylesnap.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(int userId);
    void deleteByUserId(int userId);
    Optional<Cart> findByUserIdAndProductIdAndSize(int userId, int productId, String size);
}
