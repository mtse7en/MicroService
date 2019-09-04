package com.eureka.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import com.eureka.email.entities.Email;
import com.eureka.email.entities.MailInfo;

public class MailSender {
	public static void SendMail(Email email) throws NoSuchProviderException {

		String to = email.getDestinationAddress();
		String subject = email.getMailHeader();
		String msg = email.getMailContent();
		final String from = "account@gmail.com";
		final String password = "password";

		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.debug", "true");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		// session.setDebug(true);
		Transport transport;
		try {
			transport = session.getTransport();
			InternetAddress addressFrom;
			addressFrom = new InternetAddress(from);

			MimeMessage message = new MimeMessage(session);
			message.setSender(addressFrom);
			message.setSubject(subject);
			message.setContent(msg, "text/plain");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			transport.connect();
			Transport.send(message);
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}
	// MailInfo info = new MailInfo("1","mtse7entest@gmail.com");//şifre :
	// 123mtse7en
}
