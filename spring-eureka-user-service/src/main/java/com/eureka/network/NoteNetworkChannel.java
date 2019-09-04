 
package com.eureka.network;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.eureka.user.entities.User;

 
public class NoteNetworkChannel {
	
 
	private RestTemplate restTemplate;

	public NoteNetworkChannel(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
 
	public ResponseEntity<String> getAllNotes(String userId) throws Exception{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		
		map.add("userId", userId);
			
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://note-service/GetUserNotes/", request, String.class);
		
		return response;
	}
	
	 
}
