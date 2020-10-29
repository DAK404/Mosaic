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

package Core;


//import java Libraries
import java.io.*;
import java.sql.*;

//import Mosaic Libraries and APIs
import Core.*;
import API.*;
import API.Anima.*;

/** 
* A class to boot into Mosaic. Encapsulates login program.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B01-Mosaic-Boot-CORE     |
* | Class Name  :  Boot                     |
* | Since       :  0.0.1, 17-August-2014    |
* | Updated on  :  0.5.9, 01-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class Boot
{
	private boolean SB    = false;
	private boolean Admin = false;
	private byte count    = 5;
	private String PIN    = "";
	private String User   = "";
	private String Name   = "";
	
	API.Tools.Update.UpdateInterface update = new API.Tools.Update.UpdateInterface();
	API.RestartProgram a=new API.RestartProgram();
    API.Information DispInfoObj = new API.Information();
    API.Tools.ReadFile ViewHelp = new API.Tools.ReadFile();
	API.SHA256 sha=new API.SHA256();
	
	//Initialize the streams for user inputs and database connection
	Console console = System.console();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	/**
	* Constructor to initialize SecureBoot variable
	*
	* @param SecureBoot :  Initialize the SecureBoot variable
	*/
	public Boot(boolean SecureBoot)
	{
		SB = SecureBoot;
	}

	/**
	* A Script which is run once, which will display a menu for various options.
	*
	* @throws Exception :  Throws any exception caught during runtime/execution
	*/
	public void BootScript()throws Exception
	{
		//Check if the setup has been completed successfully
		File SysChk = new File("./System");
        File UsrChk = new File("./Users");
        if ((SysChk.exists() == false) | (UsrChk.exists() == false)) {
			//SysChk.delete();
			//UsrChk.delete();
            Setup setupObj = new Setup(SB);
            setupObj.SetupScript();
        }
		
		while(menu()==true);
		while(login()==false);
		MainMenu ot = new MainMenu(SB, Name, User , Admin, PIN);
        ot.MenuScript();
	}
	
	
	/**
	* The menu which is run to execute sub-functions.
	*
	* @throws Exception :  Throws any exception caught during runtime/execution
	*/
	private boolean menu()throws Exception
	{
		System.gc();
		DispInfoObj.AboutProgram();
        System.out.println("[ SYSTEM ] > Welcome, Guest!\nTYPE \"HELP\" VIEW THE HELP FILE AND AVAILABLE COMMANDS.\n");
		switch(console.readLine("~guest@system*> ").toLowerCase())
		{
			case "login":
				return false;
					
			case "update":
				update.updateInterface();
				break;
					
			case "help":
				ViewHelp.ShowHelp("Help/Boot.manual");
				break;
				
			case "help login":
				ViewHelp.ShowHelp("Help/LoginAPI.manual");
				break;	
				
			case "eula":
				ViewHelp.ShowHelp("License.eula");
				break;
				
			case "readme":
				ViewHelp.ShowHelp("Readme.txt");
				break;
				
			case "credits":
				ViewHelp.ShowHelp("Credits.txt");
				break;
					
			case "changelog":
				ViewHelp.ShowHelp("Changelog.txt");
				break;
				
			case "restart":
				System.out.println("All unsaved work will be lost. Press Enter to restart the program.");
				console.readLine();
				a.restart();
				break;
					
			case "":
				break;
					
			case "exit":
				System.exit(0);
					
			default:
				System.out.println("Unrecognized command. Please enter a valid command name.");
				console.readLine();
				break;
		}
		return true;
	}
	
	
	/**
	* A method which is used to login to the program. Challenges the database with the user database
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private boolean login()throws Exception
	{
		DispInfoObj.AboutProgram();
        System.out.println("Login Attempts Remaining: " + count + "\n");
		
		//store the credentials given by the user
        User            = sha.encodedString(console.readLine("Username: "));
        String Password = sha.encodedString(String.valueOf(console.readPassword("Password: ")));
        String Key      = sha.encodedString(String.valueOf(console.readPassword("Security Key: ")));
		
		API.Anima.LoginAPI LogObj = new API.Anima.LoginAPI(User, Password, Key);
		
		if (LogObj.status() == true) 
		{
			getDetailsFromDatabase();
			Password = "";
			Key = "";
            return true;
        } 
		else
		{
            //Send the logic control to reduce the count.
            IncorrectLoginCounterLogic();
            return false;
        }
	}
	
	/**
	* Method which retrieves the details from the database.
	* 
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void getDetailsFromDatabase()throws Exception
	{
		String url = "jdbc:sqlite:./System/Private/Fractal.db";
		
		try 
		{
			conn = DriverManager.getConnection(url);
			
			PIN  = prepStatement("SELECT PIN FROM FCAD WHERE Username = ? ;", "PIN");
			Name = prepStatement("SELECT Name FROM FCAD WHERE Username = ? ;", "Name");
			if( prepStatement("SELECT Administrator FROM FCAD WHERE Username = ? ;", "Administrator").equals("Yes") )
				Admin=true;
			
        } catch (Exception E) {
			E.printStackTrace();
			System.exit(0);
        }
        finally{
			try{
				pstmt.close();
				rs.close();
				conn.close();
			}
			catch(Exception E){
				System.out.println("Connection couldn't be closed. Error.");
				System.exit(0);
			}
		}
	}
	
	/**
	* Method to encapsulate repeated preparedstatements easily.
	* 
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private String prepStatement(String Command, String param)throws Exception
	{
		pstmt = conn.prepareStatement(Command);
		pstmt.setString(1, User);
        rs = pstmt.executeQuery();
		return rs.getString(param);
	}
	
	/**
	* Method to handle incorrect login attempts and counter values.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void IncorrectLoginCounterLogic() throws Exception 
	{
        //Set the logic to be above 0 and below 5
        if (count > 1 & count <= 5) {
            //Decrement the count and display it
            count--;
            console.readLine("You have " + count + " attempts remaining. Press enter to continue.");
            return;
        } else {
            //Lock user inputs if the attempt threshold is over 5 times for 15 mins.
			DispInfoObj.AboutProgram();
            System.out.println("[ SYSTEM ] > Too many Attempts! Locking all inputs for 15 minutes!");
			Thread.sleep(900000);
			count=1;
			return;
        }
    }
}