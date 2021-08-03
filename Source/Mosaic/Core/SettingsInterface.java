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

package Mosaic.Core;

import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import java.util.Properties;

/**
* A class serving as a front end to administrate policies and configure it.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B05-Mosaic-policyEn-CORE |
* | Class Name  :  SettingsInterface        |
* | Since       :  0.0.1, 13-July-2020      |
* | Updated on  :  0.1.4, 29-September-2020 |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class SettingsInterface
{
    private final String FileName="./System/Private/Settings/Settings.burn";
    private String pName="";
    private String pValue="";
    private boolean Admin=false;
    private File file=null;

    Properties props = null;
    Console console=System.console();

    protected SettingsInterface(boolean Administrator)
    {
        Admin=Administrator;
    }

    protected void Menu()
    {
        try
        {
            //assert if admin is true
            if(Admin==false)
            {
                System.out.println("Only Administrators can access the settings interface. Press enter to return to main menu.");
                return;
            }

            while(true)
            {
                //File is loaded every iteration
                file=new File(FileName);
                if(file.exists()==false)
                {
                    resetInterface();
                }
                props=new Properties();
                DisplaySettings();
                switch(console.readLine("\nWhat do you want to do?\n\n[ Modify | Reset | Help | Exit ]\n>> ").toLowerCase())
                {
                    case "modify":
                                    Modify();
                                    break;

                    case "reset":
                                    resetInterface();
                                    console.readLine("Press enter to return to Main Menu.");
                                    break;

                    case "help":
                                    new Mosaic.API.Tools.ReadFile().ShowHelp("Help/PolicyEnforcement.manual");
                                    break;

                    case "":
                                    break;
                    case "exit":
                                    return;

                    default:
                                    console.readLine("Unrecognized command.\nPlease enter a valid option.");
                                    break;
                }
            }
        }
        catch(Exception E)
        {
            E.printStackTrace();
            console.readLine();
        }
    }

    protected void resetInterface()throws Exception
    {
        try
        {
            props=new Properties();
            FileOutputStream o = new FileOutputStream(FileName);
            file=new File(FileName);
            if(file.exists()==true)
                file.delete();

            String [] Settings =
            {
                "update",
                "download",
                "editor",
                "chat",
                "filemanager",
                "no_debug"
            };
            for(int i=0; i<Settings.length;++i)
            {
                props.setProperty(Settings[i], "on");
            }
            props.storeToXML(o, "GlobalSettings");
            o.close();
            System.out.println("[ SYSTEM ] > Default Program Settings Saved Successfully!");
            return;
        }
        catch(Exception E)
        {
            System.out.println("Error resetting policy file.");
            E.printStackTrace();
        }
    }

    private void Modify()
    {
        try
        {

            do
            {
                DisplaySettings();
                System.out.println("Which Policy do you want to modify?\n[ TIP : TO ADD A NEW POLICY, JUST TYPE IN THE NEW POLICY NAME AND VALUE! ]");
                pName=console.readLine("Policy Name:\n>> ").toLowerCase();
                pValue=console.readLine("Policy Value:\n>> ").toLowerCase();
                System.out.println("\nWriting policy to file. DO NOT TURN OFF THE SYSTEM!\n");
                //logic to save the file
                savePolicy();
            }
            while(console.readLine("Do you want to modify another policy? [ Y | N ]\n>> ").equalsIgnoreCase("Y"));
            return;
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    private void DisplaySettings()throws Exception
    {
        new Mosaic.API.Information().AboutProgram();
        System.gc();
        System.out.println("          Mosaic Policy Editor 1.3\n");
        System.out.println("--------------------------------------------");
        System.out.println("      - Current Policy Configuration -      ");
        System.out.println("--------------------------------------------");
        System.out.println("\nPolicy File  : "+FileName);
        System.out.println("Policy Format: XML\n");
        FileInputStream configStream = new FileInputStream(FileName);
        props.loadFromXML(configStream);
        configStream.close();
        props.list(System.out);
        System.out.println("\n--------------------------------------------\n");
        return;
    }

    private void savePolicy()throws Exception
    {
        try
        {
            props.setProperty(pName, pValue);
            FileOutputStream output = new FileOutputStream(FileName);
            props.storeToXML(output, "GlobalSettings");
            output.close();
            System.out.println("Policy "+pName+" was saved successfully.");
            return;
        }
        catch(Exception E)
        {
            E.printStackTrace();
            console.readLine("[ ERROR ] : Policy could not be saved.");
            return;
        }
    }
}