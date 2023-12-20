package com.example.securityjwt.repository;

import com.example.securityjwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
}
