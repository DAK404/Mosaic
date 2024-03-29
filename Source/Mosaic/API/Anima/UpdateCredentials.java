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

package Mosaic.API.Anima;

import java.io.Console;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

/** 
* A class for changing the current user's credentials
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  AA4-Mosaic-ChgPwd-API    |
* | Class Name  :  UpdateCredentials        |
* | Since       :  0.0.1, 30-February-2020  |
* | Updated on  :  0.3.1, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class UpdateCredentials
{	
    private String name="";
    private String user="";
    
    //Store the current password and key
    private String CurrentPassword="";
    private String CurrentKey="";
    
    //Store the new password and key
    private String NewPassword="";
    private String NewKey="";
    private String NewPIN="";
    
    //Initialize the streams and dependencies.
    Console console=System.console();
    Connection conn = null;
    PreparedStatement pstmt = null;

    
    /**
     * Constructor to initialize the logged in user's details
     *
     * @param U : Initializes the current account's username
     * @param N : Initializes the current account's name
     */
    public UpdateCredentials(String U, String N)
    {
        user=U;
        name=N;
    }
    
    /**
     * Method to change the password of the logged in user
     *
     * @throws Exception :  Throws any exception caught during runtime/execution
     */
    public void ChangePasswordRoutine()throws Exception
    {
        if(GetCurrentCredentials()==false)
        {
            console.readLine();
            return;
        }
        while(true)
        {
            new Mosaic.API.Information().AboutProgram();
            System.out.println("[ USER CREDENTIAL MANAGEMENT INTERFACE ]\n\nWhich credential information do you want to change?\nOptions : [ PIN | Password | Key | Help | Exit ]");
            switch(console.readLine(name+">>> ").toLowerCase())
            {
                case "password" : 
                        while(getPass()==false);
                        writeToDB(1);
                        break;
                case "key" :
                        while(getKey()==false);
                        writeToDB(2);
                        break;
                
                case "pin" :
                        while(getPIN()==false);
                        writeToDB(3);
                        break;
                case "help" :
                        new Mosaic.API.Tools.ReadFile().ShowHelp("Help/ChangePassword.manual");
                        break;
                case "exit" :
                        return;
                        
                case "" :
                        break;
                
                default : 
                        console.readLine("Unrecognized command. Please enter a valid command name.");
                        break;
            }
        }
    }
    
    /**
    * Method to verify the current user's credentials before changing to new credentials
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private boolean GetCurrentCredentials()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.out.println("[ ALERT ] : Sensitive credential data change requested. Identity Confirmation Required.");
        System.out.println("Username: "+name);
        System.out.print("Password: ");
        CurrentPassword=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        System.out.print("Security Key: ");
        CurrentKey=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        return new Mosaic.API.Anima.LoginAPI(user, CurrentPassword, CurrentKey).status();
    }
    
    /**
    * Method to receive the new account password.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private boolean getPass()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.out.println("\nPassword Policy\n");
        System.out.println("* Password must be atleast 8 characters long.");
        System.out.println("* Password must be the same as the password confirmation\n");
        System.out.print("Enter New Password: ");
        NewPassword=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        System.out.print("Confirm Password: ");
        String ConfirmPass=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        
        if(NewPassword.equals(ConfirmPass)==false | NewPassword.length() < 8)
        {
            console.readLine("Password must be 8 characters or longer, and must match the password confirmation. Press enter to continue.");
            return false;
        }
        return true;
    }
    
    /**
    * Method to receive the new account Security Key.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private boolean getKey()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.out.println("\nSecurity Key Policy\n");
        System.out.println("* Security Key must be the same as the Security Key confirmation\n");
        System.out.print("Enter New Security Key: ");
        NewKey=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        System.out.print("Confirm Security Key: ");
        String ConfirmKey=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        
        if(NewKey.equals(ConfirmKey)==false)
        {
            console.readLine("Security Key must match with the Security Key confirmation. Press enter to continue.");
            return false;
        }
        return true;
    }
    
    /**
    * Method to receive the new account Unlock PIN.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private boolean getPIN()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.out.println("\nPIN Policy\n");
        System.out.println("* PIN must be atleast 4 characters long.");
        System.out.println("* PIN must be the same as the PIN confirmation\n");
        System.out.print("Enter New PIN: ");
        NewPIN=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        System.out.print("Confirm PIN: ");
        String ConfirmPIN=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        
        if(NewPIN.equals(ConfirmPIN)==false | NewPIN.length() < 4)
        {
            console.readLine("PIN length must be 4 characters or more and PIN must match with the confirmation PIN. Press enter to continue.");
            return false;
        }
        return true;
    }
    
    /**
    * Method to write the new credentials to the database.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private void writeToDB(int mode)throws Exception
    {
        try
        {
            
            
            //Logic which connects to the database to change the password
            new Mosaic.API.Information().AboutProgram();
            System.out.println("Writing new credentials to Database...");
            //Write to database logic
            String url = "jdbc:sqlite:./System/Private/Fractal.db";
            conn = DriverManager.getConnection(url);
            Class.forName("org.sqlite.JDBC");
            
            if(mode==1)
            {
                pstmt = conn.prepareStatement("UPDATE FCAD SET Password=? WHERE Username=?;");
                pstmt.setString(1, NewPassword);
                pstmt.setString(2, user);
            }
            else if (mode==2)
            {
                pstmt = conn.prepareStatement("UPDATE FCAD SET SecurityKey=? WHERE Username=?;");
                pstmt.setString(1, NewKey);
                pstmt.setString(2, user);
            }
            else if (mode==3)
            {
                pstmt = conn.prepareStatement("UPDATE FCAD SET PIN=? WHERE Username=?;");
                pstmt.setString(1, NewPIN);
                pstmt.setString(2, user);
            }
            else
            {
                System.out.println("ERROR: UNKNOWN MODE.");
            }
            pstmt.executeUpdate();
            conn.close();
            System.gc();
            console.readLine("New Credentials Written to Database Successfully.\nIt is recommended to re-login to the program after the changing of credentials.\nPress Enter to Continue.");
            return;
        }
        catch(Exception E)
        {
            System.out.println("Failed to write credentials to database.");
            System.out.println("Error: "+E);
            E.printStackTrace();
        }
    }
}