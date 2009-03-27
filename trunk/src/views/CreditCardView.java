package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import views.MainView.buttonAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class CreditCardView extends View {
	
	private Panel cardInfoPanel;
	private Button submitCardButton;
	private JFormattedTextField cardNumber;
	private JFormattedTextField cardExpiryMonth;
	private JFormattedTextField cardExpiryYear;
	private Panel container;

	public CreditCardView (String title, MainView parent) {
		
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
		container.setLayout(new GridLayout(4,1));
		container.setPreferredSize(new Dimension(Short.MAX_VALUE,256));
		this.getContainer().add(container, BorderLayout.NORTH);
		
		//label for Container
		Label thisTitle = new Label("Credit Authorization:");
		thisTitle.setFont(font1);
		container.add(thisTitle);
		
		
		cardInfoPanel = new Panel(new GridLayout(1,4));
		container.add(cardInfoPanel);		
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label cardNumberLabel = new Label("Card Number:");
		cardNumberLabel.setFont(font1);
		subPanel1.add(cardNumberLabel);
		
		cardNumber = new JFormattedTextField();
		cardNumber.setFont(font1);
		cardNumber.setEditable(true);
		subPanel1.add(cardNumber);
		
		cardInfoPanel.add(subPanel1);
		
//SUB PANEL 2
		
		Panel subPanel2 = new Panel();
		subPanel2.setLayout(new GridLayout(2,1));
				
		Label cardExpiryMonthLabel = new Label("Expiry Month:");
		cardExpiryMonthLabel.setFont(font1);
		subPanel2.add(cardExpiryMonthLabel);
		
		cardExpiryMonth = new JFormattedTextField();
		cardExpiryMonth.setFont(font1);
		cardExpiryMonth.setEditable(true);
		subPanel2.add(cardExpiryMonth);
		
		cardInfoPanel.add(subPanel2);
		
//SUB PANEL 3

		Panel subPanel3 = new Panel();
		subPanel3.setLayout(new GridLayout(2,1));
		
		Label cardExpiryYearLabel = new Label("Expiry Year:");
		cardExpiryYearLabel.setFont(font1);
		subPanel3.add(cardExpiryYearLabel);
		
		cardExpiryYear = new JFormattedTextField();
		cardExpiryYear.setFont(font1);
		cardExpiryYear.setEditable(true);
		subPanel3.add(cardExpiryYear);
		
		cardInfoPanel.add(subPanel3);
		
//SUB PANEL 4
		
		Panel subPanel4 = new Panel();
		subPanel4.setLayout(new GridLayout(2,1));
		
		Label submitLabel = new Label("Submit:");
		submitLabel.setFont(font1);
		subPanel4.add(submitLabel);
		
		submitCardButton = new Button("Submit Card");
		submitCardButton.addActionListener(new loginAction());
		subPanel4.add(submitCardButton);
		
		cardInfoPanel.add(subPanel4);
		
		
		
		container.validate();
		
	}//Login PANELs
	
	
	
	
	//LISTENERS
	
	
	public class loginAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			
			
			
		}
	}// END LISTENER
	
	
	
}
