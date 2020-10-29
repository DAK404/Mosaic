--------------------------------------------------------------------
*            Mosaic Source Code Documentation v0.8.7               *
--------------------------------------------------------------------

Last updated on : 07-Oct-2020
Time to read    : 10 Minutes
Requirements    : Knowledge on Java, basic knowledge on computers.

--------------------------------------------------------------------

INTRODUCTION: 

A readme file to help new users compile and update the program on 
their own.

--------------------------------------------------------------------

REQUIREMENTS:

- Source code of Mosaic, preferrably the latest build's code.
- Knowledge on Java.
- Basic knowledge on computers.
- Patience.

--------------------------------------------------------------------

DESCRIPTION:

The source code and the packages for each Java class file coincides,
so that its easier to write, compile and debug programs.

To add a new source file, the following steps must be noted:
  
  [*] create a new folder, using the name of the
      package.
                         OR
  [*] navigate to the folder corresponding to 
      class's package name.
	  
  [*] Write the code for the new class.
  
  [*] Copy the file called "BuildScript.bat"
      from "/Scripts" to "/Source" directory.
	  
  [*] Run the BuildScript.bat file.
  
  [*] Navigate to the root of the project folder.
  
  [*] Navigate to ReleaseBuild directory.
  
  [*] You will find the compiled program. After
      which, you must:
	  
    -> Copy "/Information" from project root
       folder to "/ReleaseBuild"
    -> Copy "/org" folder located within
       "/Drivers" and paste it in "/ReleaseBuild"
    -> Copy "Run_Program.bat" from "/scripts"
       to "/ReleaseBuild" folder.
	  
  [*] Run the "Run_Program.bat" file.
  
If you find any issues or bugs, feel free to patch
and submit a Pull Request.
--------------------------------------------------------------------