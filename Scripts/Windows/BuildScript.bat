@echo off
cls
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo   Mosaic Build Tool 1.7
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo In-House Tool to Compile Release Builds
echo Compiling Program...
javac -d ../Binaries/ReleaseBuild Launcher.java
echo Note: Compiled files can be found in Binaries folder.
pause
