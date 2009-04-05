-- TRIGGERS

-- When Stores does not have that UPC already
CREATE OR REPLACE TRIGGER CreateItemStoreAutomatically
AFTER INSERT ON Item
FOR EACH ROW
DECLARE
   numbStoresInStore NUMBER;
   numbStoresInStore NUMBER;
   tempNumber NUMBER;
   tempNumber2 NUMBER;
   storeName2 VARCHAR(30);
BEGIN
	SELECT COUNT(*) INTO numbStoresInStore
    FROM Store;
	SELECT COUNT(DISTINCT name) INTO numbStoresInStore
	FROM Stored;
	tempNumber := numbStoresInStore - numbStoresInStore;
  IF tempNumber > 0  THEN
	tempNumber2 := 1;
	WHILE tempNumber2 <= tempNumber
	LOOP
		SELECT nameMissing INTO storeName2
		FROM   (SELECT Store.name AS nameMissing FROM Store EXCEPT SELECT Stored.name FROM Stored);
		WHERE  ROWNUM = tempNumber2 ;
		tempNumber2 := tempNumber2 + 1;
		
		INSERT INTO Stored VALUES (storeName2, Item.upc, 0);
		
	END LOOP;
	SELECT Store.name FROM Store
  END IF;
END;



-- ShipItem Trigger
CREATE OR REPLACE TRIGGER UpdateStoredAfterShipItem
 AFTER INSERT ON ShipItem
 FOR EACH ROW
DECLARE
	storeName VARCHAR(30);
	stockStored INT;
	finalStockStore INT;
BEGIN
 -- storeName := (SELECT Shipment.name FROM Shipment WHERE Shipment.sid = ShipItem.sid);
 SELECT Shipment.name INTO storeName FROM Shipment WHERE Shipment.sid = ShipItem.sid
 -- stockStored := (SELECT Stored.stock FROM Stored WHERE storeName = Stored.sid AND ShipItem.upc = Stored.upc);
 SELECT Stored.stock INTO stockStored FROM Stored WHERE storeName = Stored.sid AND ShipItem.upc = Stored.upc
 -- finalStockStore := ShipItem.quantity + stockStored;
 INSERT INTO Stored VALUES(	storeName, 
							ShipItem.upc,
							ShipItem.quantity + stockStored );
END;
