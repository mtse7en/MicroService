package com.eureka.note.network;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import javax.jms.Queue;
@Configuration
public class JMSConfig {
    @Bean
    public Queue queue(){
        return new ActiveMQQueue("email-queue");
    }
}