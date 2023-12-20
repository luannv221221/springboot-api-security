package com.example.securityjwt.service;

import com.example.securityjwt.model.Role;
import com.example.securityjwt.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
