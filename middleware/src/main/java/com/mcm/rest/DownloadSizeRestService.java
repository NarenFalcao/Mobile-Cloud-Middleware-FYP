package com.mcm.rest;
 
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.MCM;
import java.io.File;
import javax.ws.rs.core.Response.Status;
 
@Path("/cloud")
public class DownloadSizeRestService{
 
	@GET
	@Path("/downloadsize")
	public Response printMessage(@QueryParam("filename") String filename,@QueryParam("cloud") String cloud,@QueryParam("code") String code) {
	String result;
	//String filepath=System.getProperty("user.dir");
	String filepath="";
		try
		{
		System.out.println(filename+"\n");
		MCM m= new MCM();
		m.downloadFiles(filename,filepath,cloud,code);
		result="success";
		}
		catch(Exception e)
		{
		result= "error";
		return Response.status(200).entity(result).build();
		}
		File f= new File(filename);
		if(f.exists())
		{
		String s=f.getAbsolutePath()+"     "+f.length();
		result=result+"     "+s;
		//ResponseBuilder response = Response.ok((Object)f);
		//response.header("content-type","application/octet-stream");		
		//response.header("Content-Disposition","attachment; filename=\""+filename+"\"");
		return Response.status(200).entity(result).build();
		}
		else
		{
		ResponseBuilder response = Response.status(Status.BAD_REQUEST);
        return response.build();
		}
 
	}
 
}