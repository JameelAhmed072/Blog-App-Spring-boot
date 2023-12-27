package com.mainpackage.blogappapis.controllers;

import com.mainpackage.blogappapis.payloads.AuthRequestDTO;
import com.mainpackage.blogappapis.payloads.JwtResponseDTO;
import com.mainpackage.blogappapis.security.JwtService;
import com.mainpackage.blogappapis.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect Username or Password ! ",e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());
        String jwtToken =jwtService.GenerateToken(userDetails);
        return new JwtResponseDTO(jwtToken);

    }
}
