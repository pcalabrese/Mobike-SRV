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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Paolo
 */
@Entity
@Table(name = "clubs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Club.findAll", query = "SELECT c FROM Club c"),
    @NamedQuery(name = "Club.findById", query = "SELECT c FROM Club c WHERE c.id = :id"),
    @NamedQuery(name = "Club.findByName", query = "SELECT c FROM Club c WHERE c.name = :name"),
    @NamedQuery(name = "Club.findByCreationdate", query = "SELECT c FROM Club c WHERE c.creationdate = :creationdate"),
    @NamedQuery(name = "Club.findByLocation", query = "SELECT c FROM Club c WHERE c.location = :location")})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Club implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @TableGenerator(name="clubGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="CLUB_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="clubGen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Lob
    @Column(name = "description")
    private String description;
    
    @Column(name = "creationdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationdate;
    
    @Column(name = "location")
    private String location;
    
    @JoinTable(name = "users_refused_clubs", joinColumns = {
        @JoinColumn(name = "clubs_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersRefused;
    
    @JoinTable(name = "clubs_has_users", joinColumns = {
        @JoinColumn(name = "clubs_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersAccepted;
    
    @JoinTable(name = "users_invitedin_clubs", joinColumns = {
        @JoinColumn(name = "clubs_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "users_id", referencedColumnName = "id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> usersInvited;
    
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User owner;

    public Club() {
    }

    public Club(Long id) {
        this.id = id;
    }

    public Club(Long id, String name) {
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

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @XmlTransient
    public List<User> getUsersRefused() {
        return usersRefused;
    }

    public void setUsersRefused(List<User> usersRefused) {
        this.usersRefused = usersRefused;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
        if (!(object instanceof Club)) {
            return false;
        }
        Club other = (Club) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Club[ id=" + id + " ]";
    }
    
}
