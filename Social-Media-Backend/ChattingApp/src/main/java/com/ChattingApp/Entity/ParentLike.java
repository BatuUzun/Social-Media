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
public class ParentLike {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "like_owner")
    private UserProfile likeOwner;

   

	public UserProfile getLikeOwner() {
		return likeOwner;
	}


	public void setLikeOwner(UserProfile likeOwner) {
		this.likeOwner = likeOwner;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	


	


	


	public ParentLike(int id, UserProfile likeOwner) {
		super();
		this.id = id;
		this.likeOwner = likeOwner;
	}


	public ParentLike() {
		super();
	}
    
    
	
	
}
