package com.app.pojos;

import org.springframework.stereotype.Component;

@Component
public class LoginCredentials {

	private String email;
	private String password;

	public LoginCredentials() {
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

	@Override
	public String toString() {
		return "LoginCredentials [email=" + email + ", password=" + password + "]";
	}

}
