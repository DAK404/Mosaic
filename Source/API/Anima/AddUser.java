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
public class AddUser {
    private String curDir = System.getProperty("user.dir");
    private String UNM = "";
    private String PWD = "";
    private String KEY = "";
	private String ADM = "No";
	private boolean Admin=false;
    Console console = System.console();
    Connection conn = null;
    Statement stmt = null;
    API.Information DispInfoObj = new API.Information();

	/**
     * This constructor Initializes the program and helps in program security
     *
     * This constructor will help to add a user as administrator and verify SecureBoot
	 *
	 * @param SecureBoot : Initializes and checks for the SecureBoot's status
	 * @param Administrator : Checks if current user is an administrator to provide an option to create another administrator account
     */
	public AddUser(boolean SecureBoot, boolean Administrator) 
	{
		if(SecureBoot==false)
		{
			System.exit(0);
		}
		if(Administrator==true)
		{
			Admin=true;
		}
	}

	/**
     * This script will run to create a user by asking details
	 *
	 * @throws Exception Used to catch general exceptions and error states in program
     */
    public void AddUserScript() throws Exception {
        while (Details() == false);
        return;
    }

    /**
     * This script will run to create Administrator user during setup
	 *
	 * @param Username    : Receives and verifies if its the Administrator user
	 * @param Password    : Receives the password
	 * @param Key : Receives the SecurityKey
	 * @throws Exception Used to catch general exceptions and error states in program
     */
	public void AddUserScript(String Username, String Password, String Key) throws Exception {
        if (Username.equals("Administrator")) {
            UNM = Username;
            PWD = Password;
            KEY = Key;
			ADM = "Yes";
            add();
            return;
        } else {
            System.out.println("Unauthorized access detected. Exiting program.");
            System.exit(0);
        }
    }

    private boolean Details() throws Exception {
		
		//Give an option to the administrator to create a new Administrator account
		if(Admin==true)
		{
			System.out.println("Please choose the type of Account to be created: Administrator/Standard");
			System.out.println("Choice: [Admin/Standard]");
			if(console.readLine().equalsIgnoreCase("Admin"))
			{
				ADM="Yes";
			}
			else
			{
				ADM="No";
			}
		}
		
		//Initialize the confirm password and key strings to null;
        String CPWD = "";
        String CKEY = "";
        do {
			DispInfoObj.AboutProgram();
			showAccountCreationRules();
			System.out.println("Administrator Account: "+ADM);
            System.out.println("Enter the user details");
            System.out.print("Username: ");
            UNM = console.readLine();
            System.out.print("Password: ");
            PWD = String.valueOf(console.readPassword());
			System.out.print("Confirm Password: ");
            CPWD = String.valueOf(console.readPassword());
            System.out.print("\nSecurity Key: ");
            KEY = String.valueOf(console.readPassword());            
            System.out.print("Confirm Security Key: ");
            CKEY = String.valueOf(console.readPassword());
        }
        //conditions to check if username or password isnt blank, the username isnt "Administrator" and the confirmation is the same as the inputs
        while (UNM.equals("") | UNM.contains(" ") | UNM.contains("Administrator") | PWD.equals("") | (PWD.equals(CPWD) == false) | (KEY.equals(CKEY) == false));
        return add();
    }
	
	private void showAccountCreationRules()
	{
		//Create a block which will read out the rules of creating the account
		System.out.println("\nThese are the rules to be followed while creating an account: ");
		System.out.println("1. Account name cannot contain spaces");
		System.out.println("2. Account Password must contain 8 characters for security purposes");
		System.out.println("3. Account name cannot be left blank");
		System.out.println("4. Account name cannot contain the word \"Administrator\"");
		System.out.println("5. Account password and security key must be the same as the same password and key retyped");
		System.out.println("\n");
		return;
	}

    private boolean add() throws Exception {
        DispInfoObj.AboutProgram();
		String url = "jdbc:sqlite:./System/Private/Fractal.db";
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            String sql = "INSERT INTO FSAD(Username, Password, SecurityKey, Administrator) VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, UNM);
            pstmt.setString(2, PWD);
            pstmt.setString(3, KEY);
			pstmt.setString(4, ADM);
            pstmt.executeUpdate();
            conn.close();
			String FilesList[] = {
            "/Users/"+UNM,
            "/Users/"+UNM+"/Files"
			};
			for(int i=0; i<FilesList.length; i++)
			{
				File mkUsr = new File(curDir + FilesList[i]);
				mkUsr.mkdir();
			}
            System.out.println("User created successfully! Press Enter to Continue.");
            console.readLine();
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to create user. Please try again."); //e.getMessage());
            return false;
        }
    }
}