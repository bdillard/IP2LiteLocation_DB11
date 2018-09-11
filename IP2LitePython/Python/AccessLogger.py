#!C:/python34/python
'''
Created on Jul 28, 2018

@author:  Benjamin Dillard
@title:   Lead Full Stack Developer / Solutions Architect
@email:   bdillard@optonline.net
@website: http://innovationcomputing.org    

       Python Website AccessLogger using IP2Lite DB11
       
       Requires: IP2Lite Database DB11 from: https://lite.ip2location.com
       
       Developed Using: : CGI Apache HTTP Server 2.4.3                                 
                          Python 34
                          MySQL 5.5                                     
'''
import os
import cgi
import configparser
import mysql.connector
from mysql.connector import Error
from datetime import datetime

class AccessLogger:

    def __init__(self):
        
        print ("Content-type:text/html\r\n\r\n")
        
    # ******************************************************************
    #         Get AccessLogger Configuration Paramaters                *
    # ******************************************************************
        
        Config = configparser.ConfigParser()
        Config.read("..\\Config\\config.ini")
        
        dbhost       = Config.get('mysql', 'host')
        database     = Config.get('mysql', 'database')
        dbuserid     = Config.get('mysql', 'userid')
        dbpassword   = Config.get('mysql', 'password')
        
        logpath      = Config.get('logs', 'path')
        logname      = Config.get('logs', 'filename')
        
        current_time = datetime.now()
        logfile = ("%s_%s_%s" % (current_time.year, current_time.month, current_time.day) + logname)
        logfile = logpath + logfile
        
        # Get Remote Users Environment 
        env = AccessLogger.getEnvironment(self)
        
        # Get Remote Users IPAddress Formatted
        ipAddress = AccessLogger.getFormattedIPAddress(self) 
        
        # Open connection to MySQL Database
        conn = AccessLogger.openConnection(self, dbhost, dbuserid, dbpassword, database)
        
        # Get IPAddress Location from IP2Lite
        result = AccessLogger.getIPAddressLocation(self, conn, ipAddress)
        
        # Write Access information to Logfile
        AccessLogger.writeToLogFile(self, env, result, logfile)
        
        # Close MySQL Database Connection
        AccessLogger.closeConnection(self, conn)
        
        print ("Python Access Successfully Logged.")
        
# ******************************************************************
#                    Get Remote Users Environment Information
# ******************************************************************
        
    def getEnvironment(self):
        
        time = datetime.now()   
        date = ('{}'.format(time))
        
        return ("IPAddress = "    + cgi.escape(os.environ["REMOTE_ADDR"])     + " | " + 
                "Access Date = "  + date                                      + " | " +
                "User Agent = "   + cgi.escape(os.environ["HTTP_USER_AGENT"]) + " | " +
                "Referer = "      + os.path.realpath('.')                     + " | " +
                "Query String = " + cgi.escape(os.environ["QUERY_STRING"]))
         
        
# ******************************************************************
#                    Get Remote Users IPAddress Formatted
# ******************************************************************
        
    def getFormattedIPAddress(self):
        
        ipaddress = cgi.escape(os.environ["REMOTE_ADDR"])
        
        ipaddress = ipaddress.split(".")
        ipaddress = ''.join(ipaddress)
         
        return ipaddress
        
# ******************************************************************
#                   Open MySQL Database Connection
# ******************************************************************
        
    def openConnection(self, host, userid, password, database):
        
        try:
            conn = mysql.connector.connect(host=host,
                                           database=database,
                                           user=userid,
                                           password=password)
            if conn.is_connected():
                return conn
                
        except Error as e:
            print("MySQL Connection Error. Error = " + e)
            
# ******************************************************************
#         Get IP2Lite IPAddress Location Information
# ******************************************************************
        
    def getIPAddressLocation(self, conn, ipAddress):
        
        try:
        
            query = ("Select ip_from      as ip_from,      " +
                            "ip_to        as ip_to,        " +
                            "country_code as country_code, " +
                            "country_name as country_name, " +
                            "region_name  as region_name,  " +
                            "city_name    as city_name,    " +
                            "latitude     as latitude,     " +
                            "longitude    as longitude,    " +
                            "zip_code     as zip_code,     " +
                            "time_zone    as time_zone     " +
                     "From ip2locationdb11                 " +
                     "Where '" + ipAddress + "'" + " Between ip_from And ip_to Limit 0, 1")
            
            cursor = conn.cursor()
            
            cursor.execute(query)
        
            result = cursor.fetchall()
            
            if not cursor.rowcount:
                return " | Website Accessed but NO IP Information found on IP2Lite DB11 Database"
                
            else:
            
                for row in result:
                    
                    return (" | Country Code = "  + (''.join(map(str, [ row[2] ]))) +
                            " | Country Name = "  + (''.join(map(str, [ row[3] ]))) +
                            " | Region Name = "   + (''.join(map(str, [ row[4] ]))) +
                            " | City Name = "     + (''.join(map(str, [ row[5] ]))) +
                            " | Latitude = "      + (''.join(map(str, [ row[6] ]))) +
                            " | Longitude = "     + (''.join(map(str, [ row[7] ]))) +
                            " | Zip Code = "      + (''.join(map(str, [ row[8] ]))) +
                            " | Time Zone = "     + (''.join(map(str, [ row[9] ]))))         
                             
        except Error as e:
            print ("MySQL Error has occurred in AccessLogger - Exception = " + e)
        
        finally:  
            cursor.close()
    
# ******************************************************************
#                     Log Access Information
# ******************************************************************
        
    def writeToLogFile(self, env, result, logfile):
        
        try:
        
            with open(logfile, 'a') as logfile:
            
                logfile.write(env + result)
                logfile.write('\n')
        
        except Error as e:
            print ("Logfile error has occured - " + e)
# ******************************************************************
#                  Close MySQL Database Connection             
# ******************************************************************
      
    def closeConnection(self, conn):
        
        conn.close()
        
# ******************************************************************
#           Instantiate AccessLogger Class via Constructor
# ******************************************************************
            
AccessLogger()

