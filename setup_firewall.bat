@echo off
echo Requesting Administrator privileges to open port 8081 in Windows Firewall...
netsh advfirewall firewall add rule name="Spring Boot Port 8081" dir=in action=allow protocol=TCP localport=8081
echo.
echo Port 8081 is now open! You can connect from your Android device.
pause
