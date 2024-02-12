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
public class ParentComment {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "comment_owner")
    private UserProfile commentOwner;

    @Column(name = "comment_text")
    private String commentText;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public UserProfile getCommentOwner() {
		return commentOwner;
	}


	public void setCommentOwner(UserProfile commentOwner) {
		this.commentOwner = commentOwner;
	}


	

	public String getCommentText() {
		return commentText;
	}


	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}


	public ParentComment(int id, UserProfile commentOwner, String commentText) {
		super();
		this.id = id;
		this.commentOwner = commentOwner;
		this.commentText = commentText;
	}


	public ParentComment() {
		super();
	}
    
    
	
	
}
