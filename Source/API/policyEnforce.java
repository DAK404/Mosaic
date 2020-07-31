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

import java.util.*;
import java.io.*;

/**
 * Helps Administrators to enforce policies in the program
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 24-July-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_06<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class policyEnforce
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
	 * @throws Exception Used to catch general exceptions and error states in program
	 * @return boolean Returns the policy status set for the requested feature or module.
     */
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