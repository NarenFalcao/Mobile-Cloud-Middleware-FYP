package com.mcm.rest;
 
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import com.MCM;
 
@Path("/cloud")
public class ListFileRestService {
 
	@POST
	@Path("/list")
	public Response printMessage(@FormParam("cloud") String cloud,@FormParam("code") String code) {
	String result;
 
		try
		{
		MCM m= new MCM();
		result=m.listFiles(cloud,code);
		}
		catch(Exception e)
		{
		result= "error";
		return Response.status(400).entity(result).build();
		}
		return Response.status(200).entity(result).build();
 
	}
 
}