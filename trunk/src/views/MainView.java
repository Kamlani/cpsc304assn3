package views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

public final class MainView {
	
	private static MainView thisMainView;
	
	private static String thisController;
	
	private static JFrame mainFrame;
	private static Label title;	

	
	private static int numViews;
	private static View currentView;
	private static String[] viewTitles;
	private static View[] views;
	
	
	private static Panel buttonContainer;
	private static String[] buttonText;
	private static Button[] buttons;
	
	private static int totalCartItems;
	private static Vector<Object> cart;
	private static int cartItems;
	
	public MainView () {
		
		thisMainView = this;
		
		setFrame("CPSC 304 Assn3 Main View", 960, 600);		
		addTitle("CPSC 304 Assn3 Main View");

		totalCartItems = 16;
		cart = new Vector<Object>();
		
		numViews = 6;
		viewTitles = new String[numViews];
		views = new View[numViews];
		buttons = new Button[numViews];
		buttonText = new String[numViews];
		
		viewTitles[0] = "LoginView: ";
		viewTitles[1] = "ManagerView: ";
		viewTitles[2] = "CustomerOnlineView: ";
		viewTitles[3] = "CustomerCheckoutView: ";
		viewTitles[4] = "CustomerReceiptView: ";
		viewTitles[5] = "CustomerView: ";
		
		views[0] = new LoginView(viewTitles[0], thisMainView);
		views[1] = new ManagerView(viewTitles[1], thisMainView);
		views[2] = new CustomerOnlineView(viewTitles[2], thisMainView);
		views[3] = new CustomerCheckoutView(viewTitles[3], thisMainView);
		views[4] = new CustomerReceiptView(viewTitles[4], thisMainView);
		views[5] = new CustomerView(viewTitles[5], thisMainView);
		
		buttonText[0] = "LoginView";
		buttonText[1] = "Manager";
		buttonText[2] = "Customer Online";
		buttonText[3] = "Customer Cart";
		buttonText[4] = "Customer Receipt";
		buttonText[5] = "Customer";
		
		addButtons();
		
		currentView = views[0];
		mainFrame.add(currentView.getView());
		mainFrame.validate();
	}
	
	private static void setFrame (String text, int width, int height) {
		mainFrame = new JFrame(text);		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(width, height);
		mainFrame.setVisible(true);	
	}
	
	private static void addTitle (String text) {
		title = new Label(text);
		title.setBackground(new Color(64,64,64));
		title.setForeground(new Color(255,255,255));
		title.setFont(new Font("Arial", Font.BOLD, 32));
		mainFrame.add(title, BorderLayout.NORTH);
	}
	
	private void addButtons () {
		
		buttonContainer = new Panel();
		buttonContainer.setBackground(new Color(128,128,128));
		buttonContainer.setLayout(new BoxLayout(buttonContainer,BoxLayout.Y_AXIS));
		
		for (int i = 0; i < numViews; i++) {
			buttons[i] = new Button(buttonText[i]);
			buttons[i].addActionListener(new buttonAction());
			buttons[i].setVisible(false);
			buttonContainer.add(buttons[i]);
		}
	
		Dimension fillerSize = new Dimension(128, Short.MAX_VALUE);
		buttonContainer.add(new Box.Filler(fillerSize, fillerSize, fillerSize));
		
		mainFrame.add(buttonContainer, BorderLayout.WEST);		
	}
	
	
	//LISTENER
	
	public class buttonAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < numViews; i++) {
				if ( e.getSource() == buttons[i]) {
					switchView(i);
				}	
			} //MATCH VIEW OF BUTTON PRESSED THEN SWITCH
        }
	}//END LISTENER
	
	
	
	//SWITCH VIEWS FOR MAIN AND CHILD USE
	
	public void switchView (int view) {
		if (currentView != views[view]) {
			mainFrame.remove(currentView.getView());
			currentView = views[view];
			mainFrame.add(currentView.getView());
		}
		setButtonVisibility(view);
	}//SWITCH VIEW
	
	//PRIVATE HELPERS FOR SETTING BUTTON VISIBILITY
	
	private void clearButtons() {
		for (int i = 0; i < numViews; i++) {
			buttons[i].setVisible(false);
		}
	}
	
	private void setButtonVisibility(int view) {
		switch (view) {
		case 0: clearButtons();
			break;
		case 1: clearButtons();
			buttons[0].setVisible(true);
			break;
		case 2: clearButtons();
			buttons[0].setVisible(true);
			buttons[2].setVisible(true);
			buttons[3].setVisible(true);
			break;
		}
		mainFrame.validate();
	}
	
	
	//PUBLIC METHODS FOR GETTING THE CONTROLLER, ADDING ITEMS TO CART, GETTING VIEWS
	
	public String getController() {
		return thisController;		
	}
	
	public View getView(int view) {
		return views[view];		
	}
	
	public Vector<Object> getCart() {
		return cart;		
	}
	
	public boolean addItem(Object item) {
		if (cart.size() < totalCartItems) {
			cart.add(item);
			return true;
		} else {
			return false;
		}
	}
	
	public void removeItem(int index) {
		cart.removeElementAt(index);
	}
	

}
