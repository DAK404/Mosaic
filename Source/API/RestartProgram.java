/*
 *****************************************************
 *                                                   *
 * ! IMPORTANT ! DO NOT DELETE COMMENT ! IMPORTANT ! *
 *                                                   *
 *****************************************************
 *                                                   *
 *           THIS CODE IS NOT RELEASE READY.         *
 *                                                   *
 *       THIS CODE HAS REPORTED ISSUES AND IS        *
 *      BEING WORKED ON. THERE ARE KNOWN ISSUES      *
 *       AND CANNOT BE CONSIDERED RELEASE READY      *
 *                                                   *
 *****************************************************
 */

package API;

import java.io.*;
import API.ErrorHandler;

/** 
* A class to restart the program (HAS KNOWN ISSUES)
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  A0X-Mosaic-Restart-API   |
* | Class Name  :  RestartProgram           |
* | Since       :  0.0.1, 12-April-2020     |
* | Updated on  :  0.0.8, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class RestartProgram
{
	/**
     * This constructor is a stub.
     */
	public RestartProgram() {}
	
	/**
     * This method restarts the program depending on the OS the program is on
     *
     * System.getProperty() will help in retrieving the name of the OS
	 *
	 * @throws Exception :  Throws any exception caught during runtime/execution* @throws Exception Used to catch general exceptions and error states in program
     */
	public void restart()throws Exception
	{
		Console console=System.console();
		ErrorHandler eh=new ErrorHandler();
		try
		{
			long pid = ProcessHandle.current().pid();
			System.out.println("Shutting down Process: "+pid);
			if(System.getProperty("os.name").contains("Windows"))
			{
				//For Windows Builds use this
				new ProcessBuilder("Run_Program.bat").inheritIO().start();
				System.exit(0);
			}
			else
			{
				console.readLine("Due to implementation issues of restart in Linux Systems, please manually restart the program.\nA fix will be rolled out via an OTA update once resolved.\nPress enter to continue.");
				System.exit(0);
			}
		}
		catch(Exception E)
		{
			eh.handleError(E);
		}
	}
}