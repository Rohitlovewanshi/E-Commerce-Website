package com.myaccount.dao;

import com.myaccount.entity.StatusModel;
import com.myaccount.entity.UserDetail;
import com.myaccount.entity.UserLogin;

public interface UserDao {
	StatusModel saveUser(UserLogin userlogin);
	UserLogin getUser(String username);	
	UserDetail getUserDetail(int uid);
}
