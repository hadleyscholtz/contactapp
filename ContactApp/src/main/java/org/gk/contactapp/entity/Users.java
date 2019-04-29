package org.gk.contactapp.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 10, nullable = false)
	private Long id;
	
	@Column(name = "username", length = 100, nullable = false)
	private String userName;
	
	@Column(name = "phone", length = 50, nullable = false)
	private String phone;
	
	@Column(name = "password", nullable = false)
	private byte[] password;
	
	@OneToMany(mappedBy = "user")
	private List<Token> userTokens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	public List<Token> getUserTokens() {
		return userTokens;
	}

	public void setUserTokens(List<Token> userTokens) {
		this.userTokens = userTokens;
	}
}