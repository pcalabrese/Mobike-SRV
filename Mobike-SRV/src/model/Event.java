/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Paolo
 */
@Entity
@Table(name = "events")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByName", query = "SELECT e FROM Event e WHERE e.name = :name"),
    @NamedQuery(name = "Event.findByStartdate", query = "SELECT e FROM Event e WHERE e.startdate = :startdate"),
    @NamedQuery(name = "Event.findByStartlocation", query = "SELECT e FROM Event e WHERE e.startlocation = :startlocation"),
    @NamedQuery(name = "Event.findByCreationdate", query = "SELECT e FROM Event e WHERE e.creationdate = :creationdate")})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonView({Views.EventGeneralView.class, Views.UserEventRouteView.class})
    @Id
    @TableGenerator(name="eventGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="EVENT_ID",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="eventGen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @JsonView({Views.EventGeneralView.class, Views.UserEventRouteView.class})
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @JsonView(Views.EventDetailView.class)
    @Lob
    @Column(name = "description")
    private String description;
    
    @JsonView(Views.EventGeneralView.class)
    @Basic(optional = false)
    @Column(name = "startdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    
    @JsonView(Views.EventGeneralView.class)
    @Column(name = "startlocation")
    private String startlocation;
    
    @JsonView({Views.EventDetailView.class, Views.UserEventRouteView.class})
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    
    @JsonView(Views.EventDetailView.class)
    @JoinTable(name = "users_participatein_events", joinColumns = {
        @JoinColumn(name = "events_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersAccepted;
    
    @JsonView(Views.EventDetailView.class)
    @JoinTable(name = "users_invitedin_events", joinColumns = {
        @JoinColumn(name = "events_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersInvited;
    
    @JsonView(Views.EventDetailView.class)
    @JoinTable(name = "users_refused_events", joinColumns = {
        @JoinColumn(name = "events_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersRefused;
    
    @JsonView(Views.EventGeneralView.class)
    @Transient
    private int acceptedSize;
    
    @JsonView(Views.EventGeneralView.class)
    @Transient
    private int invitedSize;
    
    @JsonView(Views.EventGeneralView.class)
    @Transient
    private int refusedSize;
    
    @JsonView(Views.EventGeneralView.class)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User owner;
    
    @JsonView(Views.EventDetailView.class)
    @JoinColumn(name = "routes_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Route route;

    public Event() {
    }

    public Event(Long id) {
        this.id = id;
    }

    public Event(Long id, String name, Date startdate) {
        this.id = id;
        this.name = name;
        this.startdate = startdate;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public String getStartlocation() {
        return startlocation;
    }

    public void setStartlocation(String startlocation) {
        this.startlocation = startlocation;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    @XmlTransient
    public List<User> getUsersAccepted() {
        return usersAccepted;
    }

    public void setUsersAccepted(List<User> usersAccepted) {
        this.usersAccepted = usersAccepted;
    }

    @XmlTransient
    public List<User> getUsersInvited() {
        return usersInvited;
    }

    public void setUsersInvited(List<User> usersInvited) {
        this.usersInvited = usersInvited;
    }

    @XmlTransient
    public List<User> getUserRefused() {
        return usersRefused;
    }

    public void setUserRefused(List<User> userRefused) {
        this.usersRefused = userRefused;
    }

    /**
	 * @return the acceptedSize
	 */
	public int getAcceptedSize() {
		return acceptedSize;
	}

	/**
	 * @param acceptedSize the acceptedSize to set
	 */
	public void setAcceptedSize(int acceptedSize) {
		this.acceptedSize = acceptedSize;
	}
	
	public void setAcceptedSize(){
		this.acceptedSize = this.usersAccepted.size();
	}
	
	/**
	 * @return the invitedSize
	 */
	public int getInvitedSize() {
		return invitedSize;
	}

	/**
	 * @param invitedSize the invitedSize to set
	 */
	public void setInvitedSize(int invitedSize) {
		this.invitedSize = invitedSize;
	}

	public void setInvitedSize(){
		this.invitedSize = this.usersInvited.size();
	}
	
	/**
	 * @return the refusedSize
	 */
	public int getRefusedSize() {
		return refusedSize;
	}

	/**
	 * @param refusedSize the refusedSize to set
	 */
	public void setRefusedSize(int refusedSize) {
		this.refusedSize = refusedSize;
	}
	
	public void setRefusedSize(){
		this.refusedSize = this.usersRefused.size();
	}
	
	public void setAllUsersSizes(){
		this.acceptedSize = this.usersAccepted.size();
		this.invitedSize = this.usersInvited.size();
		this.refusedSize = this.usersRefused.size();
	}

	public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Event[ id=" + id + " ]";
    }
    
}
