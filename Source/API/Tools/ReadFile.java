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

package API.Tools;

import java.io.*;


/**
 * Program to load up help modules and information as requested
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_S04<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public class ReadFile {

	private File file;
	
    API.Information ShowInfo = new API.Information();
	Console console=System.console();
    /**
     * Constructor which will help in reading the user file.
     */
    
	public ReadFile() {}

	/**
     * Takes the input of which help file to read and reads it.
     *
     * @param helpFile : Receives the name of the help file to print to terminal.
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
	
	public void userReadFile(String User, String FileName)
	{
		try
		{
			file=new File("./Users/"+User+"/"+FileName);
			readFile();
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
		
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