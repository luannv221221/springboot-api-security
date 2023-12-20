package com.example.securityjwt.service;

import com.example.securityjwt.dto.request.UserRequestDTO;
import com.example.securityjwt.dto.response.UserResponseDTO;
import com.example.securityjwt.model.Role;
import com.example.securityjwt.model.User;
import com.example.securityjwt.repository.UserRepository;
import com.example.securityjwt.security.jwt.JWTProvider;
import com.example.securityjwt.security.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private RoleService roleService;
    @Override
    public User register(User user) {
        // ma hoa mat khau
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // role
        Set<Role> roles = new HashSet<>();
        // register cua user thi coi no la USER
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            roles.add(roleService.findByRoleName("USER"));
        } else {
            // Tao tai khoan va phan quyen thi phai co quyen ADMIN
            user.getRoles().forEach(role->{
                roles.add(roleService.findByRoleName(role.getName()));
            });
        }
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setStatus(user.getStatus());
        newUser.setRoles(roles);
        return userRepository.save(newUser);
    }

    @Override
    public UserResponseDTO login(UserRequestDTO userRequestDTO) {
        Authentication authentication;
        authentication = authenticationProvider.
                authenticate(new UsernamePasswordAuthenticationToken(userRequestDTO.getUserName(),userRequestDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return UserResponseDTO.builder()
                .token(jwtProvider.generateToken(userPrinciple)).
                userName(userPrinciple.getUsername()).
                roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
                .build();
    }
}
