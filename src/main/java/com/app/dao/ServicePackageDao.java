package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.pojos.ServicePackage;

public interface ServicePackageDao extends JpaRepository<ServicePackage, Long> {

}
