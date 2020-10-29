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
 

package API;

import java.util.*;
import java.io.*;

/** 
* A class to check if a policy for a module is enabled or not.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  A02-Mosaic-diag-API      |
* | Class Name  :  ErrorHandler             |
* | Since       :  0.0.1, 15-July-2020      |
* | Updated on  :  0.1.7, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/

public final class policyEnforce
{
	Console console=System.console();
	private String PN;
	private boolean stat=false;
	
	/**
     * This constructor helps loading the property of the given program
     *
     * @param PolicyName : Initializes the policy which is checked before the module is run.
     */
	public policyEnforce(String PolicyName)
	{
		PN=PolicyName;
	}
	
	/**
     * This method helps in checking the policy of the module which will be loaded.
     *
     * This API is public and the program will run it only if the policy matches with the criterion
	 * SecureBoot will be implemented later on to enforce the security of the program.
	 *
	 * @return boolean   :  Returns the policy status set for the requested feature or module.
     * @throws Exception :  Throws any exception caught during runtime/execution
	 */
	public boolean checkPolicy()throws Exception
	{
		try
		{
			System.gc();
			Properties prop = new Properties();
			String propsFileName="./System/Private/Settings/Settings.burn";
			FileInputStream configStream = new FileInputStream(propsFileName);
			prop.loadFromXML(configStream);
			if(prop.getProperty(PN).equalsIgnoreCase("off"))
				console.readLine("The Administrator has disabled this feature. Contact the Administrator for more information.\nPress enter to continue.");
			else if(prop.getProperty(PN).equalsIgnoreCase("on"))
				stat=true;
			
			configStream.close();
			return stat;
		}
		catch(Exception E)
		{
			console.readLine("[ ATTENTION ] : This policy or module is either not configured or has an issue with it. Contact your administrator for more info.");
			return false;
		}
	}
}