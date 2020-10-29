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

package API.Tools;

import java.io.*;


/** 
* A class to read a help file or a user's file.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B02-Mosaic-Reader-API    |
* | Class Name  :  ReadFile                 |
* | Since       :  0.0.1, 26-May-2014       |
* | Updated on  :  0.6.8, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class ReadFile {

	private File file;
	
    API.Information ShowInfo = new API.Information();
	Console console=System.console();
    /**
     * Constructor is a stub.
     */
    
	public ReadFile() {}

	/**
     * Takes the input of which help file to read and reads it.
     *
     * @param helpFile   : Receives the name of the help file to print to terminal.
	 * @throws Exception : Throws any exception caught during runtime/execution
     */
    public void ShowHelp(String helpFile)throws Exception
	{
		try
		{
			file=new File("./Information/"+helpFile);
			readFile();
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	/**
	* Method to read a file in the user's home directory
	*
	* @param FileName   : Name of the file in the user's dir to be read
	* @throws Exception : Throws any exception caught during runtime/execution
	*/
	public void userReadFile(String FileName)throws Exception
	{
		API.policyEnforce pe=new API.policyEnforce("editor");
		
		if(pe.checkPolicy()==false)
		{
			return;
		}
		
		try
		{
			file=new File(FileName+console.readLine("Enter the name of the file to be read: "));
			readFile();
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
		
	/**
	* Method containing the logic to read the file, either a help file or a user file
	*
	* @throws Exception : Throws any exception caught during runtime/execution
	*/
	private void readFile() throws Exception
	{
		//A link to show the build info to the user's terminal
		ShowInfo.AboutProgram();			

		//A condition to check if the given file is found or not. This prevents exception, which may or may not disrupt the program.

		//This checks if the file doesnt exist. If it doesnt exist, the error text is shown on terminal.
		if (file.exists() == false) {
			System.out.println("SYSTEM> The specified file cannot be read, found or loaded.");
		}

		//If the file exists, the file is displayed on the terminal.
		else {
			//Open the method to read files and read it
			FileReader f = new FileReader(file);
			BufferedReader ob = new BufferedReader(f);

			//Initialize the string to be null
			String p = "";

			//Logic to read the file line by line.
			while ((p = ob.readLine()) != null)

				//Read the line loaded
				System.out.println(p);

			//After reading the file, close the streams opened.
			ob.close();
			f.close();
		}

		//Finally, the program awaits the user input to continue, returning to the main program.
		console.readLine("\nPress enter to continue.");
		return;
	}
}