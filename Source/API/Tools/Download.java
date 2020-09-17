package API.Tools;

import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public final class Download
{
	private File file = null;
    private String Username;
    
	public Download()
	{
		
	}
	
	public boolean updateProgram()throws Exception
	{
		API.policyEnforce pe=new API.policyEnforce("update");
		
		if(pe.checkPolicy()==false)
		{
			return false;
		}
		return downloadUsingNIO("https://raw.githubusercontent.com/DAK404/Mosaic-Releases/master/Release.zip", "Update.zip");
	}
	
    public void downloadFile(String Un, String dir) throws Exception 
	{
		API.policyEnforce pe=new API.policyEnforce("download");
		
		if(pe.checkPolicy()==false)
		{
			return;
		}
		
		Console console = System.console();
        System.out.print("Enter URL to download: ");
        String UpdateURL = console.readLine();
        System.out.print("Enter the name of the file: ");
        String Filename = console.readLine();
        file = new File(UpdateURL);
        try {
            //downloadUsingNIO(UpdateURL, "Update.md5");
            if(downloadUsingNIO(UpdateURL, dir + Filename) == true)
				System.out.println("Download successful.");
			else
				System.out.println("Download unsuccessful. Please check the Network settings or the URL.");
			return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }	
	
    private boolean downloadUsingNIO(String urlStr, String file) throws Exception {
        try {
            URL website = new URL(urlStr);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
			System.gc();
            return true;
        } catch (Exception E) {
            //E.printStackTrace();
            return false;
        }
    }
}