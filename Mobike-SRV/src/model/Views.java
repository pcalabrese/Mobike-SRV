package model;

public class Views {

	public interface ItineraryGeneralView { }
	
	public interface ItineraryDetailView extends ItineraryGeneralView { } 
	
	public interface EventGeneralView { } 
	
	public interface EventDetailView extends EventGeneralView { } 
	
	public interface UserGeneralView { }
	
	public interface UserEventRouteView extends UserGeneralView { }
	
	public interface UserDetailView extends UserEventRouteView { } 
	
	public interface PoiGeneralView { }
	
	public interface PoiDetailView extends PoiGeneralView { }
	
	
}
