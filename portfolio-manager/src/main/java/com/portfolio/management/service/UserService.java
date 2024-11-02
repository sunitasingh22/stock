package com.portfolio.management.service;

import com.portfolio.management.model.UserBO;

public interface UserService {
	
    public UserBO addUser(UserBO user);
    
    public UserBO checkUser(UserBO loginUser);

}
