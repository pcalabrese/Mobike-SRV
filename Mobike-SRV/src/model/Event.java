package model;

import java.io.Serializable;

import javax.persistence.*;

import com.google.gson.annotations.Expose;


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
	private long id;

	@Expose
	private long creatorId;
	
	@Expose
	private String description;

	@Expose
	private String name;

	@Expose
	private long routeId;


	@Expose
	private Timestamp startDate;

	@Expose
	private String startLocation;

	@Expose
	private Timestamp creationDate;
	
	@Transient
	private List<User> participantsId;

	public Event() {
	}

	public long getid() {
		return this.id;
	}

	public void setid(long id) {
		this.id = id;
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

	public Timestamp getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public String getStartLocation() {
		return this.startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	public String toString(){
		return "Event [id=" + id + ", name=" + name + ", creatorId=" + creatorId + ", description=" + description + ", routeId=" + routeId +", startDate=" + startDate +
				", startLocation=" + startLocation +", creationDate=" + creationDate + "]";
	}

}