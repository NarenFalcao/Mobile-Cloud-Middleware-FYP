package com.mcm.rest;
 
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.FormParam;
import com.MCM;
 
@Path("/cloud")
public class TransferRestService{
 
	@POST
	@Path("/transfer")
	public Response printMessage(@FormParam("filename") String filename,@FormParam("fromcloud") String fromcloud,@FormParam("fromcode") String fromcode,@FormParam("tocloud") String tocloud,@FormParam("tocode") String tocode){
	String result;
 
		try
		{
		MCM m= new MCM();
		m.Transfer(filename,fromcloud,fromcode,tocloud,tocode);
		result="success";
		}
		catch(Exception e)
		{
		result= "error";
		return Response.status(400).entity(result).build();
		}
		return Response.status(200).entity(result).build();
 
	}
 
}