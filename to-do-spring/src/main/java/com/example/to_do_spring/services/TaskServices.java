package com.example.to_do_spring.services;
import com.example.to_do_spring.dtos.TaskRequest;
import com.example.to_do_spring.dtos.UserDTO;
import com.example.to_do_spring.entity.Task;
import com.example.to_do_spring.entity.User;
import com.example.to_do_spring.entity.enums.Status;
import com.example.to_do_spring.exceptions.BadRequestException;
import com.example.to_do_spring.exceptions.NotFoundException;
import com.example.to_do_spring.mapper.UserMapper;
import com.example.to_do_spring.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {

    private final TaskRepository repository;

    private final UserServices userServices;

    private final UserMapper userMapper;

    public TaskServices(TaskRepository repository, UserServices userServices, UserMapper userMapper) {
        this.repository = repository;
        this.userServices = userServices;
        this.userMapper = userMapper;
    }

    // CRUD
    public Task createTask(TaskRequest taskRequest){
        UserDTO user = userServices.findByCPF(taskRequest.cpf());

        if (user == null) {
            throw new BadRequestException("Usuário não encontrado.");
        }

        Task task = new Task();
        task.setDescription(taskRequest.description());
        task.setUser(userMapper.converterUserDTOToUser(user));

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

        task.setDescription(updatedTask.description());
        return repository.save(task);
    }
}
