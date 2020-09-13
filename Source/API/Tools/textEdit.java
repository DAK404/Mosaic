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
import API.*;

/**
 * Program to read files written by the user
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 09-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_S04<BR>
 * - Module Version    : 1.7.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404), Bebhin Mathew<BR></p>
 */
class textEdit {	
    private String fName = "";
    private String message = "";
    private String User = "";
    Console console = System.console();
    BufferedWriter obj = null;
    PrintWriter pr = null;
    API.Information info = new API.Information();


    protected textEdit(){}
	
	
    public void editScript(String User) throws Exception {
	{
		try
		{
			fName = console.readLine("Enter the name of file to be stored: ");
			writeContent();
		}
	}
	
    private void writeContent() throws Exception {
        try {
			boolean writeMode=true;
            File file = new File("./Users/" + user + "/" + fName+".nFile");
			if(file.exists()==true)
			{
				System.out.println("Do you want to overwrite the file or append to the file?\n[ Overwrite | Append ]");
				if(console.readLine().toLowerCase().equals("overwrite"))
					writeMode=false;
			}
            obj = new BufferedWriter(new FileWriter(file + "/" + fName, writeMode));
            pr = new PrintWriter(obj);
            info.AboutProgram();
            System.out.println("Text Editor 1.3\n\n");
            do {
				pr.println(message);
				message=console.readLine();
			}while(! (message.equalsIgnoreCase("<exit>")) );
			
			
        } catch (Exception E) {
            System.out.println("Error.");
            E.printStackTrace();
        } finally {
            pr.close();
            obj.close();
            return;
        }
    }
}