package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.User;


public interface UserDao extends JpaRepository<User, String> {

	@Query(value = "SELECT * FROM users ORDER BY CAST(username as SIGNED INTEGER) DESC LIMIT 1", nativeQuery = true)
	Optional<User> getLastUser();

}
