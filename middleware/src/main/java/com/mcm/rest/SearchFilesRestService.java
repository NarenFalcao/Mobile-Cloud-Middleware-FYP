package com.mcm.rest;
 
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import com.MCM;
 
@Path("/cloud")
public class SearchFilesRestService {
 
	@POST
	@Path("/search")
	public Response printMessage(@FormParam ("filename")String filename,@FormParam("cloud") String cloud,@FormParam("code") String code) {
	boolean result;
	String a="";
	System.out.println(filename+"\n"+cloud+"\n"+code+"\n");
		try
		{
		MCM m= new MCM();
		result=m.SearchFiles(filename,cloud,code);
		}
		catch(Exception e)
		{
		System.out.println("error in search");
		e.printStackTrace();
		result= false;
		}
		if(result)
		a="true";
		else
		a="false";
		return Response.status(200).entity(a).build();
 
	}
 
}