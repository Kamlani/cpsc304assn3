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

public class RegisterView extends View {
	
	private Panel cardInfoPanel;
	private Button registerButton;
	private JFormattedTextField name;
	private JFormattedTextField address;
	private JFormattedTextField phone;
	private JFormattedTextField id;
	private JFormattedTextField pass;
	
	private Panel container;

	public RegisterView (String title, MainView parent) {
		
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
		
		
		cardInfoPanel = new Panel(new GridLayout(1,6));
		container.add(cardInfoPanel);		
		
//SUB PANEL 1
		
		Panel subPanel1 = new Panel();
		subPanel1.setLayout(new GridLayout(2,1));
				
		Label nameLabel = new Label("Name:");
		nameLabel.setFont(font1);
		subPanel1.add(nameLabel);
		
		name = new JFormattedTextField();
		name.setFont(font1);
		name.setEditable(true);
		subPanel1.add(name);
		
		cardInfoPanel.add(subPanel1);
		
//SUB PANEL 2
		
		Panel subPanel2 = new Panel();
		subPanel2.setLayout(new GridLayout(2,1));
				
		Label addressLabel = new Label("Address:");
		addressLabel.setFont(font1);
		subPanel2.add(addressLabel);
		
		address = new JFormattedTextField();
		address.setFont(font1);
		address.setEditable(true);
		subPanel2.add(address);
		
		cardInfoPanel.add(subPanel2);
		
//SUB PANEL 3

		Panel subPanel3 = new Panel();
		subPanel3.setLayout(new GridLayout(2,1));
		
		Label phoneLabel = new Label("Phone:");
		phoneLabel.setFont(font1);
		subPanel3.add(phoneLabel);
		
		phone = new JFormattedTextField();
		phone.setFont(font1);
		phone.setEditable(true);
		subPanel3.add(phone);
		
		cardInfoPanel.add(subPanel3);
		
//SUB PANEL 4

		Panel subPanel4 = new Panel();
		subPanel4.setLayout(new GridLayout(2,1));
		
		Label idLabel = new Label("User ID:");
		idLabel.setFont(font1);
		subPanel4.add(idLabel);
		
		id = new JFormattedTextField();
		id.setFont(font1);
		id.setEditable(true);
		subPanel4.add(id);
		
		cardInfoPanel.add(subPanel4);
		
//SUB PANEL 5

		Panel subPanel5 = new Panel();
		subPanel5.setLayout(new GridLayout(2,1));
		
		Label passLabel = new Label("Password:");
		passLabel.setFont(font1);
		subPanel5.add(passLabel);
		
		pass = new JFormattedTextField();
		pass.setFont(font1);
		pass.setEditable(true);
		subPanel5.add(pass);
		
		cardInfoPanel.add(subPanel5);
		
//SUB PANEL 4
		
		Panel subPanel6 = new Panel();
		subPanel6.setLayout(new GridLayout(2,1));
		
		Label submitLabel = new Label("Register User:");
		submitLabel.setFont(font1);
		subPanel6.add(submitLabel);
		
		registerButton = new Button("Register");
		registerButton.addActionListener(new registerAction());
		subPanel6.add(registerButton);
		
		cardInfoPanel.add(subPanel6);
		
		//END OF SUB PANELS
		
		
		container.validate();
		
	}//Login PANELs
	
	
	
	
	public void register() {
		
		//CONTROLLER LOGIC FOR REGISTERING
//		name.getText();
//		address.getText();
//		phone.getText();
//		id.getText();
//		pass.getText();
		//if successful
		//get cid RETURNED FROM CONTROLLER
		
		int cid = 83837278; //EXAMPLE CID
		
		this.getParent().setCID(cid);
		this.getParent().switchView(2);
	}
	
	//LISTENERS
	
	
	public class registerAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			register();
		}
	}// END LISTENER
	
	
	
}
