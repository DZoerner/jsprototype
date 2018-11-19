package de.inmediasp.prototype.service.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.inmediasp.prototype.type.LoginData;
import de.inmediasp.prototype.type.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PrototypeController {
	private Random rnd= new Random();
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> login(@RequestBody LoginData loginData) {

		String token = null;
		LoginData appUser = new LoginData();
		appUser.setUsername("proto");
		appUser.setPassword("heinz");
		
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		if (appUser.getPassword().equals(loginData.getPassword()) && appUser.getUsername().equals(loginData.getUsername())) {
		    token = Jwts.builder().setSubject(loginData.getUsername()).claim("roles", "Role").setIssuedAt(new Date())
		            .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
		    tokenMap.put("token", token);
		    tokenMap.put("user", appUser);
		    return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
		} else {
		    tokenMap.put("token", null);
		    return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
		}
	}

	private static String firstName[]= {"Heinz", "Helga", "Daria", "Uwe", "Max", "Egon", "Janis", "Sascha", "Angela", "Björn", "Jogi", "Hans", "Peter", "Gustav"};
	private static String lastName[]= {"Merkel", "Schmidt", "Schulz", "Müller", "Lehmann", "Rehhagel", "Buschmann", "Kliensmann", "Löw", "Genscher"};
	
	@CrossOrigin(origins = "http://localhost:4200")
	
	@RequestMapping(value="/users", method = RequestMethod.GET, produces = { "application/json", "text/json" })
	public List<User> userList() {
		List<User> ret= new ArrayList<>();
		
		Long id= (long) rnd.nextInt(500);
		for(int i= 0; i < 1000; i++) {
			id+= (long) rnd.nextInt(500);
			User u= new User();
			u.setFirstName(firstName[rnd.nextInt(firstName.length)]);
			u.setLastName(lastName[rnd.nextInt(lastName.length)]);
			u.setId(id);
			u.setPassword("");
			u.setToken("");
			u.setUsername(u.getFirstName().toLowerCase() + "." + u.getLastName().toLowerCase());
			
			ret.add(u);
		}
		

		
		return ret;
	}
}