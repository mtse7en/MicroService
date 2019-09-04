package com.eureka.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eureka.db.DbContext;
import com.eureka.network.NoteNetworkChannel;
import com.eureka.user.entities.User;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private Environment env;

	@RequestMapping("/")
	public String home() {
		return "Hello from user Service running at port: " + env.getProperty("local.server.port");
	}

	@RequestMapping(value = "/notes", method = { RequestMethod.POST })
	public ResponseEntity<String> notes(@RequestBody User user) {

		NoteNetworkChannel networkChannel = new NoteNetworkChannel(restTemplate);

		ResponseEntity<String> result = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		try {
			result = networkChannel.getAllNotes(user.getUserId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
	@RequestMapping(value = "/userInfo", method = { RequestMethod.POST })
	public User userInfo(@RequestBody User user) {

		
		try {
			String id = DbContext.FindUser(user.geteMail());
			user.setUserId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;

	}

	@RequestMapping("/{id}")
	public ResponseEntity<String> getUser(@PathVariable final String id) {

//		List<Object> notes = restTemplate.getForObject("http://note-service/userNotes/1", List.class);
//		user.setNotes(notes);

		NoteNetworkChannel networkChannel = new NoteNetworkChannel(restTemplate);
		try {
			ResponseEntity<String> result = networkChannel.getAllNotes(id);
			return result;

		} catch (Exception e) {
			return null;
		}
	}

}