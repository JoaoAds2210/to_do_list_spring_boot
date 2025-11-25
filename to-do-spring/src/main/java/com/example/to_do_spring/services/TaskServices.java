package com.example.to_do_spring.services;
import com.example.to_do_spring.dtos.TaskRequest;
import com.example.to_do_spring.entity.Task;
import com.example.to_do_spring.entity.enums.Status;
import com.example.to_do_spring.exceptions.BadRequestException;
import com.example.to_do_spring.exceptions.NotFoundException;
import com.example.to_do_spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServices {

    private final TaskRepository repository;

    public TaskServices(TaskRepository repository) {
        this.repository = repository;
    }

    // CRUD
    public Task createTask(Task task){
        if (task.getDescription() == null || task.getDescription().isBlank()) {
            throw new BadRequestException("Descrição da tarefa não pode estar vazia.");
        }
        repository.saveAndFlush(task);
        return task;
    }

    public Task findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Não há tarefa com esse ID: " + id));
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id){
        Task task = findById(id); // lança NotFoundException se não existir
        repository.delete(task);
    }

    public Task updateTask(Long id, TaskRequest updatedTask) {
        Task task = findById(id); // lança NotFoundException se não existir
        if (updatedTask.description() == null || updatedTask.description().isBlank()) {
            throw new BadRequestException("Descrição da tarefa não pode estar vazia.");
        }
        task.setDescription(updatedTask.description());
        task.setStatus(updatedTask.concluido() ? Status.APPROVED : Status.PENDING);
        return repository.save(task);
    }
}
