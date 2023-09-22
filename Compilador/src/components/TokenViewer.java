package components;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import lexicalanalyzer.LexicalAnalizer;
import objects.enums.ETokenType;

public class TokenViewer extends AbstractPanelViewer {

    private static final Color BACKGROUND_PANEL = new Color(38,62,86,255), BACKGROUND_COMPONENTS = new Color(48,78,108,255);
    private LexicalAnalizer lexicalAnalizer;

    public TokenViewer(LexicalAnalizer lexicalAnalizer) {
        super(BACKGROUND_PANEL,BACKGROUND_COMPONENTS);
        this.lexicalAnalizer = lexicalAnalizer;
        appendData("--------------------------- << Comienzo del análisis léxico >> ---------------------------\n");
        appendData("------------- << [Lexema,Token] >> -------------\n");
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
}
