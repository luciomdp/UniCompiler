package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_10 implements ISemanticAction{
    /*
        A.S 10:
            - Agregar "=" al string 
     */
    //Me parece que no hay accion semantica 10, o simplemente es reconocer token asignacion
    @Override
    public void execute(SAParam params) {
        if (params.getLastReadedCharacter() == '='){
            params.getLexema().append(params.getLastReadedCharacter());
            params.setTokenType(ETokenType.ASIGNACION); o params.setTokenType(ETokenType.MAYOR_IGUAL);
        }   
        else
            params.setTokenType(ETokenType.ERROR);
        params.setReadNewCharacter(true);
    }
    
}
