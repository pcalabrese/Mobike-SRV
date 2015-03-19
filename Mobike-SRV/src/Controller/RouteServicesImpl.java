package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistence.RouteRepository;
import persistence.UserRepository;
import persistence.exception.*;
import persistence.mysql.RouteMySQL;
import persistence.mysql.UserMySQL;
import persistence.fs.*;
import utils.Crypter;
import model.Route;
import model.Views;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * RESTful Route Endpoint
 * @author Paolo, Bruno, Marco, Andrea
 * @version 3.0
 * @see RouteServices RouteServices: Interface implemented by this class
 * @see RouteMySQL RouteMySQL: SQL DAO Class
 *
 */
@Path("/routes")
	public class RouteServicesImpl implements RouteServices {
		protected RouteRepository routeRep;
		
		/** default RouteServicesImpl Constructor **/
		public RouteServicesImpl(){
			routeRep = new RouteMySQL();
		}
		
		/**
		 * Method to add a Route to persistence and create gpx file in the server filesystem
		 * @param json JSON file containing the Route Details formatted in this way: Route [id=" + id + ", name=" + name + ", creatorEmail=" + creatorEmail + ", duration=" + duration + ", length=" + length +", uploadDate=" + uploadDate +", description=" + description +", url=" + url + ", gpxString=" + gpxString + "]";
		 * @return String containing the ID of the created route
		 * @see Route Route: Model Class
		 * @version 3.1
		 * 
		 */
		@SuppressWarnings("unchecked")
		@POST
		@Path("/create")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public Response createRoute(String wrappingJson){
			
			if(wrappingJson!=null){
				ObjectMapper mapper = new ObjectMapper();
				Map<String,String> map = null;
				
				try {
					map = (Map<String,String>) mapper.readValue(wrappingJson, Map.class);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String cryptedJson = null;
				cryptedJson = map.get("route");
				
				Crypter crypter = new Crypter();
				
				String json = null;
				try {
					json = crypter.decrypt(cryptedJson);
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				Route route = null;
				
				try {
					route = mapper.readValue(json, Route.class);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				UserRepository userRepo= new UserMySQL();
				boolean exists = false;
				try {
					exists = userRepo.userExists(route.getOwner().getId(), route.getOwner().getNickname());
				} catch (PersistenceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(exists){
							
					String gpxString = route.getGpxString();
					String url = null;
					long insertedId = -1;
					
					try {
						RouteIO writer = new GpxIO();
						url = writer.write(gpxString, route.getName());
					}
					
					catch (Exception e){
						throw new UncheckedFilesystemException("Error saving file to filesystem" + e.getMessage(),e);
					}
					
					if(url != null){
						route.setUrl(url);
						try {
							insertedId = routeRep.addRoute(route);
							
						}
						catch (Exception e){
							throw new UncheckedPersistenceException("Error adding route to database" + e.getMessage(),e);
						}
					}
					else {
						throw new UncheckedFilesystemException("GPX Url not reachable");
					}
					
					String outputId = ""+insertedId+"";
					return Response.ok(outputId,MediaType.TEXT_PLAIN).build();
				}
				else{
					
					return Response.status(401).build();
				}
				
			}
			else{
				return Response.status(400).build();
			}
		}
		
		/**
		 * Method to retrieve a Route by the id
		 * @param id Long ID of the requested Route
		 * @return Response OK: json formatted Route, FAIL: Error404
		 * @see Route Route: Model Class
		 * @see RouteMySql RouteMySql: SQL DAO Class
		 * @see GpxIO GpxIO: class to read and write from and to file in the server filesystem
		 * @see Views.ItineraryDetailView Views.ItineraryDetailView: View for detailed information about Route
		 * @version 3.1
		 * 
		 */
		@GET
		@Path("/retrieve/{Id}")
		@Produces(MediaType.APPLICATION_JSON)
		@Override
		public Response getRoute(@PathParam("Id") Long id) {
			
			
			String json = null;
			Route route = null;
			RouteRepository routeREP = new RouteMySQL();
			ObjectMapper objectMapper = new ObjectMapper();
			String gpxString;
			RouteIO fsrepo = new GpxIO();
			
			//take route from db
			try{
			route = routeREP.routeFromId(id);
			}
			catch(Exception e){
				throw new UncheckedPersistenceException("Error accessing routes database", e);
			}
			
			// check route!=null -> id exists in the DB
			if(route!=null){
			//take gpxString from file on server
				try{
				gpxString = fsrepo.read(route.getUrl().substring(8));
				}	catch (Exception e){
						throw new UncheckedFilesystemException("Error reading gpx file",e);
					}
			
			//set the gpxString in the Route bean
			route.setGpxString(gpxString);
			
			//set the view to return just the json fields for the visualization and exclude the default view
			objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.ItineraryDetailView.class));
			objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);

			//starting JSON serialization
				try {
				json = objectMapper.writerWithView(Views.ItineraryDetailView.class).writeValueAsString(route);
				} 	catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
					}
			
			Crypter crypter = new Crypter();
			String cryptedJson = null;
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("route", cryptedJson);
			
			objectMapper = new ObjectMapper();
			String outputJson = null;
			try {
				outputJson = objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return Response.ok(outputJson, MediaType.APPLICATION_JSON).build();
			}
			// route=null -> id doesnt exist in the DB
			else {
				return Response.status(404).build();
			}

		}

		/**
		 * Method to retrieve a Route by the id
		 * @param id Long ID
		 * @return Response OK: json formatted Routes, FAIL: Error404
		 * @see Views.ItineraryGeneralView Views.ItineraryGeneralView: General View to visualize a list of Routes
		 * @version 3.1
		 * 
		 */
		@GET
		@Path("/retrieveall")
		@Produces(MediaType.APPLICATION_JSON)
		@Override
		public Response retrieveAllRoutes() {
			
			List<Route> allRoutes = null;
			ObjectMapper objectMapper = new ObjectMapper();
			String json = null;
			
			try {
				allRoutes = routeRep.getAllRoutes();
			} catch (Exception e) {
				throw new UncheckedPersistenceException("Error accessing route database",e);
			}
			// check if allRoutes has been populated, with isEmpty -> return 404
			if(!(allRoutes.isEmpty())){
			
			//set the view to return just the json fields for the visualization and exclude the default view
			objectMapper.setConfig(objectMapper.getSerializationConfig().withView(Views.ItineraryGeneralView.class));
			objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
			
				try {
					json = objectMapper.writerWithView(Views.ItineraryGeneralView.class).writeValueAsString(allRoutes);
				} 	catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();					
					}
			
			Crypter crypter = new Crypter();
			String cryptedJson = null;
			try {
				cryptedJson = crypter.encrypt(json);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("routes", cryptedJson);
			objectMapper = new ObjectMapper();
			String outputJson = null;
			
			try {
				outputJson = objectMapper.writeValueAsString(map);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return Response.ok(outputJson, MediaType.APPLICATION_JSON).build(); 
			}
			
			else {
				return Response.status(404).build();
			}
			
		}

		/**
		 * Method to retrieve the .gpx file by the Route id
		 * @param id Long ID
		 * @return gpxString String containing gpx-formatted infos
		 * @see GpxIO GpxIO: class to read and write from and to file in the server filesystem
		 * @version 2.1
		 * 
		 */
		@Override
		@GET
		@Path("/retrieve/{Id}/gpx")
		@Produces(MediaType.APPLICATION_XML)
		public String getRoutegpx(@PathParam("Id") Long id) {
			String gpxString;
			RouteIO fsrepo = new GpxIO();
			Route r = null;
			try {
				r = routeRep.routeFromId(id);
			}
			catch (Exception e) {
				throw new UncheckedPersistenceException("Error accessing route database",e);
			}
			try{
				gpxString = fsrepo.read(r.getUrl().substring(8));
				
			}
			catch (Exception e){
				throw new UncheckedFilesystemException("Error reading gpx file",e);
			}
			return gpxString;
			
		}
}
