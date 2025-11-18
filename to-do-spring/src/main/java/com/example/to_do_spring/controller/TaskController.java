package com.example.to_do_spring.controller;

import com.example.to_do_spring.dtos.TaskRequest;
import com.example.to_do_spring.dtos.TaskResponse;
import com.example.to_do_spring.entity.Task;
import com.example.to_do_spring.entity.enums.Status;
import com.example.to_do_spring.services.TaskServices;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskServices services;

    // CREATE
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest request) {
        Task task = new Task();
        task.setDescription(request.description());
        task.setStatus(request.concluido() ? Status.APPROVED : Status.PENDING);

        Task savedTask = services.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(savedTask));
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> findById(@PathVariable Long id) {
        Task task = services.findById(id);
        return ResponseEntity.ok(toResponse(task));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll() {
        List<TaskResponse> tasks = services.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        Task updatedTask = services.updateTask(id, request);
        return ResponseEntity.ok(toResponse(updatedTask));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        services.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private TaskResponse toResponse(Task task) {
        boolean concluido = task.getStatus() == Status.APPROVED;
        return new TaskResponse(task.getId(), task.getDescription(), concluido);
    }
}