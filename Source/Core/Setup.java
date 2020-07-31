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

import API.*;
import API.Anima.*;
import java.io.*;
import java.util.*;


/**
 * Program for one time setup for initialization of the dependencies
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: CM_01_S1<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class Setup {
    private String curDir = System.getProperty("user.dir");
    private boolean stat;
    private String Pass = "";
    private String Key = "";
    private boolean SB = false;
    private boolean test = false;
    
	API.HelpViewer moduleR = new API.HelpViewer();
	Information obje = new Information();

	Console console = System.console();
	
    /**
     * This constructor is used to intialize the SecureBoot Variable.
     *
     * @param SecureBoot : Used to transfer the SecureBoot status to the program
     */
    protected Setup(boolean SecureBoot) {
        SB = SecureBoot;
    }

    /**
     * A script which executes the setup process
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    protected void SetupScript() throws Exception {
        ShowWelcomeScreen();
        ShowPrerequisites();
        CreateDependencies();
		StoreSettings();
        API.Anima.CreateDB o = new API.Anima.CreateDB();
        o.CreateDB();
        console.readLine();
        GetAdminData();
    }

    private void ShowWelcomeScreen() throws Exception {
        obje.AboutProgram();
        System.out.println("Welcome to Mosaic!\nThis program is a platform to use multiple tools in form of modules with the least amount of trouble!\n");
        System.out.println("[WARNING] : DO NOT QUIT PROGRAM DURING SETUP.\n");
        System.out.println("Close this program if you want to setup another time, or press enter to continue.");
        console.readLine();
    }

    private void ShowPrerequisites() throws Exception {
        obje.AboutProgram();
        System.out.println("1. Legal and Important Information.\nThis will show the license of the program.\nIf you accept the the license, you declare that you have read and understood the license, you consent to abide the conditions specified.\nPress Enter to Continue.");
        console.readLine();
        moduleR.ShowHelp("License.eula");
        System.out.print("\nDo you agree with the terms and conditions of the license of the program shown above? [Y/N]: ");
        String TAC = console.readLine();
        TAC = TAC.toUpperCase();
        if (TAC.equals("Y")) {
            moduleR.ShowHelp("Readme.txt");
            moduleR.ShowHelp("ThisMonth.txt");
            moduleR.ShowHelp("Credits.txt");
        } else {
            System.out.println("Exiting the program...");
            obje.ClearScreen();
            System.exit(100);
        }
    }

    private void CreateDependencies() throws Exception {
        //this function will create all the required folder and files required to run the program.
        //This array holds the data required to be created, which are nothing but directories/folders.
        obje.AboutProgram();
        System.out.println("Creating Required Folders.\n\n[ATTENTION] : CREATING DEPENDENCIES.\n[WARNING] : DO NOT REMOVE STORAGE DRIVES, TURN OFF DEVICE, ALTER ANY NETWORK CONNECTIONS OR TERMINATE APPLICATION.\nDOING SO MAY CORRUPT PROFILE AND/OR MAY LEAD TO THE LOSS OF DATA.\n");
        String FilesList[] = {
            "/System",
            "/System/Public",
			"/System/Public/Logs",
            "/System/Private",
            "/System/Private/Info",
            "/System/Private/_hashes",
            "/System/Private/ChatRooms",
			"/System/Private/Settings",
            "/System/Public/ChatRooms",
            "/Users"
        };
        for (int i = 0; i < FilesList.length; i++) {
            //Convert the String format into a file format.
            File DirCrt = new File(curDir + FilesList[i]);
            //Create the directory.
            stat = DirCrt.mkdir();
            if (stat == true) {
                System.out.println("Created : [*] " + FilesList[i]);
            } else {
                System.out.println("Error while creating: " + FilesList[i]);
                System.out.println("Aborting... Recommended that you run the Troubleshoot program. ");
                break;
            }
        }
        if (stat == true)
            System.out.println("SYSTEM> Folders Created Successfully.");
		else
		{
			System.out.println("Failed to Created Dependencies. Please Try Again.");
			System.exit(0);
		}   
	}

	private void StoreSettings()throws Exception
	{
		Core.SettingsInterface ae = new Core.SettingsInterface(SB, true);
		ae.SettingsMenu();
		System.out.println("SYSTEM> Settings Stored Successfully!");
	}

    private void GetAdminData() throws Exception {
        //The admin username will always be #Administrator
        //The user must provide a password and a security key
        while (true) {
            obje.AboutProgram();
            System.out.println("2. Configure Administrator Account");
            System.out.println("This helps in setting up an administrator account.\nNote:\n-You cannot go online when logged in as an Administrator.\n-This account will be locally stored on this PC only.\n");
            System.out.println("Username: Administrator");
            System.out.print("Password: ");
            Pass = String.valueOf(console.readPassword());
			if(Pass.length()<8)
			{
				System.out.println("Warning: the length of the password must be greater than 8 for security reasons. Please try again.");
				console.readLine();
				continue;
			}
            //confirm the password
            System.out.print("Confirm Password: ");
            String confirm = String.valueOf(console.readPassword());
            //Security key is optional, can be used for enhanced Security
            System.out.print("Security Key (optional): ");
            Key = String.valueOf(console.readPassword());
            //Confirming the key value
            System.out.print("Confirm Security Key: ");
            String confirmKey = String.valueOf(console.readPassword());

            //Assert logics
            if (confirm.equals(Pass) & confirmKey.equals(Key)) {
                API.Anima.AddUser oa = new API.Anima.AddUser(SB, true);
                oa.AddUserScript("Administrator", Pass, Key);
                System.out.println("Administrator user has been configured. Press enter to continue!");
                console.readLine();
                break;
            } else {
                System.out.println("The Passwords do not match. Please try again. Press Enter to Continue.");
                console.readLine();
                continue;
            }
        }
    }
}