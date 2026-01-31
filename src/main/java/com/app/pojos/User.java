package com.app.pojos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "users")
@JsonInclude(Include.NON_DEFAULT)
public class User {

	private String email;
	private String password;
	private String role;
	private int active;

	public User() {

	}

	public User(String email, String password, String role, int active) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.active = active;
	}

	@Id
	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", password=" + password + ", role=" + role + ", active=" + active + "]";
	}

}
