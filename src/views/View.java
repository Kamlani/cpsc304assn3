package views;

import java.awt.*;

public class View {
	
	private Panel mainContainer;
	private Panel container;
	private String viewName;
	
	public Color bg1;
	public Color bg2;
	public Color bg3;
	
	public Font font1;
	
	public View () {}
	
	public void setContainer() {
		
		bg1 = new Color(164,164,164);
        bg2 = new Color(196,196,196);
        bg3 = new Color(223,223,223);
        
        font1 = new Font("Arial", Font.BOLD, 14);
		
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
