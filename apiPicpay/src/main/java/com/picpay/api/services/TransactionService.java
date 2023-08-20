package com.picpay.api.services;

import com.picpay.api.dtos.TransactionDTO;
import com.picpay.api.models.trasaction.Transaction;
import com.picpay.api.models.user.User;
import com.picpay.api.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction (TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validateTransaction(sender,transactionDTO.value());

        boolean isAuthorize = this.authorizeTransaction(sender, transactionDTO.value());
        if (!isAuthorize){
            throw new Exception("transação não autorizada");
        }

        Transaction transaction = new Transaction();
        transaction.setTransactionAmount(transactionDTO.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setDateTime(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.transactionRepository.save(transaction);
        this.userService.save(sender);
        this.userService.save(receiver);

        this.notificationService.sendNotification(sender, "transação enviada com sucesso");
        this.notificationService.sendNotification(receiver, "transação recebida com sucesso");

        return transaction;
    }

    public Boolean authorizeTransaction(User sender, BigDecimal value){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
        if (authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message =(String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }else return false;
    }
}
