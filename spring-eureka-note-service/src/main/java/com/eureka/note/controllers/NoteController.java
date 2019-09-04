package com.eureka.note.controllers;

import javax.jms.Queue;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eureka.db.DbContext;
import com.eureka.note.entities.Note;
import com.eureka.note.network.EmailNetworkChannel;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class NoteController {
	@Autowired
	private Environment env;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Queue queue;

	@Autowired
	private JmsTemplate jmsTemplate;

	@RequestMapping(value = "/newNote", method = { RequestMethod.POST })
	public Note newNote(@RequestBody Note note) {
		try {
			note = DbContext.Save(note);
			EmailNetworkChannel emailChannel = new EmailNetworkChannel(jmsTemplate,queue);
			emailChannel.sendNewNoteInfoToUser(note);
			return note;
		} catch (Exception e) {
			return new Note();
		}

	}

	@RequestMapping(value = "/deleteNote", method = { RequestMethod.POST })
	public String deleteNote(@RequestParam(value = "id", required = false) String id) {
		try {

			Note note = DbContext.GetNote(id);
			DbContext.Delete(id);
			/**
			 * With http request
			 * 
			 */
			 EmailNetworkChannel emailChannel = new EmailNetworkChannel(restTemplate);
			 emailChannel.sendDeleteNoteInfoToUser(note);
			jmsTemplate.convertAndSend(queue, note);

			return "SUCCESS";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/updateNote", method = { RequestMethod.POST })
	public String updateNote(@RequestBody Note note) {
		try {
			DbContext.Update(note);
			// EmailNetworkChannel emailChannel = new EmailNetworkChannel(restTemplate);
			// emailChannel.sendUpdateNoteInfoToUser(note);
			jmsTemplate.convertAndSend(queue, note);

			return "SUCCESS";
		} catch (Exception e) {
			return e.getMessage();
		}

	}

	@RequestMapping(value = "/GetUserNotes", method = { RequestMethod.POST })
	public List<Note> GetUserNotes(@RequestParam(value = "userId", required = false) String userId) throws Exception {
		try {

			return DbContext.GetUserAllNotes(userId);
		} catch (Exception e) {
			throw e;
		}

	}

}
