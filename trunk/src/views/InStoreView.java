package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class InStoreView extends View {
	
	private Panel cardInfoPanel;
	private JFormattedTextField upc;
	private JFormattedTextField quantity;
	
	private Panel container;

	public InStoreView (String title, MainView parent) {
		
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
		Label thisTitle = new Label("In Store Purchase:");
		thisTitle.setFont(font1);
		container.add(thisTitle);
		
		
		cardInfoPanel = new Panel(new GridLayout(1,2));
		container.add(cardInfoPanel);		
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label upcLabel = new Label("Upc:");
		upcLabel.setFont(font1);
		subPanel1.add(upcLabel);
		
		upc = new JFormattedTextField();
		upc.setFont(font1);
		upc.setEditable(true);
		subPanel1.add(upc);
		
		cardInfoPanel.add(subPanel1);
		
//SUB PANEL 2
		
		Panel subPanel2 = new Panel();
		subPanel2.setLayout(new GridLayout(2,1));
				
		Label quantityLabel = new Label("Quantity:");
		quantityLabel.setFont(font1);
		subPanel2.add(quantityLabel);
		
		quantity = new JFormattedTextField();
		quantity.setFont(font1);
		quantity.setEditable(true);
		subPanel2.add(quantity);
		
		cardInfoPanel.add(subPanel2);
		
//END SUB PANELS
		
		Button cashButton = new Button("Cash Purchase");
		cashButton.addActionListener(new cashAction());
		container.add(cashButton);
		
		Button creditButton = new Button("Credit Purchase");
		creditButton.addActionListener(new creditAction());
		container.add(creditButton);
		
		
		container.validate();
		
	}//Login PANELs
	
	
	public void cash() {
		
//		int thisUPC = Integer.parseInt(upc.getText());
//		int thisQuantity = Integer.parseInt(quantity.getText());
//	
//		int receiptID = thisController.inStorePurchase(true, "666", "default", null, null, null, thisUPC, thisQuantity);
//		
//		if ( receiptID != -1 )
//		{
//			//receipt view
//		} else {
//			//item not found
//		}
	}
	
	
	public void credit() {
		
		//CONTROLLER LOGIC SEARCH FOR ITEM HERE
		
//		upc.getText();
//		quantity.getText();
		
		//if successful
		//CONTROLLER LOGIC SET UP ADD TO CART ITEM SEE EXAMPLE BELOW
		//SET THIS
		
		Vector<Object> item = new Vector<Object>();
		item.add(123456789); //upc.getText();
		item.add("This is an example Item"); //item name
		item.add(5); //quantity.getText();
		
		this.getParent().getCart().add(item);
		//SWITCH TO VIEW 4 CREDIT CARD VIEW, IN STORE DOESN'T SEE CART, BUT WE USE IT TO PROCESS THE INVENTORY FOR THE CONTROLLER
		this.getParent().switchView(4);		
	}
	
	//LISTENERS
	
	
	public class cashAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
			//CONTROLLER LOGIC SEARCH FOR ITEM HERE
			
//			upc.getText();
//			quantity.getText();
			
			//if successful
			
			//CONTROLLER LOGIC FOR IN STORE  CASH   PURCHASE
			//CASH
			
		}
	}// END LISTENER
	
	public class creditAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			credit();			
		}
	}// END LISTENER
	
	
	
}
