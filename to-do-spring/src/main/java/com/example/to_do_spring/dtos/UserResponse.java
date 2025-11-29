package com.example.to_do_spring.dtos;

import java.util.List;

public record UserResponse(
        Long id,
        String username,
        String cpf,
        List<TaskResponse> tasks
) {}
