package views;

import java.awt.*;

import javax.swing.*;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/


public class ManagerView extends View {

        public ManagerView (String title) {
               
                //default setup methods defined in View.java
                setContainer();
                setTitle(title);
               
                init();
        }
       
        private void init() {
               
//EXAMPLE RESULT SET
               
                int rows = 5;
                int cols = 3;
                int cellWidth = 200;
                String header = "Example Result Set:";
                String[] titles = {"Result Col 1","Result Col 2","Result Col 3"};
               
                String[][] results = new String[rows][cols];
                for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                                results[i][j] = "Example Result: " + i + " , " + j;
                        }
                }
               
                ResultSet theResults = new ResultSet(header,titles,results,cellWidth);
               
                this.getContainer().add(theResults.getContainer(), BorderLayout.WEST);
        }
       
       
}

