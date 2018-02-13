package com.easygo.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.easygo.daolayer.ITripDAO;
import com.easygo.model.Sale;
import com.easygo.model.Trip;

import io.swagger.annotations.Api;

@Api("/trip")
@Path("/trip")
public class TripResource {
	private final ITripDAO tripDAO;
	public TripResource(ITripDAO tripDAO) {
		super();
		this.tripDAO = tripDAO;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTrip(Trip trip, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		int returnValue = 0;
		try {
			returnValue = tripDAO.addTrip(trip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		Response retResponse = Response.status(status).entity(returnValue).build();
		return retResponse;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getTrip(@QueryParam("before") int before, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		Date from = new Date();
		List<Trip> trips = null;
		try {
			trips = tripDAO.getTrip(from, before);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(trips).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
}
