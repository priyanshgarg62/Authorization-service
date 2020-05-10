package com.amdocs.media.Authorizationservice.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amdocs.media.Authorizationservice.config.KafkaProducer;
import com.amdocs.media.Authorizationservice.model.User;
import com.amdocs.media.Authorizationservice.model.UserDAO;
import com.amdocs.media.Authorizationservice.service.AuthService;

@RestController
@RequestMapping("/assignment")
public class MyController {

	@Autowired
	AuthService authService;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	KafkaProducer kafkaSender;

	String baseUrl = "http://localhost:9093/assignment/profile";
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(path = "/User/{name}", produces = "application/json")
	public User getUser(@PathVariable("name") String name) {

		return authService.getuser(name);
	}

	@PostMapping(path = "/login", consumes = "application/json")
	public String autheticateUser(@RequestBody UserDAO user) throws Exception {
		logger.info("UserName Password for authentication {}", user);
		URI uri = new URI(baseUrl);
		String token = user.getTopic().toString();
		if (authService.validateUser(user)) {
			logger.info("UserName authentication with details {}", user);

			if (token.equalsIgnoreCase("POST")) {
				logger.info("POST Method called for Profile Creation", user.getTopic());
				return restTemplate.postForObject(uri, user, String.class);
			} 
			else if (token.equalsIgnoreCase("PUT") || token.equalsIgnoreCase("DELETE")) {
				kafkaSender.send(user);
				return "KAFKA TOPIC GENERATED FOR -> " + token;
			} else
				return "NO ACTION";

		} else {
			logger.info("UserName Not found");
			return "Authentication Failed";
		}

	}
}
