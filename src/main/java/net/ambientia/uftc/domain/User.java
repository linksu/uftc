package net.ambientia.uftc.domain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OptimisticLock;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@Entity
@Table(name = "USER")
public class User {
	public static final String ADMIN = "ROLE_ADMIN";
	public static final String CHALLENGER = "ROLE_CHALLENGER";
	public static final String USER = "ROLE_USER";
	
	
	public User() {
		this.authority = USER; 
	}

	@Id
	@Column(name = "userId")
	@GeneratedValue
	protected Integer id;

	@Column(name = "USERNAME", length = 20, nullable = false)
	protected String username;
	
	@Column(name = "AUTHORITY")
	protected String authority;
	
	@OneToMany(mappedBy="user",cascade = {CascadeType.REMOVE, CascadeType.ALL})
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	protected List<Authorities> authorities = new ArrayList<Authorities>();
	
	@Column(name = "PASSWORD")
	protected String password;
	
	@Transient
	protected String retypePassword;
	
	@Column(name = "ENABLED")
	protected boolean enabled;

	@Column(name = "FIRSTNAME", length = 30, nullable = false)
	protected String firstName;

	@Column(name = "LASTNAME", length = 50, nullable = false)
	protected String lastName;

	
	  @ManyToMany  
	  @JoinTable(name = "user_challenge",    
	    joinColumns = { @JoinColumn(name = "userId")},  
	      inverseJoinColumns={@JoinColumn(name="challengeId")})  
	protected List<Challenge> challenges;

	@ManyToOne
	@JoinColumn(name = "uftcId", nullable = false)
	protected Uftc uftc;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE,
			CascadeType.ALL })
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	protected List<Workout> workouts = new ArrayList<Workout>();

	@Version
	@Column(name = "OPTLOCK")
	protected Integer version;

	public enum FieldTypes {
		username, firstName, lastName, isChallenger, uftcId, workouts,password,retypePassword
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Authorities> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authorities> authorities) {
		this.authorities = authorities;
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

	public List<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}

	public Uftc getUftc() {
		return uftc;
	}

	public void setUftc(Uftc uftc) {
		this.uftc = uftc;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = GenerateMd5(password);
	}

	public String getRetypePassword() {
		return retypePassword;
	}

	public void setRetypePassword(String retypePassword) {
		this.retypePassword = GenerateMd5(retypePassword);
		
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addWorkout(Workout workout) {
		workouts.add(workout);
	}

	protected String GenerateMd5(String password){
		if(password.equals(""))
			return "";
		PasswordEncoder encoder = new Md5PasswordEncoder();	
		return encoder.encodePassword(password, null);
	}
	public EnumSet<FieldTypes> validate() {
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		if (username != null
				&& (username.length() > 20 || username.length() < 3))
			fieldTypeErrorList.add(FieldTypes.username);
		if (firstName != null
				&& (firstName.length() > 30 || firstName.length() < 3))
			fieldTypeErrorList.add(FieldTypes.firstName);
		if (lastName != null
				&& (lastName.length() > 50 || lastName.length() < 3))
			fieldTypeErrorList.add(FieldTypes.lastName);
		if (workouts == null)
			fieldTypeErrorList.add(FieldTypes.workouts);
		if (uftc == null)
			fieldTypeErrorList.add(FieldTypes.uftcId);
		if(password != null && password.length() < 3)
			fieldTypeErrorList.add(FieldTypes.password);
		if (!password.equals(retypePassword))
			fieldTypeErrorList.add(FieldTypes.retypePassword);
		return fieldTypeErrorList;
	}

	public List<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(List<Challenge> challenges) {
		this.challenges = challenges;
	}


}
