package sql;

import java.sql.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Vector;
 

// a class that has all the methods to connect to the DB and query it.
public class Transactions {
   
    private static Connection dbConn;    // the connection to the database.
    final static int deliveriesPerDay = 3;
    
    // method to connect to the database. Throws SQLException if connection is not successful,
    // and class not found exception if driver issue occurs. - Ophir
    public static void connect(String username, String pass) throws SQLException, ClassNotFoundException
    {
        String URL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1521:ug";
        try
        {
            // load the driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException ex)
        {
            throw ex;
        }
        try
        {
            // try and connect to the DB
            dbConn = DriverManager.getConnection(URL, username, pass);
            dbConn.setAutoCommit(false);    		// to execute queries as transactions.
            System.out.println("Connected!\n"); 
        }
        catch(SQLException ex)
        {
            printSQLError(ex);
            throw ex;
        }
    }
   
    // prints the SQL exception error txt. - Ophir
    private static void printSQLError(SQLException ex)
    {
         System.out.println("SQLException: " + ex.getMessage());
         System.out.println("SQLState: " + ex.getSQLState());
         System.out.println("VendorError: " + ex.getErrorCode());
    }
   
    // to close the DB connection
    public static void closeConnection() throws SQLException
    {
        try
        {
            dbConn.close();
        }
        catch(SQLException ex)
        {
        	printSQLError(ex);
            throw ex;
        }
    }
   
    // function to create a cash purchase (ie insert into Purchase) - Ophir
    public static int insertCashPurchase(Date date, String name) throws SQLException
    {
        String sql = "INSERT INTO Purchase VALUES( receiptId_counter.nextval, ?, ?, ?, ?, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setNull(2, Types.VARCHAR);
            ps.setString(3, name);
            ps.setNull(4, Types.DECIMAL);
            ps.setNull(5, Types.DATE);
            ps.setNull(6, Types.DATE);
            ps.setNull(7, Types.DATE);
            ps.executeUpdate();
            int receiptId = getCurrentReceiptID();    // commit is issued inside the function
            ps.close();
            return receiptId;
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
       
    }
   
    // function to create a credit purchase (ie insert into Purchase) - Ophir
    public static int insertCreditPurchase(Date date, String name,
                                         BigDecimal cardNum, Date expire) throws SQLException
    {
        String sql = "INSERT INTO Purchase VALUES( receiptId_counter.nextval, ?, ?, ?, ?, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setNull(2, Types.VARCHAR);
            ps.setString(3, name);
            ps.setBigDecimal(4, cardNum);
            ps.setDate(5, expire);
            ps.setNull(6, Types.DATE);
            ps.setNull(7, Types.DATE);
            ps.executeUpdate();
            int receiptId = getCurrentReceiptID();    // commit is issued inside the function
            ps.close();
            return receiptId;
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
       
    }
   
    // function to create an online purchase (ie insert into Purchase) - Ophir
    public static int insertOnlinePurchase( Date date, String cid, String name,
                                            BigDecimal cardNum, Date expire,
                                            Date expected) throws SQLException
    {
        String sql = "INSERT INTO Purchase VALUES( receiptId_counter.nextval, ?, ?, ?, ?, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setDate(1, date);
            ps.setString(2, cid);
            ps.setString(3, name);
            ps.setBigDecimal(4, cardNum);
            ps.setDate(5, expire);
            ps.setDate(6, expected);
            ps.setNull(7, Types.DATE);
            ps.executeUpdate();
            int receiptId = getCurrentReceiptID();    // commit is issued inside the function
            ps.close();
            return receiptId;
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
    // to get the latest recieptId - Ophir
    private static int getCurrentReceiptID() throws SQLException
    {
        String sql = "SELECT receiptId_counter.currval as currentVal FROM Purchase";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return dbResult.getInt("currentVal");   
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            
            throw ex;
        }

    }
   
    // function to add an Item to a purchase. (ie insert into PurchaseItem) - Ophir
    public static void addItemToPurchase(int recieptId, BigDecimal upc, int quantity) throws SQLException
    {
        String sql = "INSERT INTO PurchaseItem VALUES(?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1, recieptId);
            ps.setBigDecimal(2, upc);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            
            String nameStore = getStorePurch ( recieptId );
            
            // This function commits
            modifyQuantityItemStore( nameStore, upc, - quantity );
            
            ps.close();
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                
                throw e;
            }
        }
    }
   
   
    // to verify that the receipt exists before executing a return - Jomat Ok
    private static boolean checkReceiptIDReturn(int recieptId) throws SQLException
    {
        String sql = "SELECT COUNT(*) as receiptId_count FROM Purchase WHERE receiptId = " + recieptId;
        try
        {
            Statement stmt = dbConn.prepareStatement(sql);
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            //stmt.close();
            return (dbResult.getInt("receiptId_count") != 0);   
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }

    }
   
   
    // inserting a new return document - Jomat Ok
    private static boolean checkDateReturn (int recieptId) throws SQLException
    {
        String sql = "SELECT dateP FROM Purchase WHERE receiptId = " + recieptId;
        try
        {
            Statement stmt = dbConn.prepareStatement(sql);
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
           
            dbResult.next();
           
            long TimeStampSystem = System.currentTimeMillis();
           
            Timestamp SQLDate = dbResult.getTimestamp("dateP");
            long TimeStampSQL = SQLDate.getTime();
           
            long noDays = (TimeStampSystem - TimeStampSQL);
           
            stmt.close();
            return (noDays <= (1000 * 24 * 60 * 60 * 15) );
           
           
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }

   
    // inserting a new return document - Jomat Ok
    public static int insertReturn(Date date, int recieptId, String name) throws SQLException
    {
        String sql = "INSERT INTO ReturnP VALUES( returnId_counter.nextval, ?, ?, ?)" ;
        try
        {           
            if ( checkReceiptIDReturn( recieptId ) )
            {
                if ( checkDateReturn( recieptId ) )
                {
                    PreparedStatement ps = dbConn.prepareStatement(sql);
                    ps.setDate(1, date);
                    ps.setInt(2, recieptId);
                    ps.setString(3, name);
                    ps.executeUpdate();
                    int returnId = getCurrentReturnID();    // commit is issued inside the function
                    ps.close();
                    return returnId;
                }
                else
                {
                    return -1;     // Out of range of 15 days.
                }
            }
            else
            {
                return -1;        // Receipt does not exists
            }
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
       
       
    }
   
   
    // Get information about the payment type - Jomat Ok
    // Returns the credit card number or 0 for cash
    public static long returnCashCredit(int recieptId) throws SQLException
    {
        String sql = "SELECT cardNo FROM Purchase WHERE receiptId = " + recieptId;
        try
        {
            Statement stmt = dbConn.prepareStatement(sql);
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            BigDecimal numb = dbResult.getBigDecimal("cardNo");
            stmt.close();
            return numb.longValue();
           
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
   
    // to get the latest retId from ReturnP table - Jomat Ok
    private static int getCurrentReturnID() throws SQLException
    {
        String sql = "SELECT returnId_counter.currval AS currentVal FROM ReturnP";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            //stmt.close();
            dbResult.next();
            return dbResult.getInt("currentVal");   
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }

    }
   
   
    // obtain the receiptId from the ReturnP table Ok
    private static int getReceiptIdFromReturn(int retId) throws SQLException
    {
        String sql = "SELECT receiptID FROM ReturnP WHERE retId = " + retId;
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            //stmt.close();
            dbResult.next();
            return dbResult.getInt("receiptID");   
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }

    }

   
    // Adds an item to the Return document - Jomat Ok
    public static void addItemToReturn(int retId, BigDecimal upc, int quantity) throws SQLException
    {
       
        String sql = "INSERT INTO ReturnItem VALUES(?, ?, ?)" ;
        try
        {           
            if (checkItemIDReturn( getReceiptIdFromReturn( retId ) , upc, quantity))
            {
                PreparedStatement ps = dbConn.prepareStatement(sql);
                ps.setInt(1, retId);
                ps.setBigDecimal(2, upc);
                ps.setInt(3, quantity);
                ps.executeUpdate();
                // dbConn.commit();
                
                String nameStore = getStoreRet ( retId );
                
                // This function commits
                modifyQuantityItemStore( nameStore, upc, quantity );
                
                ps.close();
            }
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
           
   
    // to verify that the receipt contains the item that is being returned before executing a return - Jomat Ok
    private static boolean checkItemIDReturn(int recieptId, BigDecimal upc, int quantity) throws SQLException
    {
        if (quantity > 0)
        {   
            String sql = "SELECT SUM(quantity) AS retSum FROM PurchaseItem WHERE receiptId = " + recieptId + " AND upc = " + upc;
            try
            {
                Statement stmt = dbConn.createStatement();
                ResultSet dbResult = stmt.executeQuery(sql);
                dbConn.commit();
                //stmt.close();
                dbResult.next();
                return ( dbResult.getInt("retSum") >= quantity);   
            }
            catch(SQLException ex)
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
        }
        else
        {
            return false;
        }
    }
   
   
    // creation of the username by the customer online - Jomat Ok
    public static void createUsername(String cid, String password, String name, String address, String phone) throws SQLException
    {
        String sql = "INSERT INTO Customer VALUES( ?, ?, ?, ?, ?)" ;
            try
            {           
                PreparedStatement ps = dbConn.prepareStatement(sql);
                ps.setString(1, cid);
                ps.setString(2, password);
                ps.setString(3, name);
                ps.setString(4, address);
                ps.setString(5, phone);
                ps.executeUpdate();
                dbConn.commit();
                ps.close();
                               
            }
            catch(SQLException ex)
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
       
    }
   
   
    // function to create a shipment (ie insert into Shipment) - Jomat Ok
    public static int insertShipment(String supName, String storeName, Date shipDate) throws SQLException
    {
        String sql = "INSERT INTO Shipment VALUES( shipment_counter.nextval, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, supName);
            ps.setString(2, storeName);
            ps.setDate(3, shipDate);
            ps.executeUpdate();
            int ShipmentId = getCurrentShipmentID();    // commit is issued inside the function
            ps.close();
            return ShipmentId;
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }

   
    // to get the latest sid - Jomat Ok
    private static int getCurrentShipmentID() throws SQLException
    {
        String sql = "SELECT shipment_counter.currval as currentVal FROM Shipment";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return dbResult.getInt("currentVal");   
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
   
    // function to create a Store - Jomat Ok
    public static String insertStore(String name, String address, String type) throws SQLException
    {
        if (type.equalsIgnoreCase("ONL"))
        {
            type = "ONL";
        }
        else
        {
            if (type.equalsIgnoreCase("STO"))
            {
                type = "STO";
            }
            else
            {
                System.out.println(" Wrong Type in insertStore");
            	return "WRONG TYPE";
            }
        }
       
        String sql = "INSERT INTO Store VALUES( ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, type);
            ps.executeUpdate();
            ps.close();
            return name;
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
   
    // function to create a Supplier - Jomat Ok
    public static String insertSupplier(String name, String address, String city, String status) throws SQLException
    {
        if (status.equalsIgnoreCase("Y"))
        {
            status = "Y";
        }
        else
        {
            if (status.equalsIgnoreCase("N"))
            {
                status = "N";
            }
            else
            {
            	System.out.println(" Wrong Status in insertSupplier");
            	return "WRONG STATUS";
            }
        }
       
        String sql = "INSERT INTO Supplier VALUES( ?, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, city);
            ps.setString(4, status);
            ps.executeUpdate();
            dbConn.commit();
            ps.close();
            return name;
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
   
    // Adds an item to the ShipItem Table (ie. Items included in a Shipment) - Jomat Ok
    // ??? Recall that the price of the shipment should affect directly the price of the Item (20% more)
    public static void addItemToShipment(int sid, BigDecimal upc, BigDecimal supPrice, int quantity) throws SQLException
    {

        String sql = "INSERT INTO ShipItem VALUES(?, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setBigDecimal(2, upc);
            ps.setBigDecimal(3, supPrice);
            ps.setInt(4, quantity);
            ps.executeUpdate();

            updatePriceItem(upc, supPrice.multiply(new BigDecimal(1.2)));
            
            String nameStore = getStoreShipm ( sid );
            
            // This function commits
            modifyQuantityItemStore( nameStore, upc, quantity );
            
            //dbConn.commit();
            ps.close();
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
   
   
    // Adds an item to a Store inserting the information to Stored Table (ie. Keeps the inventory) - Jomat Ok
    // To add or subtract quantity from an item in a store, use modifyQuantityItemStore
    public static void insertItemToStore(String name, BigDecimal upc, int stock) throws SQLException
    {

        String sql = "INSERT INTO Stored VALUES(?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setBigDecimal(2, upc);
            ps.setInt(3, stock);
            ps.executeUpdate();
            dbConn.commit();
            ps.close();
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
   
   
    // Modifies the quantity (Positive or Negatively) of an Item in a Store (ie. Shipment or Purchase Processes) - Jomat Ok
    // To modify directly the quantity use insertItemToStore
    // incrementDecrement can takes values between -oo to +oo. Which means: Negative (Substract) and Positive (Add)
    public static boolean modifyQuantityItemStore(String name, BigDecimal upc, int incrementDecrement) throws SQLException
    {

        try
        {           
            int tempQuantity = checkQuantity(upc, name) + incrementDecrement;
            //System.out.println(tempQuantity);
            if (tempQuantity >= 0)
            {
                String sql = "UPDATE Stored SET stock = " + tempQuantity + " WHERE name = '" + name + "' AND upc = " + upc;
                Statement stmt = dbConn.createStatement();
                stmt.executeUpdate(sql);
                dbConn.commit();
                stmt.close();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }

       
    // to get the quantity of an Item in a Store - Jomat Ok
    public static int getQuantityItemStore(String name, BigDecimal upc) throws SQLException
    {
        String sql = "SELECT stock FROM Stored WHERE name = '" + name + "' AND upc = " + upc;
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return dbResult.getInt("stock");   
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
    
   
   
    // Creates an item in the Item table - Jomat Ok
    public static boolean createItem(    BigDecimal upc, String title, String typeI, String category,
                                        String company, int year, BigDecimal sellPrice) throws SQLException
    {
        if (year > 2100 || year < 1850)
            return false;
       
        if (sellPrice.doubleValue() < 0)
            return false;
       
        if (typeI.equalsIgnoreCase("CD"))
        {
            typeI = "CD";
        }
        else
        {
            if (typeI.equalsIgnoreCase("DVD"))
            {
                typeI = "DVD";
            }
            else
            {
                return false;
            }
        }
       
        if ( ! (category.equalsIgnoreCase("Pop") || category.equalsIgnoreCase("Rock") ||
                category.equalsIgnoreCase("Rap") || category.equalsIgnoreCase("Country") ||
                category.equalsIgnoreCase("Classical") || category.equalsIgnoreCase("New Age") ||
                category.equalsIgnoreCase("Instrumental")) )
            return false;
               
        String sql = "INSERT INTO Item VALUES(?, ?, ?, ?, ?, ?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setBigDecimal(1, upc);
            ps.setString(2, title);
            ps.setString(3, typeI);
            ps.setString(4, category);
            ps.setString(5, company);
            ps.setInt(6, year);
            ps.setBigDecimal(7, sellPrice);
            ps.executeUpdate();
            dbConn.commit();
            ps.close();
            
            insertItemIntoStored(upc);
            
            return true;
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
   
   
    // Inserts a Lead Singer for Item (ie. UPC) - Jomat Ok
    public static void insertLeadSinger(BigDecimal upc, String name) throws SQLException
    {
        String sql = "INSERT INTO LeadSinger VALUES(?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setBigDecimal(1, upc);
            ps.setString(2, name);
            ps.executeUpdate();
            dbConn.commit();
            ps.close();
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
   
   
    // Inserts a Song for Item (ie. UPC) - Jomat Ok
    public static void insertHasSong(BigDecimal upc, String title) throws SQLException
    {
        String sql = "INSERT INTO HasSong VALUES(?, ?)" ;
        try
        {           
            PreparedStatement ps = dbConn.prepareStatement(sql);
            ps.setBigDecimal(1, upc);
            ps.setString(2, title);
            ps.executeUpdate();
            dbConn.commit();
            ps.close();
        }
        catch(SQLException ex)
        {
            try
            {
                dbConn.rollback();
                printSQLError(ex);
                throw ex;
            }
            catch(SQLException e)
            {
                throw e;
            }
        }
    }
   
    // deletes an specified supplier - Jomat Ok
    public static int deleteSupplier(String name) throws SQLException
    {
        String sql = "DELETE FROM Supplier WHERE name = '" + name + "'";
        try
        {           
            Statement stmt = dbConn.createStatement();
            int numRows = stmt.executeUpdate(sql);
            dbConn.commit();
            stmt.close();
            return numRows;
            
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }

   
    // process the delivery of the order (ie. Purchase) - Jomat Ok
    public static void deliveryOrder(int receiptId, Date deliveredDate) throws SQLException
    {
        String sql = "UPDATE Purchase SET deliveredDate = DATE '" + deliveredDate + "' WHERE receiptId = " + receiptId;
        try
        {           
            Statement stmt = dbConn.createStatement();
            stmt.executeUpdate(sql);
            dbConn.commit();
            stmt.close();
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
    //  search Item based on category, title, singer, and quantity (ie. searchItem) - Ophir
    public static ResultSet searchItem(String category, String title, String singer, int quantity) throws SQLException
    {
        String sql = "SELECT DISTINCT I.upc, I.title, St.stock from Item I, Stored St, LeadSinger L, Store S " +
                " WHERE I.upc=L.upc AND I.upc=St.upc AND L.name LIKE '" + singer + "' AND I.category LIKE '"+ category + "'" +
                " AND I.title LIKE '" + title + "' AND St.stock >= " + quantity + " AND St.name = S.name" +
                " AND S.typeS ='ONL' AND  rownum<=10";
        try
        {
                Statement stmt = dbConn.createStatement();
                ResultSet dbResult = stmt.executeQuery(sql);
                dbConn.commit();
                return dbResult;
        }
        catch (SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
   
    
    // verify the username and password of a customer - Ophir
    public static boolean checkUsernamePassword(String cid, String password) throws SQLException
    {
        String sql = "SELECT password FROM CUSTOMER WHERE cid = '" + cid + "'";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return (dbResult.getString("password").equals(password));
        }
        catch(SQLException ex)
        {
        	//printSQLError(ex);
            return false;
        }
    }
   
    
    // verify the quantity and return it in Stored table- Ophir
    public static int checkQuantity(BigDecimal upc, String storeName) throws SQLException
    {
        String sql = "SELECT stock FROM Stored WHERE upc = '" + upc + "' AND name = '" + storeName + "'";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return dbResult.getInt("stock");
        }
        catch(SQLException ex)
        {
        	printSQLError(ex);
            throw ex;
        }
    }
   
    // - Ophir
    public static ResultSet getPurchaseItems(BigDecimal receiptId) throws SQLException
    {
        String sql = "SELECT DISTINCT PI.upc, pi.quantity, i.sellprice FROM purchase p, purchaseitem pi, Item i WHERE pi.upc = i.upc  AND  pi.receiptid= '" + receiptId + "'";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            return dbResult;
        }
        catch(SQLException ex)
        {
        	printSQLError(ex);
            throw ex;
        }
    }
   
    // - Ophir
    public static ResultSet getDailyReport(Date date, String storeName) throws SQLException
    {
        String sql = "SELECT DISTINCT I.upc, I.category, I.sellprice, SUM(PI.quantity) AS quantity from " +
                "Item I, Purchase P, PurchaseItem PI WHERE " +
                " I.upc = PI.upc AND P.receiptid = PI.receiptid AND P.dateP = date '" + date.toString() + "' AND P.name = '" + storeName + "' " +
                " GROUP BY I.upc, I.category, I.sellprice ORDER BY CATEGORY ASC";
       
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            return dbResult;
        }
        catch(SQLException ex)
        {
            System.out.println("getDailyReport Exception");
            printSQLError(ex);
            throw ex;
        }
    }

    
    // produces a ResultSet with the Top Selling items - Ophir
    public static ResultSet getTopSellingItems(Date date, int max) throws SQLException
    {
        String sql = " SELECT X.upc, X.title, X.company, X.stock, Y.totalSold FROM " +
                " (SELECT Z.upc, I.title, I.company, SUM(Z.stock) AS stock FROM " +
                " (SELECT DISTINCT St.upc, ST.name, ST.stock FROM Stored ST, PurchaseItem PI WHERE PI.upc=ST.upc) Z, " +
                " Item I WHERE Z.upc=I.upc GROUP BY Z.upc, I.company, I.title) X, " +
                " (SELECT DISTINCT PI.upc, SUM(PI.Quantity) AS totalSold FROM PurchaseItem PI, Item I, Purchase P " +
                " WHERE I.upc=PI.upc AND P.dateP = date '"+ date.toString() + "' AND p.receiptid = pi.receiptid  GROUP BY PI.upc) Y " +
                " where X.upc=Y.upc and rownum <=" + max + " ORDER BY totalSOld DESC";
       
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            return dbResult;
        }
        catch(SQLException ex)
        {
            System.out.println("getTopSellingItems Exception");
            printSQLError(ex);
            throw ex;
        }
    }
   
    
   
 // changes the sell price after a shipment the delivery of the order (ie. Purchase) - Jomat Ok
    private static void updatePriceItem(BigDecimal upc, BigDecimal sellPrice) throws SQLException
    {
        String sql = "UPDATE Item SET sellPrice = " + sellPrice + " WHERE upc = " + upc;
        try
        {           
            Statement stmt = dbConn.createStatement();
            stmt.executeUpdate(sql);
            //dbConn.commit();
            stmt.close();
        }
        catch(SQLException ex)
        {
            dbConn.rollback();
            printSQLError(ex);
            throw ex;
        }
    }
    
    
    // get the Store Name - Ophir
    public static String getStorePurch( int receiptId ) throws SQLException
    {
         String sql = "SELECT name from Purchase WHERE receiptid = '" + receiptId + "'";
         try
         {
             Statement stmt = dbConn.createStatement();
             ResultSet dbResult = stmt.executeQuery(sql);
             //dbConn.commit();
             dbResult.next();
             return dbResult.getString("name");
         }
         catch(SQLException ex)
         {
        	 printSQLError(ex);
        	 throw ex;
         }
    }

    
    // get the Store Name - Jose
    public static String getStoreShipm ( int sid ) throws SQLException
    {
        String sql = "SELECT name from Shipment WHERE sid = '" + sid + "'";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            //dbConn.commit();
            dbResult.next();
            return dbResult.getString("name");
        }
        catch(SQLException ex)
        {
        	printSQLError(ex);
        	throw ex;
        }
   }
    
    // get the Store Name - Jose
    public static String getStoreRet ( int retId ) throws SQLException
    {
        String sql = "SELECT name from ReturnP WHERE retId = '" + retId + "'";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            //dbConn.commit();
            dbResult.next();
            return dbResult.getString("name");
        }
        catch(SQLException ex)
        {
        	printSQLError(ex);
        	throw ex;
        }
   }
    
    // get the days for the deliveryOphir
    public static int getDaysToDelivery() throws SQLException
    {
        String sql = "SELECT count(*) as count FROM Purchase WHERE name='Warehouse' AND deliveredDate IS NULL";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return ( (int) Math.ceil( dbResult.getDouble("count") / deliveriesPerDay ) );
        }
        catch(SQLException ex)
        {
            throw ex;
        }
    }

    
    // get the Price of UPC - Ophir
    public static double getPrice(BigDecimal upc) throws SQLException
    {
         String sql = "SELECT sellPrice FROM Item WHERE upc= '" + upc + "'";
         try
         {
             Statement stmt = dbConn.createStatement();
             ResultSet dbResult = stmt.executeQuery(sql);
             dbConn.commit();
             dbResult.next();
             return dbResult.getDouble("sellPrice");
         }
         catch(SQLException ex)
         {
             throw ex;
         }
    }

    // get the name of the Item - Jose
    public static String getTitleItem ( BigDecimal upc ) throws SQLException
    {
        String sql = "SELECT title FROM Item WHERE upc = '" + upc + "'";
        try
        {
            Statement stmt = dbConn.createStatement();
            ResultSet dbResult = stmt.executeQuery(sql);
            dbConn.commit();
            dbResult.next();
            return dbResult.getString("title");
        }
        catch(SQLException ex)
        {
            throw ex;
        }
   }
    
  
    // insert item into Stored of Items created
    public static void insertItemIntoStored(BigDecimal upc) throws SQLException
    {         
         String sql1 = "SELECT COUNT(*) AS numStores FROM Store";
         String sql2 = "SELECT name FROM STORE";
         String sql3 = null;
         String sql4 = "INSERT INTO Stored VALUES(?, ?, 0)" ;
     
     
         try
         {
             Statement stmt1 = dbConn.createStatement();
             ResultSet dbResult1 = stmt1.executeQuery(sql1);
             //System.out.println(sql1);
             dbResult1.next();
             int numStores = dbResult1.getInt("numStores");
             //System.out.println("numStores: " + numStores);
             
             Statement stmt2 = dbConn.createStatement();
             ResultSet dbResult2 = stmt2.executeQuery(sql2);
             //System.out.println(sql2);
             String nameStore = null;
             
             Statement stmt3 = dbConn.createStatement(); 
             ResultSet dbResult3 = null;
                              
             PreparedStatement ps4 = dbConn.prepareStatement(sql4);               
                         
             
             for (int i = 0; i < numStores; i++)
             {
                 
                 dbResult2.next();
                 nameStore = dbResult2.getString("name");
                 //System.out.println(nameStore);
                 
                 sql3 = "SELECT COUNT(*) AS numStoreUPC FROM Stored WHERE name = '" + nameStore +"' AND upc = " + upc;
                 //System.out.println(sql3);
                 dbResult3 = stmt3.executeQuery(sql3);
                 dbResult3.next();
                 
                 if  ( dbResult3.getInt("numStoreUPC") == 0 )
                 {
                     ps4.setString(1, nameStore);
                     ps4.setBigDecimal(2, upc);
                     //System.out.println(sql4);
                     ps4.executeUpdate();
                     
                 }
              }
             
             dbConn.commit();
             stmt1.close();
             stmt2.close();
             stmt3.close();
             ps4.close();
         }
         catch(SQLException ex)
         {
             throw ex;
         }
    }

  
    
       
// # # #   MAIN   # # #  ///
   
    public static void main(String[] args)
    {
        try
        {
            Transactions.connect("ora_b9e6", "a67101063");
            
            Transactions.closeConnection();
            System.out.println("Done Transaction");
            
        }
        catch(Exception ex)
        {
            SQLException e = (SQLException) ex;
            Transactions.printSQLError(e);
        }
   
    }
}
