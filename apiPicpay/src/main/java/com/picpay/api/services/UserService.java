package com.picpay.api.services;

import com.picpay.api.dtos.UserDTO;
import com.picpay.api.models.user.User;
import com.picpay.api.models.user.UserType;
import com.picpay.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void validateTransaction(User sender, BigDecimal value) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("user do tipo logista não está autorizado a realizar a transação");
        }

        if (sender.getBalance().compareTo(value) < 0){
            throw new Exception("saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception{
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("usuario não encontrado"));
    }

    public void save(User user){
        this.userRepository.save(user);
    }

    public User createUser(UserDTO userDTO) {
        User user = new User(userDTO);
        this.save(user);
        return user;
    }

    public List<User> findAll(){
        return this.userRepository.findAll();
    }
}
