package com.app.pojos;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "feedback")
@JsonInclude(value = Include.NON_DEFAULT)
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("id")
	private int id;

	@JsonProperty("customer_id")
	private int customer_id;

	@Column(length = 100)
	@JsonProperty("feedback_message")
	private String feedback_message;
	
	/* ============================== Constructor ============================== */
	public Feedback() {
		super();
	}

	public Feedback(int id, int customer_id, String feedback_message) {
		super();
		this.id = id;
		this.customer_id = customer_id;
		this.feedback_message = feedback_message;
	}

	/* =========================== Getters & Setters =========================== */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getFeedback_message() {
		return feedback_message;
	}

	public void setFeedback_message(String feedback_message) {
		this.feedback_message = feedback_message;
	}

	/* ================================ toString =============================== */
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", customer_id=" + customer_id + ", feedback_message=" + feedback_message + "]";
	}

	public static List<Feedback> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
