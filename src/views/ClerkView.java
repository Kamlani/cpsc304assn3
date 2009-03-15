package views;

import java.awt.*;
import javax.swing.*;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/

public class ClerkView extends View {

	public ClerkView (String title) {
		
		//default setup methods defined in View.java
		setContainer();
		setTitle(title);
		
		init();
	}
	
	private void init() {
		
		//The title in the main Panel returned from getContainer()
		Label label = new Label("This is an example of adding a Label Component to Panel = ManagerView.getContainer() using BorderLayout.NORTH");
		label.setFont(new Font("Arial", Font.BOLD, 12));
		this.getContainer().add(label, BorderLayout.NORTH); //Only one component can be NORTH in a BorderLayout
		
		//Buttons in the main Panel returned from getContainer() with a new layout BoxLayout
		Panel buttonContainer = new Panel();
		buttonContainer.setBackground(new Color(128,128,128));
		buttonContainer.setLayout(new BoxLayout(buttonContainer,BoxLayout.X_AXIS));
		
		Button button1 = new Button("Button 1");
		Button button2 = new Button("Button 2");
		Button button3 = new Button("Button 3");
		
		buttonContainer.add(button1);
		buttonContainer.add(button2);
		buttonContainer.add(button3);
		
		//The title in the newContainer Panel
		Label label3 = new Label("Buttons added to Panel = ManagerView.getContainer() using BorderLayout.SOUTH");
		label3.setFont(new Font("Arial", Font.BOLD, 12));
		buttonContainer.add(label3);
		
		Dimension fillerSize = new Dimension(Short.MAX_VALUE,32);
		buttonContainer.add(new Box.Filler(fillerSize, fillerSize, fillerSize));
		
		this.getContainer().add(buttonContainer, BorderLayout.SOUTH);//Only one component can be SOUTH in a BorderLayout
		

		//A new Panel that takes up the center of the border layout so we can add more components with a different layout
		Panel newContainer = new Panel();
		newContainer.setBackground(new Color(128,64,128));
		
		//Potential new layout for the center Panel
		//Default will inherit BorderLayout from Parent container which is container in View.java
		//newContainer.setLayout(new BoxLayout(buttonContainer,BoxLayout.X_AXIS));
		
		this.getContainer().add(newContainer, BorderLayout.CENTER);
		
		//The title in the newContainer Panel
		Label label2 = new Label("This is an example of adding a Label Component to a Panel nested in Panel = ManagerView.getContainer()");
		label2.setFont(new Font("Arial", Font.BOLD, 12));
		newContainer.add(label2, BorderLayout.CENTER);
		
	}
	
	
}
