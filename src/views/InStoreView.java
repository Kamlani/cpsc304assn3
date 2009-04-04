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
	
	private Panel purchaseRowPanel[];
	private JFormattedTextField[] upc;
	private JFormattedTextField[] quantity;
	private Button[] showPurchase;
	private Button[] makePurchase;
	
	private int maxPurchases;
	private Panel container;

	public InStoreView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		init();
	}
	
	private void init() {
		
		maxPurchases = 10;
		
		purchaseRowPanel = new Panel[maxPurchases];
		upc = new JFormattedTextField[maxPurchases];
		quantity = new JFormattedTextField[maxPurchases];
		showPurchase = new Button[maxPurchases];
		makePurchase = new Button[maxPurchases];
		
		
		createLoginPanels();
	}
	
	private void createLoginPanels () {
		
		//SET UP CONTAINER
		
		container = new Panel();
		container.setBackground(bg3);
		container.setLayout(new GridLayout(13,1));
		container.setPreferredSize(new Dimension(Short.MAX_VALUE,480));
		this.getContainer().add(container, BorderLayout.NORTH);
		
		//label for Panels
		
		Panel Labels = new Panel(new GridLayout(1,4));
		
		Label upcLabel = new Label("Upc:");
		upcLabel.setFont(font1);
		Labels.add(upcLabel);
		
		Label quantityLabel = new Label("Quantity:");
		quantityLabel.setFont(font1);
		Labels.add(quantityLabel);
		
		Label makePurchaseLabel = new Label("Make Purchase:");
		makePurchaseLabel.setFont(font1);
		Labels.add(makePurchaseLabel);
		
		Label showPurchaseLabel = new Label("Add Another Purchase:");
		showPurchaseLabel.setFont(font1);
		Labels.add(showPurchaseLabel);
		
		container.add(Labels);
		
		for (int i = 0; i < maxPurchases; i++) {
				
			purchaseRowPanel[i] = new Panel(new GridLayout(1,4));
			
	//SUB PANEL 1
			upc[i] = new JFormattedTextField();
			upc[i].setFont(font1);
			upc[i].setEditable(true);
			upc[i].setVisible(false);
			
			purchaseRowPanel[i].add(upc[i]);
			
	//SUB PANEL 2
			quantity[i] = new JFormattedTextField();
			quantity[i].setFont(font1);
			quantity[i].setEditable(true);
			quantity[i].setVisible(false);
			
			purchaseRowPanel[i].add(quantity[i]);
			
	//SUB PANEL 3
			makePurchase[i] = new Button("Add To Cart");
			makePurchase[i].addActionListener(new addToCartAction());
			makePurchase[i].setVisible(false);
			
			purchaseRowPanel[i].add(makePurchase[i]);
			
	//SUB PANEL 4
			showPurchase[i] = new Button("Add Purchase");
			showPurchase[i].addActionListener(new showPurchaseAction());
			
			purchaseRowPanel[i].add(showPurchase[i]);
			
	//END SUB PANELS
			
			container.add(purchaseRowPanel[i]);
			
		}
		
		Button cashPurchase = new Button("Cash Purchase");
		cashPurchase.addActionListener(new cashAction());
		container.add(cashPurchase);
		
		Button creditPurchase = new Button("Credit Purchase");
		creditPurchase.addActionListener(new creditAction());
		container.add(creditPurchase);
		
		container.validate();
		
	}//CREATE PANELs	
	
	
	//HELPER METHODS FOR LISTENERS
	
	public void cash() {
		
		//CONTROLLER LOGIC PURCHASE THE WHOLE CART!!!
		this.getParent().getCart();
		

		//REFRESH THE INSTORE VIEW
		refreshView();
		
		
		
		//STUFF FROM FRIDAY
		
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
		this.getParent().switchView(4);	
		
		//REFRESH THE INSTORE VIEW
		refreshView();
	}
	
	private void addToCart(int item) {
		
		//CONTROLLER LOGIC SEARCH FOR ITEM BASED ON UPC AND QUANTITY
		//IF UPC AND QUANTITY ARE THERE i.e. RETURNS A TUPLE, GET UPC, NAME AND QUANTITY, ADD TO CART
		//NOTE BECAUSE USER SPECIFIES QUANTITY, IT USES OVERLOADED ADD TO CART METHOD IN MAIN VIEW
		
		boolean itemFound = true;
		if (  itemFound   ) {
			
			//EXAMPLE ITEM
			Vector<Object> itemToAdd = new Vector<Object>();
			itemToAdd.add(456728);
			itemToAdd.add("SHOES");
			itemToAdd.add(5);
			
			
			//HOW TO ADD ONCE CONTROLLER RETURNS ITEM IN VECTOR FORM
			this.getParent().addItem(    itemToAdd   ,    (Integer.parseInt(itemToAdd.get(2).toString()))    );
			
			
			//SET THE VISIBILITY OF THAT ITEM TO FALSE
			purchaseVis(item, false);
			
			
			//UPDATES THE CART VIEW
			((CustomerCheckoutView) this.getParent().getView(3)).update();
			
			
		} else {
			MainView.errorDialog("Item Does Not Exist In That Quantity");
		}
		
	}
	
	
	//HELPERS 
	private void refreshView() {
		for (int i = 0; i < maxPurchases; i++) {
			upc[i].setVisible(false);
			quantity[i].setVisible(false);
			makePurchase[i].setVisible(false);
			showPurchase[i].setVisible(true);
		}
	}
	
	//HELPERS
	private void purchaseVis(int item, boolean vis) {
		upc[item].setVisible(vis);
		quantity[item].setVisible(vis);
		makePurchase[item].setVisible(vis);
		if (!vis) {
			showPurchase[item].setVisible(vis);
		}
	}

	
	
	//LISTENERS
	
	public class cashAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			cash();
		}
	}// END LISTENER
	
	public class creditAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			credit();			
		}
	}// END LISTENER
	
	public class addToCartAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < maxPurchases; i++) {
				if ( e.getSource() == makePurchase[i]) {
					addToCart(i);
				}	
			} //MATCH VIEW OF BUTTON PRESSED THEN SWITCH
        }
	}
	
	public class showPurchaseAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < maxPurchases; i++) {
				if ( e.getSource() == showPurchase[i]) {
					purchaseVis(i, true);
				}	
			} //MATCH VIEW OF BUTTON PRESSED THEN SWITCH			
		}
	}// END LISTENER
	
	
	
}
