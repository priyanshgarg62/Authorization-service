package com.amdocs.media.Authorizationservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.amdocs.media.Authorizationservice.model.UserDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	ObjectMapper objMap = new ObjectMapper();

	String kafkaTopic = "methodtype";

	public void send(UserDAO user) {
		try {

			kafkaTemplate.send(kafkaTopic, objMap.writeValueAsString(user));

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}