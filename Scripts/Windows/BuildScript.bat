@echo off
cls
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo   Mosaic Build Tool 1.0
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo An in-house tool developed to compile the program
echo Compiling Program...
javac -d ../Binaries Launcher.java > BuildLog.log
echo Note: Compiled files can be found in Binaries folder.
pause
