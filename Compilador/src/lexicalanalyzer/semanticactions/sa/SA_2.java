package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_2 implements ISemanticAction{

    /*
     A.S 2:
        - Agregar letra o digito o arroba al string
     */
    @Override
    public void execute(SAParam params) {
        if (params.getLexema().length() < 25)
            params.getLexema().append(params.getLastReadedCharacter());  
        else {
            params.setMessageWarning(new StringBuilder("WARNING: La cadena ha alzanzado un tope de 25 caracteres"));
        }
    }
    
}
