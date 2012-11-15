package net.ambientia.uftc.domain;

import javax.persistence.Entity;

@Entity
public class ChallengerUser extends User {

	public ChallengerUser() {
		super();

		this.authority = CHALLENGER;
	}

}
