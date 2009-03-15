package views;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainView {
	
	private JFrame mainFrame;
	private Label title;
	
	private View currentView;
	private View managerView;
	private View clerkView;
	private View customerView;
	
	private Panel buttonContainer;
	private Button button1;
	private Button button2;
	private Button button3;
	
	public MainView () {
		
		setFrame("CPSC 304 Assn3 Main View", 960, 600);		
		addTitle("CPSC 304 Assn3 Main View");
		addButtons("Manager", "Clerk", "Customer");
		addViews();
		
	}
	
	public void setFrame (String text, int width, int height) {
		mainFrame = new JFrame(text);		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(width, height);
		mainFrame.setVisible(true);	
	}
	
	public void addTitle (String text) {
		title = new Label(text);
		title.setBackground(new Color(64,64,64));
		title.setForeground(new Color(255,255,255));
		title.setFont(new Font("Arial", Font.BOLD, 32));
		mainFrame.add(title, BorderLayout.NORTH);
	}
	
	public void addButtons (String text1, String text2, String text3) {
		
		buttonContainer = new Panel();
		buttonContainer.setBackground(new Color(128,128,128));
		buttonContainer.setLayout(new BoxLayout(buttonContainer,BoxLayout.Y_AXIS));
		
		button1 = new Button(text1);
		button1.addActionListener(new button1Action());
		button2 = new Button(text2);	
		button2.addActionListener(new button2Action());
		button3 = new Button(text3);	
		button3.addActionListener(new button3Action());

		buttonContainer.add(button1);
		buttonContainer.add(button2);
		buttonContainer.add(button3);
		Dimension fillerSize = new Dimension(128, Short.MAX_VALUE);
		buttonContainer.add(new Box.Filler(fillerSize, fillerSize, fillerSize));
		
		mainFrame.add(buttonContainer, BorderLayout.WEST);		
	}
	
	public void addViews () {
		
		managerView = new ManagerView("Manager");
		clerkView = new ClerkView("Clerk");
		customerView = new CustomerView("Customer");
		currentView = managerView;
		mainFrame.add(currentView.getView());
		
	}
	
	public void switchView (int view, String title) {
		if (currentView.getViewName() != title) {
			mainFrame.remove(currentView.getView());
			
			switch (view) {
			case 1: currentView = managerView;			
			break;
			case 2: currentView = clerkView;
			break;
			case 3: currentView = customerView;
			break;
			}
		
			mainFrame.add(currentView.getView());
			mainFrame.validate();
		}
		
	}
	
	public class button1Action implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			switchView(1, "Manager");
        }
	}
	
	public class button2Action implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			switchView(2, "Clerk");
        }
	}
	
	public class button3Action implements ActionListener { 
		public void actionPerformed(ActionEvent e) {
			switchView(3, "Customer");
        }
	}

}
