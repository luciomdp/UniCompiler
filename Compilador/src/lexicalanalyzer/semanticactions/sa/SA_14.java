package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_14 implements ISemanticAction{
    /*
        A.S 14:
        - Inicializar string (se reservan 2 caracteres para el operador de comparador <)
        - Agregamos un "<" al string.
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(params.getLastReadedCharacter());
    }    
    
}
