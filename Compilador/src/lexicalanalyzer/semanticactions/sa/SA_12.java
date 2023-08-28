package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_12 implements ISemanticAction{
    /*
        A.S 12:
        - Inicializar string (se reservan 2 caracteres para el operador de comparador >)
        - Agregamos un ">" al string.
     */
    @Override
    public void execute(SAParam params) {
        if (params.getLastReadedCharacter() == '>')
            params.getLexema().append(params.getLastReadedCharacter());
        else
            params.setTokenType(ETokenType.ERROR);
    }
    
}
