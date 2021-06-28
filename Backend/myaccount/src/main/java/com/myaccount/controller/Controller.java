package com.myaccount.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.core.Authentication;
import com.myaccount.config.JwtTokenUtil;
import com.myaccount.entity.StatusModel;
import com.myaccount.entity.UserDetail;
import com.myaccount.entity.UserLogin;
import com.myaccount.service.JwtUserDetailsService;
import com.myaccount.service.KafkaProducerService;
import com.myaccount.service.UserService;
import com.myaccount.vo.OrderHistory;

@RestController
@RequestMapping("user")
public class Controller {
	@Autowired
	private UserService userservice;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailService;

	@Autowired
	private KafkaProducerService service;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/signup")
	@CrossOrigin(origins = "*")
	public Object addUser(@RequestParam(name = "username") String username,
			@RequestParam(name = "password") String password, @RequestParam(name = "uname") String uname,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob) {
		UserLogin userlogin = new UserLogin();
		UserDetail userdetail = new UserDetail();
		userdetail.setDob(dob);
		userdetail.setEmail(email);
		userdetail.setUname(uname);
		userlogin.setUsername(username);
		userlogin.setPassword(passwordEncoder.encode(password));
		userlogin.setUserdetails(userdetail);
		StatusModel o = userservice.addUser(userlogin);
		if (o.getStatus() == 201) // KAFKA Transactions
		{
			JSONObject jobject = new JSONObject();
			jobject.put("message", "username " + username + " created ");
			jobject.put("time", System.currentTimeMillis() + "");
			service.sendMessage(jobject.toString());
		}
		return o;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET) // To logged out the current logged in user
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "true";
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST) // Login of user using jwt tokenization method
	public ResponseEntity<Object> createAuthenticationToken(@RequestParam String username, String password)throws Exception {
		authenticate(username, password);
		final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(username);
		final String token = jwtTokenUtil.generateToken(userDetails);
		UserLogin user = userservice.getUser(username);
		JSONObject jsObj = new JSONObject();

		if (user != null) {
			jsObj.put("status", 200);
			jsObj.put("token", token);
			jsObj.put("uid", user.getUserdetails().getUid());
			jsObj.put("uname", user.getUserdetails().getUname());
			jsObj.put("email", user.getUserdetails().getEmail());
			jsObj.put("dob", user.getUserdetails().getDob());
		} 
		else {
			jsObj.put("status", 404);
		}

		JSONArray jsArr = new JSONArray();
		jsArr.put(jsObj.toMap());
		return new ResponseEntity<>(jsObj.toMap(), HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception // login helper
	{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}