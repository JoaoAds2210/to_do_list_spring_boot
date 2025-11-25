package com.example.to_do_spring.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ResponseError {
    private final int status;
    private final String error;
    private final LocalDateTime timestamp;
}
