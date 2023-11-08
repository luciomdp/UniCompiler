package lexicalanalyzer.semanticactions.sa;

import java.util.UUID;
import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.enums.EUse;

public class SA_17 implements ISemanticAction{

    //AS. 17
    @Override
    public void execute(SAParam params) {
        
        ConfigurationParams.symbolTable.insert(UUID.randomUUID().toString(),new SymbolTableItem(ETokenType.STRING_CONST,EDataType.STRING, EUse.CONST,params.getLexema().toString()));
    }
    
}
