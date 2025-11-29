package com.example.to_do_spring.mapper;

import com.example.to_do_spring.dtos.TaskResponse;
import com.example.to_do_spring.dtos.UserDTO;
import com.example.to_do_spring.entity.Task;
import com.example.to_do_spring.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User converterUserDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.userName());
        user.setSenha(userDTO.senha());
        user.setCpf(userDTO.cpf());

        return user;
    }

    public UserDTO converterUserToUserDTO (User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getSenha(),
                user.getCpf(),
                toResponseTask(user.getTasks())
        );
    }

    private List<TaskResponse> toResponseTask(List<Task> tasks) {
        if(tasks != null) {
             return tasks.stream()
                    .map(task -> new TaskResponse(task.getId(), task.getDescription(), task.getId() != 0))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
