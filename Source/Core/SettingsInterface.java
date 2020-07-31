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

package Core;

import java.io.*;
import java.util.*;
import API.Information;

/**
 * An interface for administrators to configure program policies
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 20-July-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_09<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class SettingsInterface
{
	private String SettingName="";
	private String SettingValue="";
	private	String propsFileName = "";
	private String Choice="";
	private boolean SB=false;
	
	API.HelpViewer ViewHelp = new API.HelpViewer();
	API.Information view=new API.Information();
	
	Properties props = new Properties();    
	Console console=System.console();
	
	/**
     * This constructor helps initializing the SecureBoot variable and Administrator Status
	 *
	 * @param SecureBoot Gets the SecureBoot value
	 * @param Administrator Gets the Administrator status value
     */
	protected SettingsInterface(boolean SecureBoot, boolean Administrator)
	{
		if(SecureBoot==true)
		{
			SB=SecureBoot;
			if(Administrator==true)
			{
				propsFileName="./System/Private/Settings/Settings.burn";
			}
		}
		else
		{
			System.out.println("SecureBoot has tripped. Exiting....");
			System.exit(0);
		}
	}
	
	/**
     * This method helps in interfacing the policies, accessible only to the SettingsInterface
	 *
	 * @throws Exception Used to catch general exceptions and error states in program
     */
	protected void SettingsMenu()throws Exception
	{
		do
		{
			view.AboutProgram();
			System.out.println("Here you can add, change or view the settings of the program.\nAvailable options: [Change, Display, Help, Reset, Exit]");
			Choice=console.readLine();
			Choice=Choice.toLowerCase();
			switch(Choice)
			{
				case "change":  
								changeSettings();
								break;
								
				case "display":
								DisplayCurrentSettings();
								System.out.println("Press Enter to Continue.");
								console.readLine();
								break;
								
				case "reset":
								resetSettings();
								break;
								
				case "help":
								ViewHelp.ShowHelp("Help/PolicyEnforcement.manual");
								break;
								
				case "exit":
								break;
								
				default:
								System.out.println("Please enter a valid command.");
								break;
			}
		}
		while(!Choice.equalsIgnoreCase("Exit"));
	}
	
	private void changeSettings()throws Exception
	{
		try
		{
			//Load the initial configuration file present.
			FileInputStream configStream = new FileInputStream(propsFileName);
			props.load(configStream);
			configStream.close();
			
			//display the current settings stored
			do
			{
				//Display the current settings poliy values here.
				DisplayCurrentSettings();
				
				//Request new settings which are supposed to be written to the file
				System.out.println("Please enter which setting or policy you want to create or modify.\n");
				
				//Converts both the name and value to lowercase so that the file has no conflicts
				System.out.print("Enter the name of the setting or policy  : ");
				SettingName=console.readLine();
				SettingName=SettingName.toLowerCase();
				
				System.out.print("Enter the value of the setting or policy : ");
				SettingValue=console.readLine();
				SettingValue=SettingValue.toLowerCase();
				
				//modifies existing or adds new property
				props.setProperty(SettingName, SettingValue);
				System.out.print("Do you want to create or change a policy or setting? [Y/N]   ");
			}
			while(console.readLine().equalsIgnoreCase("Y"));
			
			System.out.println("Saving..... DO NOT TURN OFF OR RESET SYSTEM WHILE SAVING.\n");
			
			//save modified property file
			FileOutputStream output = new FileOutputStream(propsFileName);
			props.store(output, "GlobalSettings");
			output.close();
			System.out.println("Save Complete. Press Enter to Continue.");
			console.readLine();
			DisplayCurrentSettings();
			System.out.println("Press Enter to Continue");
			console.readLine();
			return;
		}
		catch(Exception E)
		{
			System.err.println("Error: "+E);
			E.printStackTrace();
		}
	}
	
	private void DisplayCurrentSettings()throws Exception
	{
		view.AboutProgram();
		System.out.println("Current Configuration file: "+propsFileName);
		File file=new File(propsFileName);
		if (file.exists() == false) {
            System.out.println("SYSTEM> Settings file is missing or corrupt. Repairing Settings files...");
			resetSettings();
        }
		else
		{
			String p;
			FileReader f = new FileReader(file);
			BufferedReader ob = new BufferedReader(f);
			while ((p = ob.readLine()) != null)
				System.out.println(p);
			ob.close();
			f.close();
		}
		return;
	}
	
	private void resetSettings()
	{
		try
		{
			File f=new File(propsFileName);
			if(f.exists()==true)
			{
				System.out.println("Trying to delete the configuration file...");
				f.delete();
			}
			System.out.println("Writing configuration file...");
			writeDefaultSettings();		
			System.out.println("Settings have been reset to default values. Press Enter to continue.");
			console.readLine();
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	protected void writeDefaultSettings()
	{
		try
		{
			props.setProperty("update", "on");
			props.setProperty("download", "on");
			props.setProperty("chat", "on");
			props.setProperty("editor", "on");
			FileOutputStream output = new FileOutputStream(propsFileName);
			props.store(output, "GlobalSettings");
			output.close();
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
}