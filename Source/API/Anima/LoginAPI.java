package API.Anima;

import Core.*;
import java.sql.*;
import API.*;
import java.io.*;

/**
 * An API for the program to validate and login a given user.
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic:API_01_S03<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public class LoginAPI {
    //a universal string to read the file
    Console console = System.console();
    private String curDir = System.getProperty("user.dir");
    private String User, Pass, SecKey;
    private boolean SB;
	
	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String url = "jdbc:sqlite:./System/Private/Fractal.db";
	
	
    /**
     * Used to initialize the conditions for the login verification.
     *
     * The program will not proceed if SecureBoot is not true.
     * @param SecureBoot Used to validate if the program has been accessed safely via the SecureBoot chain.
     * @param Us Used to initialize and store the username to be validated.
     * @param Pa Used to initialize and store the password provided to be validated.
     * @param SK Used to initialize and store the Security Key to complement the fractal decryption.
     */
    public LoginAPI(boolean SecureBoot, String Us, String Pa, String SK)throws Exception 
	{
        SB = SecureBoot;
        User = Us;
        Pass = Pa;
        SecKey = SK;
		
		Class.forName("org.sqlite.JDBC");
		
    }
	
	public boolean status(){
		return checkDetails();
	}

    private boolean checkDetails() {
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url);			
            String sql = "SELECT Username, Password, SecurityKey FROM FSAD WHERE Username = ? AND Password = ? AND SecurityKey = ?;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, User);
            pstmt.setString(2, Pass);
            pstmt.setString(3, SecKey);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("Username").equals(User) & rs.getString("Password").equals(Pass) & rs.getString("SecurityKey").equals(SecKey))
                    return true;
                else
					continue;
            }
			System.out.println("Incorrect Credentials, Please try again.");
            return false;
        } catch (Exception E) {
			E.printStackTrace();
            System.out.println("[ATTENTION] Incorrect Credentials. Please check details and try again.");
			return false;
        }
        finally{
			try{
				conn.close();
			}
			catch(Exception E){
				System.out.println("Connection couldnt be closed. Error.");
				System.exit(0);
			}
		}
    }
	
	public boolean checkAdmin(String AccName)
	{
		Connection conn = null;
		try {           
			conn = DriverManager.getConnection(url);
            String sql = "SELECT Administrator FROM FSAD WHERE Username = ? ;";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, AccName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("Administrator").equals("Yes"))
                    return true;
                else
					continue;
            }
            return false;
        } catch (Exception E) {
			E.printStackTrace();
			return false;
        }
        finally{
			try{
				conn.close();
			}
			catch(Exception E){
				System.out.println("Connection couldnt be closed. Error.");
				System.exit(0);
			}
		}
	}
}