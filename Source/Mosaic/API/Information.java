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

package Mosaic.API;

import java.util.Date;
import java.util.Random;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

/**
* A class to display the program information across modules.
* <BR>
* <pre>
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* |            TECHNICAL DETAILS            |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* | Class ID    :  A01-Mosaic-info-CORE     |
* | Class Name  :  Information              |
* | Since       :  0.0.1, 15-June-2016      |
* | Updated on  :  0.9.8, 04-October-2020   |
* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
* </pre>
*/
public final class Information
{
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    final static String MOTD=MOTD();
    static String day;

    /**
     * This constructor is a stub.
     */
    public Information() {}

    /**
     * This method displays the information about the build
     *
     * It must generally contain the following details:<BR>
     * - Program name
     * - Version
     * - Kernel Version [optional: with name in brackets]
     * - Channel
     * - Build date
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    public void AboutProgram() throws Exception {
        System.gc();
        ClearScreen();
        //System.out.println("+----------------------------+");
        System.out.println("+------------------------------------+");
        System.out.println("|   __  __ ___  ____ ___  _  _  ___  |");
        System.out.println("|  |  \\/  | _ |[___ | __|  ||  |     |");
        System.out.println("|  |_|\\/|_|___|____]|   | _||_ |___  |");
        System.out.println("|                                    |");
        System.out.println("+------------------------------------+");
        System.out.println("|        > Prototype Build <         |");
        System.out.println("|           Build : 0.9.0            |");
        System.out.println("+------------------------------------+\n");


        /*System.out.println("+---------------------------+");
        System.out.println("|        -  Mosaic  -       |");
        System.out.println("+---------------------------+");
        System.out.println("|    > Prototype Build <    |");
        System.out.println("|        Build: 0.8.3       |");
        System.out.println("+---------------------------+\n");
        */

        Date date = new Date();
        System.out.println("Good "+ timeOfDayGreeting() + "!");
        System.out.println(dateFormat.format(date)+ ", "+ day +"\n");
        System.out.println("MOTD: "+MOTD+"\n");
        System.out.println("--------------------------------------\n");

        //ATTENTION! Uncomment debug() only when developing the program. It is a debug feature.
        //debug();

        return;
    }

    /**
     * This method clears the screen.
     *
     * Note: it has 2 methods for Windows type builds and for unix type builds
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    public void ClearScreen() throws Exception {
        try
        {
            if(System.getProperty("os.name").contains("Windows"))
            {
                //For Windows Builds use this
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else
                //For Linux/Unix or Mac Builds use this
            {
                //System.out.print("\033[H\033[2J");
                new ProcessBuilder("/bin/bash", "-c" ,"clear").inheritIO().start().waitFor();
                System.out.flush();
            }
            return;
        }
        catch(Exception E)
        {
            E.printStackTrace();
        }
    }

    /**
     * This method prints a greeting message based on the time of day
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    private String timeOfDayGreeting()
    {
        LocalDateTime LD = LocalDateTime.now();
        int hr=LD.getHour();
        String Greeting="";
        if(hr < 12 & hr >=00)
        {
            Greeting="Morning";
        }
        else if(hr >= 12 & hr < 16)
        {
            Greeting="Afternoon";
        }
        else if(hr > 16  &  hr <= 23)
        {
            Greeting="Evening";
        }
        return Greeting;
    }

    /**
     * This method prints a message of the day.
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    private static String MOTD()
    {
        LocalDateTime LD = LocalDateTime.now();
        Random random = new Random();
        int evalRand=random.nextInt(7);

        DayOfWeek dayOfWeek = DayOfWeek.from(LD);
        switch(dayOfWeek)
        {
            case MONDAY: String [] Monday=
                            {
                                "Monday: A great way to begin the week!",
                                "Monday?! But, I wasn't even finished with Saturday yet :V",
                                "God gave us Mondays to punish us for the things we did over the weekend.",
                                "This should be the spirit every Mondays. Know that something good will always happen.",
                                "Finally, that rare and elusive Monday we like \"-_-",
                                "The worst part of my Mondays is hearing you complain about Mondays.",
                                "Monday morning coffee is just as important as Friday night liquor, almost."
                            };
                            day="Monday";
                            return Monday[evalRand];

            case TUESDAY: String [] Tuesday=
                            {
                                "You're doing great! Keep up the good work! :)",
                                "Tuesday is a huge day.",
                                "I don't want it good. I want it Tuesday.",
                                "Tuesday! Worry less, live more.",
                                "If you don't burn out at the end of each day, you're a bum.",
                                "A lack of focus leads to a lack of progress. Tuesday = No Due Day.",
                                "Happy Tuesday! You got to admit, at least it sounds better than happy Monday."
                            };
                            day="Tuesday";
                            return Tuesday[evalRand];

            case WEDNESDAY: String [] Wednesday=
                            {
                                "Wednesdays are like Mondays in the middle of the week",
                                "If all our national holidays were observed on Wednesdays, we could wind up with nine-day weekends.",
                                "Elephants love Wednesday, and so will you",
                                "It will not be lonely on Wednesday when it has all around it",
                                "You must be thinking : \"Ahh 2 more days for the weekend\" -_-",
                                "Wednesday: Halfway to the weekend! Enjoy your day!",
                                "Wednesdays will always bring smiles for the second half of the week"
                            };
                            day="Wednesday";
                            return Wednesday[evalRand];

            case THURSDAY: String [] Thursday=
                            {
                                "Thursday, I forecast as mostly happy. It's a much-needed break.",
                                "Heave Ho! Push it a day and its a weekend go!",
                                "Today is a new day. Expect great things!",
                                "I wonder how to turn water into wine. Happy thirsty Thursday",
                                "Some people call it Thursday, I like to call it Friday Eve.",
                                "It really feels like a Thursday. Sometimes, things just work out.",
                                "Don't count the days. Make the days count. (2 days for the weekend!)"
                            };
                            day="Thursday";
                            return Thursday[evalRand];

            case FRIDAY: String [] Friday=
                            {
                                "Friday. Just a few more hours for a fun filled weekend!",
                                "Oooof!",
                                "Now, the weekend shall dawn during the dusk. Till then, lets get back to work!",
                                "That's a long way till Monday. Have a great Friday!",
                                "The most awaited day of the week.",
                                "LOL, now this week was fun!",
                                "How about a fun filled schedule of remaining work at the week end?"
                            };
                            day="Friday";
                            return Friday[evalRand];

            case SATURDAY: String [] Saturday=
                            {
                                "You're here? What happened to the movie plans?",
                                "You were supposed to be chilling with friends today :V",
                                "Did you forget that today is a Saturday?",
                                "Ooooof hope that your work is completed soon!",
                                "Happy weekend, but aren't you supposed to be chilling?",
                                "All work and no play makes Jack a dull boy. Don't be Jack.",
                                "Well, this message is supposed to give you company on a lonely weekend."
                            };
                            day="Saturday";
                            return Saturday[evalRand];

            case SUNDAY: String [] Sunday=
                            {
                                "You're working on a Sunday?!",
                                "Reminder: Sleep and fun is important. Today is a Sunday :)",
                                "Why are you even here on a sunday?! :V",
                                "Task List: Have fun, and chill! ",
                                "What?! Why?! Today's work is to have fun!",
                                "I see you here everyday -_-",
                                "All work and no play makes Jack a dull boy."
                            };
                            day="Sunday";
                            return Sunday[evalRand];
        }
        return "";
    }

    /**
     * This method prints the debug information.
     *
     * Note: Must be enabled during development
     *
     * @throws Exception Used to catch general exceptions and error states in program
     */
    private void debug()
    {
        int mb = 1024 * 1024;

        // get Runtime instance
        Runtime instance = Runtime.getRuntime();
        System.out.println("\n*********************************************");
        System.out.println("        ---   DEBUG INFORMATION   ---        ");
        System.out.println("*********************************************");
        System.out.println("\n   - Heap utilization statistics [MB] -  \n");
        System.out.println("      [*]  Process ID   : "+ProcessHandle.current().pid());
         // available memory
        System.out.println("      [*]  Total Memory : " + instance.totalMemory() / mb + " MB");
        // free memory
        System.out.println("      [*]  Free Memory  : " + instance.freeMemory() / mb + " MB");
        // used memory
        System.out.println("      [*]  Used Memory  : " + (instance.totalMemory() - instance.freeMemory()) / mb + " MB");
        // Maximum available memory
        System.out.println("      [*]  Max Memory   : " + instance.maxMemory() / mb + " MB");
        System.out.println("\n*********************************************\n\n");

    }
}