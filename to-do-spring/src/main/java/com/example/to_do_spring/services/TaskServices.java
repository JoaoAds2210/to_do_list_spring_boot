package com.example.to_do_spring.services;

import com.example.to_do_spring.dtos.TaskRequest;
import com.example.to_do_spring.entity.Task;
import com.example.to_do_spring.entity.enums.Status;
import com.example.to_do_spring.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServices {

    private final TaskRepository repository;

    public TaskServices(TaskRepository repository) {
        this.repository = repository;
    }

    //CRUD
    public Task createTask(Task task){
        repository.saveAndFlush(task);
        return task;
    }

    public Task findById (Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Não há tarefas com esse ID"));
    }

    public List<Task> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Task updateTask(Long id, TaskRequest updatedTask) {
        Task task = findById(id);
        task.setDescription(updatedTask.description());
        task.setStatus(updatedTask.concluido() ? Status.APPROVED : Status.PENDING);
        return repository.save(task);
    }
}
