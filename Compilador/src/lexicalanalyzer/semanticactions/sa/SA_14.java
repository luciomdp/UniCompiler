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
            params.setReadNewCharacter(true); 
        }
        else{
            params.getLexema().append("="); 
            params.setMessageWarning("WARNING: luego del : siempre debe ir un = para representar la asignación");
            params.setReadNewCharacter(false); 
        } 
        params.setTokenType(ETokenType.ASIGNACION); 
        
    }
    
}
