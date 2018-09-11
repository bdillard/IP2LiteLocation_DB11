#!C:/Perl64/bin/perl

=for comment

Created on Jul 28, 2018

@author:  Benjamin Dillard
@title:   Lead Full Stack Developer / Solutions Architect
@email:   bdillard@optonline.net
@website: http://innovationcomputing.org    

       Perl Website AccessLogger using IP2Lite DB11
       
       Requires: IP2Lite Database DB11 from: https://lite.ip2location.com
       
       Developed Using: : CGI Apache HTTP Server 2.4.x                                
                          Perl 64
                          MySQL 5.5                                     
=cut

use DBI;
use POSIX;
use Config::Tiny;
use CGI qw(:standard);

# **************************************************************************** #
#                     Open the Perl config file.                               #
# **************************************************************************** #

print "Content-type: text/html\n\n";

$Config = Config::Tiny->read( '..\\Config\\config.ini' );
$Config = Config::Tiny->read( '..\\Config\\config.ini', 'utf8' ); 
$Config = Config::Tiny->read( '..\\Config\\config.ini', 'encoding(iso-8859-1)');

# **************************************************************************** #
#              Get MySQL userid and password from config.ini                   #
# **************************************************************************** #

my $userid   = $Config->{mysql}->{userid};
my $passwrd  = $Config->{mysql}->{password};
my $path     = $Config->{logs}->{path};
my $filename = $Config->{logs}->{filename};

my $logfile  = $path . POSIX::strftime('%Y%m%d', localtime) . $filename;

# ******************************************************************
#                    Get Remote Users Environment Information
# ******************************************************************

sub getEnvironment {
   
   my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst)=localtime(time);
   my $timestamp = sprintf ( "%04d%02d%02d %02d:%02d:%02d",
                             $year+1900,$mon+1,$mday,$hour,$min,$sec);
                             
   return ("IPAddress = "    . $ENV{REMOTE_ADDR}     . " | " . 
           "Access Date = "  . $timestamp            . " | " .
           "User Agent = "   . $ENV{HTTP_USER_AGENT} . " | " .
           "Referer = "      . $ENV{HTTP_REFERER}    . " | " .
           "Query String = " . $ENV{QUERY_STRING});
         
}

# ******************************************************************
#                    Get Remote Users IPAddress Formatted
# ******************************************************************

sub getFormattedIPAddress {

  my $ipaddress = $ENV{REMOTE_ADDR};

  push(@ipaddress, split(/\./, $ipaddress));
  my $ipaddr    = join( "", @ipaddress );
  return ($ipaddr);
}

# **************************************************************************** #
#                          Open MySQL Connection.                              #
# **************************************************************************** #

sub openConnection {
    
    my $dbh;
    $dbh = DBI->connect( 'dbi:mysql:ip2locationdb11', $userid, $passwrd ) ||
    print "Could not connect to database.\n$DBI::errstr\n";
    return($dbh);
}

# **************************************************************************** #
#                  Query MySQL IP2Lite Database.                               #
# **************************************************************************** #

sub getIPAddressLocation {

    my $conn = shift;
    my $ip   = shift;
    
    $sql  =  "Select ip_from  as ip_from,                    " .
    
                              "ip_to        as ip_to,        " .
                              "country_code as country_code, " .
                              "country_name as country_name, " .
                              "region_name  as region_name,  " .
                              "city_name    as city_name,    " .
                              "latitude     as latitude,     " .
                              "longitude    as longitude,    " .
                              "zip_code     as zip_code,     " .
                              "time_zone    as time_zone     " .
             "From ip2locationdb11                           " .
             "Where '" . $ip . "'" . " Between ip_from And ip_to Limit 0, 1";
    
    $sth = $conn->prepare($sql);
    $sth->execute
    or print "SQL Error: $DBI::errstr\n";
    
    while (@row = $sth->fetchrow_array) {
    
      return (" | Country Code = "  . @row[2] .
              " | Country Name = "  . @row[3] .
              " | Region Name = "   . @row[4] .
              " | City Name = "     . @row[5] .
              " | Latitude = "      . @row[6] .
              " | Longitude = "     . @row[7] .
              " | Zip Code = "      . @row[8] .
              " | Time Zone = "     . @row[9]);  
    }
}

# ******************************************************************
#                     Log Access Information
# ******************************************************************

sub writeToLogFile {

  my $env    = shift;
  my $result = shift;

  open(FH, '>>', $logfile) or print "Error opening file" . $!;
 
  print FH $env . $result . "\n";
 
  close(FH);
}

# **************************************************************************** #
#                           Call Sub Routines.                                 #
# **************************************************************************** #

# Get Remote Users Environment 
my $env = &getEnvironment();

# Get Remote Users IPAddress Formatted
my $ip = &getFormattedIPAddress(); 

# Open connection to MySQL Database
my $conn = &openConnection();            

# Get IPAddress Location from IP2Lite
my $result = &getIPAddressLocation($conn, $ip);

# Write Access information to Logfile
&writeToLogFile($env, $result);

# Close MySQL DBI Connection.
$conn->disconnect();

print ("Perl Access Successfully Logged.")


