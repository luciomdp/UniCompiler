package components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public abstract class AbstractPanelViewer extends JPanel{

    private JTextPane txtArea;
    private StyledDocument styledDocument;

    public AbstractPanelViewer(Color colorPanel,Color colorComponents) {
        txtArea = new JTextPane();
        txtArea.setPreferredSize(new Dimension(700, 500));
        txtArea.setEditable(false);
        txtArea.setBackground(colorComponents);
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 12);
        txtArea.setFont(f);
        
        styledDocument = txtArea.getStyledDocument();
        
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);
        
        JScrollPane panel = new JScrollPane(txtArea);
        add(panel);
        setBackground(colorPanel);
    }

    public void appendData(String data) {
        appendText(data, Color.BLACK); // Color negro para appendData
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
