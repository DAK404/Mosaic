@echo off
echo Vulcan: A Nion Project
echo Platform: Windows NT
echo ATTENTION: This is the debug build.
pause
start "Vulcan: Debugging Mode" Run_Program.bat >> Program_Run.log 2>&1
echo Press a key to start
pause >
java Launcher > Complete_info.log
