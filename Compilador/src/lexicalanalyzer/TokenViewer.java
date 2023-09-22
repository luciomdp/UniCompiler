package lexicalanalyzer;
import java.awt.*;

import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import objects.enums.ETokenType;

public class TokenViewer extends JPanel {

    private final Color BACKGROUND_PANEL = new Color(90, 75, 181, 255), BACKGROUND_COMPONENTS = new Color(47, 159, 241, 255);
    private JTextPane txtArea;
    private StyledDocument styledDocument;
    private LexicalAnalizer lexicalAnalizer;

    public TokenViewer(LexicalAnalizer lexicalAnalizer) {
        this.lexicalAnalizer = lexicalAnalizer;
        txtArea = new JTextPane();
        txtArea.setPreferredSize(new Dimension(700, 500));
        txtArea.setEditable(false);
        txtArea.setBackground(BACKGROUND_COMPONENTS);
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 12);
        txtArea.setFont(f);
        
        styledDocument = txtArea.getStyledDocument();
        
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        styledDocument.setParagraphAttributes(0, styledDocument.getLength(), center, false);
        
        JScrollPane panel = new JScrollPane(txtArea);
        add(panel);
        setBackground(BACKGROUND_PANEL);
        
    }

    public void printToken(int tokenValue) {

        if(ETokenType.getLexemeTokenTypes().contains(Long.valueOf(tokenValue)))
            appendData("[ "+lexicalAnalizer.getLexema()+ " , "+ Integer.valueOf(tokenValue).toString() + "(" + ETokenType.getDescription(tokenValue) + ") ]\n");
        else
            appendData(Integer.valueOf(tokenValue).toString() + "(" + ETokenType.getDescription(tokenValue) + ")\n");
        appendWarning(lexicalAnalizer.getWarningMessage()!=""?lexicalAnalizer.getWarningMessage()+"\n":"");
        appendError(lexicalAnalizer.getErrorMessage()!=""?lexicalAnalizer.getErrorMessage()+"\n":"");
        
        try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();} //Espera (animación)
        
        if(tokenValue == ETokenType.END_OF_FILE.getValue()) //Si era último token
            appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");
    }

    public void parseAll() {
        Long currentToken;
        do {
            currentToken = lexicalAnalizer.getToken();
            if (currentToken!= ETokenType.IGNORE.getValue())
                if(ETokenType.getLexemeTokenTypes().contains(currentToken))
                    appendData("[ "+lexicalAnalizer.getLexema()+ " , "+ currentToken.toString() + "(" + ETokenType.getDescription(currentToken.intValue()) + ") ]\n");
                else
                    appendData(currentToken.toString() + "(" + ETokenType.getDescription(currentToken.intValue()) + ")\n");
                appendWarning(lexicalAnalizer.getWarningMessage()!=""?lexicalAnalizer.getWarningMessage()+"\n":"");
                appendError(lexicalAnalizer.getErrorMessage()!=""?lexicalAnalizer.getErrorMessage()+"\n":"");
            
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(currentToken != ETokenType.END_OF_FILE.getValue());
        
        appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");
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
