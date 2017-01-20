package com.mcm.rest;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
import com.MCM;
 
@Path("/cloud")
public class AuthenticateRestService {
 
	@GET
	@Path("/authenticate")
	public Response printMessage(@QueryParam("cloud") String cloud) {
		String result;
		try
		{
		MCM m= new MCM();
		result =m.authentication(cloud);
		}
		catch(Exception e)
		{
		result="error";
		}
		return Response.status(200).entity(result).build();
 
	}
	
 
}