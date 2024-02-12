package com.ChattingApp.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ParentPostReel {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private int id;

	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "owner_username")
	private UserProfile ownerUsername;

	

	@Column(name = "number_of_likes")
	private int numberOfLikes;

	@Column(name = "number_of_comments")
	private int numberOfComments;

	public ParentPostReel(int id, UserProfile ownerUsername, int numberOfLikes, int numberOfComments) {
		super();
		this.id = id;
		this.ownerUsername = ownerUsername;
		this.numberOfLikes = numberOfLikes;
		this.numberOfComments = numberOfComments;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public ParentPostReel(int id, UserProfile ownerUsername) {
		super();
		this.id = id;
		this.ownerUsername = ownerUsername;
	}

	public ParentPostReel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserProfile getOwnerUsername() {
		return ownerUsername;
	}

	public void setOwnerUsername(UserProfile ownerUsername) {
		this.ownerUsername = ownerUsername;
	}

	

	@Override
	public String toString() {
		return "ParentPostReel [id=" + id + ", ownerUsername=" + ownerUsername + ",  + ";
	}

}