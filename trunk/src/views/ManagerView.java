package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/


public class ManagerView extends View {

		private JPanel wrapperContainer;
		private JPanel container;
		private JScrollPane subPanel1;
		private JScrollPane subPanel2;
		private JPanel resultContainer1;
		private JPanel resultContainer2;
		
		
        public ManagerView (String title, MainView parent) {
               
                //default setup methods defined in View.java
        		setParent(parent);
                setContainer();
                setTitle(title);
               
                init();
        }
       
        private void init() {

        	Button refresh = new Button("Refresh Reports");
        	refresh.setPreferredSize(new Dimension(128,64));
        	refresh.addActionListener(new refreshAction());
        	
        	container = new JPanel(new GridLayout(2,1));
        	container.setPreferredSize(new Dimension(Short.MAX_VALUE, 480));
        	
        	wrapperContainer = new JPanel(new BorderLayout());
        	wrapperContainer.add(container,BorderLayout.CENTER);
        	wrapperContainer.add(refresh,BorderLayout.SOUTH);
        	
        	this.getContainer().add(wrapperContainer, BorderLayout.CENTER);
        	
        	updateResults();
        
        	container.add(subPanel1);
        	container.add(subPanel2);
        	
        	wrapperContainer.validate();
        } //INIT
        
        
        private void updateResults() {
        	
        	//EXAMPLE RESULT SETS FOR TOP SALES AND DAILY REPORT 

			//GET THIS INFO FROM CONTROLLER AND POPULATE THESE RESULT SETS
        	
            int rows = 15;
            int cols = 3;
            int cellWidth = 200;
            String header = "Daily Sales Report: ";
            String header2 = "Top Sales Report: ";
            String[] titles = {"Result Col 1","Result Col 2","Result Col 3"};
           
            String[][] results = new String[rows][cols];
            for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                            results[i][j] = "Example Result: " + i + " , " + j;
                    }
            }
            
            //EXAMPLE
            
			ResultSet dailySales = new ResultSet(header,titles,results,cellWidth);
			ResultSet topSales = new ResultSet(header2,titles,results,cellWidth);
		
			resultContainer1 = dailySales.getContainer();
			resultContainer1.setPreferredSize(new Dimension(512, 64 + rows * 20)); //DONT F*** THIS UP
			resultContainer2 = topSales.getContainer();
			resultContainer2.setPreferredSize(new Dimension(512, 64 + rows * 20)); //DONT F*** THIS UP
			
			subPanel1 = new JScrollPane(resultContainer1);
			subPanel2 = new JScrollPane(resultContainer2);
			
			wrapperContainer.validate();
        }
        
        
        //LISTENER
        
        public class refreshAction implements ActionListener { 
    		public void actionPerformed(ActionEvent e) {
    			updateResults();
    			MainView.errorDialog("Reports Updated");
    		}
    	}// END LISTENER
    
  
}

