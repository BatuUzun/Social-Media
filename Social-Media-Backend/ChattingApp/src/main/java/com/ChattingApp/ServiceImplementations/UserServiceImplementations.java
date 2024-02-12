package com.ChattingApp.ServiceImplementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import com.ChattingApp.DAO.UserDAO;
import com.ChattingApp.Entity.User;
import com.ChattingApp.Service.UserService;
import jakarta.transaction.Transactional;


@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class UserServiceImplementations implements UserService{

	private UserDAO userDAO;
	
	@Autowired
	public UserServiceImplementations(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Transactional
	@Override
	public void deleteByEmail(String email) {
		userDAO.deleteByEmail(email);
	}

	@Transactional
	@Override
	public User updateUser(User user) {
		return userDAO.updateUser(user);
	}

	@Override
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		userDAO.saveUser(user);
	}

	@Override
	@Transactional
	public void updateIsVerifiedEmailByEmail(String email, boolean isVerifiedEmail) {
		userDAO.updateIsVerifiedEmailByEmail(email, isVerifiedEmail);
	}

	
	
	
}
