package com.stylesnap.repositories;

import com.stylesnap.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAndPasswordHash(String email, String passwordHash);

    User findByEmail(String email);
}
