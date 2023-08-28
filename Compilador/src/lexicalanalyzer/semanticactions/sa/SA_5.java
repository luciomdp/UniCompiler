package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
public class SA_5 implements ISemanticAction{
    
    //A.S 5:
    // - Agregar dígito al string
    @Override
    public void execute(SAParam params) {
        params.getLexema().append(params.getLastReadedCharacter());        
    }
    
}
