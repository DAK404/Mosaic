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

//import java libraries
import java.io.Console;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/** 
* A class to add a new user to the database
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  AA1-Mosaic-AddUsr-API    |
* | Class Name  :  AddUser                  |
* | Since       :  0.0.1, 24-April-2016     |
* | Updated on  :  0.7.3, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class AddUser 
{
    //Variables required for the program
    private String curName = "";
    private String curUser = "";
    private String NAME= "";
    private String UNM = "";
    private String PWD = "";
    private String KEY = "";
    private String PIN = "";
    private String ADM = "No";
    private boolean Admin=false;
    
    Console console = System.console();
    Connection conn = null;
    Statement stmt = null;

    /**
     * Constructor initializes if the current user has administrative rights
     *
     * @param u : gets the username of the current user.
     * @param n : gets the name of the current user.
     * @param Administrator : initializes the Admin variable with the logged in user's privilege level.
     */
    public AddUser(String u, String n, boolean Administrator) 
    {
        if(Administrator==true)
        {
            Admin=true;
        }
        curName=n;
        curUser=u;
    }

    /**
     * Constructor to initialize the "Administrator" user during the setup program
     *
     * This constructor can also be reused for other upcoming implementation, with advancements. DEVELOPMENT AND TESTING IN PROGRESS
     *
     * @param Administrator : Checks if current user is an administrator to provide an option to create another administrator account
     * @param Name          : Receives the name of the user from the (setup) previous program
     * @param Username      : Receives the username of the accound from the (setup) previous program
     */
    public AddUser(boolean Administrator, String Name, String Username)
    {
        Admin=true;
        NAME = Name;
        UNM  = Username;
    }

    /**
    * Method to verify the current user's credentials before changing to new credentials
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private boolean GetCurrentCredentials()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.out.println("[ ALERT ] : New user accounts can only be created by an existing account.");
        System.out.println("Username: "+curName);
        System.out.print("Password: ");
        String CurrentPassword=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        System.out.print("Security Key: ");
        String CurrentKey=new Mosaic.API.SHA256().encodedString(String.valueOf(console.readPassword()));
        return new Mosaic.API.Anima.LoginAPI(curUser, CurrentPassword, CurrentKey).status();
    }
    
    /**
     * This script will be executed to add a new user to the database
     *
     * @throws Exception :  Throws any exception caught during runtime/execution
     */
    public final void AddUserScript() throws Exception 
    {
        try
        {
            if(GetCurrentCredentials()==false)
            {
                console.readLine();
                return;
            }
            else
            {
                if(Admin==true)
                {
                    userType();
                }
                while (Details() == false);
            }
            return;
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }
    
    /**
    * Requests the type of account to be created: either an administrative privilege account or a normal account.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final void userType()throws Exception
    {
        try
        {
            while(true)
            {
                new Mosaic.API.Information().AboutProgram();
                System.out.println("[ ATTENTION ] : Do you want this account to be an Administrative account?");
                System.out.println("An Administrator account has additional privileges compared to a standard user account.");
                switch(console.readLine("Choice: [ Yes | No ]\n>> ").toLowerCase())
                {
                    case "yes":
                            ADM="Yes";
                            return;
                            
                    case "no":
                            ADM="No";
                            return;
                            
                    default:
                            console.readLine("Please enter a valid choice. Press enter to continue.");
                            break;
                }
            }
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }
    
    /**
    * Method to accept details into the new user account. A container to hold a modules for taking user inputs.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution 
    */
    private final boolean Details() throws Exception 
    {
        try
        {
            while(getName()==false);
            while(getUsername()==false);
            while(getPassword()==false);
            while(getKey()==false);
            while(getPIN()==false);
            
            //Show an account summary after a user has been created.
            displayDetails();
            console.readLine("Press Enter to Continue.");
            return add();
        }
        catch(Exception E)
        {
            E.printStackTrace();
            System.exit(0);
        }
        return false;
    }
    
    
    /**
    * Method used to intialize the Administrator account during Setup.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    public final void Setup()throws Exception
    {
        Admin= true;
        NAME = "Administrator";
        UNM  = new Mosaic.API.SHA256().encodedString("Administrator");
        ADM  = "Yes";
        while(getPassword()==false);
        while(getKey()==false);
        while(getPIN()==false);
        add();
    }
    
    /**
    * Method to receive the account name.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final boolean getName()throws Exception
    {
        displayDetails();
        System.out.println("\nName Policy\n");
        System.out.println("* Name cannot be Adminstrator");
        System.out.println("* Name must be in english, can contain alphabet and number combination");
        System.out.println("* Name must have a minimum of 2 characters or more.");
        System.out.println("* Name cannot contain spaces");
        NAME=console.readLine("Account Name: ");
        if(NAME.equals("") | NAME.equals(null) | (NAME.matches("^[a-zA-Z0-9]*$")==false) | NAME.equalsIgnoreCase("Administrator") | NAME.length()<2)
        {
            NAME="";
            console.readLine("Name Policy has not been followed. Please try again.");
            return false;
        }
        return true;
    }
    
    /**
    * Method to receive the account username.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final boolean getUsername()throws Exception
    {
        displayDetails();
        System.out.println("\nUsername Policy\n");
        System.out.println("* Username cannot contain the word \"Administrator\"\n");
        UNM  = console.readLine("Account Username: ");
        if(UNM.equals("") | UNM.contains("Administrator"))
        {
            UNM="";
            console.readLine("Username Policy not followed. Please change the username and try again.");
            return false;
        }
        UNM  = new Mosaic.API.SHA256().encodedString(UNM);
        return true;
    }
    
    /**
    * Method to receive the account password.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final boolean getPassword()throws Exception
    {
        displayDetails();
        System.out.println("\nPassword Policy\n");
        System.out.println("* Password must be atleast 8 characters long.");
        System.out.println("* Password must be the same as the password confirmation\n");
        PWD  = String.valueOf(console.readPassword("Account Password : "));
        String CPWD = String.valueOf(console.readPassword("Confirm Password : "));
        if(PWD.length() < 8 | ( PWD.equals(CPWD) == false ) )
        {
            PWD="";
            console.readLine("Password Policy not followed. Please try again which follows the Password Policy.");
            return false;
        }
        CPWD = "";
        PWD  = new Mosaic.API.SHA256().encodedString(PWD);
        return true;
    }
    
    /**
    * Method to receive the account Security Key.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final boolean getKey()throws Exception
    {
        displayDetails();
        System.out.println("\nSecurity Key Policy\n");
        System.out.println("* Security Key must be the same as the Security Key confirmation\n");
        KEY  = String.valueOf(console.readPassword("Security Key : "));
        String CKEY = String.valueOf(console.readPassword("Confirm Key  : "));
        if(KEY.equals(CKEY) == false)
        {
            KEY="";
            console.readLine("Security Key Policy not followed. Please try again which follows the Security Key Policy.");
            return false;
        }
        CKEY = "";
        KEY  = new Mosaic.API.SHA256().encodedString(KEY);
        return true;
    }
    
    /**
    * Method to receive the account Unlock PIN.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */	
    private final boolean getPIN()throws Exception
    {
        displayDetails();
        System.out.println("\nPIN Policy\n");
        System.out.println("* PIN must be atleast 4 characters long.");
        System.out.println("* PIN must be the same as the PIN confirmation\n");
        PIN  = String.valueOf(console.readPassword("Unlock PIN   : "));
        String CPIN = String.valueOf(console.readPassword("Confirm PIN  : "));
        if(PIN.length() < 4 | ( PIN.equals(CPIN) == false ))
        {
            PIN="";
            console.readLine("PIN Policy not followed. Please change the username and try again.");
            return false;
        }
        CPIN = "";
        PIN  = new Mosaic.API.SHA256().encodedString(PIN);
        return true;
    }
    
    /**
    * Method to display information entered by the user.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final void displayDetails()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.gc(); 
        System.out.println("Administrator Account: "+ADM);
        
        if(! (NAME.equals(null) | NAME.equals("")) )
            System.out.println("Account Name : " + NAME);
        
        if(! (UNM.equals(null) | UNM.equals("")) )
            System.out.println("Username     : " + UNM);
        
        if(! (PWD.equals(null) | PWD.equals("")) )
            System.out.println("Password     : ********");
        
        if(! (KEY.equals(null) | KEY.equals("")) )
            System.out.println("Security Key : ********");
        
        if(! (PIN.equals(null) | PIN.equals("")) )
            System.out.println("Unlock PIN   : ****");
        return;
    }

    /**
    * Method to add the details into the database.
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private final boolean add() throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        String url = "jdbc:sqlite:./System/Private/Fractal.db";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO FCAD(Name, Username, Password, SecurityKey, PIN, Administrator) VALUES(?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, NAME);
            pstmt.setString(2, UNM);
            pstmt.setString(3, PWD);
            pstmt.setString(4, KEY);
            pstmt.setString(5, PIN);
            pstmt.setString(6, ADM);
            pstmt.executeUpdate();
            conn.close();
            console.readLine("The user \""+NAME+"\" was successfully created! Press enter to continue.");
            return true;
        } catch (Exception E) {
            E.printStackTrace();
            console.readLine();
            System.out.println("Failed to create user. Please try again."); //e.getMessage());
            return false;
        }
    }
}