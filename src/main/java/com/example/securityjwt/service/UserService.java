package com.example.securityjwt.service;

import com.example.securityjwt.dto.request.UserRequestDTO;
import com.example.securityjwt.dto.response.UserResponseDTO;
import com.example.securityjwt.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService  {
    User register(User user);
    UserResponseDTO login(UserRequestDTO userRequestDTO);
}
