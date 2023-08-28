package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_11 implements ISemanticAction{
    /*
        A.S 11:
            - Lanzar error (ya que no vino un "=" y ningún otr token válido podría seguir del ":") 
            y continuar el reconocimiento ignorando el ":"
     */
    //Me parece que no hay accion semantica 11, incluida en la 10
    @Override
    public void execute(SAParam params) {
            params.setTokenType(ETokenType.ERROR);
    }
    
}
