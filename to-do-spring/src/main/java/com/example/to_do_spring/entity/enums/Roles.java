package com.example.to_do_spring.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Roles {
    USER ("user"),
    ADMIN("admin");

    private final String role;


}
