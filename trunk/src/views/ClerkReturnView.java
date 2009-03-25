package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class ClerkReturnView extends View {
	
	private Panel purchasePanel;
	
	private int totalReturnRows;
	private int returnRows;
	private Panel[] returnRowPanel;
	private JFormattedTextField[] returnText;
	private JFormattedTextField[] returnQuantity;
	private Button returnAdd;
	private Button returnSubmit;

	public ClerkReturnView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		totalReturnRows = 16;
		
		returnRows = 1;
		
		returnRowPanel = new Panel[totalReturnRows];
		returnText = new JFormattedTextField[totalReturnRows];
		returnQuantity = new JFormattedTextField[totalReturnRows];
		
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
		purchasePanel.setLayout(new GridLayout(   totalReturnRows+2   ,1));
		purchasePanel.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(purchasePanel, BorderLayout.CENTER);
		
		//label for Purchase Panel
		Label purchaseLabel = new Label("Return Items: ");
		purchaseLabel.setFont(font1);
		purchasePanel.add(purchaseLabel);
		
		createreturnRows();
		setPurchaseRowVisible(0, true);
		
		Panel returnSubmitPanel = new Panel();
		returnSubmitPanel.setLayout(new GridLayout(1,6));
			
			returnSubmitPanel.add(new Box.Filler(filler, filler, filler));
			returnSubmitPanel.add(new Box.Filler(filler, filler, filler));
			returnSubmitPanel.add(new Box.Filler(filler, filler, filler));
			
			returnAdd = new Button("Add Item");
			returnAdd.addActionListener(new returnAddAction());
			returnSubmitPanel.add(returnAdd);
			
			returnAdd = new Button("Remove Last Item");
			returnAdd.addActionListener(new purchaseRemoveAction());
			returnSubmitPanel.add(returnAdd);
			
			returnSubmit = new Button("Submit Return");
			returnSubmit.addActionListener(new submitReturnAction());
			returnSubmitPanel.add(returnSubmit);		
		
		purchasePanel.add(returnSubmitPanel);
		
		
	}//CREATE PURCHASE PANEL
	
	
	
	private void createreturnRows () {
		
		Dimension filler = new Dimension(32,32);
		
		for (int row = 0; row < totalReturnRows; row++) {
		
			returnRowPanel[row] = new Panel(new GridLayout(1,3));
			
			 if (row % 2 == 0)
				 returnRowPanel[row].setBackground(bg2);
			 else
				 returnRowPanel[row].setBackground(bg3);
        	
			//SUB PANEL 1
			
			Panel purchaseSubPanel1 = new Panel();
			purchaseSubPanel1.setLayout(new GridLayout(1,3));
					
			Label purchaseItemLabel = new Label("Return Item " + (row+1) + ": ");
			purchaseItemLabel.setFont(font1);
			purchaseSubPanel1.add(purchaseItemLabel);
			
			returnText[row] = new JFormattedTextField();
			returnText[row].setFont(font1);
			returnText[row].setEditable(true);
			purchaseSubPanel1.add(returnText[row]);
			
			purchaseSubPanel1.add(new Box.Filler(filler, filler, filler));
			returnRowPanel[row].add(purchaseSubPanel1);
			
			//SUB PANEL 2
			
		
			Panel purchaseSubPanel2 = new Panel();
			purchaseSubPanel2.setLayout(new GridLayout(1,3));
					
			Label returnQuantityLabel = new Label("Return Quantity: ");
			returnQuantityLabel.setFont(font1);
			purchaseSubPanel2.add(returnQuantityLabel);
			
			returnQuantity[row] = new JFormattedTextField();
			returnQuantity[row].setFont(font1);
			returnQuantity[row].setSize(8, 32);
			returnQuantity[row].setEditable(true);
			purchaseSubPanel2.add(returnQuantity[row]);
			
			purchaseSubPanel2.add(new Box.Filler(filler, filler, filler));
			returnRowPanel[row].add(purchaseSubPanel2);
			
			returnRowPanel[row].setVisible(false);
			purchasePanel.add(returnRowPanel[row]);
		}//for
		
	}//CREATE PURCHASE ROWS
	
	private void clearRow(int row) {
		
		returnText[row].setText(" ");
		returnQuantity[row].setText(" ");
		
	} //CLEAR ROW
	
	private void setPurchaseRowVisible (int row, boolean vis) {
		returnRowPanel[row].setVisible(vis);
	}//SET PURCHASE ROW VISIBILE
	
	
	//LISTENERS
	
	
	public class returnAddAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			if (returnRows < totalReturnRows) {
				setPurchaseRowVisible(returnRows, true);
				returnRows++;
			}
        }
	}
	
	public class purchaseRemoveAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			if (returnRows > 1) {
				returnRows--;
				clearRow(returnRows);
				setPurchaseRowVisible(returnRows, false);
			}
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
