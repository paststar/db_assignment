LOAD DATA INFILE 'C:/Users/ladyc/Desktop/DB_asignment/recommand_system/ml-100k-csv/ratings.csv' 
INTO TABLE ratings
FIELDS TERMINATED BY ',' 
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;