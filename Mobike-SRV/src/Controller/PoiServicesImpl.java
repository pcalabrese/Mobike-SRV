package Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Poi;
import model.Views;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import persistence.PoiRepository;
import persistence.exception.PersistenceException;
import persistence.exception.UncheckedPersistenceException;
import persistence.mysql.PoiMySQL;
import utils.Authenticator;
import utils.Crypter;
import utils.Wrapper;

@Path("/pois")
public class PoiServicesImpl implements PoiServices {
	protected PoiRepository poiRep;

	public PoiServicesImpl() {
		poiRep = new PoiMySQL();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/createlist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response createPoiList(String cryptedJson){
		
		if (cryptedJson != null) {
			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(cryptedJson);
			} catch (Exception e1) {
				e1.printStackTrace();

			}

			if (map.containsKey("pois") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				Crypter crypter = new Crypter();

				List<Poi> pois = null;

				try {
					pois = (List<Poi>) mapper.readValue(
							crypter.decrypt(map.get("pois")), List.class);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boolean authorized = false;

				try {
					boolean check = true;
					for(int i = 0; i< pois.size() && check;i++){
					authorized = auth.isAuthorized(pois.get(i).getOwner().getId(), map.get("user"));
					check = authorized;
					}
					
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {

					try {
						for(Poi p : pois)
							poiRep.addPoi(p);
						
					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error adding Poi to the Database");
					}

					return Response.ok().build();
				} else {
					return Response.status(401).build();
				}

			} else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}
		
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response createPoi(String cryptedJson) {
		
		if (cryptedJson != null) {
			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(cryptedJson);
			} catch (Exception e1) {
				e1.printStackTrace();

			}

			if (map.containsKey("poi") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				Crypter crypter = new Crypter();

				Poi poi = null;

				try {
					poi = mapper.readValue(
							crypter.decrypt(map.get("poi")), Poi.class);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boolean authorized = false;

				try {
					authorized = auth.isAuthorized(poi.getOwner().getId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {

					try {
						poiRep.addPoi(poi);
						
					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error adding Poi to the Database");
					}

					return Response.ok().build();
				} else {
					return Response.status(401).build();
				}

			} else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}
		
	}

	@Override
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePoi(String cryptedJson) {
		if (cryptedJson != null) {

			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(cryptedJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (map.containsKey("poi") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				Crypter crypter = new Crypter();

				Poi poi = null;

				try {
					poi = mapper.readValue(
							crypter.decrypt(map.get("poi")), Poi.class);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				boolean authorized = false;
				try {
					authorized = auth.isAuthorized(poi.getOwner().getId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {
					try {
						
						poiRep.removePoiFromId(poi.getId());
					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error Removing Poi from DB");
					}

					return Response.ok().build();
				} else {
					return Response.status(401).build();
				}

			} else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Override
	public Response updatePoi(String cryptedJson) {
		if (cryptedJson != null) {
			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(cryptedJson);
			} catch (Exception e1) {
				e1.printStackTrace();

			}

			if (map.containsKey("poi") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				Crypter crypter = new Crypter();

				Poi poi = null;

				try {
					poi = mapper.readValue(
							crypter.decrypt(map.get("poi")), Poi.class);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boolean authorized = false;

				try {
					authorized = auth.isAuthorized(poi.getOwner().getId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {

					try {
						poiRep.updatePoi(poi);
					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error adding Poi to the Database");
					}

					return Response.ok().build();
				} else {
					return Response.status(401).build();
				}

			} else {
				return Response.status(400).build();
			}
		} else {
			return Response.status(400).build();
		}
	}

	@GET
	@Path("/retrieveall")
	@Produces(MediaType.APPLICATION_JSON)
	@Override
	public Response getAllPois() {
		List<Poi> allPois = null;
		poiRep = new PoiMySQL();
		try {
			allPois = poiRep.getAllPois();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (!(allPois.isEmpty())) {
			
			ObjectMapper mapper = new ObjectMapper();
			
			mapper.setConfig(mapper.getSerializationConfig().withView(
					Views.PoiGeneralView.class));
			mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

			String json = null;
			try {
				json = mapper.writeValueAsString(allPois);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			Crypter crypter = new Crypter();
			String cryptedJson = null;
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("pois", cryptedJson);
			Wrapper wrapper = new Wrapper();
			String jsonOutput = null;

			try {
				jsonOutput = wrapper.wrap(map);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return Response.ok(jsonOutput, MediaType.APPLICATION_JSON).build();

		} else {
			return Response.status(404).build();
		}
	}

}
