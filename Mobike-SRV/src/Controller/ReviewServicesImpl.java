package Controller;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Review;

import com.fasterxml.jackson.databind.ObjectMapper;

import persistence.ReviewRepository;
import persistence.exception.PersistenceException;
import persistence.mysql.ReviewMySQL;
import utils.Authenticator;
import utils.Crypter;
import utils.Wrapper;

@Path("/reviews")
public class ReviewServicesImpl implements ReviewServices {

	protected ReviewRepository reviewRep;
	public ReviewServicesImpl() {
		reviewRep = new ReviewMySQL();
	}

	@Override
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createReview(String wrappingJson) {
			
		Wrapper wrapper = new Wrapper();
		
		Map<String,String> map = wrapper.unwrap(wrappingJson);
		
		if(map != null){

			if(map.get("review") != null & map.get("user") != null){
				
				Authenticator auth = new Authenticator();
				boolean exists = auth.validateCryptedUser(map.get("user"));
				
				if(exists){
					Crypter crypter = new Crypter();
					String reviewPlainJson = null;
					try {
						reviewPlainJson = crypter.decrypt(map.get("review"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					ObjectMapper mapper = new ObjectMapper();
					Review review = null;
					try {
						review = mapper.readValue(reviewPlainJson, Review.class);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					boolean authorized = auth.isAuthorized(review.getReviewPK().getUsersId(), map.get("user"));
					
					if(authorized){
						try {
							reviewRep.addReview(review);
						} catch (PersistenceException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						return Response.ok().build();
					}
					else{
						return Response.status(401).build();
					}
					
					
				}
				else{
					return Response.status(401).build();
				}
			}
			else {
				return Response.status(400).build();
			}
		}
		else {
			return Response.status(400).build();
		}
		
		
		
		}
	
		


	@Override
	public Response updateReview(String cryptedJson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response removeReview(String cryptedJson) {
		// TODO Auto-generated method stub
		return null;
	}

}
