package service;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/hello")
public class JavaResource implements JavaInterface{
	
	public JavaResource() {
		
	}
	
	@GET
	@Path("/saymyname")
	@Produces(MediaType.TEXT_PLAIN)
	public String hello(@QueryParam("name") String name) {
		return "Hello, " + name + "!";
	}
	
//	@GET
//	@Path("/celsiusToFahrenheit")
//	@Produces(MediaType.TEXT_PLAIN)
//	@Override
//	public Double celsiusToFahrenheit(@QueryParam("temperatura") Double temperatura) {
//		return temperatura * 9.0 / 5.0 + 32;
//	}
}
