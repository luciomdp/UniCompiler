package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_12 implements ISemanticAction{
    /*
        A.S 12:
            - Agregar caracter distinto de ">" al string
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(params.getLastReadedCharacter());
    }
    
}
