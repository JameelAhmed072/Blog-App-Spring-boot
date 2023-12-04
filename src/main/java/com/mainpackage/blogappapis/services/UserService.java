package com.mainpackage.blogappapis.services;

import com.mainpackage.blogappapis.entities.User;
import com.mainpackage.blogappapis.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(Integer id);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);



}
