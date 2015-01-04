package Controller;

import java.util.Date;
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
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/routes")
	public class ControllerMainImpl implements ControllerMain {
		protected RouteRepository routeRep;
		public ControllerMainImpl(){
			routeRep = new RouteMySQL();
		}
		
		@POST
		@Path("/create")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String createRoute(Route route){
				if(route == null){
					return "route nulla";}
				String gpxString = route.getGpxString();
				String url = null;
			try {
				RouteWriter writer = new GpxWriter();
				url = writer.write(gpxString, route.getName());
			}
			
			catch (Exception e){
				throw new UncheckedFilesystemException("Error saving file to filesystem",e);
			}
			
			if(url != null){
				route.setUrl(url);
				try {
					routeRep.addRoute(route);
				}
				catch (Exception e){
					throw new UncheckedPersistenceException("Error adding route to database", e);
				}
			}
			return "route valida";
		}
		
		
		/*
		@POST
		@Path("/create")
		@Consumes(MediaType.APPLICATION_JSON)
		public void createRoute(String name, String description,double length, long duration, Date uploadDate,String creatorEmail, String gpxString) {
			 
			 Route route = new Route();
			 route.setName(name);
			 route.setDescription(description);
			 route.setLength(length);
			 route.setDuration(duration);
			 route.setGpxString(gpxString);
			 route.setCreatorEmail(creatorEmail);
			 route.setUploadDate(uploadDate);
			 String url = null;
			
			
			try {
				RouteWriter writer = new GpxWriter();
				url = writer.write(gpxString, route.getName());
			}
			
			catch (Exception e){
				throw new UncheckedFilesystemException("Error saving file to filesystem",e);
			}
			
			if(url != null){
				route.setUrl(url);
				try {
					routeRep.addRoute(route);
				}
				catch (Exception e){
					throw new UncheckedPersistenceException("Error adding route to database", e);
				}
			}
				
						
		} */

		@Override
		public Route searchRoute(Long id) {
			Route route = null;
			RouteRepository routeREP = new RouteMySQL();
			try{
			route = routeREP.routeFromId(id);
			}
			catch(PersistenceException pex){}
			return route;
		}

		@Override
		public List<Route> retriveAllRoutes() {
			// TODO Auto-generated method stub
			return null;
		}
	}
