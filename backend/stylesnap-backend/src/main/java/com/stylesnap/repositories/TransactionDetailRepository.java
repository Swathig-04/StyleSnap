package com.stylesnap.repositories;

import com.stylesnap.models.TransactionDetail; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, Integer> {
}
