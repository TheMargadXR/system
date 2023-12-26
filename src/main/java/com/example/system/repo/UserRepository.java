package com.example.system.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.system.model.User;


public interface UserRepository extends MongoRepository<User , Long> {

}
