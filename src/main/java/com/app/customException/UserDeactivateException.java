package com.app.customException;

import org.springframework.security.core.AuthenticationException;

public class UserDeactivateException extends AuthenticationException {

	public UserDeactivateException(String msg) {
		super(msg);
	}

}
