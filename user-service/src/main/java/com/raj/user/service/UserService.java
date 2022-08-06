package com.raj.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.raj.user.entity.Users;
import com.raj.user.repository.UserRepository;
import com.raj.user.vo.Department;
import com.raj.user.vo.ResponseTemplateVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public Users saveUser(Users user) {
		log.info("UserService::saveUser()");
		return userRepository.save(user);
	}

	public ResponseTemplateVO getUserWithDepartment(Long usrId) {
		
		log.info("UserService::getUserWithDepartment()");
		
		ResponseTemplateVO vo = new ResponseTemplateVO();
		Users user = userRepository.findByUserId(usrId);
		
		Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/department/"+user.getDepartmentId(), 
				Department.class);
		
		vo.setUser(user);
		vo.setDepartment(department);
		
		return vo;
	}
}
