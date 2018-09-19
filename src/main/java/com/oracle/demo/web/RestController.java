package com.oracle.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {
	
	private ArrayList<User> users = new ArrayList<User>();

	private final RestTemplate restTemplate;
	
	public RestController(RestTemplateBuilder restTemplateBuilder) {
		super();

		this.restTemplate = restTemplateBuilder.build();
		
		this.users.add(new User(1, "Peter"));
		this.users.add(new User(2, "Kelvin"));
	}

	@RequestMapping("/hello")
	public String greet() {
		return "Hello from the other side!!!";
	}
	
	@GetMapping("getUsers")
	public List getUsers() {
		
		return users;
	}
	
	@GetMapping(path = {"getUserById/{id}"})
	public User getUserById(@PathVariable("id") int id) {
		
		User user;
		user = this.users.get(id-1);
		return user;
	}
	
	@PutMapping(path = {"updateUser"})
	public void updateUser(@RequestBody User user) {
		this.users.set(user.getId()-1, user);
	}
	
	@PostMapping(path = {"addUser"})
	public User addUser(@RequestBody User user) {
		user.setId(this.users.size()+1);
		this.users.add(user);
		return user;
	}
	
	@RequestMapping("getQuote")
	public Quote getQuote() {
		Quote quote = this.restTemplate.getForObject(
				"http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		return quote;
	}
	
	
}
