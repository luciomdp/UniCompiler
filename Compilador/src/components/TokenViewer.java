package components;
import java.awt.*;
import objects.ConfigurationParams;
import objects.enums.ETokenType;

public class TokenViewer extends AbstractPanelViewer {

    private static final Color BACKGROUND_PANEL = new Color(178,231,203,255), BACKGROUND_COMPONENTS = new Color(94,211,152,255);

    public TokenViewer() {
        super(BACKGROUND_PANEL,BACKGROUND_COMPONENTS);
        appendData("--------------------------- << Comienzo del análisis léxico >> ---------------------------\n");
        appendData("------------- << [Lexema,Token] >> -------------\n");
    }

    public void printToken(int tokenValue) {
        if (tokenValue != ETokenType.IGNORE.getValue()){
            if(ETokenType.getLexemeTokenTypes().contains(Long.valueOf(tokenValue)))
                appendData("[ "+ConfigurationParams.lexicalAnalizer.getLexema()+ " , "+ (ConfigurationParams.VIEW_TOKEN_NUMBER?(Integer.valueOf(tokenValue).toString() + " "):"") + ETokenType.getDescription(tokenValue) + " ]\n");
            else
                appendData((ConfigurationParams.VIEW_TOKEN_NUMBER?(Integer.valueOf(tokenValue).toString() +" \"" + ETokenType.getDescription(tokenValue) + "\""):ETokenType.getDescription(tokenValue)) + "\n");
        }
        appendWarning(ConfigurationParams.lexicalAnalizer.getWarningMessage()!=""?ConfigurationParams.lexicalAnalizer.getWarningMessage()+"\n":"");
        appendError(ConfigurationParams.lexicalAnalizer.getErrorMessage()!=""?ConfigurationParams.lexicalAnalizer.getErrorMessage()+"\n":"");
        
        try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();} //Espera (animación)
        
        if(tokenValue == ETokenType.END_OF_FILE.getValue()) //Si era último token
            appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");
    }

    public void parseAll() {
        Long currentToken;
        do {
            currentToken = ConfigurationParams.lexicalAnalizer.getToken();
            printToken(currentToken.intValue());
        }while(currentToken != ETokenType.END_OF_FILE.getValue());
    }
}
