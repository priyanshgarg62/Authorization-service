package com.amdocs.media.Authorizationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amdocs.media.Authorizationservice.model.User;
import com.amdocs.media.Authorizationservice.model.UserDAO;
import com.amdocs.media.Authorizationservice.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	public User getuser(String name) {
		User userEntity = userRepository.findById(name).get();
		// logger.info("Profile for User : {}", userEntity);
		return userEntity;
	}

	public Boolean validateUser(UserDAO user) throws Exception {
		User userEntity;
		try {
			userEntity = userRepository.findById(user.getUserName()).get();

			if (userEntity.getUserName().isEmpty()) {
				return false;
			} else {
				if (user.getPassword().equals(userEntity.getPassword())) {
					return true;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Authentication Failed");
		}
		return false;
	}
}