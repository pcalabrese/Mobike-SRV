package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="userGen",table="sequence_table",pkColumnName="SEQ_NAME",valueColumnName="SEQ_COUNT",pkColumnValue="USER_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="userGen")
	@Column(unique=true, nullable=false)
	private long id;

	@Column(nullable=false, length=50)
	private String email;

	@Column(nullable=false, length=50)
	private String name;

	@Column(nullable=false, length=50)
	private String password;

	@Column(length=50)
	private String surname;

	@Column(nullable=false, length=50)
	private String username;
	
	@Transient
	private List<Route> Routes;

	public User() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return Routes;
	}

	/**
	 * @param routes the routes to set
	 */
	public void setRoutes(List<Route> routes) {
		Routes = routes;
	}

}