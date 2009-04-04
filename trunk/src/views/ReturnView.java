package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import views.CustomerOnlineView.addToCartAction;
import views.CustomerOnlineView.resultCheckAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class ReturnView extends View {
	
	private Panel resultPanel;

	private Vector<Object> results;
	
	private int totalResultRows;
	private Panel[] resultRowPanel;	
	private JFormattedTextField[] resultText;
	private JFormattedTextField[] resultQuantity;
	private Button[] returnButtons;

	private Button resultCheck;
	private Panel searchRowPanel;
	private JFormattedTextField searchSubPanelTitle;
	private Panel searchPanel;
	

	public ReturnView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		totalResultRows = 12;
		
		resultRowPanel = new Panel[totalResultRows];
		returnButtons = new Button[totalResultRows];
		resultText = new JFormattedTextField[totalResultRows];		
		resultQuantity = new JFormattedTextField[totalResultRows];
		
		results = new Vector<Object>();// INIT TO SIZE 0
		
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
		
		searchRowPanel = new Panel(new GridLayout(1,2));
		searchPanel.add(searchRowPanel);
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label categoryLabel = new Label("Receipt ID:");
		categoryLabel.setFont(font1);
		subPanel1.add(categoryLabel);
		
		searchSubPanelTitle = new JFormattedTextField();
		searchSubPanelTitle.setFont(font1);
		searchSubPanelTitle.setEditable(true);
		subPanel1.add(searchSubPanelTitle);
		
		searchRowPanel.add(subPanel1);
		
		
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
	
	
	//RESULTS PANEL
	
	
	private void createResultPanel () {
		
		//FILLS for GRID
		
		//SET UP PANELS FOR CLERK
		
		resultPanel = new Panel();
		resultPanel.setBackground(bg3);
		resultPanel.setLayout(new GridLayout(   totalResultRows+2   ,1));
		resultPanel.setPreferredSize(new Dimension(Short.MAX_VALUE,320));
		this.getContainer().add(resultPanel, BorderLayout.SOUTH);
		
		//label for Purchase Panel
		Label purchaseLabel = new Label("Matching Items: ");
		purchaseLabel.setFont(font1);
		resultPanel.add(purchaseLabel);
		
		createResultRows();
		
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
			
			Panel subPanel1 = new Panel();
			subPanel1.setLayout(new GridLayout(1,3));
					
			Label purchaseItemLabel = new Label("Item " + (row+1) + ": ");
			purchaseItemLabel.setFont(font1);
			subPanel1.add(purchaseItemLabel);
			
			resultText[row] = new JFormattedTextField();
			resultText[row].setFont(font1);
			resultText[row].setEditable(false);
			subPanel1.add(resultText[row]);
			
			subPanel1.add(new Box.Filler(filler, filler, filler));
			resultRowPanel[row].add(subPanel1);
			
//SUB PANEL 2
		
			Panel subPanel2 = new Panel();
			subPanel2.setLayout(new GridLayout(1,3));
					
			Label resultQuantityLabel = new Label("Quantity: ");
			resultQuantityLabel.setFont(font1);
			subPanel2.add(resultQuantityLabel);
			
			resultQuantity[row] = new JFormattedTextField();
			resultQuantity[row].setFont(font1);
			resultQuantity[row].setSize(8, 32);
			resultQuantity[row].setEditable(false);
			subPanel2.add(resultQuantity[row]);
			
			returnButtons[row] = new Button("Return One Item");
			returnButtons[row].addActionListener(new returnAction());
			subPanel2.add(returnButtons[row]);	
			
			resultRowPanel[row].add(subPanel2);
			
			resultPanel.add(resultRowPanel[row]);
		}//for
		
		update(); //CALL UPDATE
		
	}//CREATE RESULT ROWS
	
	//UPDATE SHOULD BE CALLED AFTER YOU GET SEARCH RESULTS FROM DB IN THE LISTENER resultCheckAction
	//Populate the cart by following the example in resultCheckAction
	
	private void update() {
		for (int i = 0; i < totalResultRows; i++ ) {
			resultRowPanel[i].setVisible(false);
		}
		for (int i = 0; i < results.size(); i++ ) {
			
			resultText[i].setText(((Vector)results.get(i)).get(1).toString());
			resultQuantity[i].setText(((Vector)results.get(i)).get(2).toString());
			resultRowPanel[i].setVisible(true);
		}
	} //CLEAR ROW
	
	
	

	//LISTENERS
	
	
	public class resultCheckAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
			results.removeAllElements();
			
			//POPULATES RESULTS OF SEARCH FROM CONTROLLER HERE IS AN EXAMPLE
			Vector<Object> exampleResult = new Vector<Object>();
			exampleResult.add(123456789);
			exampleResult.add("Blah");
			exampleResult.add(2);
			
			Vector<Object> exampleResult2 = new Vector<Object>();
			exampleResult2.add(123456789);
			exampleResult2.add("Poop");
			exampleResult2.add(7);
			
			Vector<Object> exampleResult3 = new Vector<Object>();
			exampleResult3.add(123456789);
			exampleResult3.add("More Poop");
			exampleResult3.add(6);
			
			results.add(exampleResult);
			results.add(exampleResult2);
			results.add(exampleResult3);
			
			update();
		}
	}
	
	public class returnAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
			///CONTROLLER RETURN
			
        }
	}	
	
}