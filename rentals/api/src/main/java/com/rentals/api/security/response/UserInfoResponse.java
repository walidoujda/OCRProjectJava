package com.rentals.api.security.response;

import java.sql.Date;

public class UserInfoResponse {
	private Long id;
	private String name;
	private String email;
	private Date created_at;
	private Date updated_at;

	public UserInfoResponse(Long id, String email, String name, Date created_at, Date updated_at) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
}
