package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_8 implements ISemanticAction{
    /*
    A.S 8:
        - Lanzar error (ya que no vino un "#" y ningún otr token válido podría seguir de un solo "#") 
        y continuar el reconocimiento ignorando el "#"
     */
    @Override
    public void execute(SAParam params) {
        params.setTokenType(ETokenType.ERROR);
    }
    
}
