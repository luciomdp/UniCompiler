package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_17 implements ISemanticAction{
    /*
        A.S 17:
            - Inicializar string dinamicamente para almacenar comentario 
     */
    @Override
    public void execute(SAParam params) {
        params.setTokenType(ETokenType.CONSTANTE_CADENA);
    }
    
}
