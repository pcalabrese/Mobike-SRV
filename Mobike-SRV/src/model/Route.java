/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonView;


/**
 *
 * @author Paolo
 */
@Entity
@Table(name = "routes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r"),
    @NamedQuery(name = "Route.findById", query = "SELECT r FROM Route r WHERE r.id = :id"),
    @NamedQuery(name = "Route.findByName", query = "SELECT r FROM Route r WHERE r.name = :name"),
    @NamedQuery(name = "Route.findByUrl", query = "SELECT r FROM Route r WHERE r.url = :url"),
    @NamedQuery(name = "Route.findByLength", query = "SELECT r FROM Route r WHERE r.length = :length"),
    @NamedQuery(name = "Route.findByDuration", query = "SELECT r FROM Route r WHERE r.duration = :duration"),
    @NamedQuery(name = "Route.findByUploaddate", query = "SELECT r FROM Route r WHERE r.uploaddate = :uploaddate"),
    @NamedQuery(name = "Route.findByType", query = "SELECT r FROM Route r WHERE r.type = :type"),
    @NamedQuery(name = "Route.findByDifficulty", query = "SELECT r FROM Route r WHERE r.difficulty = :difficulty"),
    @NamedQuery(name = "Route.findByRating", query = "SELECT r FROM Route r WHERE r.rating = :rating"),
    @NamedQuery(name = "Route.findByRatingnumber", query = "SELECT r FROM Route r WHERE r.ratingnumber = :ratingnumber"),
    @NamedQuery(name = "Route.findByBends", query = "SELECT r FROM Route r WHERE r.bends = :bends"),
    @NamedQuery(name = "Route.findByStartlocation", query = "SELECT r FROM Route r WHERE r.startlocation = :startlocation"),
    @NamedQuery(name = "Route.findByEndlocation", query = "SELECT r FROM Route r WHERE r.endlocation = :endlocation")})
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonView({Views.ItineraryGeneralView.class,Views.EventDetailView.class, Views.UserEventRouteView.class})
    @Id
    @TableGenerator(name="routeGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="ROUTE_ID",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="routeGen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @JsonView({Views.ItineraryGeneralView.class, Views.UserEventRouteView.class})
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @JsonView(Views.ItineraryDetailView.class)
    @Lob
    @Column(name = "description")
    private String description;
    
    
    @Column(name = "url")
    private String url;
    
    
    @JsonView({Views.ItineraryGeneralView.class,Views.EventDetailView.class})
    @Column(name = "length")
    private Double length;
    
    @JsonView({Views.ItineraryGeneralView.class,Views.EventDetailView.class})
    @Column(name = "duration")
    private BigInteger duration;
    
    @JsonView({Views.ItineraryDetailView.class, Views.UserEventRouteView.class})
    @Column(name = "uploaddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploaddate;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "type")
    private String type;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "difficulty")
    private Integer difficulty;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "rating")
    private double rating;

    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "ratingnumber")
    private Integer ratingnumber;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "bends")
    private Integer bends;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "startlocation")
    private String startlocation;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @Column(name = "endlocation")
    private String endlocation;
    
    @JsonView(Views.ItineraryGeneralView.class)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User owner;
    
    @JsonView(Views.ItineraryDetailView.class)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route", fetch = FetchType.EAGER)
    private List<Review> reviewList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route", fetch = FetchType.EAGER)
    private List<Event> eventList;
    
    //@JsonView(Views.ItineraryDetailView.class)
    @Transient
    private String gpxString;
    
    @JsonView({Views.EventDetailView.class, Views.ItineraryGeneralView.class})
    @Transient
    private String imgUrl;
    

    /**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Route() {
    }

    public Route(Long id) {
        this.id = id;
    }

    public Route(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Integer getRatingnumber() {
        return ratingnumber;
    }

    public void setRatingnumber(Integer ratingnumber) {
        this.ratingnumber = ratingnumber;
    }

    public Integer getBends() {
        return bends;
    }

    public void setBends(Integer bends) {
        this.bends = bends;
    }

    public String getStartlocation() {
        return startlocation;
    }

    public void setStartlocation(String startlocation) {
        this.startlocation = startlocation;
    }

    public String getEndlocation() {
        return endlocation;
    }

    public void setEndlocation(String endlocation) {
        this.endlocation = endlocation;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @XmlTransient
    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @XmlTransient
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
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
	
	
	public void calculateRate() {
		double ratesum = 0;
		int ratenum = 0;
		for( Review r : this.reviewList){
			ratesum += r.getRate();
			ratenum++;
		}
		this.rating = ratesum / ratenum ;
		this.ratingnumber = ratenum;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Route[ id=" + id + " ]";
    }
    
}
