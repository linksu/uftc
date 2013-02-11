package net.ambientia.uftc.domain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "UFTC")
public class Uftc {
	
	@Id
	@Column(name = "uftcId")
	@GeneratedValue
	private Integer id;


	@OneToMany(mappedBy="uftc",cascade = {CascadeType.REMOVE, CascadeType.ALL})
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Challenge> challenges = new ArrayList<Challenge>();
	
	@OneToMany(mappedBy="uftc",cascade = {CascadeType.REMOVE, CascadeType.ALL})
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<SportEvent> sportEvents = new ArrayList<SportEvent>();

	@OneToMany(mappedBy="uftc",cascade = {CascadeType.REMOVE, CascadeType.ALL})
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<User> users = new ArrayList<User>();
	
	public enum FieldTypes{
		challenges,sportevents,users
	}
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}

	public List<SportEvent> getSportEvents() {
		return sportEvents;
	}

	public void setSportEvents(List<SportEvent> sportEvents) {
		this.sportEvents = sportEvents;
	}
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	public EnumSet<FieldTypes> validate() {
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet.noneOf(FieldTypes.class);
		if(challenges != null)
			fieldTypeErrorList.add(FieldTypes.challenges);
		if(sportEvents != null)
			fieldTypeErrorList.add(FieldTypes.sportevents);
		if(users != null)
			fieldTypeErrorList.add(FieldTypes.users);
		return fieldTypeErrorList;
	}
	
}
