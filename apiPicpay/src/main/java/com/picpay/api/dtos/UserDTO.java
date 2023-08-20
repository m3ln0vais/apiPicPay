package com.picpay.api.dtos;

import com.picpay.api.models.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String cpf, BigDecimal balance, String email, String password, UserType userType) {
}
