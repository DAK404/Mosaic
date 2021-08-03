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

package Mosaic.API;

import java.util.*;
import java.text.*;
import java.io.*;

/**
* A class to log the events into a log file.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  A07-Mosaic-logger-API    |
* | Class Name  :  LogService               |
* | Since       :  0.0.1, 02-July-2014      |
* | Updated on  :  0.20.1, 04-October-2020  |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class LogService
{
    /**
    * This constructor is a stub.
    */
    public LogService() {

    }
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    Date date = new Date();

    /**
    * Creates a log file of given points in the program either as an [ERROR], [ATTENTION], [WARNING], [CONDITION TRIGGER], [NOTICE]
    *
    * This allows the developers to add the logging service to certain experimental modules which are prone to have potential errors.<BR>
    * The debugging information never contains any user identifiable information. The program logs the events only if log service has been used in the code. <BR>
    *
    * NOTE: This program is not used very often, and may be removed if the program is not required to be used.
  *
    * @param PrintToFile The string that should be written to the file.
    * @param FileName The name of the file which the text is written to. [IT USES THE FORMAT *.LOG]
    * @throws Exception :  Throws any exception caught during runtime/execution
    */
    public void Log(String PrintToFile, String FileName) throws Exception {
    try
    {
      System.gc();
      BufferedWriter obj = new BufferedWriter(new FileWriter("./" + FileName + ".log", true));
      PrintWriter pr = new PrintWriter(obj);
      pr.println(dateFormat.format(date) + ": " + PrintToFile);
      pr.close();
      obj.close();
    }
    catch(Exception E)
    {
      System.out.println("Cannot Write to file. Change the path or grant permission.");
      return;
    }
    }
}