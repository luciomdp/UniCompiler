package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
public class SA_3 implements ISemanticAction{

    /*
     A.S 3:
        - Inicializar string para la constante
        - inicializar identificador
        - inicializar digito
        - Agregar dígito,":","<",">" al string
     */
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(params.getLastReadedCharacter());  
    }
    
}
