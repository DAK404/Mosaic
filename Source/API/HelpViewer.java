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


/**
 * Program to load up help modules and information as requested
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_02<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
public class HelpViewer {

	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
     */
    public HelpViewer() {}

    /**
     * This method loads the help file by using the FileReader and BufferedReader
     *
     * Information files resides in the ./Information folder. 
	 * Help files reside in the ./Information/Help folder
	 *
	 * @param FileName : the module/file that is requested by the program 
	 * @throws Exception Used to catch general exceptions and error states in program
     */
    public void ShowHelp(String FileName) throws Exception {
        String curDir = System.getProperty("user.dir");
        Console console = System.console();

        //A link to show the build info to the user's terminal
        API.Information ShowInfo = new API.Information();
        ShowInfo.AboutProgram();

        //Set the directory to the intended location where the help modules reside.
        File file = new File(curDir + "/Information/" + FileName);

        //A condition to check if the given file is found or not. This prevents exception, which may or may not disrupt the program.

        //This checks if the file doesnt exist. If it doesnt exist, the error text is shown on terminal.
        if (file.exists() == false) {
            System.out.println("SYSTEM> The specified text module cannot be found/loaded.");
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
        System.out.println("Press enter to continue.");
        console.readLine();
        return;
    }
}