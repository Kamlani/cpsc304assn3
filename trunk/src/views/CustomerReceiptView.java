package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import views.InStoreView.cashAction;
import views.InStoreView.creditAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class CustomerReceiptView extends View {

	
	private Panel customerReceiptPanel;
	private JFormattedTextField daysToArrive;
	
	private ResultSet receipt; //CUSTOMER CLASS SEE EXAMPLE BELOW
	
	private Panel container;
	
	public CustomerReceiptView (String title, MainView parent) {
		
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
		container.setLayout(new GridLayout(3,1));
		container.setPreferredSize(new Dimension(Short.MAX_VALUE,128));
		this.getContainer().add(container, BorderLayout.NORTH);
		
		Panel container2 = new Panel();
		container2.setBackground(bg3);
		container2.setLayout(new GridLayout(1,1));
		container2.setPreferredSize(new Dimension(Short.MAX_VALUE,360));
		this.getContainer().add(container2, BorderLayout.SOUTH);
		
		//label for Container
		Label thisTitle = new Label("Customer Receipt:");
		thisTitle.setFont(font1);
		container.add(thisTitle);
		
		customerReceiptPanel = new Panel(new GridLayout(1,2));
		container.add(customerReceiptPanel);		
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label daysToArriveLabel = new Label("Days To Arrive:");
		daysToArriveLabel.setFont(font1);
		subPanel1.add(daysToArriveLabel);
		
		daysToArrive = new JFormattedTextField();
		daysToArrive.setFont(font1);
		daysToArrive.setEditable(true);
		subPanel1.add(daysToArrive);
		
		customerReceiptPanel.add(subPanel1);
		
		Button cashButton = new Button("Get Receipt");
		cashButton.addActionListener(new warehouseAction());
		customerReceiptPanel.add(cashButton);

//END SUB PANELS

//RECEIPT IS RESULT SET
		
		updateReceipt(); //MUST BE CALLED BEFORE receipt is ADDED TO RECEIPT PANEL
		
		container2.add(receipt.getContainer()); //need to use .getContainer() for you result set returns a Panel		

		container.validate();
		container2.validate();
		
	}//Login PANELs
	
	
	
	private void updateReceipt() {
		
		//CONTROLLER LOGIC to get days to arrive FROM WAREHOUSE
		//daysToArrive.setText();
		
		//GET RECEIPT INFO FROM CONTROLLER, POPULATE RESULT SET, EXAMPLE BELOW
		//EXAMPLE RESULT SET GET THIS INFO FROM CONTROLLER
        
        int rows = 5;
        int cols = 3;
        int cellWidth = 200;
      //These will be converted to strings so don't worry just stuff in numbers and shit from the DB
        Object header = "Example Result Set:";
        Object titles[] = {"Result Col 1", "Result Col 2", "Result Col 3"};
        Object[][] results = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                        results[i][j] = "Example Result: " + i + " , " + j;
                }
        }
        
        //END OF EXAMPLE
     
        receipt = new ResultSet(header,titles,results,cellWidth);
	}

	
	//LISTENERS
	
	
	public class warehouseAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
		
			updateReceipt();
			
		}
	}// END LISTENER
	
	
}
