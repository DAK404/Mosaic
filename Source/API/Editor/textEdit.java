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

package API.Editor;

import java.io.*;
import API.*;

/**
 * Program to read files written by the user
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 09-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_S04<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public class textEdit {
    private String fName = "";
    private String message = "";
    private String user = "";
    Console console = System.console();
    BufferedWriter obj = null;
    PrintWriter pr = null;
    API.Information info = new API.Information();


    /**
     * Receive the username from the previous module
     *
     * @param U: Accepts the username
     */
    public textEdit(String U) {
        user = U;
    }

    public void editScript() throws Exception {
        System.out.println("Enter the name of the file to be saved (with extension): ");
        fName = console.readLine();
        writeContent();
    }

    /**
     * This method helps in actually writing the content to the file.
     *
     * The loop keeps going on until it encounters the exit condition
     * upon where the streams are closed and the program control is returned to the previous program.
     */
    private void writeContent() throws Exception {
        try {
            File file = new File("./Users/" + user + "/Files/" + fName);
            file.mkdir();
            obj = new BufferedWriter(new FileWriter(file + "/" + fName, true));
            pr = new PrintWriter(obj);
            info.AboutProgram();
            System.out.println("Text Editor 1.3\n\n");
            while (!(message.equalsIgnoreCase("<exit>"))) {
                pr.println(message);
                message = console.readLine();
            }
        } catch (Exception E) {
            System.out.println("Error.");
            E.printStackTrace();
        } finally {
            pr.close();
            obj.close();
            return;
        }
    }
}