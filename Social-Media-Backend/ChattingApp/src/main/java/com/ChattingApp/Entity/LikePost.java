package com.ChattingApp.Entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="like_post")
public class LikePost extends ParentLike{
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "target_id")
    private Post targetPost;

	public Post getTargetPost() {
		return targetPost;
	}

	public LikePost(int id, UserProfile likeOwner, Post targetPost) {
		super(id, likeOwner);
		this.targetPost = targetPost;
	}

	public void setTargetPost(Post targetPost) {
		this.targetPost = targetPost;
	}

	public LikePost(Post targetPost) {
		super();
		this.targetPost = targetPost;
	}

	public LikePost() {
		super();
	}
    
    
    
    
    

	
	
	
}
