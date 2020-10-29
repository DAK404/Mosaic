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
 
package API.Tools.Update;

import java.io.*;
import java.util.*;
import API.*;

/**
* A class which is a front end to update the program.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  BB3-Mosaic-Updater-Update|
* | Class Name  :  UpdateInterface          |
* | Since       :  0.0.1, 06-February-2020  |
* | Updated on  :  0.2.6, 29-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class UpdateInterface {
	private boolean SB=false;
	
    API.Information DispInfoObj = new API.Information();
	API.RestartProgram rp=new API.RestartProgram();
    Console console = System.console();

    /**
     * This constructor is a stub.
     */
    public UpdateInterface() 
	{
	}

    /**
     * This script will run to update the program to the latest version
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    public void updateInterface() throws Exception {
		
		API.policyEnforce pe=new API.policyEnforce("update");
		
		if(pe.checkPolicy()==false)
		{
			return;
		}
		
        DispInfoObj.AboutProgram();
	
		System.out.println("Updater 8.0.0");
		System.out.println("Release Builds.");
		System.out.println("SYSTEM> Initiating update.\n[ATTENTION] : THE UPDATER WILL DOWNLOAD THE LATEST RELEASE AND INSTALL IT.\n[WARNING] : DO NOT CHANGE NETWORK STATES, REMOVE DRIVES, TURN OFF DEVICE OR CLOSE THIS PROGRAM. DOING SO MAY RENDER THE PROGRAM UNUSABLE.\n[ATTENTION] : You will be logged out of your current session, and will need to restart the program and re-login to continue.\n");
		System.out.println("-------------------------");
		System.out.println("SYSTEM> Downloading the Official Stable Build from the release channel.");
		
		if (download() == true) {
			System.out.println("SYSTEM> Update downloaded successfully.");
			System.out.println("SYSTEM> Update file location: ./Update.zip\n");
			System.out.println("Installing update from ./Update.zip");
			if (install() == true)
				System.out.println("SYSTEM> Update installed successfully.");
			else {
				System.out.println("SYSTEM> Failed to install update.\n");
				System.out.println("Possible Causes:\n- The downloaded update file was partially downloaded or was corrupt.\n- The download session was interrupted by a network change.\n- The update location has been moved to a new URL.\n\n");
				System.out.println("Possible Solutions:\n- Retry the update.\n- Restart the network/routers and check the network connection stability.\n- Contact the Administrator for more information.\n\n");
			}
		} else {
			System.out.println("SYSTEM> Failed to download update. Error.\n");
			System.out.println("Possible Causes:\n- Limited/restricted network access, or firewall rules which prevented downloading the file.\n- The download session was interrupted by a network change.\n- The update location has been moved to a new URL.\n");
			System.out.println("Possible Solutions:\n- Retry the update.\n- Restart the network/routers and check the network connection stability.\n- Contact the Administrator for more information.\n-------------------------\n");
		}
		System.out.println("Press Enter to Restart the program.");
		console.readLine();
		rp.restart();
	}
    private boolean download() throws Exception {
        try {
            API.Tools.Download du=new API.Tools.Download();
            return du.updateProgram();
        } catch (Exception E) {
            return false;
        }
    }

    private boolean install() throws Exception {
        try {
            InstallUpdate installProgram = new InstallUpdate();
            return installProgram.install();
        } catch (Exception E) {
            return false;
        }
    }
}