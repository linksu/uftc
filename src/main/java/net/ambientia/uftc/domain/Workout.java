package net.ambientia.uftc.domain;

import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;


@Entity
@Table(name = "WORKOUT")
public class Workout {
	
//	@Column
//	private String sportEvent;
	
	@Id
	@Column(name = "workoutId")
	@GeneratedValue
	private Integer id;
	
	@Column(name = "REPETITION", nullable = false)
	private Integer repetition;
		
	@ManyToOne
	@JoinColumn(name="userId",nullable=false)
    private User user;
	
	@Version
	private Integer version;
	
	@ManyToOne
	@JoinColumn(name = "challengeSportEventId",nullable=false)
	private ChallengeSportEvent challengeSportEventId;
	
	@Transient
	private Integer points;
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	public enum FieldTypes{
		repetition,name,user
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRepetition() {
		return repetition;
	}

	public void setRepetition(Integer repetition) {
		this.repetition = repetition;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public EnumSet<FieldTypes> validate() {
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet.noneOf(FieldTypes.class);
		if(repetition == null)
			fieldTypeErrorList.add(FieldTypes.repetition);
		if(user == null)
			fieldTypeErrorList.add(FieldTypes.user);
		return fieldTypeErrorList;
	}

	public ChallengeSportEvent getChallengeSportEventId() {
		return challengeSportEventId;
	}

	public void setChallengeSportEventId(ChallengeSportEvent challengeSportEventId) {
		this.challengeSportEventId = challengeSportEventId;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
}
