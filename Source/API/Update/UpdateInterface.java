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

package API.Update;

import java.io.*;
import API.*;

/**
 * Program to help the user to download latest version of the program
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_S03<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public class UpdateInterface {
    API.Information DispInfoObj = new API.Information();
    Console console = System.console();

    /**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
    public UpdateInterface() {}

    /**
     * This script will run to update the program to the latest version
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    public void updateInterface() throws Exception {
        DispInfoObj.AboutProgram();
	
		System.out.println("Updater 6.2.4");
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
			System.out.println("SYSTEM> Failed to download update. Network Error.\n");
			System.out.println("Possible Causes:\n- Limited/restricted network access, or firewall rules which prevented downloading the file.\n- The download session was interrupted by a network change.\n- The update location has been moved to a new URL.\n");
			System.out.println("Possible Solutions:\n- Retry the update.\n- Restart the network/routers and check the network connection stability.\n- Contact the Administrator for more information.\n-------------------------\n");
		}
		System.out.println("Press Enter to Restart the program.");
		console.readLine();
		//Restart command has a separate API active for that. This is deprecated code.
		Process p = new ProcessBuilder("java Launcher").inheritIO().start();
		System.exit(0);
	}
    private boolean download() throws Exception {
        try {
            DownloadUpdate downloadProgram = new DownloadUpdate();
            return downloadProgram.download();
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