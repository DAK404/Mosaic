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
import Core.*;
import java.text.*;
import java.util.*;
import java.text.*;
import java.io.*;
import API.*;
import API.Anima.*;

/**
 * Program for users to access the boot menu and provide a login interface.
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: CM_01<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */

public class Boot {
    //initialize basic variables for the entire class
    private String curDir = System.getProperty("user.dir");
    private static boolean SB = false;

    //initialize basic APIs and libraries to be used by the class
    Console console = System.console();
    API.Information DispInfoObj = new API.Information();
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    HelpViewer hv = new HelpViewer();

    /**
     * This constructor is used to intialize the SecureBoot Variable.
     *
     * @param SecureBoot : Used to transfer the SecureBoot status to the program
     */
    public Boot(boolean SecureBoot) {
        if (SecureBoot == false) {
            System.out.println("SecureBoot fuse tripped. Exiting Program... ");
            System.exit(0);
        } else {
            SB = SecureBoot;
        }
    }

    /**
     * A routine which is publicly accessible for the programs to run the Boot program from.
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    public void BootRoutine() throws Exception 
	{
		Login obj = new Login(SB);
        File SysChk = new File(curDir + "/System");
        File UsrChk = new File(curDir + "/Users");
        if ((SysChk.exists() == false) & (UsrChk.exists() == false)) {
            Setup setupObj = new Setup(SB);
            setupObj.SetupScript();
        }

        menu();
		obj.ReceiveLoginReq();
    }

    private void menu() throws Exception {
		API.Anima.AddUser abc = new API.Anima.AddUser(SB, false);
        API.HelpViewer ViewHelp = new API.HelpViewer();
		API.Update.UpdateInterface update = new API.Update.UpdateInterface(SB);
		API.RestartProgram a=new API.RestartProgram();
		
		
        String command = "";
		
        while (true) 
		{
            //A feature to accept input to run a certain module/feature via a non priviliged guest mode
            DispInfoObj.AboutProgram();
            Date date = new Date();
            System.out.println(dateFormat.format(date)+"\n");
            System.out.println("SYSTEM> Enter commands to access modules.\n\n[Type 'HELP' to get command list]");
            System.out.print("~GUEST> ");
            command = console.readLine();
			command = command.toLowerCase();
			
			switch(command)
			{
				case "Exit":
					System.exit(0);
				
				case "create user":
					abc.AddUserScript();
					break;
				
				case "login":
					return;
					
				case "update":
					update.updateInterface();
					break;
					
				case "help":
					ViewHelp.ShowHelp("Help/Boot.manual");
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
					ViewHelp.ShowHelp("Changelog-Master.txt");
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
					System.out.println("\nUnrecognized command: " + command + "\nPlease enter a valid command or module name");
					console.readLine();
					break;
			}
        }
		

    }
}

//---------------------------------------------------------------------------------------------------------------------------------

class Login {
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static String User = "";
    private static byte count = 5;
    private boolean SB = false;
	private static boolean Admin;
    Console console = System.console();


    Login(boolean SecureBoot) {
        if (SecureBoot == false) {
            System.out.println("SecureBoot fuse tripped. Exiting Program... ");
            System.exit(0);
        } else {
            SB = SecureBoot;
        }
    }
    private boolean GetLoginInfo() throws Exception {
        //the login interface to the program.
        API.Information DispInfoObj = new API.Information();
        DispInfoObj.AboutProgram();
        Date date = new Date();
        System.out.println(dateFormat.format(date) + "\n");
        System.out.println("Login Attempts Remaining: " + count + "\n");
        System.out.print("Username: ");
        User = console.readLine();
        //This is an assert added so that the username cannot be left blank.
        if (User.equals("") || User.contains(" ")) {
            System.out.println("Error: Invalid Username.");
            IncorrectLoginCounterLogic(count);
        }
        //accept the security key and the password to authenticate the user
        System.out.print("Password: ");
        String Password = String.valueOf(console.readPassword());
        System.out.print("Security Key: ");
        String Key = String.valueOf(console.readPassword());

        API.Anima.LoginAPI LogObj = new API.Anima.LoginAPI(SB, User, Password, Key);

        if (LogObj.status() == true) {
				Admin=LogObj.checkAdmin(User);
            return true;
        } else {
            //Send the logic control to reduce the count.
            IncorrectLoginCounterLogic(count);
            return false;
        }
    }

    private void IncorrectLoginCounterLogic(int Counter) throws Exception {
        //Set the logic to be above 0 and below 5
        if (Counter > 1 & Counter <= 5) {
            //Decrement the count and display it
            count--;
            System.out.println("You have " + count + " attempts remaining. Press enter to continue.");
            console.readLine();
            return;
        } else {
            //kick the user out if they have tried more than 5 times.
            System.out.println("Error: Too many attempts. Try again later.");
            //Exit from the program
            System.exit(105);
        }
    }

    protected void ReceiveLoginReq() throws Exception {
        if (SB == false) {
            System.out.println("Error: Access Violation. Illegal Code Access.");
            System.exit(0);
        }
        //Receive the login request and pass it on to receive the Login Info
        Login obj = new Login(SB);
        while (obj.GetLoginInfo() == false);
        MainMenu ot = new MainMenu(SB, User, Admin);
        ot.MenuScript();
    }
}