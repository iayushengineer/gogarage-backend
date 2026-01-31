package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.ServiceRequestDao;
import com.app.pojos.ServiceRequest;

@Service
public class ServiceRequestService implements IServiceRequestService {

	@Autowired
	private ServiceRequestDao serviceRequestDao;

	@Override
	public ServiceRequest getByCustomerId(int customer_id) {

		Optional<ServiceRequest> request = serviceRequestDao.findByCustomerId(customer_id);
		if (request.isPresent()) {
			return request.get();
		} else {
			return null;
		}
	}

	@Override
	public ServiceRequest addServiceRequest(ServiceRequest service_request) {

		return serviceRequestDao.save(service_request);
	}

	@Override
	public ServiceRequest getByRequestId(int request_id) {
		Optional<ServiceRequest> request = serviceRequestDao.findByRequestId(request_id);
		if (request.isPresent()) {
			return request.get();

		} else {
			return null;
		}
	}

	@Override
	public ServiceRequest getByCustomerIdAndStatus(int customer_id, String status) {

		Optional<ServiceRequest> request = serviceRequestDao.findByCustomerIdAndStatus(customer_id, status);
		if (request.isPresent()) {
			System.out.println(request.get());
			return request.get();

		} else {
			return null;
		}
	}

	@Override
	public List<ServiceRequest> getAllServices(int customer_id) {

		List<ServiceRequest> list = null;
		list = serviceRequestDao.findAllByCustomerId(customer_id);

		if (list != null) {
			return list;

		} else {
			return null;
		}

	}

}
