               RELEASE NOTES FOR 
                    Mosaic
                 VERSION: 0.8

            _____________________

              TABLE OF CONTENTS
            _____________________

            1. INTRODUCTION
            2. SYSTEM REQUIREMENTS
            3. FEATURES
            4. INSTALLATION
            5. RUNNING THE PROGRAM
            6. TROUBLESHOOTING
            7. ADDITIONAL LINKS
            8. CREDITS
            9. CONTACT INFORMATION

-----------------------------------------------------------------------------
_______________________

!      IMPORTANT      !
_______________________


If you do not agree with the License Agreement of the program, do not
use or install this Software. BY INSTALLING THE PROGRAM, YOU ACCEPT AND 
AGREE TO THE END USER LICENSE AGREEMENT (EULA) specified in the license 
file.

THIS PROGRAM NEITHER OFFERS ANY WARRANTIES NOR TAKES RESPONSIBILTY FOR
ANY LIABILITIES. THE END USER IS RESPONSIBLE FOR USING THE PROGRAM AND 
BY DOING SO, ACKNOWLEDGES THAT THE USER HAS READ THE LICENSE IN THE
LICENSE FILE.

-----------------------------------------------------------------------------
_______________________
  	      
     INTRODUCTION
_______________________

Mosaic focuses on the following: Security, Speed, Size and Reliability.
The new iteration of the Nion Project has a lot of new features, which
aims to simplify and enhance user experience with the least hassle.

Mosaic built upon the previous Elixir Kernel. Mosaic is a program which 
streamlines the flow of SecureBoot technology. This improves security, 
supports faster module loading speeds, uses lower memory and resources 
to provide the same, or in some cases, better performance.

-----------------------------------------------------------------------------
_______________________

  SYSTEM REQUIREMENTS
_______________________

This program requires Java 9 or higher to properly function.
Although it may function on previous versions, it is
recommended to update to the latest version for security and
reliability.

Minimum System Requirements:

CPU	: Intel Pentium IV or better
RAM	: 512 MB or higher
STORAGE : 250 MB Hard Drive or higher
NETWORK : 10 Kbps or better network speed

Recommended System Requirements:

CPU	: Intel Core 2 Duo or better
RAM	: 2 GB or higher
STORAGE : 25 GB or higher (SATA HDD)
NETWORK : 512 Kbps or better network speed

NOTES:-

- The minimum system requirements can support a small group of
clients, it is recommended to have a better machine as servers
to guarantee a good performance.

- The recommended system requirements demonstrates that a large
number of clients can be connected to the system, it is recommended
to have a better configuration for a higher quality of user experience,
whether over the internet or the office network infrastructure.

-----------------------------------------------------------------------------
_______________________

       FEATURES
_______________________

- Enhanced Real Time Chat (over LAN only for now)
- Censoring subsystem to keep the conversation civil and clean
- User database for easy user management and convenience
- Faster downloads of files using the given download links
(does not load the advertisements on pages to be loaded along with 
the actual download content. One member can fetch the link so others 
can download it easily)
- Easy OTA updates with quick and automated installation
- Small size of program, memory footprint and high performance
- Intuitive and simple setup
- Built in contextual help available
- Text Editor
- Unified builds supporting any OS and configuration seamlessly

-----------------------------------------------------------------------------
_______________________

     INSTALLATION
_______________________

* PREREQUISITES

The program can be downloaded officially from GitHub. The download will
be in a "*.zip" file and any program can be used to unzip it to
a specified directory.

The following programs are a few which can unzip the program:

- 7Zip
- WinRAR
- WinZip

-----

The program will set itself up during the first run, while the 
administrator can provide the details for the server program. 

Note that the program is in the compiled Java format ("*.class")
and it is recommended NOT to delete those files for smooth 
functionality of the program.

The program uses a Type 4 JDBC driver by SQLite which can be 
updated by running the update command or by downloading the 
latest driver and extracting the contents of the JAR file to
the root of the program folder.

The folder structure must be in this format after the setup.

./root_folder
  |- /API
  |- /Core
  |- /Information
  |- /org
  |- /System
  |- /Users
  |- Launcher.class
  |- Run_Program (*.bat or *.sh)

If there are issues with the build, grab the latest one from 
the official GitHub repository or clone it to customize the 
program and deploy it!

-----------------------------------------------------------------------------
_______________________

  RUNNING THE PROGRAM
_______________________

Use the Run_Program.bat (.sh in Linux) in the folder to execute the program!

-----------------------------------------------------------------------------
_______________________

    TROUBLESHOOTING
_______________________

These are the FAQs which are encountered while running the program.

Q) The program encounters some Java errors.
A) If you are not using the latest version of Java, you may encounter errors
which may be the cause of the errors. If you are using the latest version
of Java, please send log files which will help in debugging the problems.

Q) The program fails to add the user to the database.
A) Please make sure that the program folder has write access permitted.

Q) Setup fails. Not able to set it up again.
A) Please delete the following directories /Users and /System to begin the 
setup program again.

-----------------------------------------------------------------------------
_______________________

   ADDITIONAL LINKS
_______________________

Java Official Site      : https://java.com/
OpenJDK Official Site   : https://jdk.java.net/
Oracle JDK Official Site: https://www.oracle.com/technetwork/java/javase/downloads/index.html

7Zip Official Site      : https://www.7-zip.org/
WinRAR Official Site    : https://www.win-rar.com/
WinZip Official Site    : https://www.winzip.com/

Official Download       : https://github.com/DAK404/
SQLite Official Site    : https://sqlite.org/

-----------------------------------------------------------------------------
_______________________

       CREDITS
_______________________

Programming             : DAK404

Testing                 : DAK404    (Windows Builds)
                        : Sidd_Dino (Unix Builds)
                        : Bebhin Mathew (Additional Module Testing)
						
Documentation           : DAK404
                        : Taggable Tanner (Proof-reading)

-----------------------------------------------------------------------------
_______________________

  CONTACT INFORMATION
_______________________

For any queries or feedbacks, here are a few links to get in touch with me.

GitHub Repository       :  https://www.github.com/DAK404/Mosaic/
Email Address           :  mailto://phantomslayer004@gmail.com/
Website                 :  https://www.dak404.github.io/

-----------------------------------------------------------------------------
END OF FILE