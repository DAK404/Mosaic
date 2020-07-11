@echo off
cls
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo    Mosaic Reset Tool 1.0
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo An in-house tool developed to reset the program
echo Resetting program...
@RD /s /q System
@RD /s /q Users
echo Program Reset Complete.
pause
start "Mosaic 2.0" java Launcher
