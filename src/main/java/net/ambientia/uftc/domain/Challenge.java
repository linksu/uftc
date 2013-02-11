package net.ambientia.uftc.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Entity
@Table(name = "CHALLENGE")
public class Challenge {

	@Id
	@Column(name = "challengeId")
	@GeneratedValue
	private Integer id;
	
	@OneToMany(mappedBy="challenge",cascade = {CascadeType.REMOVE, CascadeType.ALL})
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<ChallengeSportEvent> challengeSportEvents = new ArrayList<ChallengeSportEvent>();
	

	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;

	@Column(name = "STARTTIME", nullable = false)
	private Date startTime;

	@Column(name = "ENDTIME", nullable = false)
	private Date endTime;
	
	@Column(name = "TOTALPOINTS", nullable = true)
	private Integer totalPoints;
	
	@Version
	@Column(name = "OPTLOCK")
	private Integer version;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User owner;

	@ManyToOne
	@JoinColumn(name = "uftcId")
	private Uftc uftc;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_challenge", 
		joinColumns = { @JoinColumn(name = "challengeId") }, 
		inverseJoinColumns = { @JoinColumn(name = "userId") })
	private List<User> users;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "challenge_non_approved_users", 
		joinColumns = { @JoinColumn(name = "challengeId") }, 
		inverseJoinColumns = { @JoinColumn(name = "userId") })
	private List<User> notApprovedUsers;
	
	@Transient
	private String startTimeString;
	
	@Transient
	private String endTimeString;

	public enum FieldTypes {
		title, starttime, endtime, challengesportevents, uftc
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeString(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		if(this.startTime == null) {
			return simpleDateFormat.format(new Date());
		}
		else { return simpleDateFormat.format(this.startTime); }
	}
	
	public void setStartTimeString(String startTimeString) throws ParseException {
		this.startTimeString = startTimeString;
		this.startTime = formatDate(startTimeString);
	}

	public String getEndTimeString(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		if(this.endTime == null) {
			return simpleDateFormat.format(new Date());
		}
		else { return simpleDateFormat.format(this.endTime); }
	}
	
	public void setEndTimeString(String endTimeString) throws ParseException {
		this.endTimeString = endTimeString;
		this.endTime = formatDate(endTimeString);
	}
	
	public Uftc getUftc() {
		return uftc;
	}

	public void setUftc(Uftc uftc) {
		this.uftc = uftc;
	}

	public EnumSet<FieldTypes> validate() {
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet
				.noneOf(FieldTypes.class);
		if (title != null && (title.length() > 50 || title.length() < 3) )
			fieldTypeErrorList.add(FieldTypes.title);
		if (startTime == null)
			fieldTypeErrorList.add(FieldTypes.starttime);
		if (endTime == null)
			fieldTypeErrorList.add(FieldTypes.endtime);
		if (challengeSportEvents == null)
			fieldTypeErrorList.add(FieldTypes.challengesportevents);
		if (uftc == null)
			fieldTypeErrorList.add(FieldTypes.uftc);
		return fieldTypeErrorList;
	}
	
	public boolean isActive(){
		
		if(endTime.after(startTime))
			return true;
		else
			return false;
		
	}
	
	private Date formatDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return simpleDateFormat.parse(date);
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public List<ChallengeSportEvent> getChallengeSportEvents() {
		return challengeSportEvents;
	}

	public void setChallengeSportEvents(List<ChallengeSportEvent> challengeSportEvent) {
		this.challengeSportEvents = challengeSportEvent;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<User> getNotApprovedUsers() {
		return notApprovedUsers;
	}

	public void setNotApprovedUsers(List<User> notApprovedUsers) {
		this.notApprovedUsers = notApprovedUsers;
	}
	

}
