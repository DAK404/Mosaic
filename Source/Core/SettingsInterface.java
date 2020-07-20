package Core;

import java.io.*;
import java.util.*;
import API.Information;

public class SettingsInterface
{
	private String SettingName="";
	private String SettingValue="";
	private	String propsFileName = "";
	private String Choice="";
	private boolean SB=false;
	
	
	Properties props = new Properties();    
	Console console=System.console();
	
	API.Information view=new API.Information();
	
	
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
	
	protected void SettingsMenu()throws Exception
	{
		do
		{
			view.AboutProgram();
			System.out.println("Here you can add, change or view the settings of the program.\nAvailable options: [Create, Change, Display, Reset, Exit]");
			Choice=console.readLine();
			if(Choice.equalsIgnoreCase("Create") | Choice.equalsIgnoreCase("Change"))
			{
				changeSettings();
			}
			else if(Choice.equalsIgnoreCase("Display"))
			{
				DisplayCurrentSettings();
				System.out.println("Press Enter to Continue.");
				console.readLine();
			}
			else if(Choice.equalsIgnoreCase("Reset"))
			{
				Setup rs=new Setup(SB);
				rs.StoreSettings();
				System.out.println("Reset Settings Complete.");
				console.readLine();
			}
		}
		while(!Choice.equalsIgnoreCase("Exit"));
	}
	
	private void changeSettings()throws Exception
	{
		try
		{
			//first load old one:
			FileInputStream configStream = new FileInputStream(propsFileName);
			props.load(configStream);
			configStream.close();
			
			do
			{
				//Display the current settings poliy values here.
				DisplayCurrentSettings();
				
				System.out.println("Please enter which setting or policy you want to create or modify.\n");
				System.out.print("Enter the name of the setting or policy  : ");
				SettingName=console.readLine();
				System.out.print("Enter the value of the setting or policy : ");
				SettingValue=console.readLine();
				
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
		System.out.println("Reading Configuration files from: "+propsFileName);
		File file=new File(propsFileName);
		if (file.exists() == false) {
            System.out.println("SYSTEM> The specified text module cannot be found/loaded.");
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
}