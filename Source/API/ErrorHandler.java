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

import java.io.*;
import API.LogService;
import API.RestartProgram;


/**
 * Program Which helps in handling exceptions and errors
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 24-July-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_05<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class ErrorHandler
{
	API.LogService ls=new API.LogService();
	API.RestartProgram rp=new RestartProgram();
	
	Console console=System.console();
	
	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
	public ErrorHandler() {}
	
	/**
     * This method handles the exception thrown by the program. <BR>
	 * It logs the error into a log file within: ./System/Private/Logs/Error.log
     *
	 * @param error : Accepts the error or exception thrown by the program
	 * @throws Exception Used to catch general exceptions and error states in program
     */
	public void displayError(Exception error)throws Exception
	{
		System.out.println("[ERROR] Stopped execution due to the following:");
		error.printStackTrace();
		console.readLine("Press Enter to Continue.");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		error.printStackTrace(pw);		
		//Log the Exception to a file
		ls.Log(sw.toString(), "/System/Public/Logs/Error");
		
		System.out.print("Exceptions generally occur when the errors are non recoverable.\nA restart might help to get the program back up running.\nDo you want to Restart the program? [Y/N]");
		if(console.readLine().equalsIgnoreCase("Y"))
			rp.restart();
		else
			System.exit(0);
	}
}