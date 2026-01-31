package com.app.pojos;

import java.time.LocalDate;
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
import jakarta.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "employee")
@JsonInclude(value = Include.NON_DEFAULT)
public class Employee {
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

	@Column(length = 45)
	@JsonProperty("email")
	private String email;

	@Column(length = 100)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@JsonProperty("vendor_id")
	private int vendorId;

	@OneToMany(mappedBy = "employeeId", cascade = CascadeType.PERSIST)
	private List<Customer> customers = new ArrayList<>();

	/* ============================== Constructor ============================== */
	public Employee() {
		super();
	}

	public Employee(int id, String name, LocalDate birthDate, String email, String password, int vendor_id) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
		this.password = password;
		this.vendorId = vendor_id;
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

	public int getVendorId() {
		return vendorId;
	}

	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}

	/* ================================ toString =============================== */
	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", birthDate=" + birthDate + ", email=" + email + ", password="
				+ password + ", vendor_id=" + vendorId + "]";
	}

}
