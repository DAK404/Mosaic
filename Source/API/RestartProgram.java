package API;

import java.io.*;
import API.ErrorHandler;

public class RestartProgram
{
	public void restart()throws Exception
	{
		ErrorHandler eh=new ErrorHandler();
		try
		{
			if(System.getProperty("os.name").contains("Windows"))
			{
				//For Windows Builds use this
				new ProcessBuilder("java", "Launcher").inheritIO().start();
				System.exit(0);
			}
			else
			{
				//For Linux/Unix or Mac Builds use this
				new ProcessBuilder("exit","java", "Launcher").inheritIO().start();
			}
		}
		catch(Exception E)
		{
			eh.displayError(E);
		}
	}
}