package sql;

import java.sql.*;
import java.math.BigDecimal;
import java.util.Calendar;

// a class that has all the methods to connect to the DB and query it.
public class Transactions {
	
	private static Connection dbConn;	// the connection to the database.
	
	// method to connect to the database. Throws SQLException if connection is not successful,
	// and class not found exception if driver issue occurs.
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
			dbConn.setAutoCommit(false);	// to execute queries as transactions.
            System.out.println("Connected!\n");  
		}
		catch(SQLException ex)
		{
			throw ex;
		}
	}
	
	// prints the SQL exception error txt.
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
			throw ex;
		}
	}
	
	// function to create a cash purchase (ie insert into Purchase)
	public static int insertCashPurchase(Date date, String cid, String name) throws SQLException
	{
		String sql = "INSERT INTO Purchase VALUES( receiptId_counter.nextval, ?, ?, ?, ?, ?, ?, ?)" ;
		try
		{			
			PreparedStatement ps = dbConn.prepareStatement(sql);
			ps.setDate(1, date);
			ps.setString(2, cid);
			ps.setString(3, name);
			ps.setNull(4, Types.DECIMAL);
			ps.setNull(5, Types.DATE);
			ps.setNull(6, Types.DATE);
			ps.setNull(7, Types.DATE);
			ps.executeUpdate();
			int receiptId = getCurrentReceiptID();	// commit is issued inside the function
			ps.close();
			return receiptId;
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
			throw ex;
		}
		
	}
	
	// function to create a credit purchase (ie insert into Purchase)
	public static int insertCreditPurchase(Date date, String cid, String name, 
										 BigDecimal cardNum, Date expire) throws SQLException
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
			ps.setNull(6, Types.DATE);
			ps.setNull(7, Types.DATE);
			ps.executeUpdate();
			int receiptId = getCurrentReceiptID();	// commit is issued inside the function
			ps.close();
			return receiptId;
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
			throw ex;
		}
		
	}
	
	// function to create an online purchase (ie insert into Purchase)
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
			int receiptId = getCurrentReceiptID();	// commit is issued inside the function
			ps.close();
			return receiptId;
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
			throw ex;
		}
	}
	
	// to get the latest recieptId
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
	
	// function to add an Item to a purchase. (ie insert into PurchaseItem)
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
			dbConn.commit();
			ps.close();
		}
		catch(SQLException ex)
		{
			try
			{
				dbConn.rollback();
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
            
			//java.util.Date dateSystem = new java.util.Date(); //long TimeStampSystem = dateSystem.getTime();
			long TimeStampSystem = System.currentTimeMillis();
			//System.out.println("System Milliseconds:" + TimeStampSystem);
			
			Timestamp SQLDate = dbResult.getTimestamp("dateP");
			long TimeStampSQL = SQLDate.getTime();
			// System.out.println("SQL Milliseconds :" + TimeStampSQL);
            
			long noDays = (TimeStampSystem - TimeStampSQL);
			
			// System.out.println("Days :" + (noDays /  (1000 * 24 * 60 * 60 )) );
			
			stmt.close();
			return (noDays <= (1000 * 24 * 60 * 60 * 15) );
			
			
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
			throw ex;
		}
	}

	
	// inserting a new return document - Jomat Ok
	public static int insertReturn(Date date, int recieptId, String name) throws SQLException
	{
		String sql = "INSERT INTO ReturnP VALUES( returnId_counter.nextval, ?, ?, ?)" ;
		try
		{			
			// ??? Unsure if this IF statement should use another try & catch clause
			if ( checkReceiptIDReturn( recieptId ) )
			{
				if ( checkDateReturn( recieptId ) )
				{
					PreparedStatement ps = dbConn.prepareStatement(sql);
					ps.setDate(1, date);
					ps.setInt(2, recieptId);
					ps.setString(3, name);
					ps.executeUpdate();
					System.out.println("Hello");
					int returnId = getCurrentReturnID();	// commit is issued inside the function
					ps.close();
					return returnId;
				}
				else
				{
					return -1; 	// Out of range of 15 days.
				}
			}
			else
			{
				return -1;		// Receipt does not exists
			}
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
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
				dbConn.commit();
				ps.close();
			}
		}
		catch(SQLException ex)
		{
			try
			{
				dbConn.rollback();
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
				System.out.println(recieptId);
				System.out.println(upc);
				System.out.println(quantity);
				System.out.println(dbResult.getInt("retSum") >= quantity);
				System.out.println(dbResult.getInt("retSum"));
				return ( dbResult.getInt("retSum") >= quantity);	
			}
			catch(SQLException ex)
			{
				dbConn.rollback();
				throw ex;
			}
		}
		else
		{
			return false;
		}
	}
	
	// creation of the username by the customer online - Jomat Ok
	public static void createUsername(String cid, String password, String name, String address, int phone) throws SQLException
	{
		String sql = "INSERT INTO Customer VALUES( ?, ?, ?, ?, ?)" ;
			try
			{			
				PreparedStatement ps = dbConn.prepareStatement(sql);
				ps.setString(1, cid);
				ps.setString(2, password);
				ps.setString(3, name);
				ps.setString(4, address);
				ps.setInt(5, phone);
				ps.executeUpdate();
				dbConn.commit();
	            ps.close();
								
			}
			catch(SQLException ex)
			{
				dbConn.rollback();
				throw ex;
			}
		
	}
	

	public static void main(String[] args)
	{
		try
		{
			Transactions.connect("ora_a3o6", "a72803067");
			String cid = "ophir";
			String name = "ophir";
			Date currentDate = new Date(System.currentTimeMillis());
			
			/*int rid = insertCashPurchase(currentDate, cid, name);
			System.out.println(rid);
			Calendar cal = Calendar.getInstance();
			cal.set(2011, 10, 1);
			Date futureDate = new Date(cal.getTime().getTime());
			BigDecimal cardnum = new BigDecimal("1111222233334444");
			rid = insertCreditPurchase(currentDate, cid, name, cardnum, futureDate);
			System.out.println(rid);
			cal.setTime(currentDate);
			cal.set(cal.get(cal.YEAR),  cal.get(cal.MONTH), cal.get(cal.DATE) + 40 );
			Date expected = new Date(cal.getTime().getTime());
			rid = insertOnlinePurchase(currentDate, cid, name, cardnum, futureDate, expected);
			System.out.println(rid);
			*/
			
			// Jomat
			// boolean temp1 = checkReceiptIDReturn(125);
			// boolean temp2 = checkDateReturn (26);
			// long paymType = returnCashCredit(41);
			// BigDecimal a = new BigDecimal (1234);
			// boolean temp3 = checkItemIDReturn(19, a, 3);
			// int numReceipt = insertReturn(currentDate, 19, "ophir");
			// BigDecimal b = new BigDecimal(1235);
			// addItemToReturn(8, b, 2);
			// createUsername("george", "hello", "George", "Here at UBC", 123 );
			Transactions.closeConnection();
		}
		catch(Exception ex)
		{
			SQLException e = (SQLException) ex;
			Transactions.printSQLError(e);
		}
	
	}
}