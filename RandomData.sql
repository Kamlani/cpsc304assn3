-- RANDOM DATA

INSERT INTO Item VALUES (123455, 'Latin Music', 'CD', 'Pop', 'A Records', 2008, 9.99);
INSERT INTO Item VALUES (123456, 'English Music', 'CD', 'Rock', 'B Records', 2007, 8.99);
INSERT INTO Item VALUES (123457, 'European Music', 'CD', 'New Age', 'C Records', 2009, 9.59);
INSERT INTO Item VALUES (123458, 'Video Sexy Music', 'DVD', 'Rap', 'D Records', 2008, 16.79);
INSERT INTO Item VALUES (123459, 'Best Video Music', 'DVD', 'Instrumental', 'E Records', 2006, 18.29);

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

INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup A', 'Warehouse', DATE '2009-03-20');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup A', 'Warehouse', DATE '2009-03-21');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup B', 'Warehouse', DATE '2009-03-22');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup B', 'Store 01', DATE '2009-03-23');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup C', 'Store 01', DATE '2009-03-23');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup B', 'Store 02', DATE '2009-03-24');
INSERT INTO Shipment VALUES (shipment_counter.nextval, 'Sup D', 'Store 03', DATE '2009-03-25');

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

INSERT INTO Customer VALUES ('jomat', '1234', 'Joseph', '13 57 Ave', '1111111');
INSERT INTO Customer VALUES ('matt', '1234', 'Matt', '24 68 Ave', '2222222');
INSERT INTO Customer VALUES ('kahlil', '1234', 'Kahlil', '97 63 Ave', '3333333');

