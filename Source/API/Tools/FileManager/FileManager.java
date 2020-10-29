/*
 *****************************************************
 *                                                   *
 * ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
 *                                                   *
 *****************************************************
 *                                                   *
 *            THIS CODE IS RELEASE READY.            *
 *                                                   *
 *      THIS CODE HAS BEEN TESTED, REVIEWED AND      *
 *      REVISED. THIS CODE HAS NO KNOWN ISSUES,      *
 *      HENCE IT IS CONSIDERED AS RELEASE READY      *
 *                                                   *
 *****************************************************
 */
 
package API.Tools.FileManager;

import java.io.*;

import API.*;
import API.Anima.*;


/**
* A class to implement the FileManager functionality
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  BA3-Mosaic-FileMgr-FMGR  |
* | Class Name  :  FileManager              |
* | Since       :  0.0.1, 17-August-2020    |
* | Updated on  :  0.2.1, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
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
	
	/**
	* Constructor to initialize the SecureBoot variable, the current user's username and name.
	*
	* @param SecureBoot : Receive the secure boot status from the previous program.
	* @param Username   : Receive the username of the account from the previous program.
	* @param name       : Receive the name of the user to make the user path and username readable.
	*/
	public FileManager(boolean SecureBoot, String Username, String name)
	{
		if(SecureBoot==false)
			System.exit(0);
		SB=SecureBoot;
		User=Username;
		Name=name;
	}
	
	
	/**
	* Method to access the encapsulated FileManager publicly.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
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
	
	/**
	* Method which actually implements the file manager functionality.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution.
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
							
				case "tree":
							TreeView();
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
				
				case "encrypt": 
							enc.encr(User, CurDir);
							break;
				
				case "decrypt": 
							dec.dcr(User, CurDir);
							break;
				
				case "read file":
							rF.userReadFile(CurDir);
							break;
				
				case "edit file": 
							te.editScript(CurDir);
							break;
							
				case "download":
							dl.downloadFile(User, CurDir);
							break;
							
				case "copy":
							copy_move_frontend(false);
							break;
				
				case "move":
							copy_move_frontend(true);
							break;
							
				case "rename":
							rename();
							break;
				
				case "": 
							break;
				
				default: System.out.println("Unrecognized command. Type Help to know available commands.\n");
						break;
			}
			
		}
	}
	
	
	// --- make a common interface to check if the directory exists or not. --- //
	
	/**
	* Method which confirms the identity of the user currently logged in.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution.
	*/
	private boolean confirmIdentity()throws Exception
	{
		String Password, Key;
		show.AboutProgram();
		System.out.println("This action will require identity confirmation. Please enter credentials to continue.");
		System.out.println("Username: "+Name);
		Password=sha.encodedString(String.valueOf(console.readPassword("Password: ")));
		Key=sha.encodedString(String.valueOf(console.readPassword("Security Key: ")));
		API.Anima.LoginAPI login=new API.Anima.LoginAPI(User, Password, Key);
		return login.status();
	}
	
	
	/**
	* Method to implement the functionality of changing the current directory.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/	
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
	
	
	/**
	* A sub-method within changeDir() implemented to change to the previous directory.
	*
	* NOTE: Program will not allow the directory to access levels above the user home path.
	*/
	private void prevDir()throws Exception
	{
		CurDir=CurDir.substring(0, CurDir.length()-1);
		CurDir=CurDir.replace(CurDir.substring(CurDir.lastIndexOf('/'), CurDir.length()), "/");
		if(CurDir.equals("./Users/"))
		{
			System.out.println("[ WARNING ] : File Management cannot be above user's home path. Reverting to user's home path.");
			CurDir="./Users/"+User+"/";
		}
		return;
	}
	
	/**
	* Method to print the tree view of the given path.
	*
	* @throws Exception : Throws any exception caught during runtime/execution.
	*/
	private void TreeHelper(int indent, File file)
	{
		System.out.print("|");
		for (int i = 0; i < indent; ++i)
		  System.out.print('-');
		System.out.println(file.getName().replace(User,Name + " [ HOME PATH ROOT ]"));
		if (file.isDirectory()) {
		  File[] files = file.listFiles();
		  for (int i = 0; i < files.length; ++i)
			TreeHelper(indent + 2, files[i]);
		}
	}
	
	/**
	* Method to view the files in a tree hierarchical view.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution.
	*/
	private void TreeView()throws Exception
	{
		try
		{
			File tree=new File(CurDir);
			System.out.println("\n--- [ TREE VIEW ] ---\n");
			TreeHelper(0, tree);
			System.out.println();
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}

	/**
	* Method to implement functionality to list the files in a given directory.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
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
	
	/**
	* A common method used to check if a file exists in a given directory or not.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private boolean checkFile(String fName)throws Exception
	{
		f=new File(fName);
		if(f.exists()==false)
			return false;
		return true;
	}
	
	/**
	* Method to implement the functionality to create a new directory
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void makeDir()throws Exception
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
	
	/**
	* Method to implement the functionality to delete a given file or directory from a specified path.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void del()throws Exception
	{
		try
		{
			String delFile=console.readLine("Enter the name of the the file/folder to be deleted: ");
			delFile=CurDir+delFile;
			if(checkFile(delFile)==true)
			{
				f=new File(delFile);
				if(f.isDirectory()==true)
				{
					delHelper(f);
				}
				else
				{
					f.delete();
				}
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
	
	/**
	* A sub-method which helps in deleting directories within the directory to be deleted.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void delHelper(File delfile)throws Exception
	{
		if (delfile.listFiles() != null) 
		{
			for (File fock : delfile.listFiles()) 
				delHelper(fock);
		}
		delfile.delete();
	}
	
	/**
	* A method to implement the functionality to rename a given file or folder.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
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
	
	/**
	* Method to copy or move the files in file manager.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void copy_move_frontend(boolean move)throws Exception
	{
		try
		{
			String source=console.readLine("Enter the source file/directory name: ");
			String destination=console.readLine("Enter the destination file/directory name: ");
			File s=new File(CurDir+source);
			File d=new File(CurDir+destination);
			copy_move_helper(s, d);
			if(move==true)
			{
				delHelper(s);
			}
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	/**
	* Method which will help in copying or moving the files.
	*
	* @param src : pass the source file to the implementation .
	* @param dest : pass the destination file to the implementation.
	* @throws Exception : Throws any exception caught during runtime/execution
	*/
	private void copy_move_helper( File src, File dest ) throws Exception 
	{
		try
		{
			if( src.isDirectory() )
			{
				dest.mkdirs();
				for( File sourceChild : src.listFiles() ) 
				{
					File destChild = new File( dest, sourceChild.getName() );
					copy_move_helper( sourceChild, destChild );
				}
			} 
			else
			{
				InputStream in = new FileInputStream( src );
				OutputStream out = new FileOutputStream( dest );
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) 
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
}