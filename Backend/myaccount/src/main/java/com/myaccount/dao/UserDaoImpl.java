package com.myaccount.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myaccount.entity.StatusModel;
import com.myaccount.entity.UserDetail;
import com.myaccount.entity.UserLogin;
import com.myaccount.repository.UserRepository;
import com.myaccount.repository.Userdetailrepository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserRepository userrepository;

    @Autowired
    private Userdetailrepository userdetailrepository;

    @Override
    public StatusModel saveUser(UserLogin user) {
        if (userrepository.existsById(user.getUsername()))
            return new StatusModel(209);
        if (userdetailrepository.existsByEmail(user.getUserdetails().getEmail().toString()))
            return new StatusModel(204);
        else {
            userrepository.save(user);
            return new StatusModel(201);
        }
    }

    @Override
    public UserLogin getUser(String username) {
        Optional < UserLogin > obj = userrepository.findById(username);
        UserLogin userlogin = new UserLogin();
        obj.ifPresent(o -> {
            userlogin.setPassword(o.getPassword());
            userlogin.setUsername(o.getUsername());
            userlogin.setUserdetails(o.getUserdetails());
        });
        return userlogin;
    }

    @Override
    public UserDetail getUserDetail(int uid) {
        if (userdetailrepository.existsById(uid))
            return userdetailrepository.getById(uid);
        else
            return null;
    }
}