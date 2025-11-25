package com.example.to_do_spring.controller;

import com.example.to_do_spring.config.TokenService;
import com.example.to_do_spring.dtos.AuthenticationDTO;
import com.example.to_do_spring.dtos.LoginResponseDTO;
import com.example.to_do_spring.dtos.RegisterDTO;
import com.example.to_do_spring.entity.User;
import com.example.to_do_spring.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repositories;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamPassword = new UsernamePasswordAuthenticationToken(data.username(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamPassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repositories.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword =new BCryptPasswordEncoder().encode(data.senha());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        this.repositories.save(newUser);

        return ResponseEntity.ok().build();
    }
}
