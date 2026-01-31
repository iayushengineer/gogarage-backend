package com.app.service;

import com.app.pojos.Admin;

public interface IAdminService {

	Admin addAdmin(Admin newAdmin);

	Admin findById(int id);

	Admin getByEmailId(String emailId);
}
