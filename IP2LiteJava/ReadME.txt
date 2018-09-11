------
README
------
                 IP2Location LITE DB11 - Java Usage
                ___________________________________ 
                
Author  - Benjamin Dillard
Title   - Lead Full Stack Developer / Solutions Architect
Email   - bdillard@optonline.net
Website - http://innovativecomputing.org

                       System Requirements
                      _____________________
                      
            - Windows (Developed under Windows 10)
            - Any Java Servlet Container Server - (Tomcat, Jetty, JBOSS, etc...)
            - MySQL 5.x
            - Java versions 1.8
            - Deployment Tool - (ANT, Jenkins etc...)
            - Optional - (Eclipse for JavaEE for modifications)
                      
                           Instuctions
                          ______________
                      
1) - Download IP2Location Lite DB11 from http://lite.ip2location.com

2) - Unzip to a folder named Databases.

3) - Run MySQLIP2LiteDB11.sql script located in the IP2LiteJava\MySQL folder
     via a MySQL command line or desired tool such as Toad for MySQL.
     
4) - As a prerequisite to running the Java IP2Lite application you will have to
     change some MySQL connection properties, specifically your username and
     password of your MySQL Server. This can be easily done via the Java
     properties file located in the IP2LiteJava\src folder. Optionally you can also
     change the path of where you want your IP2 access log to be located. By
     default the IP2 log will be located on your Windows root C drive.
     
5) - After completing step 4 the application will have to be deployed to your
     choice of Java supported application servers, Tomcat, Jetty etc...
     The application was successfully tested via Tomcat, Jetty and JBOSS. It
     was deployed via an ANT script but you can use any supported deployment
     tool of your choice such as Jenkins. Note also that a supplied ear and war
     file is located in the deploy folder. These files can be renamed to a zip
     file and you can directly change the properties within them and just copy
     the ear or war file directly to your server. Be sure to rename them back
     to their respective war or ear before copying them to the server.
     
6) - Once successfully deployed with the changes mentioned above the application
     can be run by typing the following url in your browser:
     http://localhost:8080/IP2Lite/index.html
     
7) - Click the TEST button and all went well you should get the
    "Successfully Logged Access Page" displayed. Default property Logs will reflect
    IP2 Access in the default C:\Logs folder.
     
                        

