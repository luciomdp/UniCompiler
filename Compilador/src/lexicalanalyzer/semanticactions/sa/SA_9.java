package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_9 implements ISemanticAction{
    /*
    A.S 9:
        - Inicializar string (se reservan 2 caracteres para el operador de asignación)
        - Agregar ":" al string 
     */
    //Me parece que no hay accion semantica 9
    @Override
    public void execute(SAParam params) {
        if (params.getLastReadedCharacter() == ':')
            params.getLexema().append(params.getLastReadedCharacter());
        else
            params.setTokenType(ETokenType.ERROR);
    }
    
}
