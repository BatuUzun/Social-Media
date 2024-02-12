package com.ChattingApp.Entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_profile")
public class UserProfile {

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "number_of_followers")
	private int numberOfFollowers;

	@Column(name = "number_of_followings")
	private int numberOfFollowings;

	@Column(name = "number_of_posts")
	private int numberOfPosts;

	@OneToOne
	@JoinColumn(name = "email")
	@JsonBackReference
	private User user;

	@Lob
	@Column(name = "profile_picture", length = 999999999)
	private byte[] imageData;

	@OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, mappedBy = "ownerUsername", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Post> posts;
	
	@OneToMany(mappedBy = "following", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@JsonIgnore
    private List<UserRelationship> followers;

    @OneToMany(mappedBy = "follower", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserRelationship> followings;

	

	public UserProfile(String username, int numberOfFollowers, int numberOfFollowings, int numberOfPosts, User user,
			byte[] imageData, List<Post> posts, List<UserRelationship> followers,
			List<UserRelationship> followings) {
		super();
		this.username = username;
		this.numberOfFollowers = numberOfFollowers;
		this.numberOfFollowings = numberOfFollowings;
		this.numberOfPosts = numberOfPosts;
		this.user = user;
		this.imageData = imageData;
		this.posts = posts;
		this.followers = followers;
		this.followings = followings;
	}

	public List<UserRelationship> getFollowers() {
		return followers;
	}

	public void setFollowers(List<UserRelationship> followers) {
		this.followers = followers;
	}

	public List<UserRelationship> getFollowings() {
		return followings;
	}

	public void setFollowings(List<UserRelationship> followings) {
		this.followings = followings;
	}

	public UserProfile(String username, int numberOfFollowers, int numberOfFollowings, int numberOfPosts, User user,
			byte[] imageData, List<Post> posts) {
		super();
		this.username = username;
		this.numberOfFollowers = numberOfFollowers;
		this.numberOfFollowings = numberOfFollowings;
		this.numberOfPosts = numberOfPosts;
		this.user = user;
		this.imageData = imageData;
		this.posts = posts;
		
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	
	public UserProfile(String username, int numberOfFollowers, int numberOfFollowings, int numberOfPosts, User user) {
		super();
		this.username = username;
		this.numberOfFollowers = numberOfFollowers;
		this.numberOfFollowings = numberOfFollowings;
		this.numberOfPosts = numberOfPosts;
		this.user = user;
	}

	public UserProfile(String username, int numberOfFollowers, int numberOfFollowings, int numberOfPosts, User user,
			byte[] imageData) {
		super();
		this.username = username;
		this.numberOfFollowers = numberOfFollowers;
		this.numberOfFollowings = numberOfFollowings;
		this.numberOfPosts = numberOfPosts;
		this.user = user;
		this.imageData = imageData;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public UserProfile() {

	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserProfile(String username, int numberOfFollowers, int numberOfFollowings, int numberOfPosts) {
		super();
		this.username = username;
		this.numberOfFollowers = numberOfFollowers;
		this.numberOfFollowings = numberOfFollowings;
		this.numberOfPosts = numberOfPosts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getNumberOfFollowers() {
		return numberOfFollowers;
	}

	public void setNumberOfFollowers(int numberOfFollowers) {
		this.numberOfFollowers = numberOfFollowers;
	}

	public int getNumberOfFollowings() {
		return numberOfFollowings;
	}

	public void setNumberOfFollowings(int numberOfFollowings) {
		this.numberOfFollowings = numberOfFollowings;
	}

	public int getNumberOfPosts() {
		return numberOfPosts;
	}

	public void setNumberOfPosts(int numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

	@Override
	public String toString() {
		return username;
	}

}