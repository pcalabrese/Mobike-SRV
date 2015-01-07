package Controller;

import java.util.List;

import persistence.RouteRepository;
import persistence.exception.*;
import persistence.mysql.RouteMySQL;
import persistence.fs.*;
import model.Route;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;


@Path("/routes")
	public class ControllerMainImpl implements ControllerMain {
		protected RouteRepository routeRep;
		
		/** default Controller Constructor **/
		public ControllerMainImpl(){
			routeRep = new RouteMySQL();
		}
		
		/** @author Paolo
		 *   method to add a Route to persistence and filesystem, creation of gpx file **/
		@POST
		@Path("/create")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String createRoute(String json){
				Gson gson = new GsonBuilder().create();
				Route r = gson.fromJson(json, Route.class);
				String gpxString = r.getGpxString();
				String url = null;
				long insertedId = -1;
				
				try {
					RouteIO writer = new GpxIO();
					url = writer.write(gpxString, r.getName());
				}
			
				catch (Exception e){
					throw new UncheckedFilesystemException("Error saving file to filesystem",e);
				}
			
				if(url != null){
					r.setUrl(url);
					try {
						insertedId = routeRep.addRoute(r);
					}
					catch (Exception e){
						throw new UncheckedPersistenceException("Error adding route to database", e);
					}
				}
				else {
					throw new UncheckedFilesystemException("GPX Url not reachable");
				}
				return "" +insertedId +"";
			
		}
		
		@GET
		@Path("/retrieve/{Id}")
		@Produces(MediaType.APPLICATION_JSON)
		@Override
		public String getRoute(@PathParam("Id") Long id) {
			System.out.println(id);
			Route route = null;
			RouteRepository routeREP = new RouteMySQL();
			try{
			route = routeREP.routeFromId(id);
			}
			catch(Exception e){
				throw new UncheckedPersistenceException("Error accessing routes database");
			}
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			System.out.println(route.getUrl());
			return gson.toJson(route, Route.class);
		}

		@GET
		@Path("/retrieveall")
		@Produces(MediaType.APPLICATION_JSON)
		@Override
		public String retrieveAllRoutes() {
			
			List<Route> allRoutes = null;
			try {
				allRoutes = routeRep.getAllRoutes();
			} catch (Exception e) {
				throw new UncheckedPersistenceException("Error accessing route database",e);
			}
			
			Gson gson = new GsonBuilder().create();
			
			return gson.toJson(allRoutes, List.class);
		}

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
