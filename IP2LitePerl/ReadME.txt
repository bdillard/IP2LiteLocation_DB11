------
README
------
                 IP2Location LITE DB11 - Perl Usage
                ___________________________________ 
                
Author - Benjamin Dillard
Title - Lead Full Stack developer / Solutions Architect
Email - bdillard@optonline.net
Website - http://innovativecomputing.org

                       System Requirements
                      _____________________
                      
                      - Windows (Developed under Windows 10)
                      - Any Perl CGI supported Server (Developed with Apache 2.4.x)
                      - MySQL 5.x 
                      - Perl 64
                      
                           Instuctions
                          ____________
                      
1) - Download IP2Location Lite DB11 from http://lite.ip2location.com

2) - Unzip to a folder named Databases.

3) - Copy the the folder IP2LitePerl to your server root.

4) - Run MySQLIP2LiteDB11.sql script located in the IP2LitePerl\MySQL folder
     via a MySQL command line or desired tool.
     
5) - You should now have a Database named ip2locationdb11 and two tables
     ip2locationdb11 and ip2locationlog created.
     
6) - Modify the config.ini file located in the IP2LitePerl\Config folder
     with the username and password of you MySQL Database.
     
7) - The IP2LocationLite Perl AccessLogger is a CGI module. Be sure that
     your server, Apache preferred, is configured for CGI and has a Addhandler
     to run Python. ex... AddHandler cgi-script .cgi .pl
     
8) - You will require the Perl DBI to run MySQL. This can easily be installed
     via running - "ppm install dbi" on a MS Command line prompt.
     
9) - If steps 1 thru 8 completed successfully you are now ready to run the
     PHP supplied IP2LocationLite.
     
10) - To run the Python version type the following url in your browser:
     http://localhost:80/IP2litePerl/Perl/accesslogger.py
     
11) - If all went well you should get the "Perl Access Successfully Logged" displayed.

12) - The folder IP2LitePerl\Logs should now contain a timestamped log file with
      access information from IP2Location Lite. You should also have a database table
      entry in the ip2locationlog MySQL table.
                        
                        

