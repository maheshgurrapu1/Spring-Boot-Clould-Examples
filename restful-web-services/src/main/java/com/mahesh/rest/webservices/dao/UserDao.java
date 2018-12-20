package com.mahesh.rest.webservices.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Repository;

import com.mahesh.rest.webservices.bean.User;

@Repository
public class UserDao {

	private static AtomicInteger counter = new AtomicInteger();
	private static List<User> users = new ArrayList<>();
	
	static {
		users.add(new User(counter.incrementAndGet(), "Adam", new Date()));
		users.add(new User(counter.incrementAndGet(), "Eve", new Date()));
		users.add(new User(counter.incrementAndGet(), "jack", new Date()));
	}

	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) 
			user.setId(counter.incrementAndGet());
		users.add(user);
		return user;
	}
	
	public User findOne(int id) {
		return users.stream().filter( user -> user.getId().equals(id)).findAny().orElse(null);
	}
	
	public User deleteById(int id) {
		ListIterator<User> listIterator = users.listIterator();
		while(listIterator.hasNext()) {
			User user = listIterator.next();
			if(user.getId() == id) {
				listIterator.remove();
				return user;
			}
		}
		return null;
	}
}
