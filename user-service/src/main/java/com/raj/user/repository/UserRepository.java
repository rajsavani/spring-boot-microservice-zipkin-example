package com.raj.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.user.entity.Users;


public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByUserId(Long usrId);

}
