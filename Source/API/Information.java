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

import java.io.*;
import java.util.*;
import java.text.*;
import java.time.*;

/**
 * Program to display build information 
 *
 * <br>
 * @author Deepak Anil Kumar (DAK404)
 * @version 1.0.0
 * @since 06-May-2020
 * <p>
 * *** Technical Details ***<br>
 * - Module Name       : Mosaic: API_01<BR>
 * - Module Version    : 1.0.0<BR>
 * - Module Author     : Deepak Anil Kumar (DAK404)<BR></p>
 */
public final class Information {
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	Console console=System.console();
	final static String MOTD=MOTD();
	static String day;

	/**
     * This constructor has little use in this program.
     *
     * This constructor is a stub. It doesnt have any usable part of the program.
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
        ClearScreen();
		System.out.println("    __________________________");
	    System.out.println("   /                         /");
        System.out.println("  / Mosaic : A Nion Project /");
		System.out.println(" /  Build  : Alpha 0.7.4   /");
		System.out.println("/_________________________/");
		System.out.println("    > Prototype Build <   \n");
		
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		System.out.println("Good "+ timeOfDayGreeting() + "!" + "\n");
		System.out.println(day+"'s MOTD: "+MOTD+"\n");
		
		//ATTENTION! Uncomment debug() only when developing the program. It is a debug feature.
		debug();
		
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
	
	private String timeOfDayGreeting()
	{
		LocalDateTime LD = LocalDateTime.now();
		int hr=LD.getHour();
		String Greeting="";
		if(hr < 12 & hr >=00)
		{
			Greeting="Morning";
		}
		else if(hr > 12 & hr <= 16)
		{
			Greeting="Afternoon";
		}
		else if(hr > 16  &  hr <= 23)
		{
			Greeting="Evening";
		}
		return Greeting;
	}
	
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
								"Tuesday isn't so bad... It's a sign that I've somehow survived Monday.",
								"Tuesday is a huge day.",
								"I don't want it good. I want it Tuesday.",
								"Tuesday! Worry less, live more.",
								"If you don't burn out at the end of each day, you're a bum.",
								"A lack of focus leads to a lack of progress. Focus. Grind. Grow.",
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
								"Thursday, I forecast as mostly sunny. It's a much-needed break.",
								"Heave Ho! Push it a day and its a weekend go!",
								"Today is a new day. Expect great things!",
								"I wonder how to turn water into wine. Happy thirsty Thursday",
								"Some people call it Thursday, I like to call it Friday Eve.",
								"It's Thursday and it really feels like a Thursday. Sometimes things just work out",
								"Don't count the days. Make the days count."
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
								"Oooooh boy. What's up?",
								"Oh no.",
								"Why are you even here on a sunday?! :V",
								"Go awei and have fun already!",
								"What?! Why?! Today's work is to have fun!",
								"I see you here everyday -_-",
								"Dude, don't be a workaholic, have fun a lil bit ;)"
							};
							day="Sunday";
							return Sunday[evalRand];
		}
		return "";
	}
	
	private void debug()
	{
		int mb = 1024 * 1024; 
 
		// get Runtime instance
		Runtime instance = Runtime.getRuntime();
		System.out.println("\n\n*********************************************");
		System.out.println("        ---   DEBUG INFORMATION   ---        ");
		System.out.println("*********************************************");
		System.out.println("\n   - Heap utilization statistics [MB] -  \n");
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