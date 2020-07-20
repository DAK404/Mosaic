//               WORK IN PROGRESS                //




package API.Anima;

import java.io.*;

public class AnimaInterface
{
	private boolean SB;
	private String User;
	
	Console console=System.console();
	
	public AnimaInterface(boolean SecureBoot, String Username)
	{
		if(SecureBoot==false | Username.equals(""))
		{
			System.out.println("SecureBoot fuse has tripped. Exiting Program...");
			console.readLine();
		}
		else
		{
			SB=SecureBoot;
			User=Username;
		}
	}	
	
	public void Anima_FrontEnd(String Mode)
	{
		if(Mode.equals("Create"))
		{
			//Send to creation of user
		}
		else if(Mode.equals("Change"))
		{
			//run the change password
		}
		else if(Mode.equals("Login"))
		{
			//Run the login program
		}
		else if(Mode.equals("Init"))
		{
			//run CreateDB
		}
	}
}
