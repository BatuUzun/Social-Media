package com.ChattingApp.Entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="comment_post")
public class CommentPost extends ParentComment{
	
	@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "target_id")
    private Post targetPost;

	public Post getTargetPost() {
		return targetPost;
	}

	public void setTargetPost(Post targetPost) {
		this.targetPost = targetPost;
	}

	public CommentPost(Post targetPost) {
		super();
		this.targetPost = targetPost;
	}

	public CommentPost() {
		super();
	}
    
    
    
    
    

	
	
	
}
