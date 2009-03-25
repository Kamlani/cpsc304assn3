package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import views.MainView.button1Action;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class ClerkView extends View {
	
	private Panel purchasePanel;
	private Panel returnPanel;
	
	private int totalPurchaseRows;
	private int purchaseRows;
	private Panel[] purchaseRowPanel;
	private JFormattedTextField[] purchaseText;
	private JFormattedTextField[] purchaseQuantity;
	private Button purchaseAdd;
	private Button purchaseSubmit;
	
	private JFormattedTextField[] returnText;
	private JFormattedTextField[] returnQuantity;
	
	private Button returnSubmit;

	public ClerkView (String title) {
		
		//default setup methods defined in View.java
		setContainer();
		setTitle(title);
		
		totalPurchaseRows = 8;
		
		purchaseRows = 1;
		
		purchaseRowPanel = new Panel[totalPurchaseRows];
		purchaseText = new JFormattedTextField[totalPurchaseRows];
		purchaseQuantity = new JFormattedTextField[totalPurchaseRows];
		
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
		purchasePanel.setLayout(new GridLayout(   totalPurchaseRows+2   ,1));
		purchasePanel.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(purchasePanel, BorderLayout.NORTH);
		
		//label for Purchase Panel
		Label purchaseLabel = new Label("Purchase: ");
		purchaseLabel.setFont(font1);
		purchasePanel.add(purchaseLabel);
		
		createPurchaseRows();
		setPurchaseRowVisible(0, true);
		
		Panel purchaseSubmitPanel = new Panel();
		purchaseSubmitPanel.setLayout(new GridLayout(1,6));
			
			purchaseSubmitPanel.add(new Box.Filler(filler, filler, filler));
			purchaseSubmitPanel.add(new Box.Filler(filler, filler, filler));
			purchaseSubmitPanel.add(new Box.Filler(filler, filler, filler));
			
			purchaseAdd = new Button("Add Item");
			purchaseAdd.addActionListener(new purchaseAddAction());
			purchaseSubmitPanel.add(purchaseAdd);
			
			purchaseAdd = new Button("Remove Last Item");
			purchaseAdd.addActionListener(new purchaseRemoveAction());
			purchaseSubmitPanel.add(purchaseAdd);
			
			purchaseSubmit = new Button("Submit Purchase");
			purchaseSubmitPanel.add(purchaseSubmit);		
		
		purchasePanel.add(purchaseSubmitPanel);
		
		
	}//CREATE PURCHASE PANEL
	
	
	
	private void createPurchaseRows () {
		
		Dimension filler = new Dimension(32,32);
		
		for (int row = 0; row < totalPurchaseRows; row++) {
		
			purchaseRowPanel[row] = new Panel(new GridLayout(1,3));
			
			//SUB PANEL 1
			
			Panel purchaseSubPanel1 = new Panel();
			purchaseSubPanel1.setLayout(new GridLayout(1,3));
					
			Label purchaseItemLabel = new Label("Item " + (row+1) + ": ");
			purchaseItemLabel.setFont(font1);
			purchaseSubPanel1.add(purchaseItemLabel);
			
			purchaseText[row] = new JFormattedTextField();
			purchaseText[row].setFont(font1);
			purchaseText[row].setEditable(true);
			purchaseSubPanel1.add(purchaseText[row]);
			
			purchaseSubPanel1.add(new Box.Filler(filler, filler, filler));
			purchaseRowPanel[row].add(purchaseSubPanel1);
			
			//SUB PANEL 2
		
			Panel purchaseSubPanel2 = new Panel();
			purchaseSubPanel2.setLayout(new GridLayout(1,3));
					
			Label purchaseQuantityLabel = new Label("Quantity: ");
			purchaseQuantityLabel.setFont(font1);
			purchaseSubPanel2.add(purchaseQuantityLabel);
			
			purchaseQuantity[row] = new JFormattedTextField();
			purchaseQuantity[row].setFont(font1);
			purchaseQuantity[row].setSize(8, 32);
			purchaseQuantity[row].setEditable(true);
			purchaseSubPanel2.add(purchaseQuantity[row]);
			
			purchaseSubPanel2.add(new Box.Filler(filler, filler, filler));
			purchaseRowPanel[row].add(purchaseSubPanel2);
			
			purchaseRowPanel[row].setVisible(false);
			purchasePanel.add(purchaseRowPanel[row]);
		}//for
		
	}//CREATE PURCHASE ROWS
	
	private void setPurchaseRowVisible (int row, boolean vis) {
		purchaseRowPanel[row].setVisible(vis);
	}//SET PURCHASE ROW VISIBILE
	
	
	
	
	
	
	
	
	//LISTENERS
	
	
	public class purchaseAddAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			if (purchaseRows < totalPurchaseRows) {
				setPurchaseRowVisible(purchaseRows, true);
				purchaseRows++;
			}
        }
	}
	
	public class purchaseRemoveAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			if (purchaseRows > 1) {
				purchaseRows--;
				setPurchaseRowVisible(purchaseRows, false);
			}
        }
	}
	
	
}
