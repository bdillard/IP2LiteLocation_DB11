# *********************************************************************************** #
#                            Drop Database                                            #
# *********************************************************************************** #

drop database IF EXISTS IP2LocationDB11; 

# *********************************************************************************** #
#                            Create Database IP2LiteDB11                              #
# *********************************************************************************** #

create database IF NOT EXISTS IP2LocationDB11; 

# *********************************************************************************** #
#                           Use Database IP2LiteDB11                                  #
# *********************************************************************************** #

use IP2LocationDB11;

# *********************************************************************************** #
#                             Drop Table IP2LocationDB11                              #
# *********************************************************************************** #

drop table IF EXISTS IP2LocationDB11;

# *********************************************************************************** #
#                   Create Table IP2LocationDB11                                      #
# *********************************************************************************** #

create table IF NOT EXISTS `IP2LocationDB11`(
	`ip_from`           INT(10)     UNSIGNED,
	`ip_to`             INT(10)     UNSIGNED,
	`country_code`      CHAR(2),
	`country_name`      VARCHAR(64),
	`region_name`       VARCHAR(128),
	`city_name`         VARCHAR(128),
	`latitude`          DOUBLE,
	`longitude`         DOUBLE,
	`zip_code`          VARCHAR(30),
	`time_zone`         VARCHAR(8),
	INDEX `idx_ip_from`    (`ip_from`),
	INDEX `idx_ip_to`      (`ip_to`),
	INDEX `idx_ip_from_to` (`ip_from`, `ip_to`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# *********************************************************************************** #
#                             Drop Table IP2LocationLog                               #
# *********************************************************************************** #

drop table IF EXISTS IP2LocationLog;

# *********************************************************************************** #
#                     Create Table IP2LocationLog                                     #
# *********************************************************************************** #

create table IF NOT EXISTS `IP2LocationLog`(
	`IPADDRESS`         VARCHAR(50),
	`HTTP_REFERER`      VARCHAR(5000),
	`QUERYSTRING`       VARCHAR(5000),
	`HTTP_USER_AGENT`   VARCHAR(5000),
	`country_code`      CHAR(2),
        `country_name`      VARCHAR(64),
        `region_name`       VARCHAR(128),
	`city_name`         VARCHAR(128),
	`latitude`          DOUBLE,
	`longitude`         DOUBLE,
	`zip_code`          VARCHAR(30),
	`time_zone`         VARCHAR(8),
	INDEX `idx_ip`       (`IPADDRESS`),
	INDEX `idx_zip`      (`zip_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

# *********************************************************************************** #
#                           Show Table Columns                                        #
# *********************************************************************************** #

show columns from IP2LocationDB11;
show columns from IP2LocationLog;

# *********************************************************************************** #
#                              Load Data                                              #
# *********************************************************************************** #

LOAD DATA LOCAL INFILE 'C:/Databases/IP2LOCATION-LITE-DB11.CSV'
INTO TABLE `IP2LocationDB11`
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\r\n'
IGNORE 0 LINES;