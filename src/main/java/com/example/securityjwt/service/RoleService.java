package com.example.securityjwt.service;

import com.example.securityjwt.model.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}
