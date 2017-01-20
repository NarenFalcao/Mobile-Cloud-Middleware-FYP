package mcm.gdrive;


	import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
	




	public class GDriveClient 
	{
		  private static String CLIENT_ID = "553419148291.apps.googleusercontent.com";
		  private static String CLIENT_SECRET = "WRleydrHdLC67_7bxx643rrK";

		  private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
		  Drive service=null;
		  GoogleAuthorizationCodeFlow flow=null;
		  GoogleTokenResponse response=null;
		  GoogleCredential credential = null;
		  HttpTransport httpTransport;
		  JsonFactory jsonFactory;
		  Files.List result;

		  
		  /**
		   * Download a file's content.
		   * 
		   * @param service Drive API service instance.
		   * @param file Drive File instance.
		   * @return InputStream containing the file's content if successful,
		   *         {@code null} otherwise.
		   */
		  
		  public void downloadFile(String fileName,String filepath, String code) throws IOException
		  {
			  doGDrive(code);
			  /** Downloads a file using either resumable or direct media download. */
			   
			  // create parent directory (if necessary)
			      
			      
			      OutputStream out = new FileOutputStream(new java.io.File(fileName));
			      String fileDownloadUrl=null;
			      //Searching
			      List<File> result = new ArrayList<File>();
				    Files.List request = service.files().list();
				    String fileList="";
				    for(int i=0;i<15;i++)
				    {
				      try {
				        FileList files = request.execute();

				        result.addAll(files.getItems());
				        request.setPageToken(files.getNextPageToken());
				        if(result.get(i).getOriginalFilename().equals(fileName))
				        {
				        	fileDownloadUrl=result.get(i).getDownloadUrl();
				        	break;
				        }
				        
				      } 
				      catch(FileNotFoundException e)
				      {
				    	  System.out.println("File Not Found" );
				    	  		
				      }
				      catch (IOException e) {
				        System.out.println("An error occurred: " + e);
				        request.setPageToken(null);
				      }
			      
			      try {
			    	    HttpResponse resp =
			    	        service.getRequestFactory().buildGetRequest(new GenericUrl(fileDownloadUrl)).execute();
			    	    //InputStream in = resp.getContent();
			    	    //IOUtils.copy(resp.getContent(), out,true);
			    	    
			    	    InputStream in = resp.getContent();
			    	    byte[]buf=new byte[1000];
			    	    in.read(buf);
			    	    String str = new String(buf);
			    	    System.out.println(str);
			    	    
			      } catch (IOException e) {
			    	    // An error occurred.
			    	    e.printStackTrace();
			    	   
			    	  }
			      
			      System.out.println("DOWNLOADED from GDrive");
				    }
			  
			  
		  }
		  public java.io.File downloadFile2(String fileName,String toSave,String code)  throws IOException
	      {
	       
			  doGDrive(code);
	          File file=null;
	          toSave=fileName;
	          FileOutputStream fout = new FileOutputStream(toSave);
	          
	          List<File> result = new ArrayList<File>();
	          java.io.File rFile=null;
	          Files.List request = service.files().list();

	            for(int i=0;i<10;i++)
	            {
	              try {
	                FileList files = request.execute();

	                result.addAll(files.getItems());
	                request.setPageToken(files.getNextPageToken());
	               
	                    System.out.println(result.size() + " fileName " + fileName);
	                    if(result.get(i).getOriginalFilename()!=null && result.get(i).getOriginalFilename().equals(fileName))
	                    {
	                        file=result.get(i);
	                        break;
	                    }
	                    System.out.println(result.get(i).getOriginalFilename());
	             
	               
	              }
	              catch(FileNotFoundException e)
	              {
	                  System.out.println("File Not Found" );
	                         
	              }
	              catch (IOException e) {
	                System.out.println("An error occurred: " + e);
	                request.setPageToken(null);
	              }
	           
	           
	            }
	        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
	          try {
	            HttpResponse resp =
	                service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
	                    .execute();
	            InputStream in = resp.getContent();
	            byte[]buf = new byte[1000];
	            String str=null;
	            while(in.read(buf)>0)
	            {
	                fout.write(buf);
	                String temp = new String(buf);
	                str+=temp;
	                buf=new byte[1000];
	            }
	           
	            rFile = new java.io.File(toSave);
	            System.out.println("Contents " + str);
	            
	          } catch (IOException e) {
	            // An error occurred.
	            e.printStackTrace();
	           
	          }
	          
	        }
	        return rFile;
	      }

		  
		  public void uploadFile(String fileLocation,String code) throws IOException
		  {
			  doGDrive(code);
			  //INSERT FILES
			    File body = new File();
			    String fileName = fileLocation.substring(fileLocation.lastIndexOf("/")+1 , fileLocation.lastIndexOf("."));
			    String fileExtension = fileLocation.substring(fileLocation.lastIndexOf(".")+1 , fileLocation.length());
				
			    String mimeType=null;
			    body.setTitle(fileName+"."+fileExtension);
			    //body.setDescription("A test document on Jan 30 2014 - GDRIVE");
			    
			    if(fileExtension.equalsIgnoreCase("jpg")||fileExtension.equalsIgnoreCase("jpeg"))
			    	mimeType="image/jpeg";
			    else
			    	mimeType="text/plain";
			    
			    body.setMimeType(mimeType);
			    java.io.File fileContent = new java.io.File(fileLocation);
			    FileContent mediaContent = new FileContent(mimeType, fileContent);

			    File uploadFile = service.files().insert(body, mediaContent).execute();
			    System.out.println("File Uploaded: " + uploadFile.getOriginalFilename());
		  }
		  
		  public String listFiles(String code) throws IOException
		  {

			  doGDrive(code);
			  //LIST FILES
			    List<File> result = new ArrayList<File>();
			    Files.List request = service.files().list();
			    String fileList="";
			    for(int i=0;i<5;i++)
			    {
			      try {
			        FileList files = request.execute();

			        result.addAll(files.getItems());
			        request.setPageToken(files.getNextPageToken());
			        fileList += result.get(i).getOriginalFilename();
			        System.out.println(result.get(i).getOriginalFilename());
			        fileList.concat("      ");
			      } 
			      catch(FileNotFoundException e)
			      {
			    	  System.out.println("File Not Found" );
			    	  		
			      }
			      catch (IOException e) {
			        System.out.println("An error occurred: " + e);
			        request.setPageToken(null);
			      }
			    }
			    return fileList;
		  }
		  public String doGDrive_authentication()throws IOException
		  {
			  
			  httpTransport = new NetHttpTransport();
			  jsonFactory = new JacksonFactory();
			   
			    flow = new GoogleAuthorizationCodeFlow.Builder(
			        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
			        .setAccessType("online")
			        .setApprovalPrompt("auto").build();
			    
			    
			    String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
			    return url;
			  
		  }
		  public void doGDrive(String code) throws IOException 
		  {
		    httpTransport = new NetHttpTransport();
		    jsonFactory = new JacksonFactory();
		   
		    flow = new GoogleAuthorizationCodeFlow.Builder(
		        httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
		        .setAccessType("online")
		        .setApprovalPrompt("auto").build();
		    
		   
		    
		    response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
		    credential = new GoogleCredential().setFromTokenResponse(response);
		    
		    //Create a new authorized API client
		    service = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName("SPC GDRIVE").build();
		   
		    System.out.println("App Name : " + service.getApplicationName());
		    
		 }
		 public boolean searchFiles(String SearchFileName,String code) throws IOException
		  {

			    doGDrive(code);
			    //LIST FILES
				System.out.println("back to search");
			    List<File> result = new ArrayList<File>();
			    Files.List request = service.files().list();
			   
			    int i=-1;
			    do
			    {
				System.out.println("inside loop\n");
				i++;
			       try {
			        FileList files = request.execute();
			        result.addAll(files.getItems());
					if(result.isEmpty())
					{
					System.out.println("is empty");
					}
					else
					System.out.println("is not empty");
					
			        request.setPageToken(files.getNextPageToken());
					System.out.println(result.get(i).getOriginalFilename()+"\n");
			        if( result.get(i).getOriginalFilename().equalsIgnoreCase(SearchFileName) )
					{
			        	return true;
					}
			        
			      } 
			      catch(FileNotFoundException e)
			      {
			    	  System.out.println("File Not Found" );
			    	  		
			      }
			      catch (IOException e) {
			        System.out.println("An error occurred: " + e);
			        request.setPageToken(null);
			      }
			    }while(request.getPageToken()!=null && request.getPageToken().length()>0);
			    
			    return false;
		  }
	}

	

