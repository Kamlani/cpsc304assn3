package logic;

import java.sql.*;
import java.math.*;
import java.util.*;
import java.util.Date;

public class Operations 
{
	private static Operations instance = null;
	
	
	protected Operations() throws SQLException, ClassNotFoundException
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
	protected int inStorePurchase(boolean isCash, String cid, String name, String cardNum, int expiryYear, int expiryMonth, int[] UPC, int quatity[]) throws SQLException
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
	protected int processReturn(int recieptId, String name, int[] UPC, int[] quantity) throws SQLException 
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
	}
	
	// Customer
	/*
	 * Takes the customerID, name, and creditcard info. inserts a return at the current date, adds all items to be purchased 
	 * @Return the receiptID, -1 if creditcard rejected
	 */		
	protected int PurchaseOnlineItems(String cid, String name, String cardNum, int expiryYear, int expiryMonth, BigDecimal[] UPC, int quantity[] ) throws SQLException
	{
		
		BigDecimal BD_cardNum = new BigDecimal(cardNum);
		
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		java.sql.Date expected = new java.sql.Date(System.currentTimeMillis() + (1000 * 24 * 60 * 60 *14));
		
		Calendar c1 = Calendar.getInstance();
		c1.set(expiryYear, expiryMonth, 1);
		java.sql.Date expire = new  java.sql.Date(c1.getTime().getTime());	
		
		
		if(Math.random() >= 0.5) // 50/50 chance the credit card is valid
		{
			int recieptID = sql.Transactions.insertOnlinePurchase(date, cid, name, BD_cardNum, expire, expected);
			
			for(int i = 0; i < UPC.length; i++)
				sql.Transactions.addItemToPurchase(recieptID, UPC[i], quantity[i]);	
			return recieptID;
		}
		
		return -1;		
	}
	
	
	protected String[][] searchItem(String category, String title, String singer, int quanity)
	{
		// Call got
		
	
	}
	
	
	
	
	
	
	
	protected void Register()
	{
		
	}

			
	// Manager
	protected void AddRemoveSupplier()
	{
		
	}
	
	protected void ProcessShipment()
	{
		
	}
	
	protected void ProcessDelivery()
	{
		
	}
	
	protected void DailySalesReport() // Not Really Void
	{
		
	}
	
	protected void TopSellingItems() // Not Really Void
	{
		
	}
	

	
	protected 
	
	


	
}
	
	
	
	
	
	
	
		
	
		
	
	
	
	
	
	
	

