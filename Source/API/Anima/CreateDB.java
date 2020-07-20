package API.Anima;

import java.sql.*;

/**
 * An API for the program to Initialize a user database
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_01_S01<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class CreateDB {
	
	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
	public CreateDB() {}
	
	/**
	* This method will run the actual steps to create the database
	*
	* The database will be created and then be initialized.	
	* @throws Exception Used to catch general exceptions and error states in program
	*/
    public void CreateDB() throws Exception {
        connect();
    }

    private void connect() throws Exception {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:./System/Private/Fractal.db";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("SYSTEM> Database created successfully. Initializing...");
            String sql = "CREATE TABLE IF NOT EXISTS FSAD (\n" +
                "    UserID integer PRIMARY KEY,\n" +
                "    Username text UNIQUE,\n" +
                "    Password text NOT NULL,\n" +
                "    SecurityKey text NOT NULL,\n" +
				"    Administrator text NOT NULL);";
				
            stmt.execute(sql);
            System.out.println("SYSTEM> Database Initialization Successful. Press Enter to Continue.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}