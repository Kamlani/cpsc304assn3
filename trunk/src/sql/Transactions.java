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
	
	public static void main(String[] args)
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
	}
}