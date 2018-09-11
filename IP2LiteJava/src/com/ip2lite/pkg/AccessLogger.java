package com.ip2lite.pkg;

/*
 *      AccessLogger for IP2Lite
 * 
 *      @author Benjamin Dillard
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.*;

public class AccessLogger {
	
	public String Driver;
	public String DataSource;
	public String Userid;
	public String Password;
	public String Path;
	public String Logfile;
	
	BufferedWriter bw = null;

	public static Logger logger = Logger.getLogger(AccessLogger.class);
	
	public AccessLogger(String ipAddress,   String userAgent,
			            String userReferer, String queryString) {
		
		String env = null;
		
		InputStream is = getClass().getClassLoader().getResourceAsStream("IP2Location.properties");

		Properties properties = new Properties();

		try {

		  properties.load(is);
		  
		  Driver     = properties.getProperty("Driver");
		  DataSource = properties.getProperty("DataSource");
		  Userid     = properties.getProperty("Userid");
		  Password   = properties.getProperty("Password");
		  
		  Path       = properties.getProperty("Path");
		  Logfile    = properties.getProperty("Filename");
		  
		  String logDate   = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
		  
		  String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		  
		  String propLog   = Path + logDate + Logfile;
		  
		  logger.debug("Properties Log Path = " + propLog);
		  
		  List<ObjectVO> ipInfo = ObjectDAO.getIP2Information(Driver, DataSource,
				                                              Userid, Password,
				                                              ipAddress);
		  
		  if ((ipInfo.isEmpty())) {
            env = "Remote IPAddress not found on IP2Lite Database";
        	 bw = new BufferedWriter(new FileWriter(propLog, true));
  			  bw.write(env + "\r\n");
        	   return;
          }
          
          for(ObjectVO obj: ipInfo){
        	  
        	env = "IPAdress = "    + obj.getIpaddress()   + " | " +
        		  "Time = "        + timeStamp            + " | " +
        		  "UserAgent = "   + userAgent            + " | " +
        		  "Referer = "     + userReferer          + " | " +
        		  "QueryString = " + queryString          + " | " +
        		  "CountryCode = " + obj.getCountrycode() + " | " +
        		  "CountryName = " + obj.getCountryname() + " | " +
        		  "RegionName = "  + obj.getRegionname()  + " | " +
        		  "CityName = "    + obj.getCityname()    + " | " +
        		  "Latitude = "    + obj.getLatitude()    + " | " +
        		  "Longitude = "   + obj.getLongitude()   + " | " +
        		  "ZipCode = "     + obj.getZipcode()     + " | " +
        		  "TimeZone = "    + obj.getTimezone()    + " | ";
        	
        	bw = new BufferedWriter(new FileWriter(propLog, true));
			bw.write(env + "\r\n");
          }
           
	      logger.info("Succesfully Accessed Website Using IP2Location");
		  
		} catch (FileNotFoundException fnf) {
			logger.error("FileNotFoundException opening IP2Location properties file - " + fnf);

		} catch (IOException ioe) {
			logger.error("IOException occured reading IP2Location properties file - " + ioe);

		} catch (Exception e) {
			logger.error("Exception occured while reading IP2Location properties file - " + e);
		}
		
	      finally {

		   try {

			if (bw != null)
				bw.close();

		   } catch (IOException ex) {

			 ex.printStackTrace();
		   }
	      }
         }
}
