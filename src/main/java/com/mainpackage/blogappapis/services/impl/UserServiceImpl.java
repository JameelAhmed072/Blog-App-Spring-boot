package com.mainpackage.blogappapis.services.impl;


import com.mainpackage.blogappapis.entities.User;
import com.mainpackage.blogappapis.exceptions.ResourceNotFoundException;
import com.mainpackage.blogappapis.payloads.UserDto;
import com.mainpackage.blogappapis.repositories.UserRepo;
import com.mainpackage.blogappapis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);

        User savedUser = userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", " id ",userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser = userRepo.save(user);

        return userToDto(savedUser);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User", " id ",id));

        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> allUsers = userRepo.findAll();

        List<UserDto> userDtos = allUsers.stream()
                .map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }
    @Override
    public void deleteUser(Integer userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", " id ",userId));
        userRepo.delete(user);
    }


    //   using the model mapper class
    public User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        return userDto;
    }



    //   manually converting from dto to class and class to dto
//    private User dtoToUser(UserDto userDto){
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
//
//        return user;
//    }
//
//    public UserDto userToDto(User user){
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
//
//        return userDto;
//
//    }
}

