package Core;

import java.io.*;
import java.util.*;
import API.Information;

public final class SettingsInterface
{
	private String FileName="./System/Private/Settings/Settings.burn";
	private String pName="";
	private String pValue="";
	private boolean SB=false;
	private boolean Admin=false;
	
	Properties props = new Properties();
	Console console=System.console();
	
	resetSettings re=new resetSettings();
	
	API.Tools.ReadFile ViewHelp = new API.Tools.ReadFile();
	API.Information view=new API.Information();
	

	protected SettingsInterface(boolean SecureBoot, boolean Administrator)
	{
		if(SecureBoot==false)
			System.exit(0);
		else
		{
			SB=SecureBoot;
			Admin=Administrator;
		}
	}
	
	protected void Menu()
	{
		try
		{
			System.gc();
			//assert if admin is true
			if(Admin==false)
			{
				System.out.println("Only Administrators can access the settings interface. Press enter to return to main menu.");
				return;
			}
			
			String Choice="";
			
			//Asserts are run only once
			File file=new File(FileName);
			if(file.exists()==false)
			{
				re.reset();
			}
			
			do
			{
				DisplaySettings();
				System.out.print("\nEnter command to manage policies/settings\n[ Modify | Reset | Help | Exit ]\n>> ");
				Choice=console.readLine().toLowerCase();
				switch(Choice)
				{
					case "modify":  
									Modify();
									break;
									
					case "reset":	
									resetInterface();
									return;
									
					case "help":
									ViewHelp.ShowHelp("Help/PolicyEnforcement.manual");
									break;
									
					case "":
					case "exit":
									break;
									
					default:
									console.readLine("Unrecognized command: "+Choice+"\nPlease enter a valid command or module name.");
									break;
				}
			}
			while(!Choice.equals("exit"));
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	protected void resetInterface()throws Exception
	{
		re.reset();
	}
	
	private void Modify()
	{
		try
		{
			
			do
			{
				DisplaySettings();
				pName=console.readLine("Enter the Name of the Policy/Setting:\n>> ").toLowerCase();
				pValue=console.readLine("Enter the Value of the Policy/Setting:\n>> ").toLowerCase();
				
				//logic to save the file
				savePolicy();
			}
			while(console.readLine("Do you want to change another Policy/Setting?[ Y | N ]\n>> ").equalsIgnoreCase("Y"));
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
	
	private void DisplaySettings()throws Exception
	{
		view.AboutProgram();
		System.out.println("\nCurrent Configuration file: "+FileName+"\n");
		FileInputStream configStream = new FileInputStream(FileName);
		props.load(configStream);
		configStream.close();
		props.list(System.out);
		return;
	}
	
	private void savePolicy()throws Exception
	{
		try
		{
			System.out.println("Saving the settings...\nDO NOT TURN OFF OR RESET THE SYSTEM.\n");
			props.setProperty(pName, pValue);
			FileOutputStream output = new FileOutputStream(FileName);
			props.store(output, "GlobalSettings");
			output.close();
			System.out.println("Settings Saved Successfully.");
			return;
		}
		catch(Exception E)
		{
			E.printStackTrace();
			console.readLine("ERROR: Settings could not be saved.");
			return;
		}
	}
}


class resetSettings
{
	API.RestartProgram rp = new API.RestartProgram();
	protected void reset()throws Exception
	{
		System.gc();
		Console console=System.console();
		String FileName="./System/Private/Settings/Settings.burn";
		Properties r=new Properties();
		File file=new File(FileName);
		FileOutputStream resetSettings = new FileOutputStream(file);
		try
		{
			System.out.println("Writing default values to file...");
			resetSettings.flush();
			String [] Settings = 
			{
				"update",
				"download",
				"editor",
				"chat",
				"filemanager"
			};

			for(int i=0; i<Settings.length;i++)
				r.setProperty(Settings[i], "on");
			r.store(resetSettings, "GlobalSettings");
			resetSettings.close();
			console.readLine("Default Settings saved! Press enter to continue.");
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}
}