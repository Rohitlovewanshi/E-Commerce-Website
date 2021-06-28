package com.myaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myaccount.entity.UserLogin;

@Repository
public interface UserRepository  extends JpaRepository<UserLogin, String>{

}
