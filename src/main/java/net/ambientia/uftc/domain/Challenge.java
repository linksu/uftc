package net.ambientia.uftc.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
	private Calendar startTime = Calendar.getInstance();

	@Column(name = "ENDTIME", nullable = false)
	private Calendar endTime = Calendar.getInstance();
	
	@Column(name = "TOTALPOINTS", nullable = true)
	private Integer totalPoints;
	
	@Version
	@Column(name = "OPTLOCK")
	private Integer version;


	@ManyToOne
	@JoinColumn(name = "uftcId")
	private Uftc uftc;
	
	  @ManyToMany(fetch=FetchType.EAGER)  
	  @JoinTable(name = "user_challenge",    
	    joinColumns = { @JoinColumn(name = "challengeId")},  
	      inverseJoinColumns={@JoinColumn(name="userId")})  
	  	private List<User> users;



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
		return startTime.getTime();
	}

	public void setStartTime(String startTime) throws ParseException {

		this.startTime.setTime(formatDate(startTime));
	}

	public Date getEndTime() {
		return endTime.getTime();
	}

	public void setEndTime(String endTime) throws ParseException {
		this.endTime.setTime(formatDate(endTime));
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
		
		if(endTime.getTimeInMillis()-startTime.getTimeInMillis() > 0)
			return true;
		else
			return false;
		
	}
	
	private Date formatDate(String date) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return simpleDateFormat.parse(date);
	}
	
	public String getStartTimeString(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return simpleDateFormat.format(startTime.getTime());
	}
	public String getEndTimeString(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return simpleDateFormat.format(endTime.getTime());
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	

}
