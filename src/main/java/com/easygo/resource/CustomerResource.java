package com.easygo.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.easygo.daolayer.ICustomerDAO;
import com.easygo.model.Customer;

import io.swagger.annotations.Api;

@Api("/customer")
@Path("/customer")
public class CustomerResource {
	public final ICustomerDAO customerDAO;
	public CustomerResource(ICustomerDAO customerDAO) {
		super();
		this.customerDAO = customerDAO;
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCustomer(Customer customer, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		Customer returnValue = null;
		try {
			returnValue = customerDAO.addCustomer(customer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		Response retResponse = Response.status(status).entity(returnValue).build();
		return retResponse;
	}
	
	@GET
	@Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") long id, @Context HttpHeaders header, @Context HttpServletResponse response) {
		System.out.println("given id");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		Customer customer = null;
		try {
			customer = customerDAO.getCustomer(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(customer).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context HttpHeaders header, @Context HttpServletResponse response) {
		System.out.println("checking 1 2m");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		List<Customer> customers = null;
		try {
			customers = customerDAO.getCustomer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(customers).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	
	@GET
	@Path("/types")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getTypes(@Context HttpHeaders header, @Context HttpServletResponse response) {
		System.out.println("checking 1 2m");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		List<String> customerTypes = null;
		try {
			customerTypes = customerDAO.getTypes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(customerTypes).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		Customer returnValue = null;
		try {
			int updated = customerDAO.updateCustomer(customer);
			returnValue = customer;
			System.out.println("updated position: "+updated);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		Response retResponse = Response.status(status).entity(returnValue).build();
		return retResponse;
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") long id, 
						   @Context HttpHeaders header, 
						   @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		Response retResponse;
		int status = 200;
		boolean retValue= true;
		try {
			if(customerDAO.removeCustomer(id)<=0) {
				status = 400; //bad request
				retValue = false;
			} 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
			retValue = false;
		}
		retResponse = Response.status(status).entity(retValue).build();
		return retResponse;
	}
}
