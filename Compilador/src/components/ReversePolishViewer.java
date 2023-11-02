package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

    private static String title = "Polaca inversa";
    private static final Color BACKGROUND_BORDER = new Color(249,244,161,255),BACKGROUND_PANEL = new Color(118,97,141,255);

    private JPanel verticalLayoutContainer;
    private JPanel reversePolishContainer;
    private Map<String,ReversePolishPanel> reversePolishPanels;
        

    public ReversePolishViewer() {
        reversePolishPanels = new HashMap<String,ReversePolishPanel>();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_BORDER);
        setBorder(new LineBorder(BACKGROUND_BORDER, 2, true));
        setVisible(true);

        verticalLayoutContainer = new JPanel();
        verticalLayoutContainer.setBackground(BACKGROUND_BORDER);
        verticalLayoutContainer.setLayout(new BoxLayout(verticalLayoutContainer, BoxLayout.Y_AXIS));
        verticalLayoutContainer.setPreferredSize(new Dimension(700, 515));
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        reversePolishContainer = new JPanel();
        reversePolishContainer.setBackground(BACKGROUND_PANEL);
        reversePolishContainer.setLayout(new BoxLayout(reversePolishContainer, BoxLayout.Y_AXIS));
        reversePolishContainer.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("<< " + title + " >>");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelTitle = new JPanel();
        panelTitle.setLayout(new BorderLayout());
        panelTitle.setBackground(new Color(178, 223, 250,150));
        panelTitle.setPreferredSize(new Dimension(700, 15));
        panelTitle.add(lblTitle,BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(reversePolishContainer);
        scroll.setPreferredSize(new Dimension(700, 500));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        verticalLayoutContainer.add(panelTitle);
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 10)));  
        verticalLayoutContainer.add(scroll);

        add(verticalLayoutContainer);
    }

    public void addNewStructure(String title) {
        ReversePolishPanel reversePolishPanel = new ReversePolishPanel(title);
        reversePolishContainer.add(reversePolishPanel);
        reversePolishContainer.add(Box.createRigidArea(new Dimension(0, 25)));  
        reversePolishPanels.put(title, reversePolishPanel);
    }

    public void updateTable(String reversePolish, String newVal,int index) {
        reversePolishPanels.get(reversePolish).updateTable(newVal, index);
    }

}
