               RELEASE NOTES FOR 
                  Mosaic
                 VERSION: 1.6

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

This program source code is strictly private and the license in the
License file (LICENSE.EULA) are as applicable.

If you do not agree to adhere to the License of the program, you must
not install the program on the system. BY INSTALLING THE PROGRAM, YOU
AGREE TO ADHERE TO THE END USER LICENSE AGREEMENT (EULA)

-----------------------------------------------------------------------------
_______________________
  	      
     INTRODUCTION
_______________________

Have you ever wondered if you are able to download a file securely
and at the same time talk to your clients and fellow employees with
the least amount of hassle?

Have you ever encountered errors where your web browser takes huge
resources to load and display a ton of advertisements, along with 
the load of the conference call?

Do you want to pass a download link to your fellow members, pass on
a generated download link to fellow members, still talk to them when
the conference call drops or fails, and yet be confident that your 
meeting is secure and even by using the least amount of the internet
bandwidth and, of course, being cross platform compatible?

The answers to the above questions is a program called Mosaic!
Yes, the program has a ton of features, APIs, runs in the terminal,
written in Java and cross platform compatible (Windows, Unix/Linux)

-----------------------------------------------------------------------------
_______________________

  SYSTEM REQUIREMENTS
_______________________

This program requires Java 9 or higher to function smoothly.
Although it may function on the previous versions, it is
recommended to update to the latest release for security and
reliability.

Minimum System Requirements:

CPU	: Intel Core i3 (Clarkdale) or better
RAM	: 512 MB or higher
STORAGE : 1 GB Hard Drive or higher
NETWORK : 24 Kbps or better network speed

Recommended System Requirements:

CPU	: Intel Core i7 (Skylake) or better
RAM	: 2 GB or higher
STORAGE : 25 GB or higher
NETWORK : 2 Mbps or better network speed


NOTES:-

- The minimum system requirements can support a small group of
clients, it is recommended to have a better machine as servers
to guarantee a good performance.

- The recommended system requirements demonstrates that a large
number of clients can be connected to the system, it is recommended
to have a better configuration for a higher quality real-time
group discussion, whether over the internet or the office network 
infrastructure.

-----------------------------------------------------------------------------
_______________________

       FEATURES
_______________________

- Real Time Chat (over the internet and on enterprise/closed networks)
- Censoring sub-system to keep the conversation civil and clean
- User database for easy user management and convinience
- Faster downloads of files using the given download links
(does not load the advertisements on pages to be loaded along with 
the actual download content. One member can fetch the link and all 
can download it easily)
- Easy OTA updates and quick and automated installation
- Small size of program, memory footprint and high performance
- Easy and quick setup
- Built in Help Viewer
- Secure sessions which include the option of exporting the session 
to a log file, stored with a password.

-----------------------------------------------------------------------------
_______________________

     INSTALLATION
_______________________

* PREREQUISITES

The program can be downloaded officially from GitHub. The download will
be in a "*.zip" type of a file and any program can be used to unzip it to
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
updated by running the update commmand or by downloading the 
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
the official github repository or clone it to customize the 
program and deploy it!

-----------------------------------------------------------------------------
_______________________

  RUNNING THE PROGRAM
_______________________

Use the Run_Program.bat or Run_Program.sh in the folder to get the program
running!

-----------------------------------------------------------------------------
_______________________

    TROUBLESHOOTING
_______________________

These are the FAQs which are encountered while running the program.

Q) The program encounters some Java errors.
A) If you are not using the latest version of Java, you may encounter errors
which may be the cause of the errors. If you are using the latest version of Java,
please do send a copy of the encountered errors and we will be able to use it to
debug the program.

Q) The program fails to add the user to database.
A) You will require the program to have "write" permission granted and if you are
using the program, please make sure that the program location has the write access
permitted.

<ADD MORE FAQs>

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

Official Download       : https://github.com/ArsenicRaptors64/Zen-Quantum-Releases
SQLite Official Site    : https://sqlite.org/

-----------------------------------------------------------------------------
_______________________

	CREDITS
_______________________

Programming:          Deepak Anil Kumar
                      Sither Tsering
					  Bebhin Mathew

Documentation:        Bebhin Mathew
                      Deepak Anil Kumar

Testing:              Bebhin Mathew
                      Sither Tsering

Mentoring:	          Prof. Ravi Dandu

-----------------------------------------------------------------------------
_______________________

  CONTACT INFORMATION
_______________________

For providing feedback, here are a few references for contact information


Team: ARSENIC RAPTORS

Bebhin Mathew		(R17CA199)
Deepak Anil Kumar	(R17CA239)
Sither Tsering		(R17CA254)

GitHub	: https://github.com/ArsenicRaptors64
Email	: mailto://arsenicraptors64@gmail.com

-----------------------------------------------------------------------------
END OF FILE