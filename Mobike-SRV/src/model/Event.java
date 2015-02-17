package model;

import java.io.Serializable;

import javax.persistence.*;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;


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
	@TableGenerator(name="eventGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="EVENT_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="eventGen")
	@Expose
	private long eventID;

	@Expose
	private long creatorId;
	
	@Expose
	private String description;

	@Expose
	private String name;

	@Expose
	private long routeId;

	@Temporal(TemporalType.DATE)
	@Expose
	private Date startDate;

	@Expose
	private String startLocation;

	@Expose
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	private List<User> participantsId;

	public Event() {
	}

	public long getEventID() {
		return this.eventID;
	}

	public void setEventID(long eventID) {
		this.eventID = eventID;
	}

	public long getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(long creatorId) {
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

	public long getRouteId() {
		return this.routeId;
	}

	public void setRouteId(long routeId) {
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

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}