package com.example.securityjwt.repository;

import com.example.securityjwt.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findRoleByName(String roleName);
}
