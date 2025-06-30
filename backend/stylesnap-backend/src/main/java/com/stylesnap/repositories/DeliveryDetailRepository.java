package com.stylesnap.repositories;

import com.stylesnap.models.DeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Integer> {
    DeliveryDetail findByUserId(int userId); 
}
