package com.ChattingApp.ApiRestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ChattingApp.Entity.User;
import com.ChattingApp.Entity.UserProfile;
import com.ChattingApp.Entity.UserRelationship;
import com.ChattingApp.Service.EmailService;
import com.ChattingApp.Service.EncryptionService;
import com.ChattingApp.Service.UserProfileService;
import com.ChattingApp.Service.UserService;

@RestController
@RequestMapping("/api/user/")
public class ApiUserRestController {

	private UserService userService;
	private UserProfileService userProfileService;
	private EncryptionService encryptionService;
	private EmailService emailService;
	
	@Autowired
	public ApiUserRestController(UserService userService, EncryptionService encryptionService,
			EmailService emailService, UserProfileService userProfileService) {		
		this.userService = userService;
		this.encryptionService = encryptionService;
		this.emailService = emailService;
		this.userProfileService = userProfileService;
	}

	@PostMapping("save/")
	private User saveUser(@RequestBody User user) {

		User targetUser = userService.findByEmail(user.getEmail());
		if (targetUser == null) {
			user.setPassword(encryptionService.bcryptPassword(user.getPassword()));
			userService.saveUser(user);
			return user;
		} else {
			System.out.println("User is already exists!");
			return null;
		}

	}

	@PostMapping("check/")
	private User checkUser(@RequestBody User user) {
		User target = userService.findByEmail(user.getEmail());
		if (target == null) {
			return null;
		} else {
			if (encryptionService.checkPassword(user.getPassword(), target.getPassword())) {
				return target;
			}

		}
		return null;
	}

	@PostMapping("generate-verification-code/")
	private int generateVerificationCode() {
		int generatedCode = emailService.generateVerificationCode();
		return generatedCode;
	}
	
	@PostMapping("send-verification-code/")
	private void sendEmail(@RequestBody Map<String, Integer> input) {
		
		String email = input.keySet().iterator().next();
		Integer code = input.get(email);
		
		emailService.verificationCodeEmailSender(email, code);
		
	}

	@PostMapping("change-email-status/")
	private int changeEmailStatus(@RequestBody User user) {
		userService.updateIsVerifiedEmailByEmail(user.getEmail(), true);
		return 1;
	}

	@PostMapping("save-username/")
	private UserProfile saveUsername(@RequestBody UserProfile userProfile) {
		UserProfile targetProfile = userProfileService.findProfileByEmail(userProfile.getUser().getEmail());
		if (targetProfile == null) {
			try {
				userProfileService.saveUsername(userProfile);
				return userProfile;
			} catch (DataIntegrityViolationException e) {
			}
		}
		return null;
	}

	@PostMapping("get-user-profile/")
	private UserProfile getUserProfile(@RequestBody User user) {
		UserProfile targetUserProfile = userProfileService.findProfileByEmail(user.getEmail());
		/*System.out.println(targetUserProfile.getNumberOfFollowers()+" "+targetUserProfile.getNumberOfFollowings()
		+" "+targetUserProfile.getNumberOfPosts());*/
		if (targetUserProfile == null)
			return null;
		targetUserProfile.setUser(user);
		return targetUserProfile;
	}

	@PostMapping("search-users-profile/")
	private List<UserProfile> searchUsername(@RequestBody String username) {
		List<UserProfile> users = userProfileService.searchUsernameByUsername(username);
		return users;

	}

	@PostMapping("/upload-picture")
    public String uploadImage(@RequestParam("image") MultipartFile imageFile,
    							@RequestParam("username") String username) {
        if (!imageFile.isEmpty()) {
            try {
				String base64Image = Base64.getEncoder().encodeToString(imageFile.getBytes());
				userProfileService.uploadPicture(base64Image, username);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	
        	return "Image uploaded successfully for user: " + username;
        } else {
            return "Image file is empty!";
        }
    }

	@PostMapping("save-user-relationship/")
	private void saveUserRelationship(@RequestBody UserRelationship userRelationship) {
		
		//userProfileService.saveUserRelationship(userRelationship);
		
		List<UserRelationship> relationList = new ArrayList<UserRelationship>();
		relationList.add(userRelationship);
		
		UserProfile follower = userProfileService.findProfileByUsername(userRelationship.getFollower().getUsername());
		follower.setNumberOfFollowings(follower.getNumberOfFollowings() + 1);
		follower.setFollowings(relationList);
		userProfileService.updateUserProfile(follower);

		UserProfile following = userProfileService.findProfileByUsername(userRelationship.getFollowing().getUsername());
		following.setNumberOfFollowers(following.getNumberOfFollowers() + 1);
		follower.setFollowers(relationList);
		userProfileService.updateUserProfile(following);
		
	}
	
	@DeleteMapping("delete-user-relationship/")
	private void deleteUserRelationship(@RequestBody UserRelationship userRelationship) {
		long id = userProfileService.findIdOfRelationship(userRelationship.getFollower(), userRelationship.getFollowing());
		boolean isDeleted = userProfileService.deleteUserRelationshpiById(id);
		
		if(isDeleted) {
			UserProfile follower = userProfileService.findProfileByUsername(userRelationship.getFollower().getUsername());
			follower.setNumberOfFollowings(follower.getNumberOfFollowings() - 1);
			userProfileService.updateUserProfile(follower);

			UserProfile following = userProfileService.findProfileByUsername(userRelationship.getFollowing().getUsername());
			following.setNumberOfFollowers(following.getNumberOfFollowers() - 1);
			userProfileService.updateUserProfile(following);
		}
		
	}
	
	@PostMapping("get-user-relationship-is-following/")
	private boolean getUserRelationshipListIsFollowing(@RequestBody UserRelationship userRelationship) {
		long id = -1;
		id = userProfileService.findIdOfRelationship(userRelationship.getFollower(), userRelationship.getFollowing());
		if(id == -1)
			return false;
		return true;
		
	}
	
	@PostMapping("get-user-relationship/")
	private List<UserRelationship> findUserRelationship(@RequestBody UserProfile userProfile) {
		return userProfileService.findUserRelationship(userProfile);
	}
	
	@PostMapping("update-user-profile/")
	private void updateUserProfile(@RequestBody UserProfile userProfile) {
		userProfileService.updateUserProfile(userProfile);
	}

}
