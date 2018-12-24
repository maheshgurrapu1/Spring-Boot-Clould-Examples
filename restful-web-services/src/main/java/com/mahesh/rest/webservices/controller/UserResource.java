package com.mahesh.rest.webservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mahesh.rest.webservices.bean.User;
import com.mahesh.rest.webservices.dao.UserDao;
import com.mahesh.rest.webservices.exception.UserNotFoundException;

@RestController
public class UserResource {

	@Autowired
	private UserDao userDao;
	
	@GetMapping("/users")
	public List<User> retriveAllUsers() {
		return userDao.findAll();
		
	}
	
	@GetMapping("/users/{id}")
	public User retriveUser(@PathVariable int id) {
		User user = userDao.findOne(id);
		if(user == null) {
			throw new UserNotFoundException("Requested Id Not Found");
		}
		return user;
		
	}
	
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = userDao.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUserId(@PathVariable int id) {
		User user =userDao.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("Requested Id Not Found");
		}
		
	}
}
