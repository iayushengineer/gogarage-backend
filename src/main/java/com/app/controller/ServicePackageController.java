package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.customException.ResourceNotFoundException;
import com.app.dao.ServicePackageDao;
import com.app.pojos.ServicePackage;

@RestController
@RequestMapping("/servicepackage")
public class ServicePackageController {
  @Autowired
  private ServicePackageDao packageDao;

  @GetMapping("/list")
  public List<ServicePackage> getAllPackage() {
    return packageDao.findAll();
  }

  @PostMapping("/add")
  public ServicePackage createServicePackage(@RequestBody ServicePackage servicePackage) {
    return packageDao.save(servicePackage);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ServicePackage> getServicePackageById(@PathVariable Long id) {
    ServicePackage servicePackage = packageDao.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("ServicePackage not exist with id :" + id));
    return ResponseEntity.ok(servicePackage);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<ServicePackage> updateServicePackage(@PathVariable Long id,
      @RequestBody ServicePackage servicePackageDetails) {
    ServicePackage servicePackage = packageDao.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("ServicePackage not exist with id :" + id));

    servicePackage.setPackageName(servicePackageDetails.getPackageName());
    servicePackage.setPackageDescription(servicePackageDetails.getPackageDescription());
    servicePackage.setPackagePrice(servicePackageDetails.getPackagePrice());

    ServicePackage updatedServicePackage = packageDao.save(servicePackage);
    return ResponseEntity.ok(updatedServicePackage);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteServicePackage(@PathVariable Long id) {
    ServicePackage servicePackage = packageDao.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("ServicePackage not exist with id :" + id));

    packageDao.delete(servicePackage);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return ResponseEntity.ok(response);
  }
}
