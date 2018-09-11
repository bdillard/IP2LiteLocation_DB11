------
README
------
                 IP2Location LITE DB11 - PHP Usage
                ___________________________________ 
                
Author - Benjamin Dillard
Title - Lead Full Stack developer / Solutions Architect
Email - bdillard@optonline.net
Website - http://innovativecomputing.org

                       System Requirements
                      _____________________
                      
                      - Windows (Developed under Windows 10)
                      - Any PHP supported Server (Developed with Apache 2.4)
                      - MySQL 5.x
                      - PHP 5.x
                      
                           Instuctions
                          ____________
                      
1) - Download IP2Location Lite DB11 from http://lite.ip2location.com

2) - Unzip to a folder named Databases.

3) - Copy the PHP Developed version folder IP2LitePHP to your server root.

4) - Run MySQLIP2LiteDB11.sql script located in the IP2LitePHP\MySQL folder
     via a MySQL command line or desired tool.
     
5) - You should now have a Database named ip2locationdb11 and two tables
     ip2locationdb11 and ip2locationlog created.
     
6) - Modify the ip2Lite.ini file located in the IP2LitePHP\Config folder
     with the username and password of you MySQL Database.
     
7) - If steps 1 thru 6 completed successfully you are now ready to run the
     PHP supplied IP2LocationLite.
     
8) - To run the PHP version type the following url in your browser:
     http://localhost:80/IP2litePHP/php/index.php
     
9) - If all went well you should get the "Successfully Logged Access Page" displayed.

10) - The folder IP2LitePHP\Logs should now contain a timestamped log file with
      access information from IP2Location Lite. You should also have a database table
      entry in the IP2locationlog MySQL table.
                        
                        

