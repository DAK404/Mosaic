package API.Tools.FileManager;

import java.io.*;

import API.*;
import API.Anima.*;

public final class FileManager
{
	private boolean SB=false;
	private String User = "";
	private String Name = "";
	private String CurDir="";
	private File f;
	
	Console console=System.console();
	
	API.Information show=new API.Information();
	API.Tools.ReadFile rF=new API.Tools.ReadFile();
	API.Tools.textEdit te = new API.Tools.textEdit();
	API.Tools.FileManager.Encryptor enc=new API.Tools.FileManager.Encryptor();
	API.Tools.FileManager.Decryptor dec=new API.Tools.FileManager.Decryptor();
	API.Tools.Download dl=new API.Tools.Download();
	API.SHA256 sha=new API.SHA256();
	
	//take secureboot, username and admin values
	public FileManager(boolean SecureBoot, String Username, String name)
	{
		if(SecureBoot==false)
			System.exit(0);
		SB=SecureBoot;
		User=Username;
		Name=name;
	}
	
	public void FileManagerScript()throws Exception
	{
		API.policyEnforce pe=new API.policyEnforce("filemanager");
		
		if(pe.checkPolicy()==false)
		{
			return;
		}
		//Enter the current user's password to confirm identity using LoginAPI
		show.AboutProgram();
		if(confirmIdentity()==true)
		{
			System.out.println("Identity Confirmed.");
			CurDir="./Users/"+User+'/';
			//FileManager logic
			FileManager();
		}
		else
		{
			console.readLine("Could not confirm user identity. Returning to main menu.");
			return;
		}
		
	}
	
	/* --- TO DO LIST ---
	* write file
	* read file
	* download file
	* encrypt file
	* decrypt file
	*/
	
	private void FileManager()throws Exception
	{
		System.gc();
		show.ClearScreen();
		console.readLine("[ ATTENTION ]  :  BY USING THIS FILE MANAGER, YOU HEREBY ACCEPT THAT ANY CHANGES MADE TO YOUR FILES ARE YOUR RESPONSIBILITY AND YOUR LIABILITY.\nSOME CHANGES MADE MAY BE IRREVERSABLE.\nBY USING THIS FEATURE, YOU ARE RESPONSIBLE FOR YOUR ACTIONS. PRESS ENTER TO CONTINUE.");
		show.AboutProgram();
		System.out.println("Mosaic File Manager 1.0\n");
		while(true)
		{
			//.replace(User,Name)
		switch(console.readLine("<"+Name+">"+"@"+CurDir.replace(User,Name)+"]]: ").toLowerCase())
			{
				case "exit": return;
				
				case "help": 
							rF.ShowHelp("Help/FileManager.manual");
							break;
							
				case "clear":
							show.ClearScreen();
							break;
				
				case "list dir":
							listFiles();
							break;
							
				case "change dir":
							changeDir();
							break;
							
				case "make dir": 
							makeDir();
							break;
				
				case "delete":
							del();
							break;
							
				case "rename":
							rename();
							break;
				
				case "encrypt": 
							enc.encr(User, CurDir);
							break;
				
				case "decrypt": 
							dec.dcr(User, CurDir);
							break;
				
				case "read file":
							rF.userReadFile(User, CurDir);
							break;
				
				case "edit file": 
							te.editScript(User, CurDir);
							break;
							
				case "download":
							dl.downloadFile(User, CurDir);
							break;
				
				case "": 
							break;
				
				default: System.out.println("Unrecognized command. Type Help to know available commands.\n");
						break;
			}
			
		}
	}
	
	
	// --- make a common interface to check if the directory exists or not. --- //
	
	private boolean confirmIdentity()throws Exception
	{
		String Password, Key;
		show.AboutProgram();
		System.out.println("This action will require identity confirmation. Please enter credentials to continue.");
		System.out.println("Username: "+Name);
		Password=sha.encodedString(String.valueOf(console.readPassword("Password: ")));
		Key=sha.encodedString(String.valueOf(console.readPassword("Security Key: ")));
		API.Anima.LoginAPI login=new API.Anima.LoginAPI(SB, User, Password, Key);
		return login.status();
	}
	
	private void changeDir()throws Exception
	{
		String tPath=console.readLine("Enter the directory name: ");
		if(tPath.equals(".."))
		{
			prevDir();
			return;
		}
		
		tPath=CurDir+tPath+"/";
		if(checkFile(tPath)==true)
			CurDir=tPath;
		else
			System.out.println("[ ERROR ] : The specified file/directory does not exist.");
		return;
	}
	
	private void prevDir()
	{
		CurDir=CurDir.substring(0, CurDir.length()-1);
		CurDir=CurDir.replace(CurDir.substring(CurDir.lastIndexOf('/'), CurDir.length()), "/");
		if(CurDir.equals("./Users/"))
		{
			System.out.println("[ WARNING ] : File Management cannot be out of user's home path. Reverting to default user's home path.");
			CurDir="./Users/"+User+"/";
		}
		return;
	}
	
	private void listFiles()throws Exception
	{
		
		//String format = "%1$-60s|%2$-50s|%3$-20s\n";
		String format = "%1$-50s|%2$-20s\n";
		if(checkFile(CurDir)==true)
		{
			File dPath=new File(CurDir);
			System.out.println("\n");
			System.out.format(String.format(format, "File Name", "File Size [In KB]\n"));
			for(File file : dPath.listFiles()) 
			{
				//System.out.format(String.format(format, file.getPath().replace(User,Name), file.getName().replace(User,Name), file.length()/1024+" KB"));
				System.out.format(String.format(format, file.getName().replace(User,Name), file.length()/1024+" KB"));
			}
			System.out.println();
		}
		else
			System.out.println("[ ERROR ] : The specified file/directory does not exist.");
		return;
	}
	
	private boolean checkFile(String fName)throws Exception
	{
		f=new File(fName);
		if(f.exists()==false)
			return false;
		return true;
	}
	
	private void makeDir()
	{
		try
		{
			String mkFile=console.readLine("Enter the name of the new Directory: ");
			mkFile=CurDir+mkFile+"/";
			if(checkFile(mkFile)==false)
			{
				f=new File(mkFile);
				f.mkdir();
			}
			else
				System.out.println("The specified directory name already exists. Please try again.");
			return;
		}
		catch (Exception E)
		{
			//troubleshooting tips here
			E.printStackTrace();
		}
	}
	
	private void del()
	{
		try
		{
			String delFile=console.readLine("Enter the name of the the file/folder to be deleted: ");
			delFile=CurDir+delFile;
			if(checkFile(delFile)==true)
			{
				f=new File(delFile);
				f.delete();
			}
			else
				System.out.println("[ ERROR ] : The specified file/directory does not exist.");
			return;
		}
		catch (Exception E)
		{
			//troubleshooting tips here
			E.printStackTrace();
		}
	}
	
	private void rename()
	{
		try
		{
			String rnFile=console.readLine("Enter the name of the file/folder to rename: ");
			rnFile=CurDir+rnFile;
			if(checkFile(rnFile)==true)
			{
				f=new File(rnFile);
				File fn=new File(CurDir+console.readLine("Enter new name: "));
				f.renameTo(fn);
			}
			else
				System.out.println("[ ERROR ] : The specified file/directory does not exist.");
			return;
		}
		catch (Exception E)
		{
			//troubleshooting tips here
			E.printStackTrace();
		}
	}
}