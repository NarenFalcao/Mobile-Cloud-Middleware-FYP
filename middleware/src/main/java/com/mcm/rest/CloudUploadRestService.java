package com.mcm.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import com.MCM;
 
@Path("/cloud")
public class CloudUploadRestService{
 
	private final String UPLOADED_FILE_PATH = new File("").getAbsolutePath()+"/";
 
	@POST
	@Path("/file")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
 
		String fileName = "";
		String s="";
		String cloud="";
		String code="";
 
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
 
		for (InputPart inputPart : inputParts) {
 
		 try {
 
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);
			//String str= fileName.substring(fileName.lastIndexOf("/"),fileName.length());
			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
 
			byte [] bytes = IOUtils.toByteArray(inputStream);
 
			//constructs upload file path
			//fileName = UPLOADED_FILE_PATH + fileName;
 
			writeFile(bytes,fileName);
			
			
 
			System.out.println("Done");
 
		  } catch (IOException e) {
			e.printStackTrace();
		  }
 
		}
		inputParts = uploadForm.get("cloud");
 
		for (InputPart inputPart : inputParts) {
 
		 try{
				cloud=inputPart.getBodyAsString();
			}
		catch(IOException e)
		{
		s="error";
		}
		}
		inputParts = uploadForm.get("code");
 
		for (InputPart inputPart : inputParts) {
 
		 try{
				code=inputPart.getBodyAsString();
			}
		catch(IOException e)
		{
		s="error";
		}
		}
		File f=new File(fileName);
		try
		{
		//System.out.println("code :"+code+"\ncloud: "+cloud);
		if(f.exists())
		{
		//System.out.println(f.getAbsolutePath()+"\n\n");
		MCM m= new MCM();
		m.upload(f,cloud,code);
		s="success";
		}
		else
		s= "no file found";
		}
		catch(Exception e)
		{
		s=" server error";
		e.printStackTrace();
		return Response.status(400).entity(s).build();
		}
		return Response.status(200).entity("Uploaded file name : " +fileName+"   "+s).build();
	}
 
	/**
	 * header sample
	 * {
	 * 	Content-Type=[image/png], 
	 * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
	 * }
	 **/
	//get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {
 
		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
 
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {
 
				String[] name = filename.split("=");
 
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
 
	//save to somewhere
	private void writeFile(byte[] content, String filename) throws IOException {
 
		File file = new File(filename);
 
		if (!file.exists()) {
			file.createNewFile();
		}
 
		FileOutputStream fop = new FileOutputStream(file);
 
		fop.write(content);
		fop.flush();
		fop.close();
 
	}
}

