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

import API.*;
import API.Anima.*;
import java.io.*;
import java.util.*;


/**
 * A class which helps to setup the program environment.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B03-Mosaic-Setup-CORE    |
* | Class Name  :  Setup                    |
* | Since       :  0.0.1, 25-September-2013 |
* | Updated on  :  0.8.3, 29-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class Setup {
    private String curDir = System.getProperty("user.dir");
    private boolean stat;
    private String Pass = "";
    private String Key = "";
    private boolean SB = false;
    private boolean test = false;
    
	API.Tools.ReadFile moduleR = new API.Tools.ReadFile();
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
	
	/**
	* Displays a welcome screen to user, indicating that the setup routine has started.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
    private void ShowWelcomeScreen() throws Exception 
	{	
		obje.AboutProgram();
		System.out.println("Welcome to Mosaic!");
		System.out.println("This program needs to be setup before it can be used. You may not use the program until the setup has been complete.\n");
		System.out.println("[ ATTENTION ] : DO NOT QUIT THE PROGRAM DURING THE SETUP PHASE. DOING SO MAY RENDER THE PROGRAM UNUSABLE!\n");
		System.out.println("--------------------------------------");
		System.out.println("          - SETUP PROCEDURE -         ");
		System.out.println("--------------------------------------");
		System.out.println(" A. Legal and Important Information");
		System.out.println(" \tA1. Program License and EULA");
		System.out.println(" \tA2. Readme");
		System.out.println(" \tA3. Changelog");
		System.out.println(" \tA4. Credits");
		System.out.println(" B. Program Configuration");
		System.out.println(" \tB1. Directory setup");
		System.out.println(" \tB2. Database setup");
		System.out.println(" \tB3. Save policy data");
		System.out.println(" C. Administrator user setup");
		System.out.println(" \tC1. Setup Password");
		System.out.println(" \tC2. Setup Security Key");
		System.out.println(" \tC3. Setup Unlock PIN");
		System.out.println("--------------------------------------\n");
		console.readLine("Press enter to continue, or press the [CTRL] + [C] keys to exit.");
		return;
    }

	/**
	* Shows the important imformation regarding the program to the user during setup.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
    private void ShowPrerequisites() throws Exception {
        obje.AboutProgram();
        moduleR.ShowHelp("License.eula");
        System.out.print("\nDo you agree with the terms and conditions of the license of the program shown above? [Y/N]: ");
        String TAC = console.readLine();
        TAC = TAC.toUpperCase();
        if (TAC.equals("Y")) 
		{
            moduleR.ShowHelp("Readme.txt");
            moduleR.ShowHelp("ThisMonth.txt");
            moduleR.ShowHelp("Credits.txt");
        } else {
            obje.ClearScreen();
            System.exit(100);
        }
		return;
    }

	/**
	* Helps to create the required folders and initialize the program environment.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
    private void CreateDependencies() throws Exception {
        //this function will create all the required folder and files required to run the program.
        //This array holds the data required to be created, which are nothing but directories/folders.
        obje.AboutProgram();
        System.out.println("B. Program Configuration.\n\n[ATTENTION] : CREATING DEPENDENCIES.\n[WARNING] : DO NOT REMOVE STORAGE DRIVES, TURN OFF DEVICE, ALTER ANY NETWORK CONNECTIONS OR TERMINATE APPLICATION.\nDOING SO MAY CORRUPT PROFILE AND/OR MAY LEAD TO THE LOSS OF DATA.\n");
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
        for (int i = 0; i < FilesList.length; ++i) {
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
            System.out.println("\n[ SYSTEM ] > Folders Created Successfully.");
		else
		{
			System.out.println("Failed to Created Dependencies. Please Try Again.");
			System.exit(0);
		}
		return;
	}

	/**
	* Stores initial settings to file.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
	private void StoreSettings()throws Exception
	{
		Core.SettingsInterface ae = new Core.SettingsInterface(SB, true);
		ae.resetInterface();
		return;
	}

	/**
	* Receive inputs from the user to configure the Administrator account.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution
	*/
    private void GetAdminData() throws Exception {
        //The admin username will always be #Administrator
        //The user must provide a password and a security key
		obje.AboutProgram();
		System.out.println("C. Administrator user setup");
		console.readLine("This helps in setting up an administrator account.\nNote:\n-You cannot go online when logged in as an Administrator.\n-This account will be locally stored on this PC only.\n");
		API.Anima.AddUser oa = new API.Anima.AddUser(SB, true, "Administrator", "Administrator");
		oa.Setup();
		return;
    }
}