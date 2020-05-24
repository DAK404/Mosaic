@echo off
echo Vulcan
echo Creating Program Documentation
echo NOTE: Access Documentation using index.html in root of project folder.
dir /s /B *.java > sources.txt
javadoc -d ../Documentation/Program_Documentation @sources.txt
del /s /q sources.txt
pause