package com.example.to_do_spring.controller;

import com.example.to_do_spring.dtos.UserDTO;
import com.example.to_do_spring.dtos.UserResponse;
import com.example.to_do_spring.services.UserServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints de gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserServices services;

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
            @Valid @RequestBody UserDTO request) {
        UserDTO saved = services.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
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
    public ResponseEntity<UserResponse> findById(@PathVariable String cpf) {
        UserDTO user = services.findByCPF(cpf);

        return ResponseEntity.ok(toResponse(user));
    }
//
//    @GetMapping
//    @Operation(
//            summary = "Listar usuários",
//            description = "Retorna todos os usuários cadastrados"
//    )
//    public ResponseEntity<List<UserResponse>> findAll() {
//        List<UserResponse> users = services.findAll()
//                .stream()
//                .map(this::toResponse)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(users);
//    }
//
//    @DeleteMapping("/{id}")
//    @Operation(
//            summary = "Deletar usuário",
//            description = "Remove um usuário pelo ID informado"
//    )
//    @ApiResponse(responseCode = "204", description = "Usuário removido")
//    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
//        services.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{id}")
//    @Operation(
//            summary = "Atualizar usuário",
//            description = "Atualiza os dados de um usuário"
//    )
//    @ApiResponse(responseCode = "200", description = "Usuário atualizado")
//    public ResponseEntity<UserResponse> update(
//            @PathVariable Long id,
//            @Valid @RequestBody UserDTO request) {
//
//        User updated = services.updateUser(id, request);
//
//        return ResponseEntity.ok(toResponse(updated));
//    }

    private UserResponse toResponse(UserDTO user) {
        return new UserResponse(
                user.id(),
                user.userName(),
                user.cpf(),
                user.task()
        );
    }
}
