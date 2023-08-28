package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_19 implements ISemanticAction{
    /*
        A.S 19:
            - Agregar ">" al string
            - Agregar y el caracter distinto de ">" al string
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(">");
        params.getLexema().append(params.getLastReadedCharacter());
    }
    
}
