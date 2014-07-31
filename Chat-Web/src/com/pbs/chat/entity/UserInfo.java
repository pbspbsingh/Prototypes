package com.pbs.chat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "email") })
public class UserInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false, length = 255)
	private String email;

	private String firstName;

	private String lastName;

	@Column(nullable = false)
	private String authority;

	@Column(nullable = false)
	private String providerId;

	@Column(nullable = false)
	private String providerUserId;

	@Column(nullable = false)
	private boolean isRefreshed = false;
	
	@Column(nullable = false)
	private Date lastUpdated = new Date();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean isRefreshed() {
		return isRefreshed;
	}

	public void setRefreshed(boolean isRefreshed) {
		this.isRefreshed = isRefreshed;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", authority=" + authority
				+ ", providerId=" + providerId + ", providerUserId=" + providerUserId + "]";
	}

}
