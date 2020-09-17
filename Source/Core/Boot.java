package Core;

import java.io.*;
import java.sql.*;

import Core.*;
import API.*;
import API.Anima.*;

public final class Boot
{
	private boolean SB    = false;
	private boolean Admin = false;
	private byte count    = 5;
	private String PIN    = "";
	private String User   = "";
	private String Name   = "";
		
	API.Anima.AddUser abc = new API.Anima.AddUser(false);
	API.Tools.Update.UpdateInterface update = new API.Tools.Update.UpdateInterface();
	API.RestartProgram a=new API.RestartProgram();
    API.Information DispInfoObj = new API.Information();
    API.Tools.ReadFile ViewHelp = new API.Tools.ReadFile();
	API.SHA256 sha=new API.SHA256();
	
	Console console = System.console();
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public Boot(boolean SecureBoot)
	{
		SB = SecureBoot;
	}

	public void BootScript()throws Exception
	{
		File SysChk = new File("./System");
        File UsrChk = new File("./Users");
        if ((SysChk.exists() == false) & (UsrChk.exists() == false)) {
            Setup setupObj = new Setup(SB);
            setupObj.SetupScript();
        }
		
		while(menu()==true);
		while(login()==false);
		MainMenu ot = new MainMenu(SB, Name, User , Admin, PIN);
        ot.MenuScript();
	}
	
	private boolean menu()throws Exception
	{
		System.gc();
		DispInfoObj.AboutProgram();
        System.out.println("SYSTEM> Enter commands to access modules.\n[Type 'HELP' to get command list]\n");
		switch(console.readLine("~GUEST> ").toLowerCase())
		{
			case "create user":
				abc.AddUserScript();
				break;
				
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
				System.out.println("\nUnrecognized command.\nPlease enter a valid command or module name");
				console.readLine();
				break;
		}
		return true;
	}
	
	private boolean login()throws Exception
	{
		DispInfoObj.AboutProgram();
        System.out.println("Login Attempts Remaining: " + count + "\n");
        User     = sha.encodedString(console.readLine("Username: "));
        String Password = sha.encodedString(String.valueOf(console.readPassword("Password: ")));
        String Key      = sha.encodedString(String.valueOf(console.readPassword("Security Key: ")));
		API.Anima.LoginAPI LogObj = new API.Anima.LoginAPI(SB, User, Password, Key);
		
		if (LogObj.status() == true) 
		{
			getDetailsFromDatabase();
            return true;
        } 
		else
		{
            //Send the logic control to reduce the count.
            IncorrectLoginCounterLogic();
            return false;
        }
	}
	
	private void getDetailsFromDatabase()throws Exception
	{
		String url = "jdbc:sqlite:./System/Private/Fractal.db";
		
		try 
		{
			conn = DriverManager.getConnection(url);
			
			PIN  = prepStatement("SELECT PIN FROM FSAD WHERE Username = ? ;", "PIN");
			Name = prepStatement("SELECT Name FROM FSAD WHERE Username = ? ;", "Name");
			if( prepStatement("SELECT Administrator FROM FSAD WHERE Username = ? ;", "Administrator").equals("Yes") )
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
	
	private String prepStatement(String Command, String param)throws Exception
	{
		pstmt = conn.prepareStatement(Command);
		pstmt.setString(1, User);
        rs = pstmt.executeQuery();
		return rs.getString(param);
	}
	
	private void IncorrectLoginCounterLogic() throws Exception 
	{
        //Set the logic to be above 0 and below 5
        if (count > 1 & count <= 5) {
            //Decrement the count and display it
            count--;
            console.readLine("You have " + count + " attempts remaining. Press enter to continue.");
            return;
        } else {
            //kick the user out if they have tried more than 5 times.
            System.out.println("[ ERROR ] : ATTEMPT THRESHOLD REACHED. LOCKING INPUTS FOR 5 MINUTES.");
			Thread.sleep(300000);
			count=1;
			return;
        }
    }
}