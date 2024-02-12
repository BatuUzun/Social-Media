package com.ChattingApp.ApiRestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ChattingApp.Entity.CommentPost;
import com.ChattingApp.Entity.LikePost;
import com.ChattingApp.Entity.Post;
import com.ChattingApp.Entity.UserProfile;
import com.ChattingApp.Entity.UserRelationship;
import com.ChattingApp.Service.PostService;
import com.ChattingApp.Service.UserProfileService;

@RestController
@RequestMapping("/api/post/")
public class ApiPostRestController {

	private PostService postService;
	private UserProfileService userProfileService;

	@Autowired
	public ApiPostRestController(PostService postService, UserProfileService userProfileService) {
		this.postService = postService;
		this.userProfileService = userProfileService;
	}

	@PostMapping("save-post/")
	private void savePost(@RequestBody Post post) {
		postService.savePost(post);
		UserProfile targetProfile = userProfileService.findProfileByUsername(post.getOwnerUsername().getUsername());
		targetProfile.setNumberOfPosts(targetProfile.getNumberOfPosts() + 1);
		userProfileService.updateUserProfile(targetProfile);
	}

	@PostMapping("save-post-comment/")
	private void savePostComment(@RequestBody CommentPost comment) {
		postService.savePostComment(comment);
		Post target = postService.findPostById(comment.getTargetPost().getId());
		if (target != null) {
			target.setNumberOfComments(target.getNumberOfComments() + 1);
			postService.updatePost(target);
		}

	}

	@PostMapping("save-post-like/")
	private void savePostLike(@RequestBody LikePost like) {
		postService.savePostLike(like);
		Post target = postService.findPostById(like.getTargetPost().getId());
		if (target != null) {
			target.setNumberOfLikes(target.getNumberOfLikes() + 1);
			postService.updatePost(target);
		}

	}

	@PostMapping("delete-post/")
	private void deletePost(@RequestBody Post postToDelete) {
		postService.deletePostById(postToDelete.getId());

		UserProfile userProfile = userProfileService
				.findProfileByUsername(postToDelete.getOwnerUsername().getUsername());
		userProfile.setNumberOfPosts(userProfile.getNumberOfPosts() - 1);
		userProfileService.updateUserProfile(userProfile);
	}

	@PostMapping("delete-comment/")
	private void deleteComment(@RequestBody CommentPost commentToDelete) {
		postService.deleteCommentById(commentToDelete.getId());

		Post post = postService.findPostById(commentToDelete.getTargetPost().getId());
		post.setNumberOfComments(post.getNumberOfComments() - 1);
		postService.updatePost(post);
	}

	@PostMapping("delete-post-like/")
	private void deleteLike(@RequestBody LikePost likeToDelete) {
		postService.deleteLikeById(likeToDelete.getId());

		Post post = postService.findPostById(likeToDelete.getTargetPost().getId());
		post.setNumberOfLikes(post.getNumberOfLikes() - 1);
		postService.updatePost(post);
	}
	
	@PostMapping("get-posts-by-username/")
	private List<Post> findPostsByUsername(@RequestBody Map<String, Integer> input){
		String username = input.keySet().iterator().next();
		Integer page = input.get(username);
		List<Post> posts = postService.findPostsByUsername(username, page);
		return posts;
	}
	
	@PostMapping("find-post-by-id/")
	private Post findPostById(@RequestBody int id){
		return postService.findPostById(id);
	}
	
	@PostMapping("find-like-by-like/")
	private int findLikeById(@RequestBody LikePost like){
		return postService.getIdOfLikePost(like);
	}
	
	@PostMapping("get-comments-of-post/")
	private List<CommentPost> getCommentsOfPost(@RequestBody Map<Integer, Integer> input){
		 Integer postId = input.keySet().iterator().next();
		 Integer page = input.values().iterator().next();
		 
		return postService.getCommentsOfPost(postId, page);
	}
	
	@PostMapping("find-posts-of-followings/")
	private List<Post> findPostsOfFollowings(@RequestBody Map<String, Integer> input){
		String username = input.keySet().iterator().next();
		Integer page = input.get(username);
		
		UserProfile userProfile = userProfileService.findProfileByUsername(username);
		
		List<UserRelationship> relationList = userProfileService.findUserRelationship(userProfile);
		List<UserProfile> profileList = new ArrayList<>();
		for(UserRelationship u : relationList) {
			if(!u.getFollowing().getUsername().equals(username))
				profileList.add(u.getFollowing());
		}
		return postService.findPostOfFollowings(profileList, page);
	}

	@PostMapping("find-number-of-posts-of-followings/")
	private int findPostsOfFollowings(@RequestBody UserProfile userProfile){
		
		List<UserRelationship> relationList = userProfileService.findUserRelationship(userProfile);
		int sum = 0;
		for(UserRelationship u : relationList) {
			
			if(!u.getFollowing().getUsername().equals(userProfile.getUsername())) {
				sum += u.getFollowing().getNumberOfPosts();
			}
			
		}
		return sum;
	}
	
}
