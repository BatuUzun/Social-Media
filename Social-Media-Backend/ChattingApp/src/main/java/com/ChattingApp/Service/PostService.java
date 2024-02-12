package com.ChattingApp.Service;

import java.util.List;

import com.ChattingApp.Entity.CommentPost;
import com.ChattingApp.Entity.LikePost;
import com.ChattingApp.Entity.Post;
import com.ChattingApp.Entity.UserProfile;

public interface PostService {

	void savePost(Post post);

	void savePostComment(CommentPost comment);

	void updatePost(Post post);

	Post findPostById(int id);

	void savePostLike(LikePost like);

	void deletePostById(int id);

	void deleteCommentById(int id);

	void deleteLikeById(int id);

	List<Post> findPostsByUsername(String username, int page);
	
	LikePost findLikeById(int id);
	
	int getIdOfLikePost(LikePost like);
	
	List<CommentPost> getCommentsOfPost(int postId, int page);
	
	List<Post> findPostOfFollowings(List<UserProfile> userProfiles, int page);

	int findNumberOfPostOfFollowings(List<UserProfile> userProfiles);


}
