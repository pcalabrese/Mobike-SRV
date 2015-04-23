package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * The persistent class for the pois database table.
 * 
 */
@Entity
@Table(name="pois")
@NamedQuery(name="Poi.findAll", query="SELECT p FROM Poi p")
public class Poi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="poiGen", table="sequence_table", pkColumnName="SEQ_NAME", valueColumnName="SEQ_COUNT", pkColumnValue="POI_ID", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator = "poiGen")
	@Basic(optional = false)
	@Column(name = "id")
	@JsonView({Views.PoiGeneralView.class, Views.ItineraryDetailView.class})
	private Long id;

	@JsonView({Views.PoiGeneralView.class, Views.ItineraryDetailView.class})
	@Column(name= "lat")
	private double lat;

	@JsonView({Views.PoiGeneralView.class, Views.ItineraryDetailView.class})
	@Column(name = "lon")
	private double lon;
	
	@JsonView({Views.PoiDetailView.class})
	@JoinTable(name = "routes_has_pois", joinColumns = {
			@JoinColumn(name = "pois_id", referencedColumnName = "id")}, inverseJoinColumns = {
			@JoinColumn(name = "routes_id", referencedColumnName = "id")})
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Route> routesAssociated;

	@JsonView({Views.PoiGeneralView.class, Views.ItineraryDetailView.class})
	@Lob
	@Column(name = "title")
	private String title;
	
	@JsonView({Views.PoiGeneralView.class, Views.ItineraryDetailView.class})
	@Lob
	@Column(name = "type")
	private String type;
	
	@JsonView({Views.PoiGeneralView.class, Views.ItineraryDetailView.class})
	@JoinColumn(name = "users_id", referencedColumnName = "id")
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User owner;

	public Poi() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLat() {
		return this.lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return this.lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	 * @return the routesAssociated
	 */
	public List<Route> getRoutesAssociated() {
		return routesAssociated;
	}

	/**
	 * @param routesAssociated the routesAssociated to set
	 */
	public void setRoutesAssociated(List<Route> routesAssociated) {
		this.routesAssociated = routesAssociated;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

}