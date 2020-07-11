package API;

import java.io.*;

public class RestartProgram
{
	public void restart()throws Exception
	{
		try
		{
			if(System.getProperty("os.name").contains("Windows"))
			{
				//For Windows Builds use this
				new ProcessBuilder("cmd", "/c", "java Launcher").inheritIO().start();
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
			System.err.println("Error: "+E);
		}
	}
}