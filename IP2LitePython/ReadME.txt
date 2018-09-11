------
README
------
                 IP2Location LITE DB11 - Python Usage
                ___________________________________ 
                
Author - Benjamin Dillard
Title - Lead Full Stack developer / Solutions Architect
Email - bdillard@optonline.net
Website - http://innovativecomputing.org

                       System Requirements
                      _____________________
                      
                      - Windows (Developed under Windows 10)
                      - Any Python CGI supported Server (Developed with Apache 2.4)
                      - MySQL 5.x - See Special Note for MySQl Python at bottom.
                      - Python 3.x
                      
                           Instuctions
                          ____________
                      
1) - Download IP2Location Lite DB11 from http://lite.ip2location.com

2) - Unzip to a folder named Databases.

3) - Copy the the folder IP2LitePython to your server root.

4) - Run MySQLIP2LiteDB11.sql script located in the IP2LitePython\MySQL folder
     via a MySQL command line or desired tool.
     
5) - You should now have a Database named ip2locationdb11 and two tables
     ip2locationdb11 and ip2locationlog created.
     
6) - Modify the config.ini file located in the IP2LitePython\Config folder
     with the username and password of you MySQL Database.
     
7) - The IP2LocationLite Python AccessLogger is a CGI module. Be sure that
     your server, Apache preferred, is configured for CGI and has a Addhandler
     to run Python. ex... AddHandler cgi-script .cgi .py
     
8) - If steps 1 thru 7 completed successfully you are now ready to run the
     PHP supplied IP2LocationLite.
     
9) - To run the Python version type the following url in your browser:
     http://localhost:80/IP2litePython/Python/accesslogger.py
     
10) - If all went well you should get the "Python Access Successfully Logged" displayed.

11) - The folder IP2LitePython\Logs should now contain a timestamped log file with
      access information from IP2Location Lite. You should also have a database table
      entry in the ip2locationlog MySQL table.
      
Special Note: Please ONLY use the supplied SQL script in the IP2LitePython\MySQL folder
              to build the MySQL Database and tables for this project. The Database
              and tables provided at http://lite.ip2location.com are NOT Python
              compatable for this project as Python currently has compatability
              issues with the table definitions provided there.
                        
                        

