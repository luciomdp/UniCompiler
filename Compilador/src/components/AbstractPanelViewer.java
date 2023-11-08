package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public abstract class AbstractPanelViewer extends JPanel{

    private JPanel verticalLayoutContainer;
    private JTextPane txtArea;
    private StyledDocument styledDocument;

    public AbstractPanelViewer(String title, Color colorPanel, Color colorComponents, int alignment) {

        verticalLayoutContainer = new JPanel();
        verticalLayoutContainer.setBackground(colorPanel);
        verticalLayoutContainer.setLayout(new BoxLayout(verticalLayoutContainer, BoxLayout.Y_AXIS));
        verticalLayoutContainer.setPreferredSize(new Dimension(700, 515));
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 10)));
       
        JLabel lblTitle = new JLabel("<< " + title + " >>");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        txtArea = new JTextPane();
        txtArea.setPreferredSize(new Dimension(700, 500));
        txtArea.setEditable(false);
        txtArea.setBackground(colorComponents);
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 12);
        txtArea.setFont(f);
        
        styledDocument = txtArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, alignment);
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);

        JScrollPane panel = new JScrollPane(txtArea);

        JPanel panelTitle = new JPanel();
        panelTitle.setLayout(new BorderLayout());
        panelTitle.setBackground(new Color(178, 223, 250,150));
        panelTitle.setPreferredSize(new Dimension(700, 15));
        panelTitle.add(lblTitle,BorderLayout.CENTER);

        verticalLayoutContainer.add(panelTitle);
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 10)));  
        verticalLayoutContainer.add(panel);

        add(verticalLayoutContainer);
        setBackground(colorPanel);
    }

    public void appendData(String data) {
        appendText(data, Color.WHITE); // Color blanco para appendData
    }

    public void appendWarning(String data) {
        appendText(data, Color.YELLOW); // Color rojo para appendError
    }

    public void appendError(String data) {
        appendText(data, Color.RED); // Color rojo para appendError
    }

    private void appendText(String text, Color color) {
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setForeground(attributes, color);
        
        int length = styledDocument.getLength();
        try {
            styledDocument.insertString(length, text, attributes);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
}
