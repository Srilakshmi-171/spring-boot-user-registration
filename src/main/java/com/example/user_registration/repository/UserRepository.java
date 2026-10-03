package com.example.user_registration.repository;

import com.example.user_registration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 🌟 This line tells the assistant exactly how to search by email!
    Optional<User> findByEmail(String email);
}
