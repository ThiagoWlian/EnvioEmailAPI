package com.example.email.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.email.model.EmailModel;

public interface EmailRepository extends MongoRepository<EmailModel, String>{

}
