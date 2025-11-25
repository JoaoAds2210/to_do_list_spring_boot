package com.example.to_do_spring.dtos;

import com.example.to_do_spring.entity.enums.Roles;

public record RegisterDTO(String username, String senha, Roles role) {
}
