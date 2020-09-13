@echo off
cls
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo   Mosaic DocMaker Tool 1.2
echo ~~~~~~~~~~~~~~~~~~~~~~~~~~~~
echo An in-house tool developed to create Program Documentation
echo Creating Documentation from Source Files...
dir /s /B *.java > sources.txt
javadoc -d ../Documentation/Program_Documentation @sources.txt > DocumentationLog.log
del /s /q sources.txt
echo Note: Documentation can be accessed via the "Index.html" in the root of project folder
pause
