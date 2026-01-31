package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "customer")
@JsonInclude(value = Include.NON_DEFAULT)
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private int id;

	@Column(length = 45)
	@JsonProperty("name")
	private String name;

	@Column(length = 200)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Past
	@JsonProperty("birth_date")
	private LocalDate birthDate;

	@Column(length = 20)
	@JsonProperty("contact")
	private String contact;

	@Column(length = 200)
	@JsonProperty("address")
	private String address;

	@Column(length = 45)
	@JsonProperty("email")
	private String email;

	@Column(length = 100)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(length = 50)
	@JsonProperty("employee_id")
	private int employeeId;

	@OneToMany(mappedBy = "customerId", fetch = FetchType.EAGER)
	private List<ServiceRequest> request_list = new ArrayList<>();

	/* ============================== Constructor ============================== */

	public Customer() {
		super();
	}

	public Customer(int id, String name, LocalDate birthDate, String contact, String address, String email,
			String password) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.contact = contact;
		this.address = address;
		this.email = email;
		this.password = password;
	}

	/* =========================== Getters & Setters =========================== */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public List<ServiceRequest> getRequest_list() {
		return request_list;
	}

	public void setRequest_list(List<ServiceRequest> request_list) {
		this.request_list = request_list;
	}

	/* ================================ toString =============================== */

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", contact=" + contact
				+ ", address=" + address + ", email=" + email + ", password=" + password + ", employeeId=" + employeeId
				+ ", request_list=" + request_list + "]";
	}

}
