package com.app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Admin;

public interface AdminDao extends JpaRepository<Admin, Integer> {

	Optional<Admin> findByEmailAndPassword(String email, String password);

	Admin findByEmail(String email);

	Optional<Admin> findById(int id);

}
