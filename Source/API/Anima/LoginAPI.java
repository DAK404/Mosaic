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
 
package API.Anima;

import Core.*;
import java.sql.*;
import API.*;
import java.io.*;

/** 
* A class to return status value of a queried username, password and security key for login programs
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  AA3-Mosaic-LoginAPI-API  |
* | Class Name  :  LoginAPI                 |
* | Since       :  0.0.1, 31-July-2017      |
* | Updated on  :  0.11.4, 04-October-2020  |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class LoginAPI {
    //a universal string to read the file
    Console console = System.console();
    private String curDir = System.getProperty("user.dir");
    private String User, Pass, SecKey;
	
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	private String url = "jdbc:sqlite:./System/Private/Fractal.db";
	
	
    /**
     * Constructor to initialize the values of the user details to be queried to the database.
	 * 
     * @param Us Used to initialize and store the username to be validated.
     * @param Pa Used to initialize and store the password provided to be validated.
     * @param SK Used to initialize and store the Security Key to complement the fractal decryption.
	 * @throws Exception :  Throws any exception caught during runtime/execution
     */
    public LoginAPI(String Us, String Pa, String SK)throws Exception 
	{
        User = Us;
        Pass = Pa;
        SecKey = SK;
		
		Class.forName("org.sqlite.JDBC");
		
    }
	
	/**
     * Returns whether the credentials provided are valid and exists in the database
     *
     * @return boolean : Returns whether the user credentials are valid and they exist in the database
     * @throws Exception :  Throws any exception caught during runtime/execution
	 */
	public boolean status()throws Exception
	{
		try
		{
			return checkDetails();
		}
		catch(Exception E)
		{
			E.printStackTrace();
			return false;
		}
	}

	/**
	* Implementation of the API to query the database
	*
	* @throws Exception :  Throws any exception caught during runtime/execution
	*/
    private boolean checkDetails()throws Exception
	{
		Connection conn = null;
        try {
			conn = DriverManager.getConnection(url);			
            String sql = "SELECT Username, Password, SecurityKey FROM FCAD WHERE Username = ? AND Password = ? AND SecurityKey = ?;";
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
				rs.close();
				conn.close();
			}
			catch(Exception E){
				System.out.println("Connection couldnt be closed. Error.");
				System.exit(0);
			}
		}
    }
}