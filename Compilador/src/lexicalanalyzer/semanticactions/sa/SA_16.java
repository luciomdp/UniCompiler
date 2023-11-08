package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_16 implements ISemanticAction{

    @Override
    public void execute(SAParam params) {
        params.setMessageError("ERROR: caracter inválido");
        params.setTokenType(ETokenType.IGNORE);
    }
    
}
