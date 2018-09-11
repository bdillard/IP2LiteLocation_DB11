package com.ip2lite.pkg;

/*
 *      ObjectDAO for IP2Lite
 *
 *      @author Benjamin Dillard
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ObjectDAO {

	public static Connection conn = null;

	public static Logger logger = Logger.getLogger(AccessLogger.class);

    public static Connection getConnection(String Driver, String DataSource,
                                           String Userid, String Password) {

		try {

		  Class.forName(Driver);

		  conn = DriverManager.getConnection(DataSource, Userid, Password);

		  } catch (SQLException e) {
			  System.out.println("SQL Exception occured on IP2 Database - " + e);

		  } catch (Exception e) {
			  System.out.println("Exception occured while reading IP2Location properties file - " + e);
		  }

		  return conn;
	}

    public static List<ObjectVO> getIP2Information(String Driver, String DataSource,
    		                                          String Userid, String Password,
    		                                          String ipAddress)
    		                                          throws SQLException {



    	List<ObjectVO> ipInfo = new ArrayList<ObjectVO>();

    	try {

    	String[] split   = ipAddress.split("\\.");
    	String ipaddress = String.join("", split);

    	conn = getConnection(Driver, DataSource, Userid, Password);

    	String Query =  "Select ip_from as ip_from, "      +
                        "ip_to          as ip_to,        " +
                        "country_code   as country_code, " +
                        "country_name   as country_name, " +
                        "region_name    as region_name,  " +
                        "city_name      as city_name,    " +
                        "latitude       as latitude,     " +
                        "longitude      as longitude,    " +
                        "zip_code       as zip_code,     " +
                        "time_zone      as time_zone     " +
                        "From ip2locationdb11            " +
                        "Where ? Between ip_from AND ip_to Limit 0, 1";

    	PreparedStatement ps = conn.prepareStatement(Query);

    	ps.setString(1, ipaddress);

    	ResultSet rs = ps.executeQuery();

		while(rs.next()){

			ObjectVO obj = new ObjectVO();

			obj.setIpaddress(ipAddress);
			obj.setCountrycode(rs.getString(3));
			obj.setCountryname(rs.getString(4));
			obj.setRegionname(rs.getString(5));
			obj.setCityname(rs.getString(6));
			obj.setLatitude(rs.getString(7));
			obj.setLongitude(rs.getString(8));
			obj.setZipcode(rs.getString(9));
			obj.setTimezone(rs.getString(10));

			ipInfo.add(obj);
		}

       } catch(Exception e){System.out.println(e);
	   }
    	 finally {

    		 conn.close();
    	 }
    	return ipInfo;
    }
}
