package com;
 import mcm.dropbox.DropboxClient;
import mcm.gdrive.GDriveClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import com.dropbox.core.DbxException;
public class MCM
{
	DropboxClient client_drop=null;
	GDriveClient client_drive=null;
	String cloud;
	String code_dropbox=null,code_gdrive=null;
	boolean drop=true,drive=true;
	public MCM()
	{
		 client_drop = new DropboxClient();
		 client_drive = new GDriveClient();
	}
	String filePath;
	public void setup(String cl)
	{
		cloud=cl;
	}
	public String authentication() throws IOException,DbxException
	{
		String s=null;
		
		
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			
			 s= client_drop.doDrop_authentication();
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				s=client_drive.doGDrive_authentication();
			}
		return s;
	}
	
	public String authentication(String cloud) throws IOException,DbxException
	{
		String s=null;
		
		
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			
			 s= client_drop.doDrop_authentication();
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				s=client_drive.doGDrive_authentication();
			}
		return s;
	}
	public void authenticate_code(String s)
	{
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			
			code_dropbox=s;
			drop=true;
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				code_gdrive=s;
				drive=true;
			}
	}
	public void upload(File f)throws IOException,DbxException
	{
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			if(!drop)
				throw new IOException();
			System.out.println(authentication());
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			code_dropbox=br.readLine();
			 client_drop.uploadFile(f,code_dropbox);
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				if(!drive)
					throw new IOException();
				System.out.println(authentication());
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				code_gdrive=br.readLine();
				client_drive.uploadFile(f.getAbsolutePath(),code_gdrive);
			}
			else
			throw new IOException();
		
	}
	public void upload(File f,String cloud,String code)throws IOException,DbxException
	{
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			if(!drop)
				throw new IOException();
			 code_dropbox=code;
			 client_drop.uploadFile(f,code_dropbox);
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				if(!drive)
					throw new IOException();
				code_gdrive=code;
				client_drive.uploadFile(f.getAbsolutePath(),code_gdrive);
			}
			else
			throw new IOException();
		
	}
	public String listFiles(String cloud,String code)throws IOException,DbxException
	{
		String s=null;
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			if(!drop)
				throw new IOException();
				code_dropbox=code;
			 s=client_drop.listFiles(code_dropbox);
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				if(!drive)
					throw new IOException();
				code_gdrive=code;
				s=client_drive.listFiles(code_gdrive);
			}
			else
			throw new IOException();
		
		return s;
	}
	public String listFiles(String cloud)throws IOException,DbxException
	{
		String s=null;
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			if(!drop)
				throw new IOException();
			System.out.println(authentication(cloud));
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			code_dropbox=br.readLine();
			
			 s=client_drop.listFiles(code_dropbox);
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				if(!drive)
					throw new IOException();
				System.out.println(authentication(cloud));
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				code_gdrive=br.readLine();
				s=client_drive.listFiles(code_gdrive);
			}
		
		return s;
	}
	public void downloadFiles(String filename,String filepath,String cloud)throws IOException,DbxException
	{
		String s=null;
		if(cloud.equalsIgnoreCase("dropbox"))
		{
			if(!drop)
				throw new IOException();
			System.out.println(authentication(cloud));
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			code_dropbox=br.readLine();
			
			client_drop.downloadFile(filename,filepath,code_dropbox);
		}
		else
			if(cloud.equalsIgnoreCase("gdrive"))
			{
				if(!drive)
					throw new IOException();
				System.out.println(authentication(cloud));
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				code_gdrive=br.readLine();
				client_drive.downloadFile(filename,filepath,code_gdrive);
			}
	}
		public void downloadFiles(String filename,String filepath,String cloud,String code)throws IOException,DbxException
		{
			String s=null;
			if(cloud.equalsIgnoreCase("dropbox"))
			{
				code_dropbox=code;
				client_drop.downloadFile(filename,filepath,code_dropbox);
			}
			else
				if(cloud.equalsIgnoreCase("gdrive"))
				{
					code_gdrive=code;
					client_drive.downloadFile2(filename,filepath,code_gdrive);
				}
				else
			throw new IOException();
			
		
		}
		public boolean SearchFiles(String filename,String cloud, String code)throws IOException,DbxException
		{
			if(cloud.equalsIgnoreCase("dropbox"))
			{
				
				//System.out.println(authentication());
				code_dropbox=code;
				return client_drop.searchFiles(filename,code_dropbox);
			}
			else
				if(cloud.equalsIgnoreCase("gdrive"))
				{
					System.out.println("in MCM gdrive\n");
					//System.out.println(authentication());
					code_gdrive=code;
					return client_drive.searchFiles(filename,code_gdrive);
				}
			else
				throw new IOException();
		}
		public void Transfer(String filename,String fromcloud,String fromcode,String tocloud,String tocode) throws IOException,DbxException{
		String filepath="";
		if(fromcloud.equals("dropbox"))
		{
				
				client_drop.downloadFile(filename,filepath,fromcode);
				
				System.out.println("DOWNLOADED " + filename + " from Dropbox");
		}
		else if(fromcloud.equals("gdrive"))
		{
				client_drive.downloadFile2(filename,filepath,fromcode);
				System.out.println("DOWNLOADED " + filename + " from GDrive");
		}
		else
			throw new IOException();
		File f= new File(filepath+filename);
		if(tocloud.equals("dropbox"))
		{
			
				client_drop.uploadFile(f,tocode);
				System.out.println("Uploaded " + filename + " to Dropbox");
		}
		else if(tocloud.equals("gdrive"))
		{
			//GDriveClient gc = new GDriveClient();
			
				client_drive.uploadFile(f.getAbsolutePath(), tocode);
				System.out.println("Uploaded " + filename + " to GDrive");
		}
		else
			throw new IOException();
	}

}
