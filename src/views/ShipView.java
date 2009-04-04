package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.*;

import views.ReceiptView.resultCheckAction;

/*
 * Use getContainer(), or this.getContainer() to get the main container for the view
 * add components to the Panel returned from getContainer()
 * the container returns a Panel with a BorderLayout
 * For more complicated layouts, nest an element inside the Panel returned from getContainer()
*/


public class ShipView extends View {

		private JPanel wrapperContainer;
	
		private JPanel container;

		private JPanel supplierContainer;
		private Panel supplierPanel;
		private JFormattedTextField searchSubPanelTitle;
		
		private Panel shipInfoPanel;
		private JFormattedTextField supplierName;
		private JFormattedTextField storeName;
		private JFormattedTextField shipYear;
		private JFormattedTextField shipMonth;
		private JFormattedTextField shipDay;
		private Panel shipPanel;
		
		private JScrollPane shipmentScrollPane;
		private JPanel shipmentPanel;
		private JPanel[] shipmentRowPanel;
		private JFormattedTextField[] upc;
		private JFormattedTextField[] quantity;
		private JFormattedTextField[] price;
		
		private int maxShipItems;
		
		private JPanel deliveryContainer;
		private JFormattedTextField deliveryReceiptID;
		
		
        public ShipView (String title, MainView parent) {
               
                //default setup methods defined in View.java
        		setParent(parent);
                setContainer();
                setTitle(title);
               
                init();
        }
       
        private void init() {
        	
        	maxShipItems = 10;
        	
        
        	shipmentRowPanel = new JPanel[maxShipItems];
    		upc = new JFormattedTextField[maxShipItems];
    		quantity = new JFormattedTextField[maxShipItems];
    		price = new JFormattedTextField[maxShipItems];
    		
        	container = new JPanel(new GridLayout(3,1));
        	
        	createSupplierPanel();
        	
        	createShipPanel();
        	
        	createShipment();
        	
        	createDelivery();
        	
        	container.add(shipPanel);
        	container.add(shipmentScrollPane);
        	container.add(deliveryContainer);
        	
        	wrapperContainer = new JPanel(new BorderLayout());
        	wrapperContainer.add(supplierContainer, BorderLayout.NORTH);
        	wrapperContainer.add(container, BorderLayout.CENTER);
        	       	
        	this.getContainer().add(wrapperContainer, BorderLayout.CENTER);
        
        	wrapperContainer.validate();
        	
        } //INIT
        
        
        
        
        
        
        
        private void createSupplierPanel () {
    		
    		supplierContainer = new JPanel(new GridLayout(2,1));
    		supplierContainer.setBackground(bg3);
    		
    		supplierPanel = new Panel(new GridLayout(1,4));
    		
    		Label label = new Label("Add or Remove Supplier");
    		label.setFont(font2);
    		supplierContainer.add(label);
    		
    //SUB PANEL 1
    		
    		Panel subSupplierPanel = new Panel();
    		subSupplierPanel.setLayout(new GridLayout(2,1));
    				
    		Label categoryLabel = new Label("Suplier ID:");
    		categoryLabel.setFont(font1);
    		subSupplierPanel.add(categoryLabel);
    		
    		searchSubPanelTitle = new JFormattedTextField();
    		searchSubPanelTitle.setFont(font1);
    		searchSubPanelTitle.setEditable(true);
    		subSupplierPanel.add(searchSubPanelTitle);
    		
    		supplierPanel.add(subSupplierPanel);
    
//Add BUTTON
    		
    		Button add = new Button("Add Supplier");
    		add.addActionListener(new addSupplierAction());
    		supplierPanel.add(add);
    		
    		
//Remove BUTTON
    		
    		Button remove = new Button("Remove Supplier");
    		remove.addActionListener(new removeSupplierAction());
    		supplierPanel.add(remove);
    		
    		supplierContainer.add(supplierPanel);
    		
    		
    	}//Supplier PANEL
        
        
        private void createShipPanel () {
    		
    		shipPanel = new Panel();
    		shipPanel.setBackground(bg3);
    		shipPanel.setLayout(new GridLayout(2,1));
    		
    		//label for Search Panel
    		Label purchaseLabel = new Label("Specify Supplier For Shipment: ");
    		purchaseLabel.setFont(font2);
    		shipPanel.add(purchaseLabel);
    		
    		shipInfoPanel = new Panel(new GridLayout(1,6));
    		shipPanel.add(shipInfoPanel);
    		
    //SUB PANEL 1
    		
    		Panel supplierContainer = new Panel();
    		supplierContainer.setLayout(new GridLayout(2,1));
    				
    		Label categoryLabel = new Label("Supplier Name:");
    		categoryLabel.setFont(font1);
    		supplierContainer.add(categoryLabel);
    		
    		supplierName = new JFormattedTextField();
    		supplierName.setFont(font1);
    		supplierName.setEditable(true);
    		supplierContainer.add(supplierName);
    		
    		shipInfoPanel.add(supplierContainer);
    		
    //SUB PANEL 2
    		
    		Panel subPanel2 = new Panel();
    		subPanel2.setLayout(new GridLayout(2,1));
    				
    		Label titleLabel = new Label("Store Name:");
    		titleLabel.setFont(font1);
    		subPanel2.add(titleLabel);
    		
    		storeName = new JFormattedTextField();
    		storeName.setFont(font1);
    		storeName.setEditable(true);
    		subPanel2.add(storeName);
    		
    		shipInfoPanel.add(subPanel2);
    		
    //SUB PANEL 3
    		
    		Panel subPanel3 = new Panel();
    		subPanel3.setLayout(new GridLayout(2,1));
    				
    		Label singerLabel = new Label("Year:");
    		singerLabel.setFont(font1);
    		subPanel3.add(singerLabel);
    		
    		shipYear = new JFormattedTextField();
    		shipYear.setFont(font1);
    		shipYear.setEditable(true);
    		subPanel3.add(shipYear);
    		
    		shipInfoPanel.add(subPanel3);
    		
    //SUB PANEL 4
    		
    		Panel subPanel4 = new Panel();
    		subPanel4.setLayout(new GridLayout(2,1));
    				
    		Label quantityLabel = new Label("Month:");
    		quantityLabel.setFont(font1);
    		subPanel4.add(quantityLabel);
    		
    		shipMonth = new JFormattedTextField();
    		shipMonth.setFont(font1);
    		shipMonth.setEditable(true);
    		subPanel4.add(shipMonth);
    		
    		shipInfoPanel.add(subPanel4);
    		
 //SUB PANEL 5
    		
    		Panel subPanel5 = new Panel();
    		subPanel5.setLayout(new GridLayout(2,1));
    				
    		Label label = new Label("Day:");
    		label.setFont(font1);
    		subPanel5.add(label);
    		
    		shipDay = new JFormattedTextField();
    		shipDay.setFont(font1);
    		shipDay.setEditable(true);
    		subPanel5.add(shipDay);
    		
    		shipInfoPanel.add(subPanel5);
    		
    		//BUTTON
    		
    		Button makeShipment = new Button("Make Shipment");
        	makeShipment.addActionListener(new shipmentAction());
        	shipInfoPanel.add(makeShipment);
    		
    	}//SEARCH PANEL

        
        
        
        private void createShipment () {
        	
        	shipmentPanel = new JPanel(new GridLayout(maxShipItems + 2,1));
    	     	
        	JPanel labels = new JPanel(new GridLayout(1,3));
        	
        	Label categoryLabel = new Label("UPC:");
    		categoryLabel.setFont(font1);
    		labels.add(categoryLabel);
    		
    		Label titleLabel = new Label("Quantity:");
    		titleLabel.setFont(font1);
    		labels.add(titleLabel);
    		
    		Label singerLabel = new Label("Supplier Price:");
    		singerLabel.setFont(font1);
    		labels.add(singerLabel);
    		
    		shipmentPanel.add(labels);
        	
        	for (int i = 0; i < maxShipItems; i++) {
        		
        		//SUB PANEL 1
        		
        		shipmentRowPanel[i] = new JPanel(new GridLayout(1,3));
        		
        		upc[i] = new JFormattedTextField();
        		upc[i].setFont(font1);
        		upc[i].setEditable(true);
        		shipmentRowPanel[i].add(upc[i]);
        		
        //SUB PANEL 2
        		
        		quantity[i] = new JFormattedTextField();
        		quantity[i].setFont(font1);
        		quantity[i].setEditable(true);
        		shipmentRowPanel[i].add(quantity[i]);
        		
        //SUB PANEL 3
        		
        		price[i] = new JFormattedTextField();
        		price[i].setFont(font1);
        		price[i].setEditable(true);
        		shipmentRowPanel[i].add(price[i]);
        		
        		shipmentPanel.add(shipmentRowPanel[i]);
        	}
        	
        	shipmentScrollPane = new JScrollPane(shipmentPanel);
        }
        
        private void createDelivery() {
    		
    		deliveryContainer = new JPanel();
    		deliveryContainer.setBackground(bg3);
    		deliveryContainer.setLayout(new GridLayout(2,1));
    		
    		Label label = new Label("Process a Delivery");
    		label.setFont(font2);
    		deliveryContainer.add(label);
    		
    		Panel rowPanelSub = new Panel(new GridLayout(1,2));
    		deliveryContainer.add(rowPanelSub);
    		
    //SUB PANEL 1
    		
    		Panel sub1 = new Panel();
    		sub1.setLayout(new GridLayout(2,1));
    				
    		Label categoryLabel = new Label("Receipt ID:");
    		categoryLabel.setFont(font1);
    		sub1.add(categoryLabel);
    		
    		deliveryReceiptID = new JFormattedTextField();
    		deliveryReceiptID.setFont(font1);
    		deliveryReceiptID.setEditable(true);
    		sub1.add(deliveryReceiptID);
    		
    		rowPanelSub.add(sub1);
    		
    //Search BUTTON
    	
    		Button deliveryButton = new Button("Process Delivery");
    		deliveryButton.addActionListener(new deliveryAction());
    		rowPanelSub.add(deliveryButton);
    		
    		rowPanelSub.validate();
    		
    	}//SEARCH PANEL
        
        
///////////////////////////////////////////////////////////////////////////////////
        ////////////////////CONTROLLER CODE//////////////////////
//////////////////////////////////////////////////////////////////////////////////
        
        
        //PUBLIC FUNCTIONS FOR LISTENERS
        
//ADD SUPPLIER
        
        public void addSupplier () {
        	
        	
        	
        }
        
 //REMOVE SUPPLIER
        
        public void removeSupplier () {
        	
        	
        	
        }
        
 //DO A SHIPMENT
        
        public void shipment () {
        	
        	
        	
        }
        
 //PROCESS DELIVERY
        
        public void delivery () {
        	
        	
        	
        }
        
        
        
        
        
        
        
      //  LISTENERSSSSSSSSS
      
        
        public class addSupplierAction implements ActionListener { 
    		public void actionPerformed(ActionEvent e) {
    			addSupplier();
    			MainView.errorDialog("Reports Updated");
    		}
    	}// END LISTENER
        
        
        public class removeSupplierAction implements ActionListener { 
    		public void actionPerformed(ActionEvent e) {
    			removeSupplier();
    			MainView.errorDialog("Reports Updated");
    		}
    	}// END LISTENER
      

        public class shipmentAction implements ActionListener { 
    		public void actionPerformed(ActionEvent e) {
    			shipment();
    			MainView.errorDialog("Shipment Sent");
    		}
    	}// END LISTENER
        
        public class deliveryAction implements ActionListener { 
    		public void actionPerformed(ActionEvent e) {
    			delivery();
    			MainView.errorDialog("Shipment Sent");
    		}
    	}// END LISTENER
        
    
  
}

