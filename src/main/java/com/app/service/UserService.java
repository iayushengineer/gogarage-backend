package com.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.dao.UserDao;
import com.app.pojos.User;

@Service(value = "userService")
public class UserService implements UserDetailsService {

	@Autowired
	UserDao userDao;

	public UserService() {

	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

		Optional<User> user = userDao.findById(userEmail);
		if (user.isPresent()) {
			User validateUser = user.get();
			return new org.springframework.security.core.userdetails.User(String.valueOf(validateUser.getEmail()),
					validateUser.getPassword(), getAuthority());
		}
		throw new UsernameNotFoundException("Invalid Credentials");
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("DUMMY"));
	}

	public User addUser(User user) {
		return userDao.save(user);
	}

	public User findByEmail(String userEmail) {
		Optional<User> user = userDao.findById(userEmail);
		if (user.isPresent())
			return user.get();
		return null;
	}

	public User deactivateUser(String userEmail) {
		Optional<User> user_ = userDao.findById(userEmail);
		if (user_.isPresent()) {
			User user = user_.get();
			user.setActive(0);
			return userDao.save(user);
		} else
			return null;
	}

	public User activateUser(String userEmail) {
		Optional<User> user_ = userDao.findById(userEmail);
		if (user_.isPresent()) {
			User user = user_.get();
			user.setActive(1);
			return userDao.save(user);
		} else
			return null;
	}

	public void deleteUser(String userEmail) {
		userDao.deleteById(userEmail);
	}

	public User getLastUser() {
		Optional<User> lastUser_ = userDao.getLastUser();
		if (lastUser_.isPresent())
			return lastUser_.get();
		else
			return null;
	}
}
