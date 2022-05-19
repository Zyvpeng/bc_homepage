package com.bc208.blog.service;

import com.bc208.blog.pojo.UserModel;

import java.io.IOException;

public interface UserService {
    public UserModel userLogin(String Email, String password);//登录
    public UserModel userRegister(String Email,String password) throws IOException;//注册


}
