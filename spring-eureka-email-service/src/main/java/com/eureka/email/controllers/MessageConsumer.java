package com.eureka.email.controllers;

import javax.mail.NoSuchProviderException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.eureka.db.DbContext;
import com.eureka.email.MailSender;
import com.eureka.email.entities.Email;
import com.google.gson.Gson;

@Component
@EnableJms
public class MessageConsumer {

	private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	@JmsListener(destination = "email-queue")
	public void listener(String mailJson) {
		Gson g = new Gson();
		Email param = g.fromJson(mailJson, Email.class);

		Email newMail = new Email(param.getUserId(), param.getMailHeader(), param.getMailContent(), DbContext.GetSystemMailAddress(),
		DbContext.GetUserMailAddressWith(param.getUserId()));

		try {
			MailSender.SendMail(newMail);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}

	}
}