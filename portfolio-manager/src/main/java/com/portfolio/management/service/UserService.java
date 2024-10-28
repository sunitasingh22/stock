package com.portfolio.management.service;

import com.portfolio.management.model.UserBO;

public interface UserService {
	
    public UserBO addUser(UserBO user);
    
    public String checkUser(UserBO loginUser);

	public Long getUserId(UserBO loginUser);
}
