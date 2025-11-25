package com.example.to_do_spring.repository;

import com.example.to_do_spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByCpf(String cpf);

    UserDetails findByUsername(String username);

}
