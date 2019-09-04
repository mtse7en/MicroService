package com.eureka.email.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.db.DbContext;
import com.eureka.email.MailSender;
import com.eureka.email.entities.Email;
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class EmailController {

	
	@RequestMapping(value = "/sendEmail", method = { RequestMethod.POST })
	public String sendEmail(
	        @RequestParam(value = "userId",required = false) String userId,
	        @RequestParam(value = "mailHeader",required = false) String mailHeader,
	        @RequestParam(value = "mailContent",required = false) String mailContent) {
		
		Email newMail = new Email(userId, mailHeader,mailContent, DbContext.GetSystemMailAddress(), DbContext.GetUserMailAddressWith(userId));
		
		try {
			MailSender.SendMail(newMail);
			return "true";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
