package net.ambientia.uftc.domain;

public class AdminUser extends User {

	public AdminUser() {
		super();
		
		this.authority = ADMIN;
	}

}
