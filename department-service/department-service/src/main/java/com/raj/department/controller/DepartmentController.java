package com.raj.department.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.department.entity.Department;
import com.raj.department.service.DepartmentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	
	@PostMapping("/")
	public ResponseEntity<Department> saveDepartment(@RequestBody Department department) {
		log.info("DepartmentController::saveDepartment()");
		Department dept =  departmentService.saveDepartment(department);
		return new ResponseEntity<>(dept, null, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@CircuitBreaker(name="DEPARTMENT-SERVICE", fallbackMethod = "DeptServicefallbackMethod")
	public ResponseEntity<Department> findDepartmentById(@PathVariable("id") Long departmentId) {
		log.info("DepartmentController::findDepartmentById()");
		Department dept = departmentService.findDepartmentById(departmentId);
		return new ResponseEntity<>(dept, null, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> DeptServicefallbackMethod(Long Id,Exception e) {
		return new ResponseEntity<Object>("Server Down",null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
