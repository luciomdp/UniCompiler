package components;
import java.awt.*;
import lexicalanalyzer.LexicalAnalizer;
import objects.ConfigurationParams;
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
            appendData("[ "+lexicalAnalizer.getLexema()+ " , "+ (ConfigurationParams.VIEW_TOKEN_NUMBER?(Integer.valueOf(tokenValue).toString() + " "):"") + ETokenType.getDescription(tokenValue) + " ]\n");
        else
            appendData((ConfigurationParams.VIEW_TOKEN_NUMBER?(Integer.valueOf(tokenValue).toString() +" \"" + ETokenType.getDescription(tokenValue) + "\""):ETokenType.getDescription(tokenValue)) + "\n");
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
            printToken(currentToken.intValue());
        }while(currentToken != ETokenType.END_OF_FILE.getValue());
        
        appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");
    }
}
