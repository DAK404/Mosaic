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

package Mosaic.Core;

//import java libraries
import java.io.Console;
import java.io.File;



/**
* A class which handles modules and user inputs after login.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B02-Mosaic-MainMenu-CORE |
* | Class Name  :  MainMenu                 |
* | Since       :  0.0.1, 17-August-2013    |
* | Updated on  :  0.7.3, 29-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class MainMenu {
    private boolean Admin = false;
    private String User = "";
    private String Name = "";
    private char prompt = '*';
    private String cmd = "";
    private String PIN_Value="";
    private File usrDir = null;

    //Initialize the stream for accepting inputs from the user.
    Console console = System.console();

    /**
     * Constructor to initialize MainMenu data
     *
     * @param name          : Used to receive the name of the user
     * @param Username      : Used to receive the username of the user
     * @param Administrator : Used to receive the Administrator status of the user
     * @param Pin           : Used to receive the Unlock PIN for the logged in user
     */
    protected MainMenu(String name ,String Username, boolean Administrator, String Pin) {
            if (Username.equalsIgnoreCase("") | Username.equalsIgnoreCase(" ")) {
                System.out.println("Unauthorized user detected. Exiting Program... ");
                System.exit(0);
            } else {
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

    /**
     * A script which is run once, to deal with functionalities and sub-modules
     *
     * @throws Exception  : Throws any exception caught during runtime/execution
     */
    protected void MenuScript() throws Exception {
        try
        {
            choices();
            System.exit(0);
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    /**
     * Method which loads and runs the sub-modules and functionalities
     *
     * @throws Exception  : Throws any exception caught during runtime/execution
     */
    private void choices() throws Exception {
        try
        {
            while (true) {
                if(usrDir.exists()==false)
                    setupFolders();
                System.gc();
                new Mosaic.API.Information().AboutProgram();
                System.out.println("Welcome Back, " + Name + "!");
                System.out.print(Name + prompt + "> ");
                cmd = console.readLine().toLowerCase();
                switch (cmd) {
                    //Update program
                case "update":
                    new Mosaic.API.Tools.Update.UpdateInterface().updateInterface();
                    break;

                    //Create User (Anima) program
                case "create user":
                    new Mosaic.API.Anima.AddUser(User, Name, Admin).AddUserScript();
                    break;

                    //read program info
                    //help
                case "help":
                    new Mosaic.API.Tools.ReadFile().ShowHelp("Help/MainMenu.manual");
                    break;

                    //changelog
                case "changelog":
                    new Mosaic.API.Tools.ReadFile().ShowHelp("Changelog.txt");
                    break;

                    //readme
                case "readme":
                    new Mosaic.API.Tools.ReadFile().ShowHelp("Readme.txt");
                    break;

                    //eula
                case "eula":
                    new Mosaic.API.Tools.ReadFile().ShowHelp("License.eula");
                    break;

                case "credits":
                    new Mosaic.API.Tools.ReadFile().ShowHelp("Credits.txt");
                    break;

                    //change the password
                case "update info":
                    new Mosaic.API.Anima.UpdateCredentials(User, Name).ChangePasswordRoutine();
                    break;

                    //file manager
                case "file manager":
                    new Mosaic.API.Tools.FileManager.FileManager(User, Name).FileManagerScript();
                    break;

                    //Settings program
                case "settings":
                    if (Admin == false)
                        console.readLine("[ATTENTION] Standard user accounts cannot change settings. Contact the administrators for further information.");
                    else
                        new Mosaic.Core.SettingsInterface(Admin).Menu();
                    break;

                    //Lock the console
                case "lock console":
                    consoleLock();
                    break;

                    //refresh the program
                case "":
                    break;

                    //Exit the program
                case "exit":
                    return;

                    //Default String when the command module is not found.
                default:
                    console.readLine("\nUnrecognized command: " + cmd + "\nPlease enter a valid command or module name");
                    break;
                }
            }
        } catch(Exception E) {
            new Mosaic.API.ErrorHandler().handleError(E);
        }
    }

    /**
    * Method which provides the Lock console functionality.
    *
    * @throws Exception  : Throws any exception caught during runtime/execution
    */
    private void consoleLock()throws Exception
    {
        byte count=5;
        do
        {
            new Mosaic.API.Information().AboutProgram();
            System.out.println(Name+"> Locked.");
        }while(!(console.readLine("~AFK> ").toLowerCase().equals("unlock console")));
        while(true)
        {
            new Mosaic.API.Information().AboutProgram();
            System.out.println("Attempts Remaining: "+count);
            System.out.println("Username: "+Name);
            if(new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword("Unlock PIN: "))).equals(PIN_Value))
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

    /**
    * Method which is run when the user folders are not found or has not been setup
    *
    * @throws Exception  : Throws any exception caught during runtime/execution
    */
    private void setupFolders()throws Exception
    {
        try
        {
            byte i = 0;
            String [] FileList = { User, User+"/Documents", User+"/Downloads", User+"/Miscellaneous" };
            for(i = 0; i < FileList.length; ++i)
            {
                usrDir = new File("./Users/"+FileList[i]+"/");
                usrDir.mkdir();
            }
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }
}