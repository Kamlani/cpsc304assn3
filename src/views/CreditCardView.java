package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.*;

import oracle.sql.DATE;

import views.MainView.buttonAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class CreditCardView extends View {
	
	private Panel cardInfoPanel;
	private Button submitCardButton;
	private JFormattedTextField cardNumber;
	private JFormattedTextField cardExpiryMonth;
	private JFormattedTextField cardExpiryYear;
	private Panel container;

	protected static int currReceiptID;
	public CreditCardView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		init();
	}
	
	private void init() {
		createLoginPanels();
	}
	
	private void createLoginPanels () {
		
		//SET UP CONTAINER
		
		container = new Panel();
		container.setBackground(bg3);
		container.setLayout(new GridLayout(4,1));
		container.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(container, BorderLayout.NORTH);
		
		//label for Container
		Label thisTitle = new Label("Credit Authorization:");
		thisTitle.setFont(font1);
		container.add(thisTitle);
		
		
		cardInfoPanel = new Panel(new GridLayout(1,4));
		container.add(cardInfoPanel);		
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label cardNumberLabel = new Label("Card Number:");
		cardNumberLabel.setFont(font1);
		subPanel1.add(cardNumberLabel);
		
		cardNumber = new JFormattedTextField();
		cardNumber.setFont(font1);
		cardNumber.setEditable(true);
		subPanel1.add(cardNumber);
		
		cardInfoPanel.add(subPanel1);
		
//SUB PANEL 2
		
		Panel subPanel2 = new Panel();
		subPanel2.setLayout(new GridLayout(2,1));
				
		Label cardExpiryMonthLabel = new Label("Expiry Month:");
		cardExpiryMonthLabel.setFont(font1);
		subPanel2.add(cardExpiryMonthLabel);
		
		cardExpiryMonth = new JFormattedTextField();
		cardExpiryMonth.setFont(font1);
		cardExpiryMonth.setEditable(true);
		subPanel2.add(cardExpiryMonth);
		
		cardInfoPanel.add(subPanel2);
		
//SUB PANEL 3

		Panel subPanel3 = new Panel();
		subPanel3.setLayout(new GridLayout(2,1));
		
		Label cardExpiryYearLabel = new Label("Expiry Year:");
		cardExpiryYearLabel.setFont(font1);
		subPanel3.add(cardExpiryYearLabel);
		
		cardExpiryYear = new JFormattedTextField();
		cardExpiryYear.setFont(font1);
		cardExpiryYear.setEditable(true);
		subPanel3.add(cardExpiryYear);
		
		cardInfoPanel.add(subPanel3);
		
//SUB PANEL 4
		
		Panel subPanel4 = new Panel();
		subPanel4.setLayout(new GridLayout(2,1));
		
		Label submitLabel = new Label("Submit:");
		submitLabel.setFont(font1);
		subPanel4.add(submitLabel);
		
		submitCardButton = new Button("Submit Card");
		submitCardButton.addActionListener(new submitCardAction());
		subPanel4.add(submitCardButton);
		
		cardInfoPanel.add(subPanel4);
		
		
		
		container.validate();
		
	}//Login PANELs
	
	private void submitCard() 
	{
		
		if(MainView.isOnlinePurchase)
		{
		
		
		int cartSize = this.getParent().getCart().size();
		int[] UPC = new int[cartSize];
		int[] quantity = new int[cartSize];
		for(int i = 0; i < cartSize; i++)
		{
			Vector<Object> temp = (Vector<Object>)this.getParent().getCart().get(i);
			
			if(temp.get(0) != null && temp.get(2) != null)
			{
			UPC[i] = ((Integer)temp.get(0)).intValue();
			quantity[i] = ((Integer)temp.get(2)).intValue();
			}
			                                   	
		}
		
		
		try 
		{
			/*System.out.println("Hi");
			System.out.println(this.getParent().getCID());
			System.out.println(cardNumber.getText());
			System.out.println(cardExpiryYear.getText());
			System.out.println(cardExpiryMonth.getText());
			*/
			for(int i = 0; i < UPC.length; i++)
			{
				System.out.print(UPC[i] + " ");
			}
			
			for(int i = 0; i < UPC.length; i++)
			{
				System.out.print(quantity[i] + " ");
			}
			
			
			if(cardExpiryYear.getText().length()==0 || cardExpiryMonth.getText().length()==0 || this.getParent().getCID() == null || cardNumber.getText().length()==0 || Integer.parseInt(cardExpiryYear.getText()) <= 2009 || Integer.parseInt(cardExpiryMonth.getText()) > 12)
			{
				MainView.errorDialog("Invalid Input");
				return;
			}
				
			
			int x = thisController.PurchaseOnlineItems(this.getParent().getCID(), "Warehouse", cardNumber.getText(), Integer.parseInt(cardExpiryYear.getText()), Integer.parseInt(cardExpiryMonth.getText()), UPC, quantity);
			if(x == -1)
			{
				MainView.errorDialog("Sorry Invalid/Rejected Credit Card. Try Again");
				return;
			}
			else
			{
				currReceiptID = x;
			//	ReceiptView.updateResults();
			}
				
				
		} 
		catch (NumberFormatException e) 
		{
			
			MainView.errorDialog("Bad Input Values");
			return;
		} 
		catch (SQLException e) 
		{
			MainView.errorDialog("Sorry Invalid/Rejected Credit Card. Try Again");
			return;
		}
		
		//CONTROLLER LOGIC FOR CHECKING CREDIT CARD
		
		//IF SUCCESSFUL SEND TO RECEIPT VIEW
		
		this.getParent().switchView(8);
	}
		else
		{
			if(cardExpiryYear.getText().length()==0 || cardExpiryMonth.getText().length()==0 || this.getParent().getCID() == null || cardNumber.getText().length()==0 || Integer.parseInt(cardExpiryYear.getText()) <= 2009 || Integer.parseInt(cardExpiryMonth.getText()) > 12)
			{
				MainView.errorDialog("Invalid Input");
				return;
			}
			
			
			try 
			{
			//	int x = thisController.inStorePurchase(false, this.getParent().getCID(), "default", cardNumber.getText(), Integer.parseInt(cardExpiryYear.getText()), Integer.parseInt(cardExpiryMonth.getText()), InStoreView.intUPC, InStoreView.intQuantity);
				//if( x == -1)
				//{
					MainView.errorDialog("Sorry Invalid/Rejected Credit Card. Try Again");
					return;
				//}
			}
					
			
			catch (NumberFormatException e) 
			{
				
				MainView.errorDialog("Bad Input Values");
				return;
			} 
		//	catch (SQLException e) 
		//	{
			//	MainView.errorDialog("Sorry Invalid/Rejected Credit Card. Try Again");
			//	return;
			//}
				
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	//LISTENERS
	
	
	public class submitCardAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
			submitCard();
			
		}
	}// END LISTENER
	
	
	
}
