package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "vendor")
@JsonInclude(value = Include.NON_DEFAULT)
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private int id;

	@Column(length = 45)
	@JsonProperty("name")
	private String name;

	@Column(length = 200)
	@JsonProperty("address")
	private String address;

	@Column(length = 30)
	@JsonProperty("contact")
	private String contact;

	@Column(length = 45)
	@JsonProperty("email")
	private String email;

	@Column(length = 100)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@OneToMany(mappedBy = "vendorId", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Employee> employees = new ArrayList<>();

	/* ============================== Constructor ============================== */
	public Vendor() {
		super();
	}

	public Vendor(int id, String name, String address, String contact, String email, String password,
			List<Employee> employees) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.email = email;
		this.password = password;
		this.employees = employees;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	/* ================================ toString ================================ */

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", name=" + name + ", address=" + address + ", contact=" + contact + ", email="
				+ email + ", password=" + password + "]";
	}

}
