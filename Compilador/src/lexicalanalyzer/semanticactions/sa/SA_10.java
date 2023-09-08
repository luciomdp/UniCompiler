package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_10 implements ISemanticAction{
    /*
        A.S 10:
            - Agregar el "=" o ">" al string 
     */
    @Override
    public void execute(SAParam params) {
        switch(params.getLexema().toString()) {
            case "=": params.setTokenType(ETokenType.MENOR_IGUAL);
            break;
            case ">": params.setTokenType(ETokenType.NO_IGUAL);
            break;
            // default: params.setTokenType(ETokenType.ERROR); --> PARA MI NO VA, NUNCA VA A CAER EN ESTA AS SI NO ES UN ">" o "="
        }
        params.setReadNewCharacter(true);
    }
    
}
