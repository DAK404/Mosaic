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
 * Helps in restarting the program
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 12-July-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_04<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class RestartProgram
{
	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
	public RestartProgram() {}
	
	/**
     * This method restarts the program depending on the OS the program is on
     *
     * System.getProperty() will help in retrieving the name of the OS
	 *
	 * @throws Exception Used to catch general exceptions and error states in program
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
				new ProcessBuilder("cmd", "/c", "java Launcher").inheritIO().start();
				System.exit(0);
			}
			else
			{
				console.readLine("NOTE: LINUX SUPPORT HAS BEEN DROPPED SINCE THERE IS A RESTART ISSUE.\nIf you are using Linux, the program will exit.\nPress enter to continue.");
				System.exit(0);
			}
		}
		catch(Exception E)
		{
			eh.displayError(E);
		}
	}
}