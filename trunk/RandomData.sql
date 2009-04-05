-- RANDOM DATA

INSERT INTO Item VALUES (123455, 'Latin Music', 'CD', 'Pop', 'A Records', 2008, 9.99);
INSERT INTO Item VALUES (123456, 'English Music', 'CD', 'Rock', 'B Records', 2007, 8.99);
INSERT INTO Item VALUES (123457, 'European Music', 'CD', 'New Age', 'C Records', 2009, 9.59);
INSERT INTO Item VALUES (123458, 'Video Sexy Music', 'DVD', 'Rap', 'D Records', 2008, 16.79);
INSERT INTO Item VALUES (123459, 'Best Video Music', 'DVD', 'Instrumental', 'E Records', 2006, 18.29);
INSERT INTO Item VALUES (1, 'Latin Music', 'CD', 'Pop', 'A Records', 2008, 9.99);
INSERT INTO Item VALUES (2, 'English Music', 'CD', 'Rock', 'B Records', 2007, 8.99);
INSERT INTO Item VALUES (3, 'European Music', 'CD', 'Pop', 'C Records', 2009, 9.59);
INSERT INTO Item VALUES (4, 'Video Sexy Music', 'DVD', 'Rock', 'D Records', 2008, 16.79);
INSERT INTO Item VALUES (5, 'Best Video Music', 'DVD', 'Rap', 'E Records', 2006, 18.29);
INSERT INTO Item VALUES (6, 'Best Trance Music', 'CD', 'Rock', 'W Records', 2006, 10.29);
INSERT INTO Item VALUES (7, 'Best Trance Music', 'DVD', 'Rock', 'W Records', 2007, 13.29);

INSERT INTO LeadSinger VALUES (123456, 'Jomat');
INSERT INTO LeadSinger VALUES (123456, 'Reggeaton');
INSERT INTO LeadSinger VALUES (123457, 'Matty');
INSERT INTO LeadSinger VALUES (123458, 'KJay');
INSERT INTO LeadSinger VALUES (123459, 'OpppHir');
INSERT INTO LeadSinger VALUES (123459, 'Babyyy');
INSERT INTO LeadSinger VALUES (123455, 'Destructors');

INSERT INTO HasSong VALUES (123456, 'Yo Yo');
INSERT INTO HasSong VALUES (123456, 'Bailando');
INSERT INTO HasSong VALUES (123457, 'Rocky');
INSERT INTO HasSong VALUES (123457, 'Leaving Me');
INSERT INTO HasSong VALUES (123458, 'Dance');
INSERT INTO HasSong VALUES (123458, 'Fully Dance');
INSERT INTO HasSong VALUES (123458, 'Ibiza');
INSERT INTO HasSong VALUES (123459, 'Touch Me');
INSERT INTO HasSong VALUES (123459, 'Be With Me');
INSERT INTO HasSong VALUES (123459, 'Never Leave');
INSERT INTO HasSong VALUES (123455, 'Mozart I');
INSERT INTO HasSong VALUES (123455, 'Mozart II');
INSERT INTO HasSong VALUES (123455, 'Syrus Sim');


INSERT INTO Supplier VALUES ('Sup A', '12 34 Ave', 'Vancouver', 'Y');
INSERT INTO Supplier VALUES ('Sup B', '56 78 Ave', 'Vancouver', 'Y');
INSERT INTO Supplier VALUES ('Sup C', '90 12 Ave', 'Surrey', 'Y');
INSERT INTO Supplier VALUES ('Sup D', '34 56 Ave', 'Langley', 'Y');
INSERT INTO Supplier VALUES ('Sup E', '12 34 Ave', 'Richmond', 'N');
INSERT INTO Supplier VALUES ('Sup F', '10 10 Ave', 'Vancouver', 'N');


INSERT INTO Store VALUES ('Warehouse', '12 34 Street', 'ONL');
INSERT INTO Store VALUES ('Store 01', '56 78 Street', 'STO');
INSERT INTO Store VALUES ('Store 02', '90 12 Street', 'STO');
INSERT INTO Store VALUES ('Store 03', '34 56 Street', 'STO');
INSERT INTO Store VALUES ('Store 04', '78 90 Street', 'STO');
INSERT INTO Store VALUES ('Store 05', '20 20 Street', 'STO');

INSERT INTO Stored VALUES ('Warehouse', 123455, 20);
INSERT INTO Stored VALUES ('Warehouse', 123456, 20);
INSERT INTO Stored VALUES ('Warehouse', 123457, 20);
INSERT INTO Stored VALUES ('Store 01', 123455, 10);
INSERT INTO Stored VALUES ('Store 01', 123456, 10);
INSERT INTO Stored VALUES ('Store 01', 123458, 10);
INSERT INTO Stored VALUES ('Store 02', 123455, 10);
INSERT INTO Stored VALUES ('Store 02', 123457, 10);
INSERT INTO Stored VALUES ('Store 02', 123459, 10);
INSERT INTO Stored VALUES ('Store 03', 123458, 10);
INSERT INTO Stored VALUES ('Store 02', 6, 15);
INSERT INTO Stored VALUES ('Store 02', 7, 15);
INSERT INTO Stored VALUES ('Warehouse', 6, 15);
INSERT INTO Stored VALUES ('Warehouse', 7, 15);


INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup A', 'Warehouse', DATE '2009-03-20');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup A', 'Warehouse', DATE '2009-03-21');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup B', 'Warehouse', DATE '2009-03-22');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup B', 'Store 01', DATE '2009-03-23');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup C', 'Store 01', DATE '2009-03-23');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup B', 'Store 02', DATE '2009-03-24');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup D', 'Store 03', DATE '2009-03-25');

INSERT INTO ShipItem Values (1, 123455, 2.32, 12);
INSERT INTO ShipItem Values (1, 123456, 2.49, 12);
INSERT INTO ShipItem Values (2, 123457, 2.13, 12);
INSERT INTO ShipItem Values (3, 6, 1.42, 15);
INSERT INTO ShipItem Values (3, 7, 1.57, 15);
INSERT INTO ShipItem Values (4, 123455, 2.22, 12);
INSERT INTO ShipItem Values (4, 123458, 3.36, 12);
INSERT INTO ShipItem Values (5, 123456, 1.30, 12);
INSERT INTO ShipItem Values (6, 6, 1.11, 14);
INSERT INTO ShipItem Values (7, 7, 5.03, 14);


INSERT INTO Customer VALUES ('jomat', '1234', 'Joseph', '13 57 Ave', '1111111');
INSERT INTO Customer VALUES ('matt', '1234', 'Matt', '24 68 Ave', '2222222');
INSERT INTO Customer VALUES ('kahlil', '1234', 'Kahlil', '97 63 Ave', '3333333');
INSERT INTO Customer VALUES ('StoreClient', '0', 'StoreClient', 'Storese', '0000000');


INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-04' ,'jomat', 'Store 01', 0, null, null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-04' ,'jomat', 'Store 01', 0, null, null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-04' ,'jomat', 'Store 01', 0, null, null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-04' ,'jomat', 'Store 01', 0, null, null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-05' ,'jomat', 'Store 01', 0, null, null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-04' ,'matt', 'Store 02', 1, DATE '2009-10-10', null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-05' ,'matt', 'Store 02', 1, DATE '2009-10-10', null, null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-06' ,'kahlil', 'Warehouse', 2, DATE '2009-12-12', DATE '2009-04-10', null);
INSERT INTO Purchase Values (receiptId_counter.nextval, DATE '2009-04-07' ,'kahlil', 'Warehouse', 2, DATE '2009-12-12', DATE '2009-04-11', null);


INSERT INTO PurchaseItem values(1, 123455, 10);
INSERT INTO PurchaseItem values(1, 123456, 3);
INSERT INTO PurchaseItem values(1, 2, 12);
INSERT INTO PurchaseItem values(1, 5, 12);
INSERT INTO PurchaseItem values(1, 123457, 6);
INSERT INTO PurchaseItem values(2, 123456, 10);
INSERT INTO PurchaseItem values(2, 3, 21);
INSERT INTO PurchaseItem values(2, 4, 14);
INSERT INTO PurchaseItem values(2, 123457, 2);
INSERT INTO PurchaseItem values(2, 123458, 7);
INSERT INTO PurchaseItem values(3, 123455, 100);
INSERT INTO PurchaseItem values(3, 123458, 133);
INSERT INTO PurchaseItem values(3, 123459, 12);
INSERT INTO PurchaseItem values(4, 123455, 1);
INSERT INTO PurchaseItem values(4, 123458, 2);
INSERT INTO PurchaseItem values(4, 123459, 3);
INSERT INTO PurchaseItem values(5, 123455, 1);
INSERT INTO PurchaseItem values(5, 123458, 2);
INSERT INTO PurchaseItem values(5, 123459, 3);
INSERT INTO PurchaseItem values(6, 6, 1);
INSERT INTO PurchaseItem values(6, 7, 2);
INSERT INTO PurchaseItem values(7, 6, 3);
INSERT INTO PurchaseItem values(7, 7, 2);
INSERT INTO PurchaseItem values(8, 6, 1);
INSERT INTO PurchaseItem values(8, 7, 1);
INSERT INTO PurchaseItem values(9, 7, 4);


INSERT INTO ReturnP Values (returnId_counter.nextval, DATE '2009-04-18' , 7, 'Store 02');
INSERT INTO ReturnP Values (returnId_counter.nextval, DATE '2009-04-19' , 9, 'Store 01');

INSERT INTO ReturnItem Values (1, 6, 2);
INSERT INTO ReturnItem Values (1, 7, 1);
INSERT INTO ReturnItem Values (2, 7, 1);



-- QUERY OPHIR --

-- SELECT DISTINCT I.upc, I.category, I.sellprice, SUM(PI.quantity) 
-- FROM Item I, purchase P, PurchaseItem PI 
-- WHERE I.upc = PI.upc AND P.receiptid = PI.receiptid AND P.dateP = '05-APR-09' AND  P.name ='Store 01' 
-- GROUP BY I.upc, i.category, i.sellprice;



