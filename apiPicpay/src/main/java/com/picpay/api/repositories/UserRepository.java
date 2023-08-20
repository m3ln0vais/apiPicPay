package com.picpay.api.repositories;

import com.picpay.api.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User>findUserByCpf(String cpf);
    Optional<User>findUserById(Long id);
}
