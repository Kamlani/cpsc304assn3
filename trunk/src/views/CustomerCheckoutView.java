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
	
	private Panel purchasePanel;
	
	private int totalRows;
	private int checkoutRows;
	private Panel[] checkoutRowPanel;
	private JFormattedTextField[] itemText;
	private JFormattedTextField[] itemQuantity;
	private Button[] removeItem;
	private Button returnSubmit;

	public CustomerCheckoutView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		totalRows = 16;
		
		checkoutRows = 1;
		
		removeItem = new Button[totalRows];
		
		checkoutRowPanel = new Panel[totalRows];
		itemText = new JFormattedTextField[totalRows];
		itemQuantity = new JFormattedTextField[totalRows];
		
		init();
	}
	
	private void init() {
		
		createPurchasePanel();
		
		//returnSubmit = new Button("Submit Return");
		
	}
	
	private void createPurchasePanel () {
		
		//FILLS for GRID
		
		Dimension filler = new Dimension(32,32);
		
		//SET UP PANELS FOR CLERK
		
		purchasePanel = new Panel();
		purchasePanel.setBackground(bg3);
		purchasePanel.setLayout(new GridLayout(   totalRows+2   ,1));
		purchasePanel.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(purchasePanel, BorderLayout.CENTER);
		
		//label for Purchase Panel
		Label purchaseLabel = new Label("Return Items: ");
		purchaseLabel.setFont(font1);
		purchasePanel.add(purchaseLabel);
		
		createcheckoutRows();
		setPurchaseRowVisible(0, true);
		
		Panel returnSubmitPanel = new Panel();
		returnSubmitPanel.setLayout(new GridLayout(1,6));
			
			returnSubmitPanel.add(new Box.Filler(filler, filler, filler));
			returnSubmitPanel.add(new Box.Filler(filler, filler, filler));
			returnSubmitPanel.add(new Box.Filler(filler, filler, filler));
			
			
			returnSubmit = new Button("Submit Return");
			returnSubmit.addActionListener(new submitReturnAction());
			returnSubmitPanel.add(returnSubmit);		
		
		purchasePanel.add(returnSubmitPanel);
		
		
	}//CREATE PURCHASE PANEL
	
	
	
	private void createcheckoutRows () {
		
		Dimension filler = new Dimension(32,32);
		
		for (int row = 0; row < totalRows; row++) {
		
			checkoutRowPanel[row] = new Panel(new GridLayout(1,3));
			
			 if (row % 2 == 0)
				 checkoutRowPanel[row].setBackground(bg2);
			 else
				 checkoutRowPanel[row].setBackground(bg3);
        	
			//SUB PANEL 1
			
			Panel purchaseSubPanel1 = new Panel();
			purchaseSubPanel1.setLayout(new GridLayout(1,3));
					
			Label purchaseItemLabel = new Label("Item " + (row+1) + ": ");
			purchaseItemLabel.setFont(font1);
			purchaseSubPanel1.add(purchaseItemLabel);
			
			itemText[row] = new JFormattedTextField();
			itemText[row].setFont(font1);
			itemText[row].setEditable(false);
			purchaseSubPanel1.add(itemText[row]);
			
			purchaseSubPanel1.add(new Box.Filler(filler, filler, filler));
			checkoutRowPanel[row].add(purchaseSubPanel1);
			
			//SUB PANEL 2
			
		
			Panel purchaseSubPanel2 = new Panel();
			purchaseSubPanel2.setLayout(new GridLayout(1,3));
					
			Label itemQuantityLabel = new Label("Quantity: ");
			itemQuantityLabel.setFont(font1);
			purchaseSubPanel2.add(itemQuantityLabel);
			
			itemQuantity[row] = new JFormattedTextField();
			itemQuantity[row].setFont(font1);
			itemQuantity[row].setSize(8, 32);
			itemQuantity[row].setEditable(false);
			purchaseSubPanel2.add(itemQuantity[row]);
			
			removeItem[row] = new Button("Remove From Cart");
			removeItem[row].addActionListener(new removeCartItem());
			purchaseSubPanel2.add(removeItem[row]);
			
			checkoutRowPanel[row].add(purchaseSubPanel2);
			
			checkoutRowPanel[row].setVisible(false);
			
			purchasePanel.add(checkoutRowPanel[row]);
		}//for
		
	}//CREATE PURCHASE ROWS
	
	private void clearRow(int row) {
		
		itemText[row].setText(" ");
		itemQuantity[row].setText(" ");
		
	} //CLEAR ROW
	
	private void setPurchaseRowVisible (int row, boolean vis) {
		checkoutRowPanel[row].setVisible(vis);
	}//SET PURCHASE ROW VISIBILE
	
	
	//LISTENERS

	
	public class removeCartItem implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			//Controller
        }
	}
	
	
	public class submitReturnAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			showResults();
        }
	}
	
	private void showResults() {
		this.getParent().showResultsView(3);
	}
	
	
}
