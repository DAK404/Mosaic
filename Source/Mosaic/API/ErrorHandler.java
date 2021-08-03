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

import java.io.Console;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.File;

/**
* A class to handle all types of errors thrown by the program.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  A05-Mosaic-diag-API      |
* | Class Name  :  ErrorHandler             |
* | Since       :  0.0.1, 17-August-2015    |
* | Updated on  :  0.7.1, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class ErrorHandler
{
    Console console=System.console();

    /**
     * This constructor is a stub.
     */
    public ErrorHandler() {}

    /**
    * A method to handle errors from the modules loaded.
    *
    * @param E          :  Accepts the exception thrown by other modules
     * @throws Exception :  Throws any exception caught during runtime/execution
    */
    public void handleError(Exception E)throws Exception
    {
        try
        {
            System.out.println("\n\n");
            String ErrDesc = "";
            ErrDesc="JAVA INTERNAL ERROR.";
            System.out.println("[ SYSTEM FAILURE ] : "+ErrDesc+"\nThe error is non recoverable and the program cannot continue.\nAny unsaved data will be lost.");
            System.out.println("\n************************************");
            System.out.println("*          Failure Details         *");
            System.out.println("************************************\n");
            E.printStackTrace();
            System.out.println("\n************************************");
            System.out.println("*      End of Error Description    *");
            System.out.println("************************************\n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            E.printStackTrace(pw);
            File f=new File("./Logs");
            if(f.exists()==false)
                f.mkdir();
            new Mosaic.API.LogService().Log(sw.toString(), "./Logs/Error");
            new Mosaic.API.LogService().Log(sw.toString(), "./System/Public/Logs/Error");
            System.out.println("[ ATTENTION ] : The error details has successfully been written to the log file.");
            System.gc();
            console.readLine("The program will now exit. Please restart it manually to continue.");
            System.exit(0);

            //Use this only while debugging the program
            //return;
        }
        catch(Exception Ex)
        {
            System.err.println("Error: "+Ex);
            Ex.printStackTrace();
        }
    }
}