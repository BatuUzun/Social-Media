package com.ChattingApp.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="is_verified_email")
	private Boolean is_verified_email;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
	private UserProfile userProfile;

	public User() {
		
	}
	
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public User(String email, String password, Boolean is_verified_email, UserProfile userDetail) {
		super();
		this.email = email;
		this.password = password;
		this.is_verified_email = is_verified_email;
		this.userProfile = userDetail;
	}

	public User(String email, String password, Boolean is_verified_email) {
		super();
		this.email = email;
		this.password = password;
		this.is_verified_email = is_verified_email;
	}

	public Boolean getIs_verified_email() {
		return is_verified_email;
	}

	

	public void setIs_verified_email(Boolean is_verified_email) {
		this.is_verified_email = is_verified_email;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserProfile getUserDetail() {
		return userProfile;
	}

	public void setUserDetail(UserProfile userDetail) {
		this.userProfile = userDetail;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", is_verified_email=" + is_verified_email
				+ ", userProfile=" + userProfile + "]";
	}
	
	
	
}
