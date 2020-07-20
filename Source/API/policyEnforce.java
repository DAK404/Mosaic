package API;

import java.util.*;
import java.io.*;

public class policyEnforce
{
	Console console=System.console();
	private String PN;
	private boolean stat=false;
	
	public policyEnforce(String PolicyName)
	{
		PN=PolicyName;
	}
	
	public boolean checkPolicy()throws Exception
	{
		Properties prop = new Properties();
		String propsFileName="./System/Private/Settings/Settings.burn";
		FileInputStream configStream = new FileInputStream(propsFileName);
		prop.load(configStream);
		if(prop.getProperty(PN).equalsIgnoreCase("off"))
		{
			System.out.println("The Administrator has disabled this feature. Contact the Administrator for more information.\nPress enter to continue.");
			console.readLine();
		}
		else if(prop.getProperty(PN).equalsIgnoreCase("on"))
			stat=true;
		
		configStream.close();
		return stat;
	}
}