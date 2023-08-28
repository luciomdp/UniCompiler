package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_3 implements ISemanticAction{

    /*
     A.S 3:
        - Agregar letra o digito o arroba al string
     */
    @Override
    public void execute(SAParam params) {
        if (params.getLexema().length() < 25)
            params.getLexema().append(params.getLastReadedCharacter());   
    }
    
}
