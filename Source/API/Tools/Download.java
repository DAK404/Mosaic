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


package API.Tools;

import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.net.URL;

/**
* A class to implement the FileManager functionality
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  B03-Mosaic-Dload-TOOLS   |
* | Class Name  :  Download                 |
* | Since       :  0.0.1, 21-January-2020   |
* | Updated on  :  0.4.7, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class Download
{
	private File file = null;
    private String Username;
    
	/**
	* Constuctor is a stub.
	*/
	public Download()
	{
		
	}
	
	/**
	* Method to download the program update.
	*
	* @throws Exception  : Throws any exception caught during runtime/execution.
	* @return boolean    : Returns the download status of the update package. 
	*/
	public boolean updateProgram()throws Exception
	{
		API.policyEnforce pe=new API.policyEnforce("update");
		
		if(pe.checkPolicy()==false)
		{
			return false;
		}
		return downloadUsingNIO("https://raw.githubusercontent.com/DAK404/Mosaic-Releases/master/Release.zip", "Update.zip");
	}
	
	/**
	* Method to encapsulate the implementation of the file download
	*
	* @param Un          : receives the username currently logged in.
	* @param dir         : the destination directory where the file must be downloaded.
	* @throws Exception  : Throws any exception caught during runtime/execution.
	*/
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
	
	/**
	* Method to implement the download logic.
	*
	* @param urlStr : The URL of the file to be downloaded.
	* @param file   : The name of the file which is to be saved, along with the full path inside the user home directory.
	* @throws       : Throws any exception caught during runtime/execution.
 	*/
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