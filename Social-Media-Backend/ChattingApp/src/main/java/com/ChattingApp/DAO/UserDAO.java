package com.ChattingApp.DAO;

import com.ChattingApp.Entity.User;

public interface UserDAO {
	
	void deleteByEmail(String email);
	
	User updateUser(User user);
	
	User findByEmail(String email);
	
	void saveUser(User user);
	
	void updateIsVerifiedEmailByEmail(String email, boolean isVerifiedEmail);
	
	

	
}
