package com.example.to_do_spring.dtos;

import jakarta.validation.constraints.NotBlank;


public record TaskRequest (

        @NotBlank(message = "A descrição da tarefa é obrigatória.")
        String description,
        @NotBlank(message = "CPF é obrigatório, pois precisa informar o usuário.")
        String cpf
) {}

