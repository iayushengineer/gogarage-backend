package com.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.AdminDao;
import com.app.pojos.Admin;

@Service
public class AdminService implements IAdminService {

	@Autowired
	AdminDao adminDao;

	public AdminService() {
	}

	@Override
	public Admin findById(int id) {
		Optional<Admin> admin = adminDao.findById(id);
		if (admin.isPresent())
			return admin.get();
		else
			return null;
	}

	@Override
	public Admin addAdmin(Admin newAdmin) {
		return adminDao.save(newAdmin);
	}

	@Override
	public Admin getByEmailId(String emailId) {

		return adminDao.findByEmail(emailId);
	}
}
