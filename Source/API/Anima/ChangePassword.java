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

import java.sql.*;
import java.io.*;
import API.*;
import API.Anima.*;

/**
 * Program to change the account password which is logged in
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_02<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public final class ChangePassword
{
	String url = "jdbc:sqlite:./System/Private/Fractal.db";
	
	private String name="";
	private String user="";
	
	//Store the current password and key
	private String CurrentPassword="";
	private String CurrentKey="";
	
	//Store the new password and key
	private String NewPassword="";
	private String NewKey="";
	
	//SecureBoot values
	private boolean SB=false;
	
	//Initialize the streams and dependencies.
	Console console=System.console();
	API.Information show=new API.Information();
	API.Tools.ReadFile ViewHelp = new API.Tools.ReadFile();
	API.SHA256 sha=new API.SHA256();
	
	/**
     * This constructor initializes the SecureBoot and name of user who has logged in
     *
     * @param U Gets the user currently logged in
	 * @param SecureBoot Gets the status of the SecureBoot
     */
	public ChangePassword(String U, String N, boolean SecureBoot)
	{
		if(SecureBoot==false)
		{
			System.out.println("SecureBoot Error : NOT_INITIALIZED");
			System.exit(0);
		}
		user=U;
		SB=SecureBoot;
	}
	
	/**
     * This method will change the currently logged in user's password
     *
	 * @throws Exception Used to catch general exceptions and error states in program
     */
	public void ChangePasswordRoutine()throws Exception
	{
		if(console.readLine("Type HELP to get help info or press Enter to continue.").equalsIgnoreCase("Help"))
			ViewHelp.ShowHelp("Help/ChangePassword.manual");
		
		while(GetCurrentCredentials()==false)
		{
			console.readLine();
		}
		while(getNewCredentials()==false);
		writeToDB();
	}
	
	//A method to get current credentials from the user.
	private boolean GetCurrentCredentials()throws Exception
	{
		show.AboutProgram();
		System.out.println("Username: "+user);
		System.out.print("Password: ");
		CurrentPassword=sha.encodedString(String.valueOf(console.readPassword()));
		System.out.print("Security Key: ");
		CurrentKey=sha.encodedString(String.valueOf(console.readPassword()));
		API.Anima.LoginAPI login=new API.Anima.LoginAPI(SB, user, CurrentPassword, CurrentKey);
		return login.status();
	}
	
	//A method to get new credentials from the user.
	private boolean getNewCredentials()throws Exception
	{
		show.AboutProgram();
		System.out.print("Enter New Password: ");
		NewPassword=sha.encodedString(String.valueOf(console.readPassword()));
		System.out.print("Confirm New Password: ");
		String ConfirmPass=sha.encodedString(String.valueOf(console.readPassword()));
						
		System.out.print("Enter New Security Key: ");
		NewKey=sha.encodedString(String.valueOf(console.readPassword()));
		System.out.print("Confirm New Security Key: ");
		String ConfirmKey=sha.encodedString(String.valueOf(console.readPassword()));
		
		if(NewPassword.equals(ConfirmPass) & NewKey.equals(ConfirmKey) & !(NewPassword.equals(CurrentPassword) & NewKey.equals(CurrentKey)) & !(NewPassword.equals("")) & (NewPassword.length()>=8))
			return true;
		else
		{
			System.out.println("Warning: The Password cannot be a single space or blank due to security measures. Please enter a strong password with atleast 8 characters.");
			console.readLine();
			return false;

		}
	}
	
	private void writeToDB()throws Exception
	{
		try
		{
			Connection conn = DriverManager.getConnection(url);
			
			//Logic which connects to the database to change the password
			show.AboutProgram();
			System.out.println("Writing credentials to Database...");
			//Write to database logic
			String url = "jdbc:sqlite:./System/Private/Fractal.db";
            Class.forName("org.sqlite.JDBC");
            String sql = "UPDATE FSAD SET Password=?, SecurityKey=? WHERE Username=?;";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, NewPassword);
            pstmt.setString(2, NewKey);
			pstmt.setString(3, user);
            pstmt.executeUpdate();
            conn.close();
			console.readLine("Updated Database Connection Successfully! Press Enter to Continue.");
			return;
		}
		catch(Exception E)
		{
			System.out.println("Error: "+E);
			E.printStackTrace();
		}
	}
}