package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class ReversePolishViewer extends JPanel{
    private static final Color BACKGROUND_BORDER = new Color(249,244,161,255),BACKGROUND_PANEL = new Color(118,97,141,255);

    private JPanel verticalLayoutContainer;
    private Map<String,ReversePolishPanel> reversePolishPanels;
        

    public ReversePolishViewer() {
        reversePolishPanels = new HashMap<String,ReversePolishPanel>();

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_BORDER);
        setBorder(new LineBorder(BACKGROUND_BORDER, 2, true));
        setVisible(true);
        verticalLayoutContainer = new JPanel();
        verticalLayoutContainer.setBackground(BACKGROUND_PANEL);
        verticalLayoutContainer.setLayout(new BoxLayout(verticalLayoutContainer, BoxLayout.Y_AXIS));
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(verticalLayoutContainer);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);
    }

    public void addNewStructure(String title) {
        ReversePolishPanel reversePolishPanel = new ReversePolishPanel(title);
        verticalLayoutContainer.add(reversePolishPanel);
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 25)));  
        reversePolishPanels.put(title, reversePolishPanel);
    }

    public void updateTable(String reversePolish, String newVal,int index) {
        reversePolishPanels.get(reversePolish).updateTable(newVal, index);
    }

}
