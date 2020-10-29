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
 
 
package API;

import java.io.*;
import API.LogService;
import API.RestartProgram;

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
	API.LogService ls=new API.LogService();
	API.RestartProgram rp=new RestartProgram();
	
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
			String Err=E.toString();
			String ErrDesc = "";
			if(Err.contains("ArithmeticException"))
				ErrDesc="An error occured while an arithmetic task was queued up or was running.";
			else if(Err.contains("ArrayIndexOutOfBoundsException"))
				ErrDesc="An internal program error was encountered while a non-existent array index was trying to be accessed.";
			else if(Err.contains("ClassNotFoundException"))
				ErrDesc="The program or module specified to be loaded does not exist.";
			else if(Err.contains("FileNotFoundException"))
				ErrDesc="The file specified to be loaded does not exist.";
			else if(Err.contains("InterruptedException"))
				ErrDesc="An error occured relating to the program thread.";
			else if(Err.contains("NoSuchFieldException"))
				ErrDesc="The specified variable does not exist.";
			else if(Err.contains("NoSuchMethodException"))
				ErrDesc="The specified method does not exist.";
			else if(Err.contains("NullPointerException"))
				ErrDesc="A null object was referenced, or a member of a null object was referred to during runtime.";
			else if(Err.contains("NumberFormatException"))
				ErrDesc="Error while converting a String to a number.";
			else if(Err.contains("RuntimeException"))
				ErrDesc="Failure during the runtime of a program or module.";
			else if(Err.contains("StringIndexOutOfBoundsException"))
				ErrDesc="The index of the string trying to be accessed is out of the specified limits.";
			else
				ErrDesc="GENERIC INTERNAL ERROR.";
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
			ls.Log(sw.toString(), "./Logs/Error");
			ls.Log(sw.toString(), "./System/Public/Logs/Error");
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