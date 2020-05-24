package API.Download;

import java.io.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class Download {
	private static String Username;
	protected Download(String u)
	{
		Username=u;
	}
    protected boolean downloadScript() throws Exception {

        Console console = System.console();
        System.out.print("Enter URL to download: ");
        String dl = console.readLine();
        System.out.print("Enter the name of the file (With Extension): ");
        String fn = console.readLine();
        if (download(dl, fn) == false) {
			return false;
        } else {
            return true;
        }
    }
    private boolean download(String DURL, String Filename) throws Exception {
         String curDir = System.getProperty("user.dir");
        String UpdateURL = DURL;
		File crDir=new File(curDir + "/Users/" + Username + "/Files/" + Filename);
		crDir.mkdir();
        File file = new File(UpdateURL);
        try {            
            //downloadUsingNIO(UpdateURL, "Update.md5");
            return downloadUsingNIO(UpdateURL, curDir + "/Users/" + Username + "/Files/" + Filename +"/"+ Filename);
        } catch (Exception e) {
            return false;
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
            return true;
        } catch (Exception E) {
            E.printStackTrace();
            System.out.println("1113");
            return false;
        }
    }
}