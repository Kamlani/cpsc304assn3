
---- ***** DROP ALL TABLES ***** ----
DROP TABLE ReturnItem;
DROP TABLE PurchaseItem;
DROP TABLE LeadSinger;
DROP TABLE HasSong;
DROP TABLE ShipItem;
DROP TABLE Shipment;
DROP TABLE Stored;
DROP TABLE ReturnP;
DROP TABLE Purchase;
DROP TABLE Customer;
DROP TABLE Item;
DROP TABLE Supplier;
DROP TABLE Store;

DROP SEQUENCE shipment_counter;
DROP SEQUENCE receiptId_counter;
DROP SEQUENCE returnId_counter;

---- ***** CREATE ALL TABLES ***** ----

-- Entity Table
CREATE TABLE Item(
upc DECIMAL(12,0) PRIMARY KEY, 
title VARCHAR(30) NOT NULL,
typeI VARCHAR(3) NOT NULL,
category VARCHAR(15) NOT NULL, 
company VARCHAR(30), 
yearI INT NOT NULL, 
sellPrice DECIMAL(10,2) NOT NULL,
CONSTRAINT Cons_CDDVD_TypeI CHECK (typeI IN ('CD', 'DVD')),
CONSTRAINT Cons_Category CHECK (category IN ('Pop', 'Rock', 'Rap', 'Country', 'Classical', ' New Age', 'Instrumental')),
CONSTRAINT Cons_Year CHECK (yearI BETWEEN 1850 AND 2100),
CONSTRAINT Cons_SellPrice CHECK ( sellPrice > 0 ) );

-- Entity Table
CREATE TABLE Supplier(
name VARCHAR(30) PRIMARY KEY, 
address VARCHAR(30), 
city VARCHAR(20) NOT NULL, 
status VARCHAR(1),
CONSTRAINT Cons_Bool_Status CHECK (status IN ('Y', 'N')) );

-- Entity Table
CREATE TABLE Store(
name VARCHAR(30) PRIMARY KEY,
address VARCHAR(30),
typeS VARCHAR(3) NOT NULL,
CONSTRAINT Cons_TypeS CHECK (typeS IN ('ONL', 'STO') ) );

-- Entity Table
CREATE TABLE Customer(
cid VARCHAR (12) PRIMARY KEY,
password VARCHAR (12) NOT NULL,
name VARCHAR (20) NOT NULL,
address VARCHAR(30) NOT NULL,
phone VARCHAR (20) );

-- Relationship Table
CREATE TABLE LeadSinger( 
upc DECIMAL(12,0), 
name VARCHAR(30),
PRIMARY KEY (upc, name),
FOREIGN KEY (upc) REFERENCES Item 
ON DELETE CASCADE);

-- Relationship Table
CREATE TABLE HasSong(
upc DECIMAL(12,0),
title VARCHAR(30),
PRIMARY KEY (upc, title),
FOREIGN KEY (upc) REFERENCES Item
ON DELETE CASCADE);

-- Entity Table
CREATE TABLE Shipment(
sid INTEGER PRIMARY KEY,
supName VARCHAR(30),
name VARCHAR(30) NOT NULL,
shipDate DATE NOT NULL,
FOREIGN KEY (name) REFERENCES Store);

-- Relationship Table
CREATE TABLE ShipItem(
sid INTEGER,
upc DECIMAL(12,0),
supPrice DECIMAL(10,2) NOT NULL,
quantity INTEGER NOT NULL,
PRIMARY KEY (sid, upc),
FOREIGN KEY (sid) REFERENCES Shipment,
FOREIGN KEY (upc) REFERENCES Item,
CONSTRAINT Cons_Quantity_Ship CHECK ( quantity > 0 ) );

-- Relationship Table
CREATE TABLE Stored(
name VARCHAR(30),
upc DECIMAL(12,0),
stock INTEGER NOT NULL,
PRIMARY KEY (name, upc),
FOREIGN KEY (name) REFERENCES Store,
FOREIGN KEY (upc) REFERENCES Item,
CONSTRAINT Cons_Stock CHECK ( stock > 0 ) );

-- Entity Table
CREATE TABLE Purchase(
receiptId INTEGER PRIMARY KEY,
dateP DATE NOT NULL,
cid VARCHAR (12) DEFAULT 'StoreClient', 	-- Default Name for Customer Buying in Store
name VARCHAR(30) NOT NULL,
cardNo DECIMAL (16,0) DEFAULT 0,    	-- 0 Represents Cash. Otherwise, Credit Card
expire DATE DEFAULT NULL,
expectedDate DATE DEFAULT NULL,
deliveredDate DATE DEFAULT NULL,
FOREIGN KEY (cid) REFERENCES Customer,
FOREIGN KEY (name) REFERENCES Store);

-- Relationship Table
CREATE TABLE PurchaseItem(
receiptId INTEGER,
upc DECIMAL(12,0),
quantity INTEGER NOT NULL,
FOREIGN KEY (receiptID) REFERENCES Purchase,
FOREIGN KEY (upc) REFERENCES Item,
CONSTRAINT Cons_Quantity_Purchase CHECK ( quantity > 0 ) );

-- Entity Table
CREATE TABLE ReturnP(
retId INTEGER PRIMARY KEY,
dateR DATE NOT NULL,
receiptId INTEGER NOT NULL,
name VARCHAR(30) NOT NULL,
FOREIGN KEY (receiptID) REFERENCES Purchase,	-- Dont put ON DELETE CASCADE since there is no option to delete Purchase. More constraining to preserve Data Integrity
FOREIGN KEY (name) REFERENCES STORE );

-- Relationship Table
CREATE TABLE ReturnItem(
retId INTEGER,
upc DECIMAL(12,0),
quantity INTEGER NOT NULL,
FOREIGN KEY (retId) REFERENCES ReturnP,
FOREIGN KEY (upc) REFERENCES Item,
CONSTRAINT Cons_Quantity_Return CHECK ( quantity > 0 ) );


---- ***** SEQUENCES ***** ----
CREATE SEQUENCE shipment_counter
START WITH    1
INCREMENT BY  1
NOCYCLE
ORDER;

CREATE SEQUENCE receiptId_counter
START WITH    1
INCREMENT BY  1
NOCYCLE
ORDER;

CREATE SEQUENCE returnId_counter
START WITH    1
INCREMENT BY  1
NOCYCLE
ORDER;