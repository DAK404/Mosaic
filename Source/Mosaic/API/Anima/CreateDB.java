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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/** 
* A class to initialize the database file
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  AA2-Mosaic-CrDB-API      |
* | Class Name  :  CreateDB                 |
* | Since       :  0.0.1, 16-December-2019  |
* | Updated on  :  0.4.6, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class CreateDB {
    
    /**
     * This constructor is a stub
     */
    public CreateDB() {}
    
    /**
    * Public method which is an API, executes the method required to create the database
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    public void CreateDBLogic() throws Exception {
        connect();
    }

    /**
    * Implementation of the API to to create and initialize a blank and new database
    *
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    private void connect() throws Exception {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:./System/Private/Fractal.db";
            // create a connection to the database
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            System.out.println("[ SYSTEM ] > Database created successfully. Initializing...");
            String sql = "CREATE TABLE IF NOT EXISTS FCAD (\n" +
                "    UserID integer PRIMARY KEY,\n" +
                "    Name text NOT NULL,\n" +
                "    Username text UNIQUE,\n" +
                "    Password text NOT NULL,\n" +
                "    SecurityKey text NOT NULL,\n" +
                "    PIN text NOT NULL,\n" +
                "    Administrator text NOT NULL);";
                
            stmt.execute(sql);
            stmt.close();
            conn.close();
            System.gc();
            System.out.println("[ SYSTEM ] > Database Initialization Successful. Press Enter to Continue.");
        } catch (Exception E) {
            E.printStackTrace();
            System.in.read();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.in.read();
            }
        }
    }
}