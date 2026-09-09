@echo off
set "JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.11.10-hotspot"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo JAVA_HOME=%JAVA_HOME%
java -version
echo.
echo === Building kick-self-mod ===
..\gradlew.bat build --no-daemon

