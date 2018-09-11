package com.ip2lite.pkg;

import java.io.Serializable;

/*
 *      Value Object for IP2Lite
 * 
 *      @author Benjamin Dillard
 */

public class ObjectVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private String ipaddress;
    private String countrycode;
    private String countryname;
    private String regionname;
    private String cityname;
    private String latitude;
    private String longitude;
    private String zipcode;
    private String timezone;

    public String getIpaddress() {
	return ipaddress;
    }
    
    public void setIpaddress(String ipaddress) {
	  this.ipaddress = ipaddress;
    }
    
    public String getCountrycode() {
      return countrycode;
    }
        
    public void setCountrycode(String countrycode) {
      this.countrycode = countrycode;
    }
    
    public String getCountryname() {
      return countryname;
    }
        
    public void setCountryname(String countryname) {
      this.countryname = countryname;
    }
        
    public String getRegionname() {
      return regionname;
    }
            
    public void setRegionname(String regionname) {
      this.regionname = regionname;
    }
    
    public String getCityname() {
      return cityname;
    }
              
    public void setCityname(String cityname) {
      this.cityname = cityname;
    }
    
    public String getLatitude() {
      return latitude;
      }
                
    public void setLatitude(String latitude) {
      this.latitude = latitude;
    }
      
    public String getLongitude() {
      return longitude;
    }
                  
    public void setLongitude(String longitude) {
      this.longitude = longitude;
    }
        
    public String getZipcode() {
      return zipcode;
    }
                    
    public void setZipcode(String zipcode) {
      this.zipcode = zipcode;
    }
          
    public String getTimezone() {
      return timezone;
    }
                      
    public void setTimezone(String timezone) {
      this.timezone = timezone;
    }
}

