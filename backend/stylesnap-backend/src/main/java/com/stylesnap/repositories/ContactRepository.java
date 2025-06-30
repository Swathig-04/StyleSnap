package com.stylesnap.repositories;

import com.stylesnap.models.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {
}
