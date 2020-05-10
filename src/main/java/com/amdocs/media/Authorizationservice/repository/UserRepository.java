package com.amdocs.media.Authorizationservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.media.Authorizationservice.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}
