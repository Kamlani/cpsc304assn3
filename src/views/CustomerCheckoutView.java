package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import views.CustomerOnlineView.addToCartAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class CustomerCheckoutView extends View {
	
	private Panel checkoutPanel;
	
	private int totalRows;
	private Panel[] checkoutRowPanel;
	private JFormattedTextField[] itemText;
	private JFormattedTextField[] itemQuantity;
	private Button[] removeItem;
	private Button submitOrder;

	public CustomerCheckoutView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);
		setContainer();
		setTitle(title);
		
		totalRows = 16;
		removeItem = new Button[totalRows];
		checkoutRowPanel = new Panel[totalRows];
		itemText = new JFormattedTextField[totalRows];
		itemQuantity = new JFormattedTextField[totalRows];
		
		init();
	}
	
	private void init() {
		createCheckoutPanel();
	}
	
	private void createCheckoutPanel () {
		
		//FILLS for GRID
		
		Dimension filler = new Dimension(32,32);
		
		//SET UP PANELS FOR Checkout
		
		checkoutPanel = new Panel();
		checkoutPanel.setBackground(bg3);
		checkoutPanel.setLayout(new GridLayout(   totalRows+2   ,1));
		checkoutPanel.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(checkoutPanel, BorderLayout.CENTER);
		
		//label for Purchase Panel
		Label purchaseLabel = new Label("Return Items: ");
		purchaseLabel.setFont(font1);
		checkoutPanel.add(purchaseLabel);
		
		createCheckoutRows();
		
		Panel submitOrderPanel = new Panel();
		submitOrderPanel.setLayout(new GridLayout(1,6));
		submitOrderPanel.add(new Box.Filler(filler, filler, filler));
		submitOrderPanel.add(new Box.Filler(filler, filler, filler));
		submitOrderPanel.add(new Box.Filler(filler, filler, filler));
		submitOrder = new Button("Submit Order");
		submitOrder.addActionListener(new submitOrderAction());
		submitOrderPanel.add(submitOrder);		
		
		checkoutPanel.add(submitOrderPanel);
		
	}//CREATE PURCHASE PANEL
	
	
	//PUBLIC UPDATES THE CHECKOUT ROWS
	
	public void update() {
		for (int i = 0; i < totalRows; i++) {
			checkoutRowPanel[i].setVisible(false);
			itemText[i].setText("");
		}
		for (int i = 0; i < this.getParent().getCart().size(); i++) {
			checkoutRowPanel[i].setVisible(true);
			itemText[i].setText(this.getParent().getCart().get(i).toString());
		}
	}//update
	
	
	private void createCheckoutRows () {
		
		Dimension filler = new Dimension(32,32);
		
		for (int i = 0; i < totalRows; i++) {
			checkoutRowPanel[i] = new Panel(new GridLayout(1,3));
			//background
			 if (i % 2 == 0)
				 checkoutRowPanel[i].setBackground(bg2);
			 else
				 checkoutRowPanel[i].setBackground(bg3);
        	
//SUB PANEL 1
			Panel purchaseSubPanel1 = new Panel();
			purchaseSubPanel1.setLayout(new GridLayout(1,3));
					
			Label purchaseItemLabel = new Label("Item " + (i+1) + ": ");
			purchaseItemLabel.setFont(font1);
			purchaseSubPanel1.add(purchaseItemLabel);
			
			itemText[i] = new JFormattedTextField();
			itemText[i].setFont(font1);
			itemText[i].setEditable(false);
			purchaseSubPanel1.add(itemText[i]);
			purchaseSubPanel1.add(new Box.Filler(filler, filler, filler));
			
			checkoutRowPanel[i].add(purchaseSubPanel1);
			
//SUB PANEL 2		
			Panel purchaseSubPanel2 = new Panel();
			purchaseSubPanel2.setLayout(new GridLayout(1,3));
					
			Label itemQuantityLabel = new Label("Quantity: ");
			itemQuantityLabel.setFont(font1);
			purchaseSubPanel2.add(itemQuantityLabel);
			
			itemQuantity[i] = new JFormattedTextField();
			itemQuantity[i].setFont(font1);
			itemQuantity[i].setEditable(false);
			purchaseSubPanel2.add(itemQuantity[i]);
			
			removeItem[i] = new Button("Remove From Cart");
			removeItem[i].addActionListener(new removeItemAction());
			purchaseSubPanel2.add(removeItem[i]);
			
			checkoutRowPanel[i].add(purchaseSubPanel2);
			
//ADD THE ROW PANEL
			checkoutPanel.add(checkoutRowPanel[i]);
		}//FOR
		
		update(); //CALL UPDATE
		
	}//CREATE CHECKOUT ROWS

	
	//LISTENERS

	
	public class removeItemAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < totalRows; i++) {
				if (e.getSource() == removeItem[i]) {
					removeItem(i);
				}
			}
        }
	}	
	
	public class submitOrderAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			submitOrder();
        }
	}
	
	//HELPERS
	
	private void removeItem(int item) {
		this.getParent().removeItem(item);
		update();
	}
	
	private void submitOrder() {
		//CONTROLLER STUFF
		this.getParent().switchView(4);
	}
	
	
}
