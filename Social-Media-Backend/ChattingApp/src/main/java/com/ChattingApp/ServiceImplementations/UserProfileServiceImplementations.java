package com.ChattingApp.ServiceImplementations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import com.ChattingApp.DAO.UserProfileDAO;
import com.ChattingApp.Entity.UserProfile;
import com.ChattingApp.Entity.UserRelationship;
import com.ChattingApp.Service.UserProfileService;
import jakarta.transaction.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class UserProfileServiceImplementations implements UserProfileService{

	
	private UserProfileDAO userProfileDAO;
	
	@Autowired
	public UserProfileServiceImplementations(UserProfileDAO userProfileDAO) {
		this.userProfileDAO = userProfileDAO;
	}
	
	@Override
	@Transactional
	public void saveUsername(UserProfile userDetail) {
		userProfileDAO.saveUsername(userDetail);
		
	}

	@Override
	public UserProfile findProfileByEmail(String email) {
		return userProfileDAO.findProfileByEmail(email);
	}

	@Override
	public List<UserProfile> searchUsernameByUsername(String username) {
		return userProfileDAO.searchUsernameByUsername(username);
	}

	@Override
	@Transactional
	public void updateUserProfile(UserProfile userDetail) {
		userProfileDAO.updateUserProfile(userDetail);
	}

	@Override
	public UserProfile findProfileByUsername(String username) {
		return userProfileDAO.findProfileByUsername(username);
		
	}

	@Override
	@Transactional
	public void saveUserRelationship(UserRelationship userRelationship) {
		userProfileDAO.saveUserRelationship(userRelationship);
	}

	@Override
	@Transactional
	public boolean deleteUserRelationshpiById(long id) {
		return userProfileDAO.deleteUserRelationshpiById(id);
	}

	@Override
	public long findIdOfRelationship(UserProfile follower, UserProfile following) {
		return userProfileDAO.findIdOfRelationship(follower, following);
	}

	@Override
	@Transactional
	public void uploadPicture(String image, String username) {
		userProfileDAO.uploadPicture(image, username);		
	}

	@Override
	public List<UserRelationship> findUserRelationship(UserProfile userProfile) {
		return userProfileDAO.findUserRelationship(userProfile);
	}
}
