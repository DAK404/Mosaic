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
import API.Download. *;
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
public class MainMenu {
	private boolean SB = false;
	private boolean Admin = false;
	private String User = "";
	private char prompt = '*';
	private String cmd = "";

	//Initialize the streams
	Console console = System.console();
	API.Information ShowInfo = new API.Information();
	API.HelpViewer ViewHelp = new API.HelpViewer();
	API.ErrorHandler eh=new API.ErrorHandler();
	API.RestartProgram restart = new API.RestartProgram();
	
	/**
     * This constructor is used to intialize the SecureBoot Variable.
     *
     * @param SecureBoot    : Used to transfer the SecureBoot status to the program
     * @param Username      : Used to receive the username from Login
	 * @param Administrator : Used to get the Administrator status value of the current user
     */
	protected MainMenu(boolean SecureBoot, String Username, boolean Administrator) {
		if (SecureBoot == false) {
			System.out.println("SecureBoot fuse tripped. Exiting Program... ");
			System.exit(0);
		} else {
			if (Username.equalsIgnoreCase("") | Username.equalsIgnoreCase(" ")) {
				System.out.println("Unauthorized user detected. Exiting Program... ");
				System.exit(0);
			} else {
				SB = SecureBoot;
				User = Username;
				if (Administrator == true) {
					Admin = true;
					prompt = '!';
				}
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
			API.Download.DownloadInterface at = new API.Download.DownloadInterface(User);
			API.Editor.EditorInterface ei = new API.Editor.EditorInterface();
			API.Anima.AddUser adU = new API.Anima.AddUser(SB, Admin);
			API.Anima.ChangePassword cp = new API.Anima.ChangePassword(User, SB);
			API.Update.UpdateInterface update = new API.Update.UpdateInterface(SB);
			
			Core.SettingsInterface ae = new Core.SettingsInterface(SB, Admin);
			Messenger oj = new Messenger(SB, User, Admin);

			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			while (true) {
				ShowInfo.AboutProgram();
				Date date = new Date();
				System.out.println(dateFormat.format(date) + "\n");
				System.out.println("Welcome Back, " + User + "!");
				System.out.print(User + prompt + "> ");
				cmd = console.readLine();
				cmd = cmd.toLowerCase();
				switch (cmd) {
					//Update program
				case "update":
					update.updateInterface();
					break;

					//Chat program
				case "chat":
					if (User.contains("Administrator")) {
						System.out.println("\n[ATTENTION]: Administrator cannot access chat module, please login as standard user and try again.\nPress Enter to continue. ");
						console.readLine();
					} else {
						oj.MsgScript();
					}
					break;

					//Download program
				case "download":
					at.DInterfaceScript(SB);
					break;

					//Encrypt program
				case "encrypt":
					at.Encrypt();
					break;

					//Decrypt program
				case "decrypt":
					at.Decrypt();
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
					ViewHelp.ShowHelp("Changelog-Master.txt");
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

					//Text editor
				case "edit":
					ei.EditorScript("Write", User);
					break;

					//read file
				case "read file":
					ei.EditorScript("Read", User);
					break;

					//restart program
				case "restart":
					System.out.println("All unsaved work will be lost. Press Enter to restart the program.");
					console.readLine();
					restart.restart();
					break;

					//Settings program
				case "settings":
					if (Admin == false) {
						System.out.println("[ATTENTION] Standard user accounts cannot change settings. Contact the administrators for further information.");
						console.readLine();
					} else ae.SettingsMenu();
					break;

					//refresh the program
				case "":
					break;

				case "exit":
					return;
					
					//Default String when the command module is not found.
				default:
					System.out.println("\nUnrecognized command: " + cmd + "\nPlease enter a valid command or module name");
					console.readLine();
					break;
				}
			}
		} catch(Exception E) {
			eh.displayError(E);
		}
	}
}