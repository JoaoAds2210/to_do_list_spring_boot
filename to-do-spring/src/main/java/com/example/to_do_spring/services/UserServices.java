package com.example.to_do_spring.services;
import com.example.to_do_spring.dtos.UserDTO;
import com.example.to_do_spring.dtos.UserResponse;
import com.example.to_do_spring.entity.User;
import com.example.to_do_spring.exceptions.BadRequestException;
import com.example.to_do_spring.exceptions.NotFoundException;
import com.example.to_do_spring.mapper.UserMapper;
import com.example.to_do_spring.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
@Setter
public class UserServices implements UserDetailsService {
    @Autowired
    private UserRepository repositories;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserMapper userMapper;

    // Validações de negócio
    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BadRequestException("O nome não pode ser nulo.");
        }
        if (!username.matches(".*[A-Z].*")) {
            throw new BadRequestException("O nome deve conter ao menos uma letra maiúscula.");
        }
    }

    private void validatePassword(String senha) {
        if (senha == null || senha.length() < 4) {
            throw new BadRequestException("A senha deve ter no mínimo 4 caracteres.");
        }
        if (!senha.matches("^[a-zA-Z0-9!@#$%^&*()_+=\\-{}\\[\\]:\";'<>?,./]+$")) {
            throw new BadRequestException("A senha contém caracteres inválidos.");
        }
    }

    // CRUD
    public UserDTO createUser(UserDTO request){
        validateUsername(request.userName());
        validatePassword(request.senha());

        if (findByCPF(request.cpf()).equals(request.cpf())) {
            throw new BadRequestException("CPF já cadastrado.");
        }

        User user = userMapper.converterUserDTOToUser(request);

        User user1 = repositories.save(user);

        UserDTO userResponse = userMapper.converterUserToUserDTO(user1);

        return userResponse;
    }

    public List<User> findAll() {
        return repositories.findAll();
    }

    public UserDTO findById(Long id){
        User user = repositories.findById(id).orElseThrow(() -> new NotFoundException("Não há usuário com esse ID: " + id));

        UserDTO response = userMapper.converterUserToUserDTO(user);

        return response;
    }

//    public void deleteById(Long id){
//        User user = findById(id); // lança NotFoundException se não existir
//        repositories.delete(user);
//    }
//
//    public User updateUser(Long id, UserDTO updateUser) {
//        User user = findById(id); // lança NotFoundException se não existir
//        user.setUsername(updateUser.userName());
//        user.setCpf(updateUser.cpf());
//        return repositories.save(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositories.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public UserDTO findByCPF (String cpf) {
        Optional<User> user = repositories.findByCpf(cpf);

        UserDTO response = userMapper.converterUserToUserDTO(user.get());

        return response;
    }
}