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
        	
        	container = new JPanel(new GridLayout(2,1));
        	container.setPreferredSize(new Dimension(Short.MAX_VALUE, 512));
        	this.getContainer().add(container, BorderLayout.CENTER);
        	
        	updateResults();
        
        	container.add(subPanel2);
        	container.add(subPanel1);
       
			container.validate();
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
			resultContainer2 = topSales.getContainer();
			
			subPanel1 = new JScrollPane(resultContainer1);
			subPanel2 = new JScrollPane(resultContainer2);
			
			
        }
    
  
}

