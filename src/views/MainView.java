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
		
		numViews = 5;
		viewTitles = new String[numViews];
		views = new View[numViews];
		
		viewTitles[0] = "ManagerView";
		viewTitles[1] = "CustomerOnlineView";
		viewTitles[2] = "CustomerCheckoutView";
		viewTitles[3] = "CustomerReceiptView: ";
		viewTitles[4] = "CustomerView: ";
		
		buttons = new Button[numViews];
		buttonText = new String[numViews];
		buttonText[0] = "Manager";
		buttonText[1] = "Customer Online";
		buttonText[2] = "Customer Cart";
		buttonText[3] = "Customer Receipt";
		buttonText[4] = "Customer";
		
		totalCartItems = 16;
		cart = new Vector<Object>();
		
		addButtons();
		addViews();
		
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
	
	private static void addViews () {
		
		views[0] = new ManagerView(viewTitles[0], thisMainView);
		views[1] = new CustomerOnlineView(viewTitles[1], thisMainView);
		views[2] = new CustomerCheckoutView(viewTitles[2], thisMainView);
		views[3] = new CustomerReceiptView(viewTitles[3], thisMainView);
		views[4] = new CustomerView(viewTitles[4], thisMainView);
	
		currentView = views[0];
		mainFrame.add(currentView.getView());
		mainFrame.validate();
	}
	
	
	//SWITCH VIEWS
	
	private static void switchView (int view, String title) {
		if (currentView.getViewName() != title) {
			mainFrame.remove(currentView.getView());
			currentView = views[view];
			mainFrame.add(currentView.getView());
			mainFrame.validate();
		}
	}//SWITCH VIEW
	
	
	
	
	//LISTENER
	
	public class buttonAction implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < numViews; i++) {
				if ( e.getSource() == buttons[i]) {
					switchView(i,buttonText[i]);
				}	
			} //MATCH VIEW OF BUTTON PRESSED THEN SWITCH
        }
	}//END LISTENER
	
	
	//PUBLIC VIEW SWITCHING FROM CHILD VIEWS USED FOR DISPLAYING RESULTS
	
	
	public void showResultsView(int view) {
		views[view].clear(); //Clears Results Views
		views[view].reInit(viewTitles[view], thisMainView); //Reinits the Label for the View and sets it up
		
		//EXAMPLE CODE TO BE REPLACED BY CONTROL LOGIC
		
		 int rows = 5;
         int cols = 3;
         int cellWidth = 200;
         String header = "EXAMPLE FOR A CLERK RESULT VIEW:";
         String[] titles = {"Result Col 1","Result Col 2","Result Col 3"};
        
         String[][] results = new String[rows][cols];
         for (int i = 0; i < rows; i++) {
                 for (int j = 0; j < cols; j++) {
                         results[i][j] = "Example Result: " + i + " , " + j;
                 }
         }
         
         //END EXAMPLE CODE
         
         views[view].showResults(header,titles,results,cellWidth);
         
         
		mainFrame.remove(currentView.getView());
		currentView = views[view];
		mainFrame.add(currentView.getView());
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
