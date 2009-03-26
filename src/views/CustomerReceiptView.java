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

public class CustomerReceiptView extends View {

	public CustomerReceiptView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);
		setContainer();
		setTitle(title);

	}
	
	public void error (String error) {
	       
		Label errorLabel = new Label("Error: " + error);
		errorLabel.setFont(font1);
		this.getContainer().add(errorLabel);
	}
	
	
	//LISTENER
	public class purchaseRemoveAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
        }
	}
	
	
}
