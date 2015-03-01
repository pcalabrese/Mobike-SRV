/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Paolo
 */

@Embeddable
public class ReviewPK implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "users_id")
    private long usersId;
	
    @Basic(optional = false)
    @Column(name = "routes_id")
    private long routesId;

    public ReviewPK() {
    }

    public ReviewPK(long usersId, long routesId) {
        this.usersId = usersId;
        this.routesId = routesId;
    }

    public long getUsersId() {
        return usersId;
    }

    public void setUsersId(long usersId) {
        this.usersId = usersId;
    }

    public long getRoutesId() {
        return routesId;
    }

    public void setRoutesId(long routesId) {
        this.routesId = routesId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usersId;
        hash += (int) routesId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReviewPK)) {
            return false;
        }
        ReviewPK other = (ReviewPK) object;
        if (this.usersId != other.usersId) {
            return false;
        }
        if (this.routesId != other.routesId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ReviewPK[ usersId=" + usersId + ", routesId=" + routesId + " ]";
    }
    
}
