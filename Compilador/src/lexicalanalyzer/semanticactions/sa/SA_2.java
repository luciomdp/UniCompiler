package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;

public class SA_2 implements ISemanticAction{

    /*
     A.S 2:
        - Inicializar string (se reserva 25 caracteres para identificadores)
        - Agregar letra o arroba al string
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(params.getLastReadedCharacter());
    }
    
}
