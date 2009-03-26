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

public class LoginView extends View {
	
	private Panel[] loginRowPanel;
	private Button[] loginButton;
	private String[] loginText;
	private JFormattedTextField[] loginName;
	private JFormattedTextField[] loginPass;
	private Panel container;

	public LoginView (String title, MainView parent) {
		
		//default setup methods defined in View.java
		setParent(parent);

		setContainer();
		setTitle(title);
		
		loginButton = new Button[3];
		loginRowPanel = new Panel[3];
		
		loginText = new String[3];
		loginText[0] = "Manager";
		loginText[1] = "Clerk";
		loginText[2] = "Customer";

		loginName = new JFormattedTextField[3];
		loginPass = new JFormattedTextField[3];
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
		Label thisTitle = new Label("Login as any User:");
		thisTitle.setFont(font1);
		container.add(thisTitle);
		
		for (int i = 0; i < 3; i++) {
			
			loginRowPanel[i] = new Panel(new GridLayout(1,3));
			container.add(loginRowPanel[i]);		
			
	//SUB PANEL 1
			
			Panel subPanel1 = new Panel();
			subPanel1.setLayout(new GridLayout(2,1));
					
			Label categoryLabel = new Label(loginText[i] + " Username:");
			categoryLabel.setFont(font1);
			subPanel1.add(categoryLabel);
			
			loginName[i] = new JFormattedTextField();
			loginName[i].setFont(font1);
			loginName[i].setEditable(true);
			subPanel1.add(loginName[i]);
			
			loginRowPanel[i].add(subPanel1);
			
	//SUB PANEL 2
			
			Panel subPanel2 = new Panel();
			subPanel2.setLayout(new GridLayout(2,1));
					
			Label titleLabel = new Label(loginText[i] + " Password:");
			titleLabel.setFont(font1);
			subPanel2.add(titleLabel);
			
			loginPass[i] = new JFormattedTextField();
			loginPass[i].setFont(font1);
			loginPass[i].setEditable(true);
			subPanel2.add(loginPass[i]);
			
			loginRowPanel[i].add(subPanel2);
			
	//SUB PANEL 3
		
			//Login BUTTON
			
			Panel subPanel3 = new Panel();
			subPanel3.setLayout(new GridLayout(2,1));
			
			Label searchLabel = new Label("Login:");
			searchLabel.setFont(font1);
			subPanel3.add(searchLabel);
			
			loginButton[i] = new Button("Login " + loginText[i]);
			loginButton[i].addActionListener(new loginAction());
			subPanel3.add(loginButton[i]);
			
			loginRowPanel[i].add(subPanel3);
		}
		
		container.validate();
		
	}//Login PANELs
	
	
	public void login(int user){
		
		switch (user) {
		case 0: //loginManager CONTROLLER LOGIC Manager Login with inputName[0], inputPass[0]
			this.getParent().switchView(1);
			break;
		case 1: //loginClerk CONTROLLER LOGIC Manager Login with inputName[1], inputPass[1]
			this.getParent().switchView(1);
			break;
		case 2: //loginCustomer CONTROLLER LOGIC Manager Login with inputName[2], inputPass[2]
			this.getParent().switchView(2);
			break;
		}

	}
	
	
	
	
	//LISTENERS
	
	
	public class loginAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 3; i++) {
				if (e.getSource() == loginButton[i]) {
					login(i);
				}
			}
		}
	}// END LISTENER
	
	
	
}
