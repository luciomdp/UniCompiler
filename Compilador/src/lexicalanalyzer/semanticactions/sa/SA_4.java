package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_4 implements ISemanticAction{
    /*
     A.S 4:
        - Inicializar string para la constante
        - Agregar dígito al string
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(params.getLastReadedCharacter());  
    }
    
}
