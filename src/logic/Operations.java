package logic;

import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;

public class Operations 
{
	private static Operations instance = null;
	
	
	public Operations() throws SQLException, ClassNotFoundException
	{
		// TO ENTER USER NAME AND PASSWORD
		sql.Transactions.connect("user", "pass");	
	}
	
	public static Operations getInstance() throws SQLException, ClassNotFoundException
	{
		if(instance == null)
			instance = new Operations();
		return instance;
	}
	
	
	
	// Clerk
	/* Takes in if its a cash or not, customer ID, name, cardNumber, and a JAVA.SQL.DATE expiry, array of UPC and quantity items (same size)
	 * @returns true if cash purchase OR if credit purchase is valid. Returns false if credit purchase is invalid. IE request for cash if false!
	 */
	public int inStorePurchase(boolean isCash, String cid, String name, String cardNum, int expiryYear, int expiryMonth, int[] UPC, int quatity[]) throws SQLException
	{
		
		BigDecimal BD_cardNum = new BigDecimal(cardNum);
		
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		

		Calendar c1 = Calendar.getInstance();
		c1.set(expiryYear, expiryMonth, 1);
		java.sql.Date expiry = new  java.sql.Date(c1.getTime().getTime());
		
		BigDecimal[] BD_UPC = new BigDecimal[UPC.length];
		for(int i = 0; i < UPC.length; i++)
			BD_UPC[i] = new BigDecimal(UPC[i]);
		
		
		
		if(isCash)
		{
			int recieptID = sql.Transactions.insertCashPurchase(date, name);
			
			for(int i = 0; i < UPC.length; i++)
				sql.Transactions.addItemToPurchase(recieptID, BD_UPC[i], quatity[i]);
			
			return recieptID;
		}
		else		
		{
			if(Math.random() >= 0.5) // 50/50 chance the credit card is valid
				{
					int recieptID = sql.Transactions.insertCreditPurchase(date, cid, name, BD_cardNum, expiry);
					
					for(int i = 0; i < UPC.length; i++)
						sql.Transactions.addItemToPurchase(recieptID, BD_UPC[i], quatity[i]);
					
					return recieptID;
				}
		}
		return -1;		
	}
	
	
	/*
	 * Takes the recieptID and name, inserts a return at the current date, adds return item for all
	 * @Return the returnID, -1 otherwise
	 */	
	public int processReturn(int recieptId, String name, int[] UPC, int[] quantity) throws SQLException 
	{
				
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		
		BigDecimal[] BD_UPC = new BigDecimal[UPC.length];
		for(int i = 0; i < UPC.length; i++)
			BD_UPC[i] = new BigDecimal(UPC[i]);
		
			
		int returnID = sql.Transactions.insertReturn(date, recieptId, name);
		
		if(returnID != -1)
		{
			for(int i = 0; i < UPC.length; i++)
				sql.Transactions.addItemToReturn(returnID, BD_UPC[i], quantity[i]);
		}
		
		return returnID;
		// NOTE:: NEED TO HANDLE CASE OF REFUNDING THE MONEY, CASH CREDIT
	}
	
	
	
	// Customer
	/*
	 * Takes the customerID, name, and creditcard info. inserts a return at the current date, adds all items to be purchased 
	 * @Return the receiptID, -1 if creditcard rejected
	 */		
	public int PurchaseOnlineItems(String cid, String name, String cardNum, int expiryYear, int expiryMonth, int[] UPC, int quantity[] ) throws SQLException
	{
		
		BigDecimal BD_cardNum = new BigDecimal(cardNum);
		
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		java.sql.Date expected = new java.sql.Date(System.currentTimeMillis() + (1000 * 24 * 60 * 60 *14));
		
		Calendar c1 = Calendar.getInstance();
		c1.set(expiryYear, expiryMonth, 1);
		java.sql.Date expire = new  java.sql.Date(c1.getTime().getTime());	
		
		BigDecimal[] BD_UPC = new BigDecimal[UPC.length];
		for(int i = 0; i < UPC.length; i++)
			BD_UPC[i] = new BigDecimal(UPC[i]);
		
		
		
		if(Math.random() >= 0.5) // 50/50 chance the credit card is valid
		{
			int recieptID = sql.Transactions.insertOnlinePurchase(date, cid, name, BD_cardNum, expire, expected);
			
			for(int i = 0; i < UPC.length; i++)
				sql.Transactions.addItemToPurchase(recieptID, BD_UPC[i], quantity[i]);	
			return recieptID;
		}
		
		return -1;		
	}
		
	/*
	public String[][] searchItem(String category, String title, String singer, int quanity)
	{
		// Call Ophir code to get Result set searchResults
		
		//ResultSet x
		
		int numRows = 0;
		
		while(x.next())
			numRows++;
		
		while(x.previous());
		
		String[][] searchReturn = new String[numRows][3];
		
		int rownum = 0;
		while(x.next())
		{
			
			searchReturn[rownum][0] = 
			searchReturn[rownum][1] = 0;
			searchReturn[rownum][2] = 0;
			rownum++;
		}
		
		return searchReturn;
		
	}
	*/
	
	
	
	
	
	
	public void Register(String cid, String password, String name, String address, String phone) throws SQLException 
	{
			sql.Transactions.createUsername(cid, password, name, address, phone);
	}

			
	// Manager

	
	public void ProcessShipment(String supName, String storeName, int ShipYear, int ShipMonth, int ShipDate, int[] UPC, int[] quantity, double[] supPrice)
	{
		
		Calendar c1 = Calendar.getInstance();
		c1.set(ShipYear, ShipMonth, ShipDate);
		java.sql.Date shipDate = new  java.sql.Date(c1.getTime().getTime());
		
		
		int sid = sql.Transactions.insertShipment(supName, storeName, shipDate);
		
		BigDecimal[] BD_UPC = new BigDecimal[UPC.length];
		BigDecimal[] BD_supPrice = new BigDecimal[supPrice.length];
		for(int i = 0; i < UPC.length; i++)
		{
			BD_UPC[i] = new BigDecimal(UPC[i]);
			
			sql.Transactions.addItemToShipment(sid, BD_UPC[i], BD_supPrice[i], quantity[i]);
		}
		
	}
	
	
	
	
	public void insertItemsToStore(String name, int[] UPC, int[] stock) throws SQLException
	{
		BigDecimal[] BD_UPC = new BigDecimal[UPC.length];
		for(int i = 0; i < UPC.length; i++)
		{
			BD_UPC[i] = new BigDecimal(UPC[i]);
			
			sql.Transactions.insertItemToStore(name, BD_UPC[i], stock[i]);
		}
		
		
		
	}
	
	
	
	
	
	public void changeItemQuanity(String name, int UPC, int amount) throws SQLException
	{
			BigDecimal BD_UPC = new BigDecimal(UPC);			
			sql.Transactions.modifyQuantityItemStore(name, BD_UPC, amount);
	}
	
	
	public void DailySalesReport() // Not Really Void
	{
		
	}
	
	public void TopSellingItems() // Not Really Void
	{
		
	}
	

	
	public void createItems(int[] UPC, String[] title, String[] type, String[] category, String[] company, int[] year, double[] sellPrice) throws SQLException
	{
		
		BigDecimal[] BD_UPC = new BigDecimal[UPC.length];
		BigDecimal[] BD_sellPrice = new BigDecimal[UPC.length];
		for(int i = 0; i < UPC.length; i++)
		{
			BD_UPC[i] = new BigDecimal(UPC[i]);
			BD_sellPrice[i] = new BigDecimal(sellPrice[i]);
			
			sql.Transactions.createItem(BD_UPC[i], title[i], type[i], category[i], company[i], year[i], BD_sellPrice[i]);
		}
		
		
	}
		
	
	
	public void insertLeadSinger(int UPC, String name) throws SQLException
	{
		BigDecimal BD_UPC = new BigDecimal(UPC);
		sql.Transactions.insertLeadSinger(BD_UPC, name);
	}
	

	public void insertHasSong(int UPC, String title) throws SQLException
	{
		BigDecimal BD_UPC = new BigDecimal(UPC);
		sql.Transactions.insertHasSong(BD_UPC, title);
		
	}
	
	public void insertStore(String name, String address, String type) throws SQLException
	{
		sql.Transactions.insertStore(name, address, type);
	}
		
	public void insertSupplier(String name, String address, String city, String status) throws SQLException
	{
		sql.Transactions.insertSupplier(name, address, city, status);
	}

	
	
}
	
	
	
	
	
	
	
		
	
		
	
	
	
	
	
	
	

