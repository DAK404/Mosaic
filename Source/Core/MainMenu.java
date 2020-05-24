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
import API.*;
import API.Download.*;
import API.Anima.*;
import API.Editor.*;


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
public class MainMenu 
{
	private boolean SB = false;
    private boolean Admin = false;
    private String User = "";
	private char prompt='*';
	private String cmd="";
	
    //Initialize the streams
    Console console = System.console();
	API.Information ShowInfo = new API.Information();    
    API.HelpViewer ViewHelp = new API.HelpViewer();
	
	
	/**
     * This constructor is used to intialize the SecureBoot Variable.
     *
     * @param SecureBoot : Used to transfer the SecureBoot status to the program
	 * @param Username   : Used to receive the username from Login
     */
    protected MainMenu(boolean SecureBoot, String Username) {
		if(SecureBoot==false)
		{
			System.out.println("SecureBoot fuse tripped. Exiting Program... ");
			System.exit(0);
		}
		else
		{
			if(Username.equalsIgnoreCase("")|Username.equalsIgnoreCase(" "))
			{
				System.out.println("Unauthorized user detected. Exiting Program... ");
				System.exit(0);				
			}
			else
			{
				SB = SecureBoot;
				User = Username;
				if(Username.equals("Administrator"))
				{
					Admin=true;
					prompt='!';
				}
			}
		}        
    }
	
	/**
     * A script which executes the menu script for entering the required module to load
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
	protected void MenuScript()throws Exception
	{
		choices();
		System.exit(0);
		
	}
	
	private void choices()throws Exception
	{
		try
		{
		API.Download.DownloadInterface at=new API.Download.DownloadInterface(User);
		API.Editor.textEdit ed=new API.Editor.textEdit(User);
		API.Editor.ReadFile re=new API.Editor.ReadFile(User);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		while(true)
		{
			ShowInfo.AboutProgram();
			Date date = new Date();
			System.out.println(dateFormat.format(date));
			System.out.println("Welcome Back, "+User+"!");
			System.out.print(User+prompt+"> ");
			cmd = console.readLine();
			if(cmd.equalsIgnoreCase("Update"))
			{
				API.Update.UpdateInterface update = new API.Update.UpdateInterface();
				update.updateInterface();
			}
			else if(cmd.equalsIgnoreCase("chat") || cmd.equalsIgnoreCase("Messenger"))
			{
				if(User.contains("Administrator")){
					System.out.println("[ATTENTION]: ADMINISTRATOR CANNOT ENTER THE CHATROOM. LOGIN AS A ANOTHER USER. PRESS ENTER TO CONTINUE.");
					console.readLine();
				}
				else{
					Messenger oj=new Messenger(SB, User, Admin);
					oj.MsgScript();
				}
			}
		
			else if(cmd.equalsIgnoreCase("Download"))
			{
				at.DInterfaceScript(SB);
			}
			else if(cmd.equalsIgnoreCase("Decrypt"))
			{
				at.Decrypt();
			}
			else if(cmd.equalsIgnoreCase("Encrypt"))
			{
				at.Encrypt();
			}
			else if(cmd.equalsIgnoreCase("Create User"))
			{
				API.Anima.AddUser adU=new API.Anima.AddUser();
				adU.AddUserScript();
			}
			else if(cmd.equalsIgnoreCase("logout"))
			{
				new ProcessBuilder("cmd", "/c", "java Launcher").inheritIO().start();
				return;
			}
			else if(cmd.equalsIgnoreCase("help"))
			{
				API.HelpViewer hlp=new API.HelpViewer();
				hlp.ShowHelp("Help/MainMenu.manual");
			}
			//Option to display the License file
            else if (cmd.equalsIgnoreCase("EULA")) {
                ViewHelp.ShowHelp("License.eula");

            }
            //Option to view the program about
            else if (cmd.equalsIgnoreCase("Readme")) {
                ViewHelp.ShowHelp("Readme.txt");

            }
			else if (cmd.equalsIgnoreCase("Credits")) {
                ViewHelp.ShowHelp("Credits.txt");

            }
            //Option to view the changelog
            else if (cmd.equalsIgnoreCase("Changelog")) {
                ViewHelp.ShowHelp("Changelog-Master.txt");

            }
			else if(cmd.equalsIgnoreCase("exit"))
			{
				return;
			}
			else if(cmd.equalsIgnoreCase("Edit"))
			{				
				ed.editScript();
			}
			else if(cmd.equalsIgnoreCase("read file"))
			{				
				re.ShowFile();
			}
			else if(cmd.equalsIgnoreCase("Restart"))
			{
				System.out.println("All unsaved work will be lost. Press Enter to restart the program.");
				console.readLine();
				new ProcessBuilder("cmd", "/c", "java Launcher").inheritIO().start();
				System.exit(0);
			}
			else if(cmd.equalsIgnoreCase(""))
			{
				continue;
			}
			else
			{
				System.out.println("The Module/Command cannot be found. Type help for a list of  commands.\nPress Enter to continue.");
				console.readLine();
			}
		}
		}
			catch(Exception E)
			{
				E.printStackTrace();
			}
	}
}