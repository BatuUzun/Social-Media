package com.ChattingApp.ServiceImplementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import com.ChattingApp.DAO.PostDAO;
import com.ChattingApp.Entity.CommentPost;
import com.ChattingApp.Entity.LikePost;
import com.ChattingApp.Entity.Post;
import com.ChattingApp.Entity.UserProfile;
import com.ChattingApp.Service.PostService;

import jakarta.transaction.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
@Transactional
public class PostServiceImplementations implements PostService{

private PostDAO postDAO;
	
	@Autowired
	public PostServiceImplementations(PostDAO postDAO) {
		this.postDAO = postDAO;
	}
	
	
	@Override
	@Transactional
	public void savePost(Post post) {
		postDAO.savePost(post);
	}


	@Override
	@Transactional
	public void savePostComment(CommentPost comment) {
		postDAO.savePostComment(comment);
	}


	@Override
	@Transactional
	public void updatePost(Post post) {
		postDAO.updatePost(post);
	}


	@Override
	public Post findPostById(int id) {
		return postDAO.findPostById(id);
	}


	@Override
	@Transactional
	public void savePostLike(LikePost like) {
		postDAO.savePostLike(like);
		
	}


	@Override
	@Transactional
	public void deletePostById(int id) {
		postDAO.deletePostById(id);
	}


	@Override
	@Transactional
	public void deleteCommentById(int id) {
		postDAO.deleteCommentById(id);
	}


	@Override
	@Transactional
	public void deleteLikeById(int id) {
		postDAO.deleteLikeById(id);
	}


	@Override
	public List<Post> findPostsByUsername(String username, int page) {
		return postDAO.findPostsByUsername(username, page);
	}


	@Override
	public LikePost findLikeById(int id) {
		return postDAO.findLikeById(id);
	}


	@Override
	public int getIdOfLikePost(LikePost like) {
		
		return postDAO.getIdOfLikePost(like);
	}


	@Override
	public List<CommentPost> getCommentsOfPost(int postId, int page) {
		return postDAO.getCommentsOfPost(postId, page);
	}


	@Override
	public List<Post> findPostOfFollowings(List<UserProfile> userProfiles, int page) {
		return postDAO.findPostOfFollowings(userProfiles, page);
	}


	@Override
	public int findNumberOfPostOfFollowings(List<UserProfile> userProfiles) {
		return postDAO.findNumberOfPostOfFollowings(userProfiles);
	}

}
