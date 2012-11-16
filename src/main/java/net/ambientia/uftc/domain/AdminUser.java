package net.ambientia.uftc.domain;

import javax.persistence.Entity;

@Entity
public class AdminUser extends User {

	public AdminUser() {
		super();
		
		this.authority = ADMIN;
	}

}
