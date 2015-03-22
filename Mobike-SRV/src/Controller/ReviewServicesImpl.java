package Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import persistence.exception.UncheckedPersistenceException;
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

		if (wrappingJson != null) {
			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(wrappingJson);
			} catch (Exception e1) {
				e1.printStackTrace();

			}

			if (map.containsKey("review") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				Crypter crypter = new Crypter();

				Review review = null;

				try {
					review = mapper.readValue(
							crypter.decrypt(map.get("review")), Review.class);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boolean authorized = false;

				try {
					authorized = auth.isAuthorized(review.getReviewPK()
							.getUsersId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {

					try {
						reviewRep.addReview(review);
					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error adding Review to the Database");
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
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateReview(String wrappingJson) {

		if (wrappingJson != null) {
			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(wrappingJson);
			} catch (Exception e1) {
				e1.printStackTrace();

			}

			if (map.containsKey("review") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				Crypter crypter = new Crypter();

				Review review = null;

				try {
					review = mapper.readValue(
							crypter.decrypt(map.get("review")), Review.class);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				boolean authorized = false;

				try {
					authorized = auth.isAuthorized(review.getReviewPK()
							.getUsersId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {

					try {
						reviewRep.updateReview(review);
					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error adding Review to the Database");
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
	public Response removeReview(String wrappingJson) {

		if (wrappingJson != null) {

			Wrapper wrapper = new Wrapper();
			Map<String, String> map = null;
			try {
				map = wrapper.unwrap(wrappingJson);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (map.containsKey("review") & map.containsKey("user")) {

				Authenticator auth = new Authenticator();
				ObjectMapper mapper = new ObjectMapper();
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				mapper.setDateFormat(dateFormat);
				Crypter crypter = new Crypter();

				Review review = null;

				try {
					review = mapper.readValue(
							crypter.decrypt(map.get("review")), Review.class);
				} catch (Exception e2) {
					e2.printStackTrace();
				}

				boolean authorized = false;
				try {
					authorized = auth.isAuthorized(review.getReviewPK()
							.getUsersId(), map.get("user"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				if (authorized) {
					System.out.println(review.getReviewPK().getRoutesId() + " " + review.getReviewPK().getUsersId());
					try {
						reviewRep.removeReviewFromId(review.getReviewPK());

					} catch (PersistenceException e) {
						e.printStackTrace();
						throw new UncheckedPersistenceException(
								"Error Removing Review from DB");
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

}
