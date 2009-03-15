package views;

import java.awt.*;

public class View {
	
	private Panel mainContainer;
	private Panel container;
	private String viewName;
	
	public View () {}
	
	public void setContainer() {
		
		mainContainer = new Panel();
		mainContainer.setLayout(new BorderLayout());
		mainContainer.setBackground(new Color(255,255,255));
		
		container = new Panel();
		container.setLayout(new BorderLayout());
		container.setBackground(new Color(255,255,255));
		
	}
	
	public void setTitle(String title) {
		viewName = title;
		
		Label label = new Label("View: " + title);
		label.setBackground(new Color(128,128,128));
		label.setFont(new Font("Arial", Font.BOLD, 24));
		mainContainer.add(label, BorderLayout.NORTH);
		
		mainContainer.add(container, BorderLayout.CENTER);
		
	}
	
	public String getViewName() {
		return viewName;		
	}
	
	public Panel getContainer() {
		return container;		
	}
	
	public Panel getView() {
		return mainContainer;		
	}
}
