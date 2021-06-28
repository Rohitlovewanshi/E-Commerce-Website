package com.myaccount.service;

import com.myaccount.entity.StatusModel;
import com.myaccount.entity.UserDetail;
import com.myaccount.entity.UserLogin;

public interface UserService {
	StatusModel addUser(UserLogin userlogin);// Add user when  signup rest api called
	UserLogin getUser(String username);//get username and password for a username exist
	UserDetail getUserDetail(int uid);// get all userdetail for a uid exist
}
