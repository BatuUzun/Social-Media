package com.ChattingApp.Entity;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post extends ParentPostReel{
	
	
	@Lob
    @Column(name = "image", length = 2099999999)
    private byte[] image;
	
	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REMOVE,CascadeType.REFRESH}, mappedBy = "targetPost",
			fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CommentPost> comments;
	
	@OneToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, mappedBy = "targetPost",
			fetch = FetchType.LAZY)
	@JsonIgnore
	private List<LikePost> likes;
	
	public Post(int id, UserProfile ownerUsername, int numberOfLikes, int numberOfComments,
			byte[] image, List<CommentPost> comments) {
		super(id, ownerUsername, numberOfLikes, numberOfComments);
		this.image = image;
		this.comments = comments;
	}

	public List<CommentPost> getComments() {
		return comments;
	}

	public void setComments(List<CommentPost> comments) {
		this.comments = comments;
	}

	public Post() {
		super();
	}

	public Post(int id, UserProfile ownerUsername, byte[] image) {
		super(id, ownerUsername);
		this.image = image;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Post(int id, UserProfile ownerUsername, Date datePosted) {
		super(id, ownerUsername);
	}
	
	
}
