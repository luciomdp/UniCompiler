package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ReversePolishViewer extends JPanel{
    private static final Color BACKGROUND_BORDER = new Color(118,97,141,255),BACKGROUND_PANEL = new Color(157,142,173,255), BACKGROUND_COMPONENTS = new Color(118,97,141,255), BACKGROUND_HEADER = new Color(241,171,192,255);

    private JPanel verticalLayoutContainer;
    private Map<String,ReversePolishPanel> reversePolishPanels;
        

    public ReversePolishViewer() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_BORDER);
        setBorder(new LineBorder(BACKGROUND_BORDER, 10));
        setVisible(true);
        verticalLayoutContainer = new JPanel();
        verticalLayoutContainer.setBackground(BACKGROUND_PANEL);
        verticalLayoutContainer.setLayout(new BoxLayout(verticalLayoutContainer, BoxLayout.Y_AXIS));
        add(new JScrollPane(verticalLayoutContainer), BorderLayout.CENTER);
        reversePolishPanels = new HashMap<String,ReversePolishPanel>();
    }

    public void addNewStructure(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        titleLabel.setHorizontalAlignment(JLabel.CENTER); 
        verticalLayoutContainer.add(titleLabel, BorderLayout.CENTER);
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 5)));  
         
        ReversePolishPanel reversePolishPanel = new ReversePolishPanel();
        verticalLayoutContainer.add(reversePolishPanel);
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 25)));  

        reversePolishPanels.put(title, reversePolishPanel);
    }

    public void updateTable(String reversePolish, String newVal,int index) {
        reversePolishPanels.get(reversePolish).updateTable(newVal, index);
    }

}
