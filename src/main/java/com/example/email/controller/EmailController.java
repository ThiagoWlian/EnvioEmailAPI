package com.example.email.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.email.dto.EmailDto;
import com.example.email.model.EmailModel;
import com.example.email.repository.EmailRepository;
import com.example.email.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EmailRepository emailRepository;
	
	@PostMapping("/send-email")
	public ResponseEntity<EmailModel> sendEmail(@RequestBody @Valid EmailDto emailDto){
		EmailModel emailModel = new EmailModel();
		BeanUtils.copyProperties(emailDto, emailModel);
		emailService.sendEmail(emailModel);
		
		return new ResponseEntity<EmailModel>(emailModel, HttpStatus.OK);
		
	}
	
	@GetMapping
	public List<EmailModel> get(){
		return this.emailRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmailModel> getById(@PathVariable String id) {
		Optional<EmailModel> email = this.emailRepository.findById(id);
		
		if(!email.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(email.get(), HttpStatus.OK);
	}
	
}
