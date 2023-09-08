package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_14 implements ISemanticAction{
    /*
        A.S 14:
            - Agregar "=" al string 
     */
    //Me parece que no hay accion semantica 10, o simplemente es reconocer token asignacion
    @Override
    public void execute(SAParam params) {
        if (params.getLastReadedCharacter() == '='){
            params.getLexema().append(params.getLastReadedCharacter());
            params.setTokenType(ETokenType.ASIGNACION); 
        }   
        params.setReadNewCharacter(true);
    }
    
}
