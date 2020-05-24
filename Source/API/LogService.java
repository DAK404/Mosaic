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

package API;

import java.util.*;
import java.text.*;
import java.io.*;

/**
 * Create a log file which contains useful for debugging.
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 0.0.1
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : NN:A03<BR>
 * - Module Version    : 0.0.1<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class LogService {
    /**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
    public LogService() {

    }
    //Get the current folder the program is in.
    String curDir = System.getProperty("user.dir");
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();

    /**
     * Creates a log file of given points in the program either as an [ERROR], [ATTENTION], [WARNING], [CONDITION TRIGGER], [NOTICE]
     *
     * This allows the developers to add the logging service to certain experimental modules which are prone to have potential errors.<BR>
     * The debugging information never contains any user identifiable information. The program logs the events only if log service has been used in the code. <BR>
     * NOTE: This program is not used very often, and may be removed if the program is not required to be used.
     * @param PrintToFile The string that should be written to the file.
     * @param FileName The name of the file which the text is written to. [IT USES THE FORMAT *.LOG]
     * @throws Exception Used to catch general exceptions and error states in program
     */
    public void Log(String PrintToFile, String FileName) throws Exception {
        BufferedWriter obj = new BufferedWriter(new FileWriter(curDir + "/" + FileName + ".log", true));
        PrintWriter pr = new PrintWriter(obj);
        pr.println(dateFormat.format(date) + ": " + PrintToFile);
        pr.close();
        obj.close();
    }
}