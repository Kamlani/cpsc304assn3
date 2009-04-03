package views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Vector;

import logic.Operations;

public final class MainView {
	
	private static MainView thisMainView;
	
	private static Operations thisController; //CONTROLLER REFERENCE

	
	//VIEW AND BUTTON INFORMATION DON'T EDIT
	private static JFrame mainFrame;
	private static Label title;	
	private static int numViews;
	private static View currentView;
	private static String[] viewTitles;
	private static View[] views;
	private static Panel buttonContainer;
	private static String[] buttonText;
	private static Button[] buttons;
	
	private static int cid; //CUSTOMER ID
	private static int totalCartItems; //MAX CART ITEMS
	private static Vector<Object> cart; //USE ARRAYS IN THE VECTOR WITH YOUR INFO FOR EACH ITEM THAT NEEDS TO BE DISPLAYED
	
	public MainView () {
		
		//CART AND USER STUFF
		
		cid = 0;
		totalCartItems = 16;
		cart = new Vector<Object>();// INIT TO SIZE 0
		
		//DON'T NEED TO EDIT UNLESS ADDING A VIEW
		
		thisMainView = this;
		
		setFrame("CPSC 304 Assn3 Main View", 960, 600);		
		addTitle("CPSC 304 Assn3 Main View");
		
		numViews = 9;
		viewTitles = new String[numViews];
		views = new View[numViews];
		buttons = new Button[numViews];
		buttonText = new String[numViews];
		
		viewTitles[0] = "LoginView: ";
		viewTitles[1] = "ManagerView: ";
		viewTitles[2] = "CustomerOnlineView: ";
		viewTitles[3] = "CustomerCheckoutView: ";
		viewTitles[4] = "CreditCardView: ";
		viewTitles[5] = "RegisterView: ";
		viewTitles[6] = "InStoreView: ";
		viewTitles[7] = "ReturnView: ";
		viewTitles[8] = "ReceiptView: ";
		viewTitles[9] = "ShipmentView: ";
		
		views[0] = new LoginView(viewTitles[0], thisMainView);
		views[1] = new ManagerView(viewTitles[1], thisMainView);
		views[2] = new CustomerOnlineView(viewTitles[2], thisMainView);
		views[3] = new CustomerCheckoutView(viewTitles[3], thisMainView);
		views[4] = new CreditCardView(viewTitles[4], thisMainView);
		views[5] = new RegisterView(viewTitles[5], thisMainView);
		views[6] = new InStoreView(viewTitles[6], thisMainView);		
		views[7] = new ReturnView(viewTitles[7], thisMainView);
		views[8] = new CustomerReceiptView(viewTitles[8], thisMainView);
		views[9] = new ManagerView(viewTitles[9], thisMainView);

		buttonText[0] = "LoginView";
		buttonText[1] = "Manager";
		buttonText[2] = "Customer Online";
		buttonText[3] = "Customer Cart";
		buttonText[4] = "Credit Card";
		buttonText[5] = "Register Customer";
		buttonText[6] = "In Store Customer";
		buttonText[7] = "Customer Return";
		buttonText[8] = "Customer Receipt";
		buttonText[9] = "Shipment Control";

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
	
	//PUBLIC METHODS FOR SWITCH VIEWS
	
	public void switchView (int view) {
		if (view == 0) {
			cid = 0;
			cart.removeAllElements();
		}//reset all data
		if (currentView != views[view]) {
			mainFrame.remove(currentView.getView());
			currentView = views[view];
			mainFrame.add(currentView.getView());
		}
		//UPDATE VIEWS
		((CustomerCheckoutView)views[3]).update();
		mainFrame.validate();
	}//SWITCH VIEW
	
	
	//PUBLIC METHODS FOR GETTING THE CONTROLLER, ADDING ITEMS TO CART, GETTING VIEWS
	
	public Operations getController() {
		return thisController;		
	}
	
	public View getView(int view) {
		return views[view];		
	}
	
	public Vector<Object> getCart() {
		return cart;		
	}
	
	public boolean addItem(Vector<Object> item) {
		if (cart.size() < totalCartItems) {
			for (int i = 0; i < cart.size(); i++) {
				if ( ((Vector)cart.get(i)).get(0) == item.get(0) ) { //ITEM IS IN CART, UPC MATCH
					
					((Vector)cart.get(i)).set   ( 2,   1 + (Integer) ((Vector)cart.get(i)).get(2)   ); //Sets index 2 (QUANTITY) to plus 1
					return true;
				}
			}
			
			item.set(2, 1); //SETS THE QUANTITY OF THE ITEM TO ADD TO 1
			cart.add(item);
			return true;
		} else {
			return false;
		}
	}
	
	public void removeItem(int index) {
		cart.removeElementAt(index);
	}
	
	public void removeAll() {
		cart.removeAllElements();
	}
	
	public int getCID () {
		return cid;
	}
	
	public void setCID (int newID) {
		cid = newID;
	}
	
	
	
	
//PRIVATE HELPERS FOR SETTING BUTTON VISIBILITY ACCESS CONTROL DEPRECIATED
	
//	private void clearButtons() {
//		for (int i = 0; i < numViews; i++) {
//			buttons[i].setVisible(false);
//		}
//	}
//	
//	private void setButtonVisibility(int view) {
//		switch (view) {
//			case 0: clearButtons();
//				break;
//			case 1: clearButtons();
//				buttons[0].setVisible(true);
//				break;
//			case 2: clearButtons();
//				buttons[0].setVisible(true);
//				buttons[2].setVisible(true);
//				buttons[3].setVisible(true);
//				break;
//			case 3: clearButtons();
//				buttons[0].setVisible(true);
//				buttons[2].setVisible(true);
//				buttons[3].setVisible(true);
//			break;
//			case 4: clearButtons();
//				buttons[0].setVisible(true);
//				buttons[4].setVisible(true);
//			break;
//			case 5: clearButtons();
//				buttons[0].setVisible(true);
//			break;
//			case 6: clearButtons();
//				buttons[0].setVisible(true);
//			break;
//			case 7: clearButtons();
//				buttons[0].setVisible(true);
//			break;
//			case 8: clearButtons();
//				buttons[0].setVisible(true);
//			break;
//		}
//		
//	}

}
