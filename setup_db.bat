@echo off
echo Creating CoLabCraft Database...
"C:\Program Files\MySQL\MySQL Server 9.6\bin\mysql.exe" -u root -ppassword -e "CREATE DATABASE IF NOT EXISTS colabcraft; CREATE USER IF NOT EXISTS 'colabuser'@'localhost' IDENTIFIED BY 'password'; GRANT ALL PRIVILEGES ON colabcraft.* TO 'colabuser'@'localhost'; FLUSH PRIVILEGES;"
echo Database setup complete.
pause
