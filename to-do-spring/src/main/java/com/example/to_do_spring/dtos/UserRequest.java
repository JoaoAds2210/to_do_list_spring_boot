package com.example.to_do_spring.dtos;

import jakarta.validation.constraints.NotBlank;


public record UserRequest(

        @NotBlank(message = "O nome é obrigatório.")
        String username,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @NotBlank(message = "O CPF é obrigatório.")
        String cpf

) {}
