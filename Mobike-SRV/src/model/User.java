package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.google.gson.annotations.Expose;


/**
 * The model class with JPA annotation for Users database table.
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
	@Expose
	private long id;
	
	@Expose
	@Column(nullable=false, length=50)
	private String email;

	@Expose
	@Column(nullable=false, length=50)
	private String name;

	//@Column(nullable=false, length=50)
	@Transient
	private String password;

	@Expose
	@Column(nullable=false, length=50)
	private String surname;

	//@Column(nullable=false, length=50)
	@Transient
	private String username;
	
	/**@Transient
	 * 
	 */
	//@OneToMany(mappedBy="creatorUser")
	@Transient
	private List<Route> Routes;

	/**
	 * default empty User Constructor
	 */
	public User() {
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
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return name
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
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * @param username
	 */
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
	 * @param List<Route> the routes to set
	 */
	public void setRoutes(List<Route> routes) {
		Routes = routes;
	}

}