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

public class CustomerOnlineView extends View {
	
	private Panel resultPanel;
	
	private int totalResultRows;
	private int resultRows;
	private Panel[] resultRowPanel;

	private JFormattedTextField[] resultText;
	private int[] resultID;
	private JFormattedTextField[] resultQuantity;
	
	private Button resultCheck;
	private Button[] resultAddCart;

	private Panel searchRowPanel;
	private JFormattedTextField searchSubPanelTitle;
	private Panel searchPanel;

	public CustomerOnlineView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		totalResultRows = 8;
		
		resultRows = 1;
		
		resultRowPanel = new Panel[totalResultRows];
		resultText = new JFormattedTextField[totalResultRows];
		resultID = new int[totalResultRows];
		resultQuantity = new JFormattedTextField[totalResultRows];
		resultAddCart = new Button[totalResultRows];
		
		init();
	}
	
	private void init() {
		createSearchPanel();
		createResultPanel();
	}
	
	private void createSearchPanel () {
		
		searchPanel = new Panel();
		searchPanel.setBackground(bg3);
		searchPanel.setLayout(new GridLayout(2,1));
		searchPanel.setPreferredSize(new Dimension(Short.MAX_VALUE,128));
		this.getContainer().add(searchPanel, BorderLayout.NORTH);
		
		//label for Search Panel
		Label purchaseLabel = new Label("Search for Items: ");
		purchaseLabel.setFont(font1);
		searchPanel.add(purchaseLabel);
		
		searchRowPanel = new Panel(new GridLayout(1,5));
		searchPanel.add(searchRowPanel);
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label categoryLabel = new Label("Category:");
		categoryLabel.setFont(font1);
		subPanel1.add(categoryLabel);
		
		searchSubPanelTitle = new JFormattedTextField();
		searchSubPanelTitle.setFont(font1);
		searchSubPanelTitle.setEditable(true);
		subPanel1.add(searchSubPanelTitle);
		
		searchRowPanel.add(subPanel1);
		
//SUB PANEL 2
		
		Panel subPanel2 = new Panel();
		subPanel2.setLayout(new GridLayout(2,1));
				
		Label titleLabel = new Label("Title:");
		titleLabel.setFont(font1);
		subPanel2.add(titleLabel);
		
		searchSubPanelTitle = new JFormattedTextField();
		searchSubPanelTitle.setFont(font1);
		searchSubPanelTitle.setEditable(true);
		subPanel2.add(searchSubPanelTitle);
		
		searchRowPanel.add(subPanel2);
		
//SUB PANEL 3
		
		Panel subPanel3 = new Panel();
		subPanel3.setLayout(new GridLayout(2,1));
				
		Label singerLabel = new Label("Singer:");
		singerLabel.setFont(font1);
		subPanel3.add(singerLabel);
		
		searchSubPanelTitle = new JFormattedTextField();
		searchSubPanelTitle.setFont(font1);
		searchSubPanelTitle.setEditable(true);
		subPanel3.add(searchSubPanelTitle);
		
		searchRowPanel.add(subPanel3);
		
//SUB PANEL 4
		
		Panel subPanel4 = new Panel();
		subPanel4.setLayout(new GridLayout(2,1));
				
		Label quantityLabel = new Label("Quantity:");
		quantityLabel.setFont(font1);
		subPanel4.add(quantityLabel);
		
		searchSubPanelTitle = new JFormattedTextField();
		searchSubPanelTitle.setFont(font1);
		searchSubPanelTitle.setEditable(true);
		subPanel4.add(searchSubPanelTitle);
		
		searchRowPanel.add(subPanel4);
		
//Search BUTTON
		
		Panel subPanel5 = new Panel();
		subPanel5.setLayout(new GridLayout(2,1));
		
		Label searchLabel = new Label("Search:");
		searchLabel.setFont(font1);
		subPanel5.add(searchLabel);
		
		resultCheck = new Button("Search");
		resultCheck.addActionListener(new resultCheckAction());
		subPanel5.add(resultCheck);
		
		searchRowPanel.add(subPanel5);
		
	}//SEARCH PANEL
	
	private void createResultPanel () {
		
		//FILLS for GRID
		
		Dimension filler = new Dimension(32,32);
		
		//SET UP PANELS FOR CLERK
		
		resultPanel = new Panel();
		resultPanel.setBackground(bg3);
		resultPanel.setLayout(new GridLayout(   totalResultRows+2   ,1));
		resultPanel.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(resultPanel, BorderLayout.SOUTH);
		
		//label for Purchase Panel
		Label purchaseLabel = new Label("Matching Items: ");
		purchaseLabel.setFont(font1);
		resultPanel.add(purchaseLabel);
		
		createResultRows();
		setPurchaseRowVisible(0, true);		
		
	}//CREATE RESULT PANEL
	
	
	
	private void createResultRows () {
		
		Dimension filler = new Dimension(32,32);
		
		for (int row = 0; row < totalResultRows; row++) {
		
			resultRowPanel[row] = new Panel(new GridLayout(1,3));
			
			 if (row % 2 == 0)
				 resultRowPanel[row].setBackground(bg2);
			 else
				 resultRowPanel[row].setBackground(bg3);
        	
//SUB PANEL 1
			
			Panel purchaseSubPanel1 = new Panel();
			purchaseSubPanel1.setLayout(new GridLayout(1,3));
					
			Label purchaseItemLabel = new Label("Item " + (row+1) + ": ");
			purchaseItemLabel.setFont(font1);
			purchaseSubPanel1.add(purchaseItemLabel);
			
			resultText[row] = new JFormattedTextField();
			resultText[row].setFont(font1);
			resultText[row].setEditable(false);
			purchaseSubPanel1.add(resultText[row]);
			
			purchaseSubPanel1.add(new Box.Filler(filler, filler, filler));
			resultRowPanel[row].add(purchaseSubPanel1);
			
//SUB PANEL 2
		
			Panel purchaseSubPanel2 = new Panel();
			purchaseSubPanel2.setLayout(new GridLayout(1,3));
					
			Label resultQuantityLabel = new Label("Quantity: ");
			resultQuantityLabel.setFont(font1);
			purchaseSubPanel2.add(resultQuantityLabel);
			
			resultQuantity[row] = new JFormattedTextField();
			resultQuantity[row].setFont(font1);
			resultQuantity[row].setSize(8, 32);
			resultQuantity[row].setText("Example Item");
			resultQuantity[row].setEditable(false);
			purchaseSubPanel2.add(resultQuantity[row]);
			
			resultAddCart[row] = new Button("Add To Cart");
			resultAddCart[row].addActionListener(new addToCartAction());
			purchaseSubPanel2.add(resultAddCart[row]);	
			
			resultRowPanel[row].add(purchaseSubPanel2);
			
			resultRowPanel[row].setVisible(false);
			resultPanel.add(resultRowPanel[row]);
		}//for
		
	}//CREATE RESULT ROWS
	
	private void clearRow(int row) {
		
		resultText[row].setText(" ");
		resultQuantity[row].setText(" ");
		
	} //CLEAR ROW

	private void setPurchaseRowVisible (int row, boolean vis) {
		resultRowPanel[row].setVisible(vis);
	}//SET PURCHASE ROW VISIBILE
	
	
	//LISTENERS
	
	
	public class resultCheckAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
			
		}
	}
	
	public class addToCartAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < totalResultRows; i++) {
				if ( e.getSource() == resultAddCart[i]) {
					addToCart(i);
				}	
			} //MATCH VIEW OF BUTTON PRESSED THEN SWITCH
        }
	}
	
	private void addToCart(int row) {
		this.getParent().addItem("Example Item");
		((CustomerCheckoutView) this.getParent().getView(2)).update();
	}
	
	
}
