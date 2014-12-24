package model;

import java.time.Duration;
import java.util.Date;

public class Path {
	
	private long pathId;
	private String name;
	private String description;
	private double length;
	private Duration duration;
	private Date uploadTime;
	private long userId;
	private String link;
	
	public Path(){
		super();
	}
	
	public Path(int id, String name){
		super();
		this.pathId = id;
		this.name = name;
	}
	
	/**
	 * @param id
	 * @param name
	 * @param description
	 * @param length
	 * @param duration
	 * @param uploadTime
	 */
	public Path(int id, String name, String description, double length,
			Duration duration, Date uploadTime) {
		super();
		this.pathId = id;
		this.name = name;
		this.description = description;
		this.length = length;
		this.duration = duration;
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return pathId;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.pathId = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the length
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return the duration
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	/**
	 * @return the upload Time
	 */
	public Date getUploadTime() {
		return uploadTime;
	}

	/**
	 * @param createdon the upload Time to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	/**
	 * @return the owner userId 
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * @param userId the owner userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	
	
	
	
	
}
