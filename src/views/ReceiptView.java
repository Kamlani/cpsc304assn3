package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;

import views.ReturnView.resultCheckAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/


public class ReceiptView extends View {

		private JPanel wrapperContainer;
		private JPanel subPanel1;
		private JPanel subPanel3;
		private JScrollPane subPanel2;
		private JPanel resultContainer;
		
		private Button resultCheck;
		private Panel searchRowPanel;
		private JFormattedTextField searchSubPanelTitle;
		
		private JFormattedTextField totalPrice;
		private JFormattedTextField shipDays;
		
        public ReceiptView (String title, MainView parent) {
               
                //default setup methods defined in View.java
        		setParent(parent);
                setContainer();
                setTitle(title);
               
                init();
        }
       
        private void init() {

        	wrapperContainer = new JPanel(new BorderLayout());
        	
        	this.getContainer().add(wrapperContainer, BorderLayout.CENTER);
        	
        	createSearchPanel();
        	
        	createTotals();
        	
        	updateResults();
        
        	wrapperContainer.add(subPanel1,BorderLayout.NORTH);
        	wrapperContainer.add(subPanel2,BorderLayout.CENTER);
        	wrapperContainer.add(subPanel3,BorderLayout.SOUTH);
        	
        	wrapperContainer.validate();
        } //INIT
        
        private void createSearchPanel () {
    		
    		subPanel1 = new JPanel();
    		subPanel1.setBackground(bg3);
    		subPanel1.setLayout(new GridLayout(2,1));
    		
    		searchRowPanel = new Panel(new GridLayout(1,2));
    		subPanel1.add(searchRowPanel);
    		
    //SUB PANEL 1
    		
    		Panel subPanel1 = new Panel();
    		subPanel1.setLayout(new GridLayout(2,1));
    				
    		Label categoryLabel = new Label("Receipt ID:");
    		categoryLabel.setFont(font1);
    		subPanel1.add(categoryLabel);
    		
    		searchSubPanelTitle = new JFormattedTextField();
    		searchSubPanelTitle.setFont(font1);
    		searchSubPanelTitle.setEditable(true);
    		subPanel1.add(searchSubPanelTitle);
    		
    		searchRowPanel.add(subPanel1);
    		
    		
    //Search BUTTON
    		
    		Panel subPanel5 = new Panel();
    		subPanel5.setLayout(new GridLayout(2,1));
    		
    		Label searchLabel = new Label("Search:");
    		searchLabel.setFont(font1);
    		subPanel5.add(searchLabel);
    		
    		resultCheck = new Button("Search");
    		resultCheck.addActionListener(new resultCheckAction());
    		subPanel5.add(resultCheck);
    		
    		searchRowPanel.add(subPanel5);
    		
    	}//SEARCH PANEL
        
        String x = "5";
     //   ResultSet exampleReceipt;
        int counter = 0;
        
        private void updateResults() {
        	
        	
        	
        	x = x + "1";
        	String header;
    		String[] titles = { "UPC", "Quantity", "Price"+x };

    		if (true)
    		{
    			

    			try
    			{
    			header = "Online Bill" + x;
    			
    				Vector<Object> rIDInfo = thisController.getOnlineReceiptInfo(CreditCardView.currReceiptID);
    	        	System.out.println("Returend Here yo");
    	        	
    	    		String[] UPC = new String[rIDInfo.size()];
    	    		String[] quantity = new String[rIDInfo.size()];
    	    		String[] price = new String[rIDInfo.size()];
    	    		
    				for(int i = 0; i < rIDInfo.size(); i++)
    				{
    					Vector<Object> temp = (Vector<Object>)rIDInfo.get(i);
    	    			
    					if(temp.get(0) != null && temp.get(2) != null)
    					{

    						Integer Iupc = ((Integer)temp.get(0)).intValue();
    						Integer Iquantity = ((Integer)temp.get(1)).intValue();
    						Double Iprice = ((Double)temp.get(2)).doubleValue();
    	    				
    						UPC[i] = Iupc.toString();
    						quantity[i] = Iquantity.toString();
    						price[i] = Iprice.toString(); 
    	    				
    					}
    	    			
    	    			                                   	
    				}
    	    		
    
    							int rows = 2;//UPC.length;
    							int cols = 3;
    							int cellWidth = 200;
    							Object[][] results = new Object[rows][cols];
    							for (int i = 0; i < rows; i++)
    			{
    				results[i][0] = x;//UPC[i];
    				results[i][1] = x;//quantity[i];
    				results[i][2] = x;//price[i];
    			}

        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	//EXAMPLE RESULT SETS FOR RECEIPT ID

			//GET THIS INFO FROM CONTROLLER AND POPULATE THESE RESULT SETS
        	
            
            //EXAMPLE
    							
            
          ResultSet exampleReceipt = new ResultSet(header,titles,results,cellWidth);
            
            
      
            
            
            
			resultContainer = exampleReceipt.getContainer();
			resultContainer.setPreferredSize(new Dimension(512, 64 + rows * 20)); //DONT F*** THIS UP
			
			subPanel2 = new JScrollPane(resultContainer);
			
			
/////////////////////////////////////////////////////////////////////////////
			//SPECIAL COMMENT FOR YOU GUYS
////////////////////////////////////////////////////////////////////////////
			
			totalPrice.setText("" + x);
			shipDays.setText("" + x);
			
/////////////////////////////////////////////////////////////////////////////
			//SPEaCIAL COMMENT FOR YOU GUYS
////////////////////////////////////////////////////////////////////////////
			
			wrapperContainer.validate();
			resultContainer.validate();
    			}
    			catch(SQLException x34)
    			{}
    			
        }
        }
        
        
        
        private void createTotals () {
    		
    		subPanel3 = new JPanel();
    		subPanel3.setBackground(bg3);
    		subPanel3.setLayout(new GridLayout(2,2));
    		
    		Label label1 = new Label("Total Price");
    		label1.setFont(font2);
    		subPanel3.add(label1);

    		Label label2 = new Label("Shipment Days");
    		label2.setFont(font2);
    		subPanel3.add(label2);
    		
    		totalPrice = new JFormattedTextField();
    		totalPrice.setFont(font1);
    		totalPrice.setEditable(false);
    		subPanel3.add(totalPrice);
    		
    		shipDays = new JFormattedTextField();
    		shipDays.setFont(font1);
    		shipDays.setEditable(false);
    		subPanel3.add(shipDays);
    		
    	}//SEARCH PANEL
        
        //LISTENER
        
        public class resultCheckAction implements ActionListener { 
    		public void actionPerformed(ActionEvent e) {
    			//resultContainer.removeAll();
    		//	init();
    			updateResults();
    		//	wrapperContainer.validate();
    			MainView.errorDialog("Receipt Updated");
    		}
    	}
  
}

