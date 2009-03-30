package sql;

import java.sql.*;
import java.math.BigDecimal;

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
			dbConn.setAutoCommit(false);	// to exectute queries as transactions.
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
		String sql = "SELECT * FROM Purchase WHERE receiptId = receiptId_counter.currvalue";
		try
		{
			Statement stmt = dbConn.createStatement();
			ResultSet dbResult = stmt.executeQuery(sql);
			dbConn.commit();
			return dbResult.getInt("receiptId");	
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
	
	// to verify that the receipt exists before executing a return - Jomat
	private static boolean checkReceiptIDReturn(int recieptId) throws SQLException
	{
		String sql = "SELECT receiptId FROM Purchase WHERE receiptId = ?";
		try
		{
			PreparedStatement ps = dbConn.prepareStatement(sql);
			ps.setInt(1, recieptId);
			ResultSet dbResult = ps.executeQuery(sql);
			dbConn.commit();
			return (dbResult.getInt("receiptId") == recieptId);	
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
			throw ex;
		}

	}
	
	// inserting a new return document - Jomat
	public static int insertReturn(Date date, int recieptId, String name) throws SQLException
	{
		// ??? Unsure if this IF statement should use another try & catch clause
		if (checkReceiptIDReturn( recieptId ))
		{
			String sql = "INSERT INTO ReturnP VALUES( returnId_counter.nextval, ?, ?, ?)" ;
			try
			{			
				PreparedStatement ps = dbConn.prepareStatement(sql);
				ps.setDate(1, date);
				ps.setInt(2, recieptId);
				ps.setString(3, name);
				ps.executeUpdate();
				int returnId = getCurrentReturnID();	// commit is issued inside the function
				return returnId;
			}
			catch(SQLException ex)
			{
				dbConn.rollback();
				throw ex;
			}
		}
		else
		{
			return -1;
			// ??? Add to tell that the receiptId does not exists
		}
		
	}
	
	// to get the latest retId from ReturnP table - Jomat
	private static int getCurrentReturnID() throws SQLException
	{
		String sql = "SELECT * FROM ReturnP WHERE retId = returnId_counter.currvalue";
		try
		{
			Statement stmt = dbConn.createStatement();
			ResultSet dbResult = stmt.executeQuery(sql);
			dbConn.commit();
			return dbResult.getInt("retId");	
		}
		catch(SQLException ex)
		{
			dbConn.rollback();
			throw ex;
		}

	}
	
	// Adds an item to the Return document - Jomat
	public static void addItemToReturn(int retId, BigDecimal upc, int quantity) throws SQLException
	{
		// ??? Unsure if this IF statement should use another try & catch clause
		if (checkItemIDReturn(retId, upc, quantity))
		{
			String sql = "INSERT INTO PurchaseItem VALUES(?, ?, ?)" ;
			try
			{			
				PreparedStatement ps = dbConn.prepareStatement(sql);
				ps.setInt(1, retId);
				ps.setBigDecimal(2, upc);
				ps.setInt(3, quantity);
				ps.executeUpdate();
				dbConn.commit();
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
		else
		{
			// ??? Add to tell that the receiptId does not exists
		}
	}
	
	
	// to verify that the receipt contains the item that is being returned before executing a return - Jomat
	private static boolean checkItemIDReturn(int recieptId, BigDecimal upc, int quantity) throws SQLException
	{
		if (quantity > 0)
		{	
			String sql = "SELECT SUM(quantity) AS retSum FROM PurchaseItem WHERE receiptId = ? AND upc = ?";
			try
			{
				PreparedStatement ps = dbConn.prepareStatement(sql);
				ps.setInt(1, recieptId);
				ps.setBigDecimal(2, upc);
				ResultSet dbResult = ps.executeQuery(sql);
				dbConn.commit();
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
/*	public static void main(String[] args)
	{
		try
		{
			Transactions.connect("ora_a3o6", "a72803067");
		}
		catch(Exception ex)
		{
			SQLException e = (SQLException) ex;
			Transactions.printSQLError(e);
		}
	}*/
}