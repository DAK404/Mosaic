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
import API.Editor.*;

/**
 // * An API to edit user files
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 28-June-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_03_S01<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class EditorInterface
{
	API.HelpViewer ViewHelp = new API.HelpViewer();
	
	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
	 *
	 * @throws Exception Used to catch general exceptions and error states in program
     */
	public EditorInterface() {}
	
	/**
	* This will help in editing and reading text files
	*
	* @param Mode Determines whether the file must be read or written
	* @param User Determines which user logged in wants to edit the program
	* @throws Exception Used to catch general exceptions and error states in program
	*/
	public void EditorScript(String Mode, String User)throws Exception
	{
		API.policyEnforce pe=new API.policyEnforce("editor");
		
		if(pe.checkPolicy()==false)
		{
			return;
		}
		
		Console console=System.console();
		System.out.println("\nType HELP to display help for Text Editor or Text Reader, or press ENTER to continue.");
		if(console.readLine().equalsIgnoreCase("Help"))
			ViewHelp.ShowHelp("Help/TextEditor.manual");
		
		if(Mode.equals("Read"))
		{
			ReadFile r=new ReadFile(User);
			r.ShowFile();
		}
		else if (Mode.equals("Write"))
		{
			textEdit te=new textEdit(User);
			te.editScript();
		}
	}
}