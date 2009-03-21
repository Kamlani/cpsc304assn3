
-- Entity Table
CREATE TABLE Item( 
upc DECIMAL(12,0) PRIMARY KEY, 
title VARCHAR(30) NOT NULL,
typeI VARCHAR(3) NOT NULL,
category VARCHAR(15) NOT NULL, 
company VARCHAR(30), 
yearI DATE NOT NULL, 
sellPrice DECIMAL(10,2) NOT NULL)

-- Relationship Table
CREATE TABLE LeadSinger( 
upc DECIMAL(12,0), 
name VARCHAR(30),
PRIMARY KEY (upc, name),
FOREIGN KEY (upc) REFERENCES Item)

-- Relationship Table
CREATE TABLE HasSong(
upc DECIMAL(12,0),
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
name VARCHAR(30) NOT NULL,
shipDate DATE NOT NULL,
FOREIGN KEY (name) REFERENCES Store)

-- Relationship Table
CREATE TABLE ShipItem(
sid INTEGER,
upc DECIMAL(12,0),
supPrice DECIMAL(10,2) NOT NULL,
quantity INTEGER NOT NULL,
PRIMARY KEY (sid, upc),
FOREIGN KEY (sid) REFERENCES Shipment,
FOREIGN KEY (upc) REFERENCES Item)

-- Entity Table
CREATE TABLE Store(
name VARCHAR(30) PRIMARY KEY,
address VARCHAR(30),
typeS VARCHAR(3) NOT NULL)

-- Relationship Table
CREATE TABLE Stored(
name VARCHAR(30),
upc DECIMAL(12,0),
stock INTEGER NOT NULL,
PRIMARY KEY (name, upc),
FOREIGN KEY (name) REFERENCES Store,
FOREIGN KEY (upc) REFERENCES Item)

-- Entity Table
CREATE TABLE Purchase(
receiptId INTEGER PRIMARY KEY,
dateP DATE NOT NULL,
cid VARCHAR (12),						-- NEED SOMETHING IN CASE IS CUSTOMER IN STORE (NULL OR DEFAULT) What about Foreign Key
name VARCHAR(30) NOT NULL,
cardNo DECIMAL (16,0),
expire DATE,
expectedDate DATE,
deliveredDate DATE,
FOREIGN KEY (cid) REFERENCES Customer,  -- NEED SOMETHING IN CASE IS CUSTOMER IN STORE
FOREIGN KEY (name) REFERENCES Store)

-- Relationship Table
CREATE TABLE PuchaseItem(
receiptId INTEGER,
upc DECIMAL(12,0),
quantity INTEGER NOT NULL,
FOREIGN KEY (receiptID) REFERENCES Purchase,
FOREIGN KEY (upc) REFERENCES Item)

-- Entity Table
CREATE TABLE Customer(
cid VARCHAR (12) PRIMARY KEY,
password VARCHAR (12) NOT NULL,
name VARCHAR (20) NOT NULL,
address VARCHAR(30) NOT NULL,
phone DECIMAL (10,0) )

-- Entity Table
CREATE TABLE ReturnP(
retId INTEGER PRIMARY KEY,
dateR DATE NOT NULL,
receiptId INTEGER NOT NULL,
name VARCHAR(30) NOT NULL,
FOREIGN KEY (receiptID) REFERENCES Purchase,
FOREIGN KEY (name) REFERENCES STORE )

-- Relationship Table
CREATE TABLE ReturnItem(
retId INTEGER,
upc DECIMAL(12,0),
quantity INTEGER NOT NULL,
FOREIGN KEY (retId) REFERENCES ReturnP,
FOREIGN KEY (upc) REFERENCES Item )


---- ***** SEQUENCES ***** ----
CREATE SEQUENCE shipment_counter
START WITH    1
INCREMENT BY  1
NOCYCLE
ORDER

CREATE SEQUENCE purchase_counter
START WITH    1
INCREMENT BY  1
NOCYCLE
ORDER

CREATE SEQUENCE return_counter
START WITH    1
INCREMENT BY  1
NOCYCLE
ORDER
