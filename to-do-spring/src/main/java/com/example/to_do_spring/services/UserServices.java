package com.example.to_do_spring.services;
import com.example.to_do_spring.dtos.UserRequest;
import com.example.to_do_spring.entity.User;
import com.example.to_do_spring.exceptions.BadRequestException;
import com.example.to_do_spring.exceptions.NotFoundException;
import com.example.to_do_spring.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Getter
@Setter
public class UserServices implements UserDetailsService {

    private final UserRepository repositories;
    private final PasswordEncoder encoder;

    // Validações de negócio
    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new BadRequestException("O nome não pode ser nulo.");
        }
        if (!username.matches(".*[A-Z].*")) {
            throw new BadRequestException("O nome deve conter ao menos uma letra maiúscula.");
        }
        if (!username.matches(".*[0-9].*")) {
            throw new BadRequestException("O nome deve conter ao menos um número.");
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
    public User createUser(UserRequest request){
        validateUsername(request.username());
        validatePassword(request.senha());

        if (repositories.findByCpf(request.cpf()).isPresent()) {
            throw new BadRequestException("CPF já cadastrado.");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setCpf(request.cpf());
        user.setSenha(encoder.encode(request.senha()));

        return repositories.save(user);
    }

    public List<User> findAll() {
        return repositories.findAll();
    }

    public User findById(Long id){
        return repositories.findById(id)
                .orElseThrow(() -> new NotFoundException("Não há usuário com esse ID: " + id));
    }

    public void deleteById(Long id){
        User user = findById(id); // lança NotFoundException se não existir
        repositories.delete(user);
    }

    public User updateUser(Long id, UserRequest updateUser) {
        User user = findById(id); // lança NotFoundException se não existir
        user.setUsername(updateUser.username());
        user.setCpf(updateUser.cpf());
        return repositories.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repositories.findByUsername(username);
    }
}