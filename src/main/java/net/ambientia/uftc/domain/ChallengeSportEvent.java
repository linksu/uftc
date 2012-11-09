package net.ambientia.uftc.domain;

import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;


@Entity
@Table(name = "CHALLENGESPORTEVENT")
public class ChallengeSportEvent {
		
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "challengeId", nullable = false)
    private Challenge challenge;
	
	@Column(name = "TITLE", length = 50, nullable = false)
	private String title;
	
	@Column(name = "POINTFACTORTYPE")
	@Enumerated(EnumType.STRING)
	private PointFactorType pointFactorType = PointFactorType.Hours;
	
	@Column(name = "POINTFACTOR", nullable = false)
	private Integer pointFactor = new Integer(0);
	
	@Version
	@Column(name = "OPTLOCK")
	private Integer version;
	
	public enum FieldTypes{
		title,pointFactorType,pointFactor,challenge
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Challenge getChallenge() {
		return challenge;
	}

	public void setChallenge(Challenge challenge) {
		this.challenge = challenge;
	}
	
	public EnumSet<FieldTypes> validate() {
		EnumSet<FieldTypes> fieldTypeErrorList = EnumSet.noneOf(FieldTypes.class);
		if(challenge != null)
			fieldTypeErrorList.add(FieldTypes.challenge);
		return fieldTypeErrorList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PointFactorType getPointFactorType() {
		return pointFactorType;
	}

	public void setPointFactorType(PointFactorType pointFactorType) {
		this.pointFactorType = pointFactorType;
	}

	public Integer getPointFactor() {
		return pointFactor;
	}

	public void setPointFactor(Integer pointFactor) {
		this.pointFactor = pointFactor;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
