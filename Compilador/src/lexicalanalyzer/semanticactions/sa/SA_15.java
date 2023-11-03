package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.ConfigurationParams;
import objects.enums.ETokenType;

public class SA_15 implements ISemanticAction{

    @Override
    public void execute(SAParam params) {
        ConfigurationParams.generateCodeComponent.generateAssemblerCode();
        params.setTokenType(ETokenType.END_OF_FILE);
    }
    
}
