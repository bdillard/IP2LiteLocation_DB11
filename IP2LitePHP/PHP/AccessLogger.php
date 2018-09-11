<?php

/*---------------------------------------------------------------------------------------- */
/*                       PHP Website AccessLogger using IP2Lite DB11                       */
/*                                                                                         */
/*            Requires: IP2Lite Database DB11 from: https://lite.ip2location.com           */
/*                                                                                         */
/*            Developed Using: : Apache HTTP Server 2.4.3                                  */
/*                               PHP 5.4                                                   */
/*                               MySQL 5.5                                                 */
/*                               PHP Mars Eclipse                                          */
/*                                                                                         */
/*                                                Author:  Benjamin Dillard                */
/*                                                Title:   Lead Full Stack Developer       */
/*                                                Email:   bdillard@optonline.net          */
/*                                                Website: http://innovativecomputing.org  */
/*---------------------------------------------------------------------------------------- */

  class AccessLogger {
   
  /*--------------------------------------------------------------------------------------- */
  /*                         AccessLogger Class Constructor                                 */
  /*--------------------------------------------------------------------------------------- */

  function __construct() {
      
      // Parse ini file for DB Connection Parms
      $ini = parse_ini_file('../config/ip2Lite.ini',true);
      
      $host     = $ini['Database'] ['Host'];
      $username = $ini['Database'] ['Username'];
      $password = $ini['Database'] ['Password'];
      
      $path     = $ini['Path'] ['Dir'];
      $logfile  = $ini['Path'] ['Logfile'];
      
      // Connect to MySQL Database
      $conn = AccessLogger::openConnection($Host, $username, $password);
      
      // Get Environment Information
      $env = AccessLogger::setIPAddressLocation();
      
      // Get IP2Location Lite DB11 Access Information
      $result = AccessLogger::getIPAddressLocation($conn);
      
      // Wtite IP Information to Log File.
      $val = AccessLogger::writeToLogFile($env, $result, $path, $logfile);
      
      // Wtite IP Information to Database Log File.
      AccessLogger::writeToDBLog($conn, $env, $result);
      
      // Optionally Email IP Information.
      // Note: Remove comment and Be sure to add YOUR Email Address in the function.
      // Not advisable of course for high traffic sites!!!
      //AccessLogger::emailAccessInformation($val);
      
      // Close MySQL Database Connection
      AccessLogger::closeConnection($conn);
  }
  
  /*--------------------------------------------------------------------------------------- */
  /*                          Open MySQL Database Connection                                */
  /*--------------------------------------------------------------------------------------- */
  
  function openConnection($database, $user_name, $password) {
  
     $conn =  mysql_connect( $database, $user_name, $password  );
     
     if ( ! $conn ) {
         die( "Couldn't connect to MySQL: ".mysql_error() );
     } 
     
     return $conn;
  }
  
  /*--------------------------------------------------------------------------------------- */
  /*                         Set Environment Access Information                             */
  /*--------------------------------------------------------------------------------------- */
  
  function setIPAddressLocation() {
  
  	$ip        = $_SERVER["REMOTE_ADDR"];
  	$time      = date("M j G:i:s Y");
  	$userAgent = getenv('HTTP_USER_AGENT');
  	$referrer  = getenv('HTTP_REFERER');
  	$query     = getenv('QUERY_STRING');
  
  	return $ip . "|" . $time . "|" . $userAgent . "|" . $referrer . "|" . $query . "|";
  }
  
  /*--------------------------------------------------------------------------------------- */
  /*                    Get Database IP2Lite DB11 Information                               */
  /*--------------------------------------------------------------------------------------- */
  
  function getIPAddressLocation($conn) {
     
     $ip    = getenv('REMOTE_ADDR');
     $Array = explode('.',$ip);
     $ip    = implode($Array);

      mysql_select_db( "ip2locationdb11", $conn ) 
      or die ( "Can Not Open ip2locationdb11 Database: ".mysql_error() );
     
     $query = sprintf("Select ip_from       as ip_from,      " .
                              "ip_to        as ip_to,        " .
                              "country_code as country_code, " .
                              "country_name as country_name, " .
                              "region_name  as region_name,  " .
                              "city_name    as city_name,    " .
                              "latitude     as latitude,     " .
                              "longitude    as longitude,    " .
                              "zip_code     as zip_code,     " .
                              "time_zone    as time_zone     " .
                      "From ip2locationdb11.ip2locationdb11  " .
                      "Where $ip Between ip_from AND ip_to Limit 0, 1");
                          
      $result = mysql_query($query);
      
      if (mysql_num_rows($result)==0) {
      	
      	$returnVal = " | Website Accessed but NO IP Information found on IP2Lite DB11 Database";
      	
      } else {
                                                            
        while ($row = mysql_fetch_assoc($result)) {
        
          $returnVal = $row['country_code'] . "|" .
                       $row['country_name'] . "|" .
                       $row['region_name']  . "|" .
                       $row['city_name']    . "|" .
                       $row['latitude']     . "|" .
                       $row['longitude']    . "|" .
                       $row['zip_code']     . "|" .
                       $row['time_zone'];               
        }
      }

      return $returnVal;
  }
    
  /*--------------------------------------------------------------------------------------- */
  /*                        Log Access Information to Log File                              */
  /*--------------------------------------------------------------------------------------- */
   
  function writeToLogFile($env, $result, $path, $logfile) {
  
    $today        = date("Y_m_d");
    $logfile      = $today . $logfile; 
    $saveLocation = $path  . $logfile;
    $log          = $env   . $result;
    $array        = explode('|',$log);
    
    echo("saveLocation = " . $saveLocation);
    
    $log = "Ipaddress = "    . $array[0]  . " | " .
           "Date Time = "    . $array[1]  . " | " .
           "User Agent = "   . $array[2]  . " | " .
           "Referrer = "     . $array[3]  . " | " .
           "Query = "        . $array[4]  . " | " .
           "Country Code = " . $array[5]  . " | " .
           "Country Name = " . $array[6]  . " | " .
           "Region Name = "  . $array[7]  . " | " .
           "City Name = "    . $array[8]  . " | " .
           "Latitude = "     . $array[9]  . " | " .
           "Longitude = "    . $array[10] . " | " .
           "ZipCode = "      . $array[11] . " | " .
           "Time Zone = "    . $array[12];
  
    if  (!$handle = @fopen($saveLocation, "a")) {
     echo('writeToLogFile failed to open');	
      exit;
    
    } else { 
    	if (@fwrite($handle,"$log\r\n") === FALSE) {
         echo('writeToLogFile Write Failed');	 
          exit; 
  }               
    @fclose($handle);
    return $log;
  } 
} 

/*--------------------------------------------------------------------------------------- */
/*                        Log Access Information to Database Table                        */
/*--------------------------------------------------------------------------------------- */
 
function writeToDBLog($conn, $env, $result) {
	
	mysql_select_db( "ip2locationdb11", $conn )
	or die ( "Can Not Open ip2locationdb11 Database: ".mysql_error() );
	
	$log          = $env . $result;
	$array        = explode('|',$log);	
	
	$INSERT  = sprintf("INSERT INTO ip2locationdb11.IP2LocationLog " .
			           "(ipaddress, datetime, "                      .
			           "http_user_agent, referrer, querystring, "    .
			           "country_code, country_name, "                .
			           "region_name, city_name, "                    .
			           "latitude, longitude, "                       .
			           "zip_code, time_zone, created) "              .
			           "VALUES ('" .  $array[0]  . "', '"            .
			                          $array[1]  . "', '"            .
			                          $array[2]  . "', '"            .
			                          $array[3]  . "', '"            .
			                          $array[4]  . "', '"            .
			                          $array[5]  . "', '"            .
			                          $array[6]  . "', '"            .
			                          $array[7]  . "', '"            .
			                          $array[8]  . "', '"            .
			                          $array[9]  . "', '"            .
			                          $array[10] . "', '"            .
			                          $array[11] . "', '"            .
			                          $array[12] . "', now())");
	
	
	
	$result = mysql_query($INSERT);
	
	if (!$result) {
		die('Invalid Insert in Access Logger ' . mysql_error());
	}	
}
  /*--------------------------------------------------------------------------------------- */
  /*                        Email Log Access Information                                    */
  /*--------------------------------------------------------------------------------------- */

  function emailAccessInformation($val) {
    
      $to      = "Your Email Address";
         
      $subject = "Website Accessed";
                    
      if ( mail($to, $subject, $val) ) {
	      
	 } else {
	 	  echo("Email, failed in AccessLogger.");
	 }
    }
    
    /*--------------------------------------------------------------------------------------- */
    /*                         Close MySQL Database Connection                                */
    /*--------------------------------------------------------------------------------------- */
    
    function closeConnection($conn) {
    
    	mysql_close( $conn);
    }
  }

$AccessLogger = new AccessLogger();
?>
