package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.ETokenType;
public class SA_1 implements ISemanticAction{

    public SA_1 (){

    }
    //  A.S 1:
    // - Devolver a la entrada el último carácter leído
    // - Buscar en la TS
    //     * Si está, +Si es PR, devolver la Palabra Reservada +Si no, Devolver ID + Punt TS
    //     * Si no está, +Alta en la TS
    // -Devolver ID + Punt TS
    @Override
    public void execute(SAParam params) {
        if (!ConfigurationParams.symbolTable.contains(params.getLexema().toString())) 
            ConfigurationParams.symbolTable.insert(params.getLexema().toString(),new SymbolTableItem(ETokenType.ID, null));
        params.setTokenType(ConfigurationParams.symbolTable.lookup(params.getLexema().toString()).getTokenType());   
        params.setReadNewCharacter(false);
    }
    
}
