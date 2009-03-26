package logic;

import java.sql.*;
import java.math.*;

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
	
	// returns true if cash purchase or if credit purchase is valid. Returns false if credit purchase is invalid. IE request for cash!
	protected boolean PurchaseItems(boolean isCash, Date date, String cid, String name, BigDecimal cardNum, Date expire) throws SQLException
	{
		if(isCash)
		{
			sql.Transactions.insertCashPurchase(date, cid, name);
			return true;
		}
		else		
		{
			if(Math.random() >= 0.5) // 50/50 chance the credit card is valid
				{
					sql.Transactions.insertCreditPurchase(expire, cid, name, cardNum, expire);
					return true;
				}
		}
		return false;		
	}
	
	protected void ReturnItem()
	{
		
	}
	
	// Customer
	protected void Register()
	{
		
	}
	
	protected void PurchaseOnlineItems(Date date, String cid, String name, BigDecimal cardNum, Date expire, Date expected) throws SQLException
	{
		sql.Transactions.insertOnlinePurchase(date, cid, name, cardNum, expire, expected);
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
	

}
	
	
	
	
	
	
	
		
	
		
	
	
	
	
	
	
	

