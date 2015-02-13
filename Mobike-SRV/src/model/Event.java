package model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.math.BigInteger;


/**
 * The persistent class for the events database table.
 * 
 */
@Entity
@Table(name="events")
@NamedQuery(name="Event.findAll", query="SELECT e FROM Event e")
public class Event implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String eventID;

	private BigInteger creatorId;

	private String description;

	private String name;

	private BigInteger routeId;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	private String startLocation;

	private Timestamp startTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
	private List<User> participantsId;

	public Event() {
	}

	public String getEventID() {
		return this.eventID;
	}

	public void setEventID(String eventID) {
		this.eventID = eventID;
	}

	public BigInteger getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(BigInteger creatorId) {
		this.creatorId = creatorId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getRouteId() {
		return this.routeId;
	}

	public void setRouteId(BigInteger routeId) {
		this.routeId = routeId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartLocation() {
		return this.startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}