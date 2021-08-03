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


//import java Libraries
import java.io.Console;
import java.io.File;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
* A class to boot into Mosaic. Encapsulates login program.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B01-Mosaic-Boot-CORE     |
* | Class Name  :  Boot                     |
* | Since       :  0.0.1, 17-August-2014    |
* | Updated on  :  0.5.9, 01-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class Boot
{
    private boolean Admin = false;
    private byte count    = 5;
    private String PIN    = "";
    private String User   = "";
    private String Name   = "";

    //Initialize the streams for user inputs and database connection
    Console console = System.console();

    /**
    * Constructor is a stub.
    */
    public Boot()
    {
    }

    /**
    * A Script which is run once, which will display a menu for various options.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    public void BootScript()throws Exception
    {
        //Check if the setup has been completed successfully
        File SysChk = new File("./System");
        File UsrChk = new File("./Users");
        if ((SysChk.exists() == false) | (UsrChk.exists() == false)) {
            //SysChk.delete();
            //UsrChk.delete();
            Setup setupObj = new Setup();
            setupObj.SetupScript();
        }

        while(menu()==true);
        while(login()==false);
        MainMenu ot = new MainMenu(Name, User , Admin, PIN);
        ot.MenuScript();
    }


    /**
    * The menu which is run to execute sub-functions.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private boolean menu()throws Exception
    {
        System.gc();
        new Mosaic.API.Information().AboutProgram();
        System.out.println("[ SYSTEM ] > Welcome, Guest!\nTYPE \"HELP\" VIEW THE HELP FILE AND AVAILABLE COMMANDS.\n");
        switch(console.readLine("~guest@system*> ").toLowerCase())
        {
            case "login":
                return false;

            case "update":
                new Mosaic.API.Tools.Update.UpdateInterface();
                break;

            case "help":
                new Mosaic.API.Tools.ReadFile().ShowHelp("Help/Boot.manual");
                break;

            case "help login":
                new Mosaic.API.Tools.ReadFile().ShowHelp("Help/LoginAPI.manual");
                break;

            case "eula":
                new Mosaic.API.Tools.ReadFile().ShowHelp("License.eula");
                break;

            case "readme":
                new Mosaic.API.Tools.ReadFile().ShowHelp("Readme.txt");
                break;

            case "credits":
                new Mosaic.API.Tools.ReadFile().ShowHelp("Credits.txt");
                break;

            case "changelog":
                new Mosaic.API.Tools.ReadFile().ShowHelp("Changelog.txt");
                break;

            case "":
                break;

            case "exit":
                System.exit(0);

            default:
                System.out.println("Unrecognized command. Please enter a valid command name.");
                console.readLine();
                break;
        }
        return true;
    }


    /**
    * A method which is used to login to the program. Challenges the database with the user database
    *
    * @throws Exception  : Throws any exception caught during runtime/execution
    */
    private boolean login()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.out.println("Login Attempts Remaining: " + count + "\n");

        //store the credentials given by the user
        User            = new Mosaic.API.SHA256().encodedString(console.readLine("Username: "));
        String Password = new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword("Password: ")));
        String Key      = new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword("Security Key: ")));

        if (new Mosaic.API.Anima.LoginAPI(User, Password, Key).status() == true)
        {
            getDetailsFromDatabase();
            Password = "";
            Key = "";
            return true;
        }
        else
        {
            //Send the logic control to reduce the count.
            IncorrectLoginCounterLogic();
            return false;
        }
    }

    /**
    * Method which retrieves the details from the database.
    *
    * @throws Exception  : Throws any exception caught during runtime/execution
    */
    private void getDetailsFromDatabase()throws Exception
    {


        try
        {
            PIN  = prepStatement("SELECT PIN FROM FCAD WHERE Username = ? ;", "PIN");
            Name = prepStatement("SELECT Name FROM FCAD WHERE Username = ? ;", "Name");
            if( prepStatement("SELECT Administrator FROM FCAD WHERE Username = ? ;", "Administrator").equals("Yes") )
                Admin=true;

        } catch (Exception E) {
            E.printStackTrace();
            System.exit(0);
        }
    }

    /**
    * Method to encapsulate repeated preparedstatements easily.
    *
    * @throws Exception  : Throws any exception caught during runtime/execution
    */
    private String prepStatement(String Command, String param)throws Exception
    {
        String url = "jdbc:sqlite:./System/Private/Fractal.db";
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(Command);
        pstmt.setString(1, User);
        ResultSet rs = pstmt.executeQuery();

        String Result = rs.getString(param);

        pstmt.close();
        rs.close();
        conn.close();
        System.gc();
        return Result;
    }

    /**
    * Method to handle incorrect login attempts and counter values.
    *
    * @throws Exception  : Throws any exception caught during runtime/execution
    */
    private void IncorrectLoginCounterLogic() throws Exception
    {
        //Set the logic to be above 0 and below 5
        if (count > 1 & count <= 5) {
            //Decrement the count and display it
            count--;
            console.readLine("You have " + count + " attempts remaining. Press enter to continue.");
            return;
        } else {
            //Lock user inputs if the attempt threshold is over 5 times for 15 mins.
            new Mosaic.API.Information().AboutProgram();
            System.out.println("[ SYSTEM ] > Too many Attempts! Locking all inputs for 15 minutes!");
            Thread.sleep(900000);
            count=1;
            return;
        }
    }
}