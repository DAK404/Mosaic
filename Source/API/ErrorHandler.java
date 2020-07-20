package API;

import java.io.*;
import API.LogService;
import API.RestartProgram;

public class ErrorHandler
{
	API.LogService ls=new API.LogService();
	API.RestartProgram rp=new RestartProgram();
	
	Console console=System.console();
	public void displayError(Exception error)throws Exception
	{
		System.out.println("[ERROR] Stopped execution due to the following:");
		error.printStackTrace();
		console.readLine("Press Enter to Continue.");
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		error.printStackTrace(pw);		
		//Log the Exception to a file
		ls.Log(sw.toString(), "/System/Public/Logs/Error");
		
		System.out.print("Exceptions generally occur when the errors are non recoverable.\nA restart might help to get the program back up running.\nDo you want to Restart the program? [Y/N]");
		if(console.readLine().equalsIgnoreCase("Y"))
			rp.restart();
		else
			System.exit(0);
	}
}