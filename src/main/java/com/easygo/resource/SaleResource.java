package com.easygo.resource;

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

import com.easygo.daolayer.ISaleDAO;
import com.easygo.model.Car;
import com.easygo.model.Customer;
import com.easygo.model.Sale;
import com.easygo.model.Trip;

import io.swagger.annotations.Api;

@Api("/sale")
@Path("/sale")
public class SaleResource {
	private final ISaleDAO saleDAO;	
	public SaleResource(ISaleDAO saleDAO) {
		super();
		this.saleDAO = saleDAO;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addTrip(Sale sale, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		int returnValue = 0;
		try {
			returnValue = saleDAO.addSale(sale);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		Response retResponse = Response.status(status).entity(returnValue).build();
		return retResponse;
	}
	
	@GET
	@Path("/complex")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getComplex(@QueryParam("outstation") int outstationtrip,@QueryParam("cartype") String carType, @QueryParam("customertype") String customerType, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		List<Sale> sales = null;
		try {
			sales = saleDAO.getSales(outstationtrip, carType, customerType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(sales).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	
	
	@GET
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") Long id, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		Sale sale = null;
		try {
			sale = saleDAO.getSale(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(sale).build();
		return retResponse;
	}
	
}
