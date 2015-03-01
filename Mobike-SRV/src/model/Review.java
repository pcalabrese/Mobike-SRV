/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Paolo
 */
@Entity
@Table(name = "reviews")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findById", query = "SELECT r FROM Review r WHERE r.id = :id"),
    @NamedQuery(name = "Review.findByRate", query = "SELECT r FROM Review r WHERE r.rate = :rate"),
    @NamedQuery(name = "Review.findByUsersId", query = "SELECT r FROM Review r WHERE r.reviewPK.usersId = :usersId"),
    @NamedQuery(name = "Review.findByRoutesId", query = "SELECT r FROM Review r WHERE r.reviewPK.routesId = :routesId")})
public class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected ReviewPK reviewPK;
    
    @Basic(optional = false)
    @Column(name = "id")
    private long id;
    
    @Lob
    @Column(name = "message")
    private String message;
    
    @Basic(optional = false)
    @Column(name = "rate")
    private int rate;
    
    @JoinColumn(name = "users_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User owner;
    
    @JoinColumn(name = "routes_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Route route;

    public Review() {
    }

    public Review(ReviewPK reviewPK) {
        this.reviewPK = reviewPK;
    }

    public Review(ReviewPK reviewPK, long id, int rate) {
        this.reviewPK = reviewPK;
        this.id = id;
        this.rate = rate;
    }

    public Review(long usersId, long routesId) {
        this.reviewPK = new ReviewPK(usersId, routesId);
    }

    public ReviewPK getReviewPK() {
        return reviewPK;
    }

    public void setReviewPK(ReviewPK reviewPK) {
        this.reviewPK = reviewPK;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
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
        hash += (reviewPK != null ? reviewPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.reviewPK == null && other.reviewPK != null) || (this.reviewPK != null && !this.reviewPK.equals(other.reviewPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Review[ reviewPK=" + reviewPK + " ]";
    }
    
}
