package org.gk.contactapp.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.gk.contactapp.enums.TokenStatus;

@Entity
@Table(name = "token")
public class Token {
	
	@Id
	@Column(name = "id", length = 10, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private Users user;
	
	@Column(name = "session_token", nullable = false)
	private String sessionToken;
	
	@Column(name = "last_updated", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated = new Date();
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private TokenStatus status = TokenStatus.VALID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public TokenStatus getStatus() {
		return status;
	}

	public void setStatus(TokenStatus status) {
		this.status = status;
	}
}