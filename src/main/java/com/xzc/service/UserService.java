package com.xzc.service;

import com.xzc.dao.UserDao;
import com.xzc.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public List<User> list(){
        List<User> allUser = userDao.findAll();
        return allUser;
    }

    //判断是否存在用户名
    public boolean isExist(String username) {
        User user = getByName(username);
        return null != user;//存在则不为null,返回true
    }

    public User getByUserName(String username){
        User user = getByName(username);
        return user;
    }

    private User getByName(String username) {
        return userDao.findByUsername(username);
    }

    public User getByUsernameAndPassword(String username,String password){
        return userDao.getByUsernameAndPassword(username,password);
    }

    //这里使用JPA的save方法直接添加
    public void add(User user){
        userDao.save(user);
    }

}
