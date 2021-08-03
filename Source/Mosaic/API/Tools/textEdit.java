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

package Mosaic.API.Tools;

import java.io.Console;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
* A class to read a help file or a user's file.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B03-Mosaic-txtEdt-API    |
* | Class Name  :  textEdit                 |
* | Since       :  0.0.1, 26-May-2014       |
* | Updated on  :  0.6.8, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class textEdit {
    private String message = "";
    private File file = null;

    Console console = System.console();



    /**
    * Constructor is a stub.
    */
    public textEdit()
    {

    }

    /**
    * Method to create or edit a file.
    *
    * @param dir : receives the directory where the file needs to be written.
    * @throws Exception Used to catch general exceptions and error states in program.
    */
    public void editScript(String dir) throws Exception
    {
        try
        {
            file = new File(dir+console.readLine("Enter the name of file to be saved: "));
            writeContent();
            return;
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    /**
    * Method which implements the logic to write strings into a file.
    *
    * @throws Exception Used to catch general exceptions and error states in program
    */
    private void writeContent() throws Exception
    {
        try
        {
            boolean writeMode=true;
            if(file.exists()==true)
            {
                System.out.println("Do you want to overwrite the file or append to the file?\n[ Overwrite | Append ]");
                if(console.readLine().toLowerCase().equals("overwrite"))
                    writeMode=false;
            }

            BufferedWriter obj = new BufferedWriter(new FileWriter(file, writeMode));
            PrintWriter pr =  new PrintWriter(obj);



            System.out.println("Mosaic Text Editor 1.7");
            System.out.println("______________________\n\n");

            while(true)
            {
                message=console.readLine();

                //Keep receiving inputs until the user types <exit>
                if(message.equalsIgnoreCase("<exit>"))
                    break;

                pr.println(message);
            }

            pr.close();
            obj.close();
            System.gc();
            return;

        } catch (Exception E)
        {
            System.out.println("Error.");
            E.printStackTrace();
        }
    }
}