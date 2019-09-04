package com.eureka.note.network;

import javax.jms.Queue;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.eureka.note.entities.Email;
import com.eureka.note.entities.Note;
import com.google.gson.Gson;

public class EmailNetworkChannel {

	private JmsTemplate jmsTemplate;
	private Queue queue;
	private RestTemplate restTemplate;

	public EmailNetworkChannel(JmsTemplate jmsTemplate, Queue queue) {
		this.queue = queue;
		this.jmsTemplate = jmsTemplate;
	}

	public EmailNetworkChannel(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void sendUpdateNoteInfoToUser(Note note) throws Exception {
		String mailHeader = note.getNoteHeader() + ", new note info";
		String mailContent = "This is a System Message, new not added like \"" + note.getNoteDetail() + "\"";
		this.sendMail(note.getUserId() + "", mailHeader, mailContent);
	}

	public void sendNewNoteInfoToUser(Note note) throws Exception {
		String mailHeader = note.getNoteHeader() + ", updated note info";
		String mailContent = "This is a System Message, your notes updated like \"" + note.getNoteDetail() + "\"";
		this.sendMail(note.getUserId() + "", mailHeader, mailContent);
	}

	public void sendDeleteNoteInfoToUser(Note note) throws Exception {

		String mailHeader = note.getNoteHeader() + ", delete note info";
		String mailContent = "This is a System Message, your notes deleted, old note : \"" + note.getNoteDetail()
				+ "\"";
		this.sendMail(note.getUserId() + "", mailHeader, mailContent);
	}

	private void sendMail(String userId, String mailHeader, String mailContent) throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		if (jmsTemplate != null) {
			/*
			 * use activeMq to email-service for sending mail
			 * 
			 */
			Email email = new Email(userId, mailHeader, mailContent);
			Gson g = new Gson();
			String jsonBody = g.toJson(email);
			jmsTemplate.convertAndSend(queue, jsonBody);
			
		} 
		else if (restTemplate != null) {
			
			/*
			 * send http request to email-service for sending mail
			 * 
			 */
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();

			map.add("userId", userId);
			map.add("mailHeader", mailHeader);
			map.add("mailContent", mailContent);
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);
			ResponseEntity<String> response = restTemplate.postForEntity("http://email-service/sendEmail/", request,
					String.class);

		}
	}

}
