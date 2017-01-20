package com.mcm.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.io.*;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import com.MCM;
@Path("/cloud")
public class UploadRestService{
 
	private final String UPLOADED_FILE_PATH = new File("").getAbsolutePath()+"\\";
 
	@POST
	@Path("/upload")
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) {
 
		String fileName = "";
		String s="";
		String code="";
		String cloud="";
 
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
 
		for (InputPart inputPart : inputParts) {
 
		 try {
 
			MultivaluedMap<String, String> header = inputPart.getHeaders();
			fileName = getFileName(header);
 
			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
 
			byte [] bytes = IOUtils.toByteArray(inputStream);
 
			//constructs upload file path
			fileName = UPLOADED_FILE_PATH + fileName;
 
			writeFile(bytes,fileName);
			
			
 
			System.out.println("Done");
		  } catch (IOException e) {
			e.printStackTrace();
		  }
		}
		try
		{
		List<InputPart> inputParts2 = uploadForm.get("cloud");
		for (InputPart inputPart : inputParts2) {
			 cloud=inputPart.getBodyAsString() ;
			}
		List<InputPart> inputParts3 = uploadForm.get("code");
		for (InputPart inputPart : inputParts3) {
			 code=inputPart.getBodyAsString() ;
			}
		File f=new File(fileName);
		MCM m= new MCM();
		m.upload(f,cloud,code);
		s="success";
		}
		catch(Exception e)
		{
		s=" server error";
		e.printStackTrace();
		return Response.status(404)
		    .entity("uploadFile is called, Uploaded file name : " + fileName+"          "+s).build();
		}
		return Response.status(200)
		    .entity("uploadFile is called, Uploaded file name : " + fileName+"          "+s).build();
 
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

