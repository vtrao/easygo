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

import com.easygo.daolayer.ICarDAO;
import com.easygo.model.Car;

import io.swagger.annotations.Api;

@Api("/car")
@Path("/car")
public class CarResource {
	public final ICarDAO carDAO;
	
	public CarResource(ICarDAO carDAO) {
		super();
		this.carDAO = carDAO;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car car, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		Car returnValue = null;
		try {
			returnValue = carDAO.addCar(car);
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
		
		Car car = null;
		try {
			car = carDAO.getCar(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(car).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	
	@GET
	@Path("/currentbranch/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getCarsInCurrentBranch(@PathParam("id") long id, @Context HttpHeaders header, @Context HttpServletResponse response) {
		System.out.println("currentbranch");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		List<Car> cars = null;
		try {
			cars = carDAO.getCars(id, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(cars).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	
	
	@GET
	@Path("/homebranch/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getCarsInHomeBranch(@PathParam("id") long id, @Context HttpHeaders header, @Context HttpServletResponse response) {
		System.out.println("homebranch");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		
		List<Car> cars = null;
		try {
			cars = carDAO.getCars(id, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(cars).
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
		
		List<Car> cars = null;
		try {
			cars = carDAO.getCars(-1L, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(cars).
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
		
		List<String> carTypes = null;
		try {
			carTypes = carDAO.getTypes();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = 500;
		}
		
		Response retResponse = Response.status(status).
                entity(carTypes).
                /*header("Access-Control-Allow-Origin", "*").
                header("Access-Control-Allow-Credentials","true").*/ build();
		return retResponse;
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCar(Car car, @Context HttpHeaders header, @Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		response.setHeader("Access-Control-Allow-Credentials","true"); 
		int status = 200;
		Car returnValue = null;
		try {
			int updated = carDAO.updateCar(car);
			returnValue = car;
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
			if(carDAO.removeCar(id)<=0) {
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
