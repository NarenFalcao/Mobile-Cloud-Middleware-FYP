package mcm.dropbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;

public class DropboxClient 
{
	DbxRequestConfig config=null;
	DbxWebAuthNoRedirect webAuth=null;
	DbxClient client=null;
	DbxAuthFinish authFinish=null;
	String authorizeUrl=null;
	public String doDrop_authentication()throws IOException, DbxException
	{
		final String APP_KEY = "6ue32gjzt79gmtm";
        final String APP_SECRET = "tjaw7unqar8e1w4";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
	    System.out.println("3. Copy the authorization code.");
	        //String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
	        
	    authorizeUrl = webAuth.start();
	    
        return authorizeUrl;
	}
	public String listFiles(String url) throws DbxException
	{
		//LISTING

		final String APP_KEY = "6ue32gjzt79gmtm";
        final String APP_SECRET = "tjaw7unqar8e1w4";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        authFinish = webAuth.finish(url);
        client = new DbxClient(config, authFinish.accessToken);
        
		String listFiles=null;
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) 
        {
        	listFiles += "	" + child.name ;
            System.out.println("	" + child.name + ": " + child.toString());
        }
        return listFiles;
	}
	
	public void uploadFile(File file,String url) throws DbxException, IOException
	{
		//Upload FILE
		final String APP_KEY = "6ue32gjzt79gmtm";
        final String APP_SECRET = "tjaw7unqar8e1w4";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
		System.out.println(url+"\n"+file.getAbsolutePath()+"\n");
        FileInputStream inputStream = new FileInputStream(file);
		if(!file.exists())
		System.out.println("file not found \n\n");
		else
		System.out.println("file found");
        
        try {
        	
        	authFinish = webAuth.finish(url);
            
            client = new DbxClient(config, authFinish.accessToken);
            
            DbxEntry.File uploadedFile = client.uploadFile("/"+file.getName(),
                DbxWriteMode.add(), file.length(), inputStream);
            System.out.println("Uploaded to Dropbox: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
	}
	
	public void downloadFile(String fileName,String filepath,String url) throws DbxException, IOException
	{
		//Download File
		String toSave=fileName;
        FileOutputStream outputStream = new FileOutputStream(toSave);
        try {
        	
        	final String APP_KEY = "6ue32gjzt79gmtm";
            final String APP_SECRET = "tjaw7unqar8e1w4";

            DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

            config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
            webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        	authFinish = webAuth.finish(url);
            
            client = new DbxClient(config, authFinish.accessToken);

        	
            DbxEntry.File downloadedFile = client.getFile("/" + fileName, null,outputStream);
            System.out.println("Metadata: " + downloadedFile.toString());
        } finally {
            outputStream.close();
        }
	}
	public boolean searchFiles(String SearchFileName,String url) throws DbxException
	{
		//LISTING

		final String APP_KEY = "6ue32gjzt79gmtm";
        final String APP_SECRET = "tjaw7unqar8e1w4";
		System.out.println(SearchFileName+"\n"+url+"\n");
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        authFinish = webAuth.finish(url);
        client = new DbxClient(config, authFinish.accessToken);
        
		
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        System.out.println("Files in the root path:");
        for (DbxEntry child : listing.children) 
        {
        	System.out.println(child.name);
        	if(child.name.equalsIgnoreCase(SearchFileName))
            	return true;
        }
        return false;
	}
}


