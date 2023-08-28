package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_16 implements ISemanticAction{
    /*
        A.S 16:
            - Agregar el "=" o ">" al string 
     */
    @Override
    public void execute(SAParam params) {
        switch(params.getLexema().toString()) {
            case "=": params.setTokenType(ETokenType.MENOR_IGUAL);
            break;
            case ">": params.setTokenType(ETokenType.NO_IGUAL);
            break;
            default: params.setTokenType(ETokenType.ERROR);
        }
        params.setReadNewCharacter(true);
    }
    
}
