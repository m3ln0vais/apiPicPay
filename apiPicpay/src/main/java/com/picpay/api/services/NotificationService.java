package com.picpay.api.services;

import com.picpay.api.dtos.NotificationRequest;
import com.picpay.api.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationRequest notificationRequest = new NotificationRequest(email, message);

//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("http://o4d9z.mocklab.io/notify", notificationRequest, String.class);
//
//        if (!(notificationResponse.getStatusCode() == HttpStatus.OK)){
//            System.out.println("error ao enviar notificação");
//            throw  new Exception("serviço de notificação fora do ar");
//        }

        System.out.println("noti enviada");
    }
}
