package views;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

public class ResultSet {

        private static final long serialVersionUID = 1L;
        
        private JPanel container;
        private JPanel headers;
        private JPanel innerContainer;
        
        private Color bg1;
        private Color bg2;
        private Color bg3;

        public ResultSet (Object header, Object[] titles, Object[][] results, int cellWidth) {
                int width = results[0].length * cellWidth;
                int height = results.length * 24;
                
                bg1 = new Color(164,164,164);
                bg2 = new Color(196,196,196);
                bg3 = new Color(223,223,223);
                
                container = new JPanel();
                container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
                
                JLabel label = new JLabel((String)header);
                label.setBackground(bg1);
                label.setFont(new Font("Arial", Font.BOLD, 24));
                container.add(label);
                
                setTitles(titles);
                
                innerContainer = new JPanel();
                innerContainer.setPreferredSize(new Dimension(width,height));           
                innerContainer.setLayout(new GridLayout(results.length, results[0].length));
                container.add(innerContainer);
                
                updateResults(results);
                
                Dimension fillerSize = new Dimension(32,Short.MAX_VALUE);
                container.add(new Box.Filler(fillerSize, fillerSize, fillerSize));
                
        }
        
        public void setTitles (Object[] titles) {
                
                headers = new JPanel();
                headers.setLayout(new GridLayout(1,titles.length));
                container.add(headers);
                
                for (int i = 0; i < titles.length; i++) {
                        JLabel title = new JLabel((String)titles[i]);
                        title.setBackground(bg3);
                        title.setFont(new Font("Arial", Font.BOLD, 16));
                        headers.add(title);
                }
        }
        
        public void updateResults (Object[][] results) {
                
                innerContainer.removeAll();
                
                for (int i = 0; i < results.length; i++) {
                        for (int j = 0; j < results[0].length; j++) {
                                
                                JTextArea thisText = new JTextArea((String)results[i][j]);
                                if (i % 2 == 0)
                                        thisText.setBackground(bg2);
                                else
                                        thisText.setBackground(bg3);
                                thisText.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
                                thisText.setEditable(false);
                                innerContainer.add(thisText);
                                
                        }
                }

                container.validate();
                
        }
        
        public JPanel getContainer() {
                return container;               
        }
}