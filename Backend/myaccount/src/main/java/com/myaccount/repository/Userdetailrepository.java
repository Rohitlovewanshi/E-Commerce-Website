package com.myaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myaccount.entity.UserDetail;
@Repository
public interface Userdetailrepository extends JpaRepository<UserDetail,Integer> {

	boolean existsByEmail(String email); //return true if user already exist while signup process 
}
