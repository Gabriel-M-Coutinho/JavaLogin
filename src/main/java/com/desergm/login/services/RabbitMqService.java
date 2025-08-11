package com.desergm.login.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendDto(String queue, Object dto) {
        rabbitTemplate.convertAndSend(queue, dto);
    }

    @RabbitListener(queues = "minha-fila")
    public void receiveMessage(String message) {
        // lógica para processar mensagem recebida
    }
}