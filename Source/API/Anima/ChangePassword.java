package API.Anima;

import java.sql.*;
import java.io.*;
import API.*;
import API.Anima.*;

public class ChangePassword
{
	String url = "jdbc:sqlite:./System/Private/Fractal.db";
	
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
	
	
	public ChangePassword(String U, boolean SecureBoot)
	{
		if(SecureBoot==false)
		{
			System.out.println("Error. SecureBoot is false");
			System.exit(0);
		}
		user=U;
		SB=SecureBoot;
	}
	
	//a publicly accessible method which is always executed
	public void ChangePasswordRoutine()throws Exception
	{
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
		CurrentPassword=String.valueOf(console.readPassword());
		System.out.print("Security Key: ");
		CurrentKey=String.valueOf(console.readPassword());
		API.Anima.LoginAPI login=new API.Anima.LoginAPI(SB, user, CurrentPassword, CurrentKey);
		return login.status();
	}
	
	//A method to get new credentials from the user.
	private boolean getNewCredentials()throws Exception
	{
		show.AboutProgram();
		System.out.print("Enter New Password: ");
		NewPassword=String.valueOf(console.readPassword());
		System.out.print("Confirm New Password: ");
		String ConfirmPass=String.valueOf(console.readPassword());
						
		System.out.print("Enter New Security Key: ");
		NewKey=String.valueOf(console.readPassword());
		System.out.print("Confirm New Security Key: ");
		String ConfirmKey=String.valueOf(console.readPassword());
		
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
			System.out.println("Updated Database Connection Successfully! Press Enter to Continue.");
			console.readLine();
			return;
		}
		catch(Exception E)
		{
			System.out.println("Error: "+E);
			E.printStackTrace();
		}
	}
}