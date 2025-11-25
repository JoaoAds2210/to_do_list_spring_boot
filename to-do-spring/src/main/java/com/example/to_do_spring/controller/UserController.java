package com.example.to_do_spring.controller;
import com.example.to_do_spring.dtos.UserRequest;
import com.example.to_do_spring.dtos.UserResponse;
import com.example.to_do_spring.entity.User;
import com.example.to_do_spring.exceptions.BadRequestException;
import com.example.to_do_spring.services.UserServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
public class UserController {

    private final UserServices services;

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Cria um novo usuário no sistema"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Usuário criado com sucesso",
            content = @Content(schema = @Schema(implementation = UserResponse.class))
    )
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request) {
        User saved = services.createUser(request);

        UserResponse response = new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getCpf()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário pelo ID informado"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Usuário encontrado"
    )
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = services.findById(id);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getUsername()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Retorna todos os usuários cadastrados"
    )
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> users = services.findAll()
                .stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getCpf()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar usuário",
            description = "Remove um usuário pelo ID informado"
    )
    @ApiResponse(responseCode = "204", description = "Usuário removido")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        services.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário"
    )
    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
    public ResponseEntity<UserResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        User updated = services.updateUser(id, request);

        UserResponse response = new UserResponse(
                updated.getId(),
                updated.getUsername(),
                updated.getCpf()
        );

        return ResponseEntity.ok(response);
    }
}
