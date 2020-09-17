/*
 *****************************************************
 *                                                   *
 * ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
 *                                                   *
 *****************************************************
 *                                                   *
 *            THIS CODE IS RELEASE READY.            *
 *                                                   *
 *       THIS CODE HAS BEEN TESTED HEAVILY AND       *
 *       CONSIDERED STABLE. THIS MODULE HAS NO       *
 *       KNOWN ISSUES. CONSIDERED RELEASE READY      *
 *                                                   *
 *****************************************************
 */

package Core;

import java.io.*;
import java.util.*;
import java.text.*;
import Core.*;
import API.*;
import API.Tools.*;
import API.Tools.FileManager.*;
import API.Tools.Update.*;
import API.Anima.*;

/**
 * Program to provide an interface for displaying the Main Menu
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: CM_03<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public final class MainMenu {
	private boolean SB = false;
	private boolean Admin = false;
	private String User = "";
	private String Name = "";
	private char prompt = '*';
	private String cmd = "";
	private String PIN_Value="";
	private File usrDir = null;
	
	//Initialize the streams
	Console console = System.console();
	API.Information ShowInfo = new API.Information();
	API.Tools.ReadFile ViewHelp = new API.Tools.ReadFile();
	API.ErrorHandler eh=new API.ErrorHandler();
	API.RestartProgram restart = new API.RestartProgram();
	API.Tools.Update.UpdateInterface update = new API.Tools.Update.UpdateInterface();
	API.SHA256 sha=new API.SHA256();
	/**
     * This constructor is used to intialize the SecureBoot Variable.
     *
     * @param SecureBoot    : Used to transfer the SecureBoot status to the program
     * @param Username      : Used to receive the username from Login
	 * @param Administrator : Used to get the Administrator status value of the current user
     */
	protected MainMenu(boolean SecureBoot, String name ,String Username, boolean Administrator, String Pin) {
		if (SecureBoot == false) {
			System.out.println("SecureBoot fuse tripped. Exiting Program... ");
			System.exit(0);
		} else {
			if (Username.equalsIgnoreCase("") | Username.equalsIgnoreCase(" ")) {
				System.out.println("Unauthorized user detected. Exiting Program... ");
				System.exit(0);
			} else {
				SB = SecureBoot;
				Name = name;
				User = Username;
				usrDir = new File("./Users/"+User+"/");
				if (Administrator == true) {
					Admin = true;
					prompt = '!';
				}
				PIN_Value=Pin;
			}
		}
	}

	/**
     * A script which executes the menu script for entering the required module to load
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
	protected void MenuScript() throws Exception {
		choices();
		System.exit(0);

	}

	private void choices() throws Exception {
		try {			
			//API.Download.DownloadInterface at = new API.Download.DownloadInterface(User);
			API.Tools.ReadFile rf = new API.Tools.ReadFile();
			API.Tools.FileManager.FileManager fm=new API.Tools.FileManager.FileManager(SB, User ,Name);
			API.Anima.AddUser adU = new API.Anima.AddUser(Admin);
			API.Anima.ChangePassword cp = new API.Anima.ChangePassword(User, Name, SB);

			Core.SettingsInterface ae = new Core.SettingsInterface(SB, Admin);
			Messenger oj = new Messenger(SB, Name, Admin);

			
			if(usrDir.exists()==false)
				setupFolders();
			
			while (true) {
				System.gc();
				ShowInfo.AboutProgram();
				System.out.println("Welcome Back, " + Name + "!");
				System.out.print(Name + prompt + "> ");
				cmd = console.readLine().toLowerCase();
				switch (cmd) {
					//Update program
				case "update":
					update.updateInterface();
					break;

					//Chat program
				case "chat":
					if (User.contains(sha.encodedString("Administrator"))) {
						console.readLine("\n[ATTENTION]: Administrator cannot access chat module, please login as standard user and try again.\nPress Enter to continue.");
					} else {
						oj.MsgScript();
					}
					break;

					//Create User (Anima) program
				case "create user":
					adU.AddUserScript();
					break;

					//read program info
					//help
				case "help":
					ViewHelp.ShowHelp("Help/MainMenu.manual");
					break;

					//changelog
				case "changelog":
					ViewHelp.ShowHelp("Changelog.txt");
					break;

					//readme
				case "readme":
					ViewHelp.ShowHelp("Readme.txt");
					break;

					//eula
				case "eula":
					ViewHelp.ShowHelp("License.eula");
					break;

				case "credits":
					ViewHelp.ShowHelp("Credits.txt");
					break;

					//change the password
				case "change password":
					cp.ChangePasswordRoutine();
					break;
					
					//file manager
				case "file manager":
					fm.FileManagerScript();
					break;

					//restart program
				case "restart":
					console.readLine("All unsaved work will be lost. Press Enter to restart the program.");
					restart.restart();
					break;

					//Settings program
				case "settings":
					if (Admin == false)
						console.readLine("[ATTENTION] Standard user accounts cannot change settings. Contact the administrators for further information.");
					else 
						ae.Menu();
					break;
					
					//Lock the console
				case "lock console":
					consoleLock();
					break;

					//refresh the program
				case "":
					break;

				case "exit":
					return;
					
					//Default String when the command module is not found.
				default:
					console.readLine("\nUnrecognized command: " + cmd + "\nPlease enter a valid command or module name");
					break;
				}
			}
		} catch(Exception E) {
			eh.displayError(E);
		}
	}
	
	
	private void consoleLock()throws Exception
	{
		byte count=5;
		do
		{
			ShowInfo.AboutProgram();
			System.out.println(Name+"> Locked.");
		}while(!(console.readLine("~AFK> ").toLowerCase().equals("unlock console")));
		while(true)
		{
			ShowInfo.AboutProgram();
			System.out.println("Attempts Remaining: "+count);
			System.out.println("Username: "+Name);
			if(sha.encodedString(String.valueOf(console.readPassword("Unlock PIN: "))).equals(PIN_Value))
			{
				count=5;
				break;
			}	
			else
			{
				
				count--;
				System.out.println("WARNING : Incorrect PIN entered.");
				if(count==0)
				{
					System.out.println("SYSTEM!> Suspending All Input For 5 Minutes.");
					Thread.sleep(300000);
					count=1;
					System.out.println("Please try again.\nPress enter to continue.");
				}
				console.readLine();
			}
		}
		return;
	}
	
	private void setupFolders()
	{
		byte i = 0;
		String [] FileList = { User, User+"/Documents", User+"/Downloads", User+"/Miscellaneous" };
		for(i = 0; i < FileList.length; i++)
		{
			usrDir = new File("./Users/"+FileList[i]+"/");
			usrDir.mkdir();
		}
	}
}