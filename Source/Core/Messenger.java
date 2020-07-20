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

package Core;

import java.io.*;
import API.*;
import java.text.*;
import java.util.*;

/**
 * Module to provide a messaging interface to chat with other online users
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: M_01<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public class Messenger {
    Console console = System.console();
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private String curDir = System.getProperty("user.dir");
    private String User = "";
    private boolean SB = false;
    private boolean Admin = false;
    API.Download.DownloadInterface at = new API.Download.DownloadInterface(User);
    //Write the messages to the public chatroom only now. Implement the private chatroom later
    String Loc = curDir + "./System/Public/ChatRooms/Chatroom.log";
    File file = new File(Loc);

    Messenger(boolean SecureBoot, String username, boolean admin) {
        if (SecureBoot == false) {
            System.out.println("SecureBoot fuse tripped. Exiting Program... ");
            System.exit(0);
        } else {
            SB = SecureBoot;
            User = username;
            Admin = admin;
        }
    }


    protected void MsgScript() throws Exception {

        while (WriteMessage() == true);
        return;
    }

    private boolean WriteMessage() throws Exception {

        //Get the links, and integrations required by the program.
        //Set Default URI for ChatRoom

        ReadMessageLog();
        System.out.print(User + "> ");
        String input = console.readLine();
        if (input.equalsIgnoreCase("<T>")) {
            Templates();
            return true;
        } else if (input.equalsIgnoreCase("<exit>") || input.equalsIgnoreCase("<Quit>")) {
            return false;
        } else if (input.equalsIgnoreCase("<delete>")) {
            if (Admin == true) {
                file.delete();

            } else {
                System.out.println("[ATTENTION] Only Administrators can execute this command.");
                console.readLine();
                return true;
            }
        } else if (input.equalsIgnoreCase("<help>")) {
            API.HelpViewer HEV = new API.HelpViewer();
            HEV.ShowHelp("Help/Messenger.manual");
        } else if (input.equalsIgnoreCase("") || input.equalsIgnoreCase(" "))
            return true;
        else if (input.equalsIgnoreCase("<Download>")) {
            at.DInterfaceScript(SB);
            return true;
        } else if (input.equalsIgnoreCase("<Encrypt>")) {
            at.Encrypt();
            return true;
        } else if (input.equalsIgnoreCase("<Decrypt>")) {
            at.Decrypt();
            return true;
        }
        WriteMessageLog(User, input, Loc);
        return true;
    }


    private void ReadMessageLog() throws Exception {
        if (file.exists() == false) {
            WriteMessageLog("SYSTEM", "BEGINNING OF CHAT\n", Loc);
        }
        API.Information DispInfoObj = new API.Information();
        DispInfoObj.AboutProgram();
        System.out.println("SYSTEM> HINT: TYPE \"<HELP>\" TO OPEN A CONTEXTUAL HELP.");
        String p;
        FileReader f = new FileReader(file);
        BufferedReader ob = new BufferedReader(f);
        while ((p = ob.readLine()) != null)
            System.out.println(p);
        ob.close();
        f.close();
        return;
    }


    private void WriteMessageLog(String User, String Message, String Location) throws Exception {
        String[] Censor = {
            "fuck",
            "bitch",
            "twat",
            "asshole",
            "cunt",
            "nigga",
            "nude",
            "bastard",
            "Whore",
            "Jizz",
            "Faggot",
            "Jerk"
        };
        for (int i = 0; i < Censor.length; i++) {
            Message = Message.replaceAll("(?i)" + Censor[i] + "(?i)", "****");
        }
        Date date = new Date();
        BufferedWriter obj = new BufferedWriter(new FileWriter(file, true));
        PrintWriter pr = new PrintWriter(obj);
        pr.println(dateFormat.format(date) + "\t" + User + "> " + Message);
        pr.close();
        obj.close();
        return;
    }

    private void Templates() {
        String Macros[] = {
            "I have arrived! Now lets make Hell",
            "That's weak dude! Grow up.",
            "I better waste my time on being sarcastic and funny at the same time.",
            "Get a Life... Ohh wait, people dead inside don't need a life LMAO.",
            "Legends needn't respond to this shit.",
            "Ohh wait...  Yet another lamer with a lame joke.",
            "Hmmm. I'm surrounded by fools for the fools themselves have made a fool out of them yet again.",
            "Cow Goes Mooooooooo, Cats go Meow!"
        };
        System.out.println("Built-in Templates: ");
        for (int i = 0; i < Macros.length; i++) {
            System.out.println("[" + (i + 1) + "] " + Macros[i]);
        }
        System.out.print("Enter which template to send to chat: [1-" + Macros.length + "] ");
        try {
            int choice = Integer.parseInt(console.readLine());
            WriteMessageLog(User, Macros[choice - 1], Loc);
        } catch (Exception E) {
            System.out.println("[ATTENTION] The entered input needs to be in numerical format within the given range. Please try again.");
            console.readLine();
            return;
        }
    }
}