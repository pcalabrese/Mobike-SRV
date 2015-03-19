package Controller;

import java.io.IOException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Review;
import Controller.exception.UncheckedControllerException;

import com.fasterxml.jackson.databind.ObjectMapper;

import persistence.ReviewRepository;
import persistence.exception.PersistenceException;
import persistence.exception.UncheckedPersistenceException;
import persistence.mysql.ReviewMySQL;
import utils.Authenticator;
import utils.Crypter;
import utils.Wrapper;
import utils.exception.AuthenticationException;
import utils.exception.UncheckedAuthenticationException;
import utils.exception.UncheckedCryptingException;
import utils.exception.UncheckedWrappingException;
import utils.exception.WrappingException;

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

		Map<String, String> map;
		try {
			map = wrapper.unwrap(wrappingJson);
		} catch (WrappingException e1) {
			e1.printStackTrace();
			throw new UncheckedWrappingException();
		}

		if (map != null) {

			if (map.get("review") != null & map.get("user") != null) {

				Authenticator auth = new Authenticator();
				boolean exists = false;
				try {
					exists = auth.validateCryptedUser(map.get("user"));
				} catch (AuthenticationException e1) {
					e1.printStackTrace();
					throw new UncheckedAuthenticationException();
				}

				if (exists) {
					Crypter crypter = new Crypter();
					String reviewPlainJson = null;
					try {
						reviewPlainJson = crypter.decrypt(map.get("review"));
					} catch (Exception e) {
						e.printStackTrace();
						throw new UncheckedCryptingException();
					}

					ObjectMapper mapper = new ObjectMapper();
					Review review = null;
					try {
						review = mapper
								.readValue(reviewPlainJson, Review.class);
					} catch (IOException e) {
						e.printStackTrace();
						throw new UncheckedControllerException("Error Reading Json");
						
					}

					boolean authorized;
					try {
						authorized = auth.isAuthorized(review.getReviewPK()
								.getUsersId(), map.get("user"));
					} catch (AuthenticationException e1) {
						e1.printStackTrace();
						throw new UncheckedAuthenticationException();
					}

					if (authorized) {
						try {
							reviewRep.addReview(review);
						} catch (PersistenceException e) {
							e.printStackTrace();
							throw new UncheckedPersistenceException("Error adding Review to the Database");
						}

						return Response.ok().build();
					} else {
						return Response.status(401).build();
					}

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

		Wrapper wrapper = new Wrapper();

		Map<String, String> map;
		try {
			map = wrapper.unwrap(wrappingJson);
		} catch (WrappingException e1) {
			e1.printStackTrace();
			throw new UncheckedWrappingException();
		}

		if (map != null) {

			if (map.get("review") != null & map.get("user") != null) {

				Authenticator auth = new Authenticator();
				boolean exists;
				try {
					exists = auth.validateCryptedUser(map.get("user"));
				} catch (AuthenticationException e1) {
					e1.printStackTrace();
					throw new UncheckedAuthenticationException();
				}

				if (exists) {
					Crypter crypter = new Crypter();
					String reviewPlainJson = null;
					try {
						reviewPlainJson = crypter.decrypt(map.get("review"));
					} catch (Exception e) {
						e.printStackTrace();
						throw new UncheckedCryptingException();
					}

					ObjectMapper mapper = new ObjectMapper();
					Review review = null;
					try {
						review = mapper
								.readValue(reviewPlainJson, Review.class);
					} catch (IOException e) {
						e.printStackTrace();
						throw new UncheckedControllerException("Error reading Json");
					}

					boolean authorized = false;
					try {
						authorized = auth.isAuthorized(review.getReviewPK()
								.getUsersId(), map.get("user"));
					} catch (AuthenticationException e1) {
						e1.printStackTrace();
						throw new UncheckedAuthenticationException();
					}

					if (authorized) {
						try {
							reviewRep.updateReview(review);
							;
						} catch (PersistenceException e) {
							e.printStackTrace();
							throw new UncheckedPersistenceException("Error updating Review");
						}

						return Response.ok().build();
					} else {
						return Response.status(401).build();
					}

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
		Wrapper wrapper = new Wrapper();

		Map<String, String> map;
		try {
			map = wrapper.unwrap(wrappingJson);
		} catch (WrappingException e1) {
			e1.printStackTrace();
			throw new UncheckedWrappingException();
		}

		if (map != null) {

			if (map.get("review") != null & map.get("user") != null) {

				Authenticator auth = new Authenticator();
				boolean exists;
				try {
					exists = auth.validateCryptedUser(map.get("user"));
				} catch (AuthenticationException e1) {
					e1.printStackTrace();
					throw new UncheckedAuthenticationException();
				}

				if (exists) {
					Crypter crypter = new Crypter();
					String reviewPlainJson = null;
					try {
						reviewPlainJson = crypter.decrypt(map.get("review"));
					} catch (Exception e) {
						e.printStackTrace();
						throw new UncheckedCryptingException();
					}

					ObjectMapper mapper = new ObjectMapper();
					Review review = null;
					try {
						review = mapper
								.readValue(reviewPlainJson, Review.class);
					} catch (IOException e) {
						e.printStackTrace();
						throw new UncheckedControllerException();
					}

					boolean authorized;
					try {
						authorized = auth.isAuthorized(review.getReviewPK()
								.getUsersId(), map.get("user"));
					} catch (AuthenticationException e1) {
						e1.printStackTrace();
						throw new UncheckedAuthenticationException();
					}

					if (authorized) {
						try {
							reviewRep.removeReviewFromId(review.getReviewPK());
							
						} catch (PersistenceException e) {
							e.printStackTrace();
							throw new UncheckedPersistenceException("Error Removing Review from DB");
						}

						return Response.ok().build();
					} else {
						return Response.status(401).build();
					}

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
