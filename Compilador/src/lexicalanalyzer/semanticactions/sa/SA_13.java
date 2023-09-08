package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_13 implements ISemanticAction{
    /*
        A.S 13:
            - Agregar ">" al string
            - Agregar caracteres distinto de ">" al string
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(">");
        params.getLexema().append(params.getLastReadedCharacter());
    }
    
}
