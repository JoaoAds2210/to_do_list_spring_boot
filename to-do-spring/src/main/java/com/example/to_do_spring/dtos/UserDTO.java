package com.example.to_do_spring.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;

import java.util.List;


public record UserDTO(

        Long id,

        @NotBlank(message = "O nome é obrigatório.")
        String userName,

        @NotBlank(message = "A senha é obrigatória.")
        String senha,

        @NotBlank(message = "O CPF é obrigatório.")
        String cpf,

        @JsonIgnore
        List<TaskResponse> task

) {}
