package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the routes database table.
 * 
 */
@Entity
@Table(name="routes")
@NamedQuery(name="Route.findAll", query="SELECT r FROM Route r")
public class Route implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="routeGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="ROUTE_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="routeGen")
	@Column(unique=true, nullable=false)
	private long id;

	@Column(nullable=false)
	private String creatorEmail;

	@Column(nullable=false, length=160)
	private String description;

	private long duration;

	private double length;

	@Column(nullable=false, length=50)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;

	@Column(nullable=false, length=160)
	private String url;
	
	@Transient
	private String gpxString;

	public Route() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatorEmail() {
		return this.creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDuration() {
		return this.duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public double getLength() {
		return this.length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * @return the gpxString
	 */
	public String getGpxString() {
		return gpxString;
	}

	/**
	 * @param gpxString the gpxString to set
	 */
	public void setGpxString(String gpxString) {
		this.gpxString = gpxString;
	}

	public String toString(){
		return "Route [id=" + id + ", creatorEmail=" + creatorEmail + ", description=" + description +
				", duration=" + duration + ", length=" + length +", name=" + name + ", uploadDate=" + uploadDate +
				", url=" + url + ", gpxString=" + gpxString + "]";
	}

}