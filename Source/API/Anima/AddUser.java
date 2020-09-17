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

package API.Anima;

import java.io.*;
import java.sql.*;
import API.*;


/**
 * An API for the program to add a user into the database
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_01_S02<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public final class AddUser {
    private String curDir = System.getProperty("user.dir");
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
    API.Information DispInfoObj = new API.Information();
	API.SHA256 sha=new API.SHA256();

	/**
     * This constructor Initializes the program and helps in program security
     *
     * This constructor will help to add a user as administrator and verify SecureBoot
	 *
	 * @param SecureBoot : Initializes and checks for the SecureBoot's status
	 * @param Administrator : Checks if current user is an administrator to provide an option to create another administrator account
     */
	public AddUser(boolean Administrator) 
	{
		if(Administrator==true)
		{
			Admin=true;
		}
	}

	public AddUser(boolean SecureBoot, boolean Administrator, String Name, String Username)
	{
		if(SecureBoot==false)
		{
			System.exit(0);
		}
		if(Administrator==true)
		{
			Admin=true;
		}
		NAME = Name;
		UNM  = Username;
	}

	/**
     * This script will run to create a user by asking details
	 *
	 * @throws Exception Used to catch general exceptions and error states in program
     */
    public final void AddUserScript() throws Exception {
        while (Details() == false);
        return;
    }

    private final boolean Details() throws Exception {
		
		//Give an option to the administrator to create a new Administrator account
		if(Admin==true)
		{
			System.out.println("Please choose the type of Account to be created: Administrator/Standard");
			if(console.readLine("Choice: [ Admin | Standard ] : ").toLowerCase().equalsIgnoreCase("admin"))
			{
				ADM="Yes";
			}
			else
			{
				ADM="No";
			}
		}
		
		while(NAME.equals("") | NAME.equalsIgnoreCase(null) | NAME.equalsIgnoreCase("Administrator"))
			NAME = console.readLine("Name: ");
		while(getUsername()==false & UNM.equalsIgnoreCase(""));
		while(getPassword()==false);
		while(getKey()==false);
		while(getPIN()==false);
		displayDetails();
		console.readLine("Press Enter to Continue.");
        return add();
    }
	
	public final void Setup()throws Exception
	{
		Admin= true;
		NAME = "Administrator";
		UNM  = sha.encodedString("Administrator");
		ADM  = "Yes";
		while(getPassword()==false);
		while(getKey()==false);
		while(getPIN()==false);
		add();
	}
	
	private final boolean getUsername()throws Exception
	{
		displayDetails();
		UNM  = console.readLine("Account Username: ");
		if(UNM.equals("") | UNM.contains("Administrator"))
		{
			console.readLine("Username Policy not followed. Please change the username and try again.");
			UNM="";
			return false;
		}
		UNM  = sha.encodedString(UNM);
		return true;
	}
	
	private final boolean getPassword()throws Exception
	{
		displayDetails();
		PWD  = String.valueOf(console.readPassword("Account Password : "));
		String CPWD = String.valueOf(console.readPassword("Confirm Password : "));
		if(PWD.length() < 8 | ( PWD.equals(CPWD) == false ) )
		{
			PWD="";
			console.readLine("Password Policy not followed. Please try again which follows the Password Policy.");
			return false;
		}
		CPWD = "";
		PWD  = sha.encodedString(PWD);
		return true;
	}
	
	private final boolean getKey()throws Exception
	{
		displayDetails();
		KEY  = String.valueOf(console.readPassword("Security Key : "));
		String CKEY = String.valueOf(console.readPassword("Confirm Key  : "));
		if(KEY.equals(CKEY) == false)
		{
			KEY="";
			console.readLine("Security Key Policy not followed. Please try again which follows the Security Key Policy.");
			return false;
		}
		CKEY = "";
		KEY  = sha.encodedString(KEY);
		return true;
	}
	
	private final boolean getPIN()throws Exception
	{
		displayDetails();
		PIN  = String.valueOf(console.readPassword("Unlock PIN   : "));
		String CPIN = String.valueOf(console.readPassword("Confirm PIN  : "));
		if(PIN.length() < 4 | ( PIN.equals(CPIN) == false ))
		{
			PIN="";
			console.readLine("PIN Policy not followed. Please change the username and try again.");
			return false;
		}
		CPIN = "";
		PIN  = sha.encodedString(PIN);
		return true;
	}
	
	private final void displayDetails()throws Exception
	{
		DispInfoObj.AboutProgram();
		credentialPolicy();
		System.out.println("Administrator Account: "+ADM);
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
	
	private final void credentialPolicy()
	{
		//Create a block which will read out the rules of creating the account
		System.out.println("\nHere are a few rules which are hard enforced while creating a new user: ");
		System.out.println("A. Username Policy");
		System.out.println("\t1. Username cannot contain the word \"Administrator\"");
		System.out.println("B. Password And Security Key Policy");
		System.out.println("\t1. Password must be atleast 8 characters long.");
		System.out.println("\t2. Password must be the same as the password confirmation");
		System.out.println("\t3. Security Key must be the same as the Security Key confirmation");
		System.out.println("C. PIN Policy");
		System.out.println("\t1. PIN must be atleast 4 characters long.");
		System.out.println("\t2. PIN must be the same as the PIN confirmation\n");
		return;
	}

    private final boolean add() throws Exception {
        DispInfoObj.AboutProgram();
		String url = "jdbc:sqlite:./System/Private/Fractal.db";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO FSAD(Name, Username, Password, SecurityKey, PIN, Administrator) VALUES(?,?,?,?,?,?)";
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
        } catch (SQLException e) {
            System.out.println("Failed to create user. Please try again."); //e.getMessage());
            return false;
        }
    }
}