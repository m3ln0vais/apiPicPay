package com.picpay.api.controllers;

import com.picpay.api.dtos.TransactionDTO;
import com.picpay.api.models.trasaction.Transaction;
import com.picpay.api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trans")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO dto) throws Exception{
        Transaction t = this.transactionService.createTransaction(dto);
        return new ResponseEntity<>(t, HttpStatus.CREATED);
    }
}
