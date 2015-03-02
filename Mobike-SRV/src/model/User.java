/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Paolo
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findBySurname", query = "SELECT u FROM User u WHERE u.surname = :surname"),
    @NamedQuery(name = "User.findByNickname", query = "SELECT u FROM User u WHERE u.nickname = :nickname"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByBikemodel", query = "SELECT u FROM User u WHERE u.bikemodel = :bikemodel")})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public interface UserNicknameView { }
    
    public interface UserPrivateView { }
    
    public interface UserPublicView { } 
    
    @Id
    @TableGenerator(name="userGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="USER_ID",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE, generator="userGen")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "surname")
    private String surname;
    
    @Basic(optional = false)
    @Column(name = "nickname")
    private String nickname;
    
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    
    @Lob
    @Column(name = "imgurl")
    private String imgurl;
    
    @Column(name = "bikemodel")
    private String bikemodel;
    
    @ManyToMany(mappedBy = "usersRefused", fetch = FetchType.EAGER)
    private List<Club> clubsRefused;
    
    @ManyToMany(mappedBy = "usersAccepted", fetch = FetchType.EAGER)
    private List<Club> clubsAccepted;
    
    @ManyToMany(mappedBy = "usersInvited", fetch = FetchType.EAGER)
    private List<Club> clubsInvited;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Club> clubsOwned;
    
    @ManyToMany(mappedBy = "usersRefused", fetch = FetchType.EAGER)
    private List<Event> eventsRefused;
    
    @ManyToMany(mappedBy = "usersAccepted", fetch = FetchType.EAGER)
    private List<Event> eventsAccepted;
    
    @ManyToMany(mappedBy = "usersInvited", fetch = FetchType.EAGER)
    private List<Event> eventsInvited;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Event> eventsOwned;
   
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Route> routeList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Review> reviewList;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String name, String surname, String nickname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getBikemodel() {
        return bikemodel;
    }

    public void setBikemodel(String bikemodel) {
        this.bikemodel = bikemodel;
    }

    @XmlTransient
    public List<Club> getClubsRefused() {
        return clubsRefused;
    }

    public void setClubsRefused(List<Club> clubsRefused) {
        this.clubsRefused = clubsRefused;
    }

    @XmlTransient
    public List<Event> getEventsRefused() {
        return eventsRefused;
    }

    public void setEventsRefused(List<Event> eventsRefused) {
        this.eventsRefused = eventsRefused;
    }

    @XmlTransient
    public List<Event> getEventsAccepted() {
        return eventsAccepted;
    }

    public void setEventsAccepted(List<Event> eventsAccepted) {
        this.eventsAccepted = eventsAccepted;
    }

    @XmlTransient
    public List<Event> getEventsInvited() {
        return eventsInvited;
    }

    public void setEventsInvited(List<Event> eventsInvited) {
        this.eventsInvited = eventsInvited;
    }

    @XmlTransient
    public List<Club> getClubsAccepted() {
        return clubsAccepted;
    }

    public void setClubsAccepted(List<Club> clubsAccepted) {
        this.clubsAccepted = clubsAccepted;
    }

    @XmlTransient
    public List<Club> getClubsInvited() {
        return clubsInvited;
    }

    public void setClubsInvited(List<Club> clubsInvited) {
        this.clubsInvited = clubsInvited;
    }

    @XmlTransient
    public List<Route> getRouteList() {
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    @XmlTransient
    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @XmlTransient
    public List<Club> getClubsOwned() {
        return clubsOwned;
    }

    public void setClubsOwned(List<Club> clubsOwned) {
        this.clubsOwned = clubsOwned;
    }

    @XmlTransient
    public List<Event> getEventsOwned() {
        return eventsOwned;
    }

    public void setEventsOwned(List<Event> eventsOwned) {
        this.eventsOwned = eventsOwned;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.User[ id=" + id + " ]";
    }
    
}
