package com.raj.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.user.entity.Users;
import com.raj.user.service.UserService;
import com.raj.user.vo.ResponseTemplateVO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public Users saveUser(@RequestBody Users user) {
		log.info("UserController::saveUser()");
		return userService.saveUser(user);
	}
	
	@GetMapping("/{id}")
	@CircuitBreaker(name="USER-SERVICE", fallbackMethod = "UserServicefallbackMethod")
	public ResponseEntity<ResponseTemplateVO> getUserWithDepartment(@PathVariable("id") Long userId) {
		log.info("UserController::getUserWithDepartment()");
		ResponseTemplateVO vo = userService.getUserWithDepartment(userId);
		
		 return new ResponseEntity<>(vo, null, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> UserServicefallbackMethod(Long Id,Exception e) {
		return new ResponseEntity<Object>("Server Down",null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
