package com.example.to_do_spring.controller;
import com.example.to_do_spring.dtos.TaskRequest;
import com.example.to_do_spring.dtos.TaskResponse;
import com.example.to_do_spring.dtos.UserResponse;
import com.example.to_do_spring.entity.Task;
import com.example.to_do_spring.entity.User;
import com.example.to_do_spring.entity.enums.Status;
import com.example.to_do_spring.services.TaskServices;
import com.example.to_do_spring.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tarefas", description = "Endpoints de gerenciamento de tarefas")
public class TaskController {

    @Autowired
    private TaskServices services;

    @PostMapping
    @Operation(
            summary = "Criar tarefa",
            description = "Cria uma nova tarefa no sistema"
    )
    @ApiResponse(responseCode = "201", description = "Tarefa criada")
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        Task savedTask = services.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedTask));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar tarefa",
            description = "Retorna uma tarefa pelo ID informado"
    )
    @ApiResponse(responseCode = "200", description = "Tarefa encontrada")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        Task task = services.findById(id);
        return ResponseEntity.ok(toResponse(task));
    }

    @GetMapping
    @Operation(
            summary = "Listar tarefas",
            description = "Retorna todas as tarefas cadastradas"
    )
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<TaskResponse> tasks = services.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar tarefa",
            description = "Atualiza os dados de uma tarefa existente"
    )
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {

        Task updatedTask = services.updateTask(id, request);
        return ResponseEntity.ok(toResponse(updatedTask));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir tarefa",
            description = "Remove uma tarefa pelo ID informado"
    )
    @ApiResponse(responseCode = "204", description = "Tarefa removida")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        services.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TaskResponse toResponse(Task task) {
        boolean concluido = task.getId() != 0;
        return new TaskResponse(task.getId(), task.getDescription(), concluido);
    }
}