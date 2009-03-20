-- Entity Table
CREATE TABLE Item( 
upc INTEGER(12) PRIMARY KEY, 
title VARCHAR(30),
category VARCHAR(15), 
company VARCHAR(30), 
yearI DATE, 
sellPrice DECIMAL(10,2))

-- Relationship Table
CREATE TABLE LeadSinger( 
upc INTEGER(12), 
name VARCHAR(30),
PRIMARY KEY (upc, name),
FOREIGN KEY (upc) REFERENCES Item)

-- Relationship Table
CREATE TABLE HasSong(
upc INTEGER(12),
title VARCHAR(30),
PRIMARY KEY (upc, title),
FOREIGN KEY (upc) REFERENCES Item)

-- Entity Table
CREATE TABLE Supplier(
name VARCHAR(30) PRIMARY KEY, 
address VARCHAR(30), 
city VARCHAR(20) NOT NULL, 
status BOOLEAN NOT NULL)

-- Entity Table
CREATE TABLE Shipment(
sid INTEGER PRIMARY KEY,
supName VARCHAR(30),
storeName VARCHAR(30) NOT NULL,
shipDate DATE NOT NULL)

-- Relationship Table
CREATE TABLE ShipItem(

