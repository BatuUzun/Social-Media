package com.ChattingApp.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_relationship")
public class UserRelationship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "follower_username")
	
	private UserProfile follower;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST })
	@JoinColumn(name = "following_username")
	private UserProfile following;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserProfile getFollower() {
		return follower;
	}

	public void setFollower(UserProfile follower) {
		this.follower = follower;
	}

	public UserProfile getFollowing() {
		return following;
	}

	public void setFollowing(UserProfile following) {
		this.following = following;
	}

	public UserRelationship(Long id, UserProfile follower, UserProfile following) {
		super();
		this.id = id;
		this.follower = follower;
		this.following = following;
	}

	public UserRelationship() {
		super();
	}

	@Override
	public String toString() {
		return "UserRelationship [follower=" + follower + ", following=" + following + "]";
	}

}