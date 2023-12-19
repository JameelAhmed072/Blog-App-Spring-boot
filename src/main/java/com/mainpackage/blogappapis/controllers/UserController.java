package com.mainpackage.blogappapis.controllers;

import com.mainpackage.blogappapis.payloads.ApiResponse;
import com.mainpackage.blogappapis.payloads.UserDto;
import com.mainpackage.blogappapis.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {


    @Autowired
    private UserService userService;


    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        UserDto userCreated = userService.createUser(userDto);

        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);

    }
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> allUser(){

        List<UserDto> allUsers = userService.getAllUsers();

        return ResponseEntity.ok(allUsers);

    }
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer id){

        UserDto allUsers = userService.getUserById(id);
        return ResponseEntity.ok(allUsers);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer id){

        UserDto updatedUser = userService.updateUser(userDto,id);
        return ResponseEntity.ok(updatedUser);
    }
//    @PutMapping("/user/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
//        userService.deleteUser(id);
//        return new ResponseEntity(Map.of("Message","User deleted Successfully"),HttpStatus.OK);
//    }
    //   or we can make ApiResponse class seperatly and then call it here
    @DeleteMapping("/user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("User deleted Successfully",true),HttpStatus.OK);
    }

    @GetMapping("/current-user-logged-in")
    public String getLoggedInUser(Principal principal){
        return principal.getName();
    }

}
