package com.ChattingApp.Service;

import java.util.List;

import com.ChattingApp.Entity.UserProfile;
import com.ChattingApp.Entity.UserRelationship;

public interface UserProfileService {
	
	void saveUsername(UserProfile userDetail);
	
	UserProfile findProfileByEmail(String email);

	List<UserProfile> searchUsernameByUsername(String username);

	void updateUserProfile(UserProfile userDetail);
	
	UserProfile findProfileByUsername(String username);
	
	void saveUserRelationship(UserRelationship userRelationship);

	boolean deleteUserRelationshpiById(long id);
	
	long findIdOfRelationship(UserProfile follower, UserProfile following);

	void uploadPicture(String image, String username);
	
	List<UserRelationship> findUserRelationship(UserProfile userProfile);


}
