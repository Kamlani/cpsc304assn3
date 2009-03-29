package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class ReturnView extends View {
	
	private Panel returnInfoPanel;
	private Button returnButton;
	private JFormattedTextField receiptID;
	
	private Panel container;

	public ReturnView (String title, MainView parent) {
		
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
		container.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(container, BorderLayout.NORTH);
		
		//label for Container
		Label thisTitle = new Label("Return Purchase:");
		thisTitle.setFont(font1);
		container.add(thisTitle);
		
		
		returnInfoPanel = new Panel(new GridLayout(1,2));
		container.add(returnInfoPanel);		
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label receiptIDLabel = new Label("receiptID:");
		receiptIDLabel.setFont(font1);
		subPanel1.add(receiptIDLabel);
		
		receiptID = new JFormattedTextField();
		receiptID.setFont(font1);
		receiptID.setEditable(true);
		subPanel1.add(receiptID);
		
		returnInfoPanel.add(subPanel1);
		
		
//END SUB PANELS
		
		Button cashButton = new Button("Return Purchase");
		cashButton.addActionListener(new returnAction());
		container.add(cashButton);
	
		container.validate();
		
	}//Login PANELs
	
	
	
	
	public void returnFunction () {
		
		//CONTROLLER LOGIC TO RETURN ITEM HERE
//		receiptID.getText();
		//THROW DIALOGUE TO LET USER KNOW WHETHER IT WAS CASH OR CREDIT RETURN
		
		//SWITCH TO VIEW 0 LOGIN VIEW MAIN SCREEN
		this.getParent().switchView(0);		
	}
	
	//LISTENERS
	
	
	public class returnAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			returnFunction();
		}
	}// END LISTENER
	
	
}
