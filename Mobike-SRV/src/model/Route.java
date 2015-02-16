package model;

import java.io.Serializable;

import javax.persistence.*;

import com.google.gson.annotations.Expose;

import java.util.Date;


/**
 * The model class with JPA annotation for Routes database table.
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
	@Expose
	private long id;
	
	@Column(nullable=false, length=50)
	@Expose
	private String name;
	
	@Column(nullable=false)
	@Expose
	private String creatorEmail;
	
	/*@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USERID")
	private User creatorUser; */

	@Expose
	private long duration;
	@Expose
	private double length;

	@Temporal(TemporalType.TIMESTAMP)
	@Expose
	private Date uploadDate;
	
	@Column(nullable=false, length=160)
	@Expose
	private String description;
	
	@Column(nullable=false, length=160)
	private String url;
	
	@Transient
	@Expose
	private String gpxString;
	
	@Column(length=50)
	@Expose
	private String type;
	
	@Column
	@Expose
	private int difficulty;
	
	@Column
	@Expose
	private int rating;
	
	@Column
	@Expose
	private int bends;
	
	@Column
	@Expose
	private int ratingnumber;
	
	
	
	
	

	/**
	 * default empty Route Constructor
	 */
	public Route() {
	}

	/**
	 * @return id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return creatorEmail
	 */
	public String getCreatorEmail() {
		return this.creatorEmail;
	}

	/*
	public User getCreatorUser() {
		return creatorUser;
	}

	 
	public void setCreatorUser(User creatorUser) {
		this.creatorUser = creatorUser;
	}
	*/

	/**
	 * @param creatorEmail
	 */
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	/**
	 * @return description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Duration
	 */
	public long getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 */
	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * @return Length
	 */
	public double getLength() {
		return this.length;
	}

	/**
	 * @param length
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return Name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Date
	 */
	public Date getUploadDate() {
		return this.uploadDate;
	}

	/**
	 * @param uploadDate
	 */
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	/**
	 * @return url the Url String
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url
	 */
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

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the bends
	 */
	public int getBends() {
		return bends;
	}

	/**
	 * @param bends the bends to set
	 */
	public void setBends(int bends) {
		this.bends = bends;
	}

	/**
	 * @return the ratingnumber
	 */
	public int getRatingnumber() {
		return ratingnumber;
	}

	/**
	 * @param ratingnumber the ratingnumber to set
	 */
	public void setRatingnumber(int ratingnumber) {
		this.ratingnumber = ratingnumber;
	}

	public String toString(){
		return "Route [id=" + id + ", name=" + name + ", creatorEmail=" + creatorEmail + ", duration=" + duration + ", length=" + length +", uploadDate=" + uploadDate +
				", description=" + description +", url=" + url + ", gpxString=" + gpxString + ", type=" + type + ", difficulty=" + difficulty + ", rating=" + rating + ", bends=" + bends + ", ratingnumber=" + ratingnumber + "]";
	}

}