package com.easygo.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.easygo.daolayer.IBookingDAO;
import com.easygo.model.Booking;
import com.easygo.model.DemoCompleteBooking;

import io.swagger.annotations.Api;

@Api("/booking")
@Path("/booking")
public class BookingResource {
	public final IBookingDAO bookingDAO;

	public BookingResource(IBookingDAO bookingDAO) {
		super();
		this.bookingDAO = bookingDAO;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBooking(List<DemoCompleteBooking> booking, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		int returnValue = 0;
		try {
			returnValue = bookingDAO.addBooking(booking);
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
	public Response getBooking(@Context HttpHeaders header, @Context HttpServletResponse response) {
		System.out.println("currentbranch");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		List<Booking> booking = null;
		try {
			booking = bookingDAO.getBooking();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(booking).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
}
