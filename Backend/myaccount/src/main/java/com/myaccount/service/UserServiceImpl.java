package com.myaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myaccount.dao.UserDao;
import com.myaccount.entity.StatusModel;
import com.myaccount.entity.UserDetail;
import com.myaccount.entity.UserLogin;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userdao;

	@Override
	public StatusModel addUser(UserLogin userlogin) {
		return userdao.saveUser(userlogin);
	}

	@Override
	public UserLogin getUser(String username) {
		return userdao.getUser(username);
	}

	@Override
	public UserDetail getUserDetail(int uid) {
		return userdao.getUserDetail(uid);
	}

}