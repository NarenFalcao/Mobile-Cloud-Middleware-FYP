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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
 
@Path("/cloud")
public class DownloadFileExampleRestService{
 
	@GET
	@Path("/downloadexample")
	@Produces("application/octet-stream")
	public Response printMessage(@QueryParam("content") String content) {
	String result;
	//String filepath=System.getProperty("user.dir");
	String filepath="";
	String filename ="santhosh";
	String Extension =".txt";
	File f= new File(filename);
		try
		{
		System.out.println("sample"+"\n");
		//MCM m= new MCM();
		//m.downloadFiles(filename,filepath,cloud,code);
		if (!f.exists()) {
				f.createNewFile();
			}
 
			FileWriter fw = new FileWriter(f.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		result="success";
		}
		catch(Exception e)
		{
		result= "error";
		}
		if(f.exists())
		{
		ResponseBuilder response = Response.ok((Object)f);
		response.header("content-type","application/octet-stream");
		response.header("Content-Disposition","attachment; filename=\""+filename+Extension+"\"");
		return response.build();	
		}
		else
		{
		ResponseBuilder response = Response.status(Status.BAD_REQUEST);
            return response.build();
		}
 
	}
 
}