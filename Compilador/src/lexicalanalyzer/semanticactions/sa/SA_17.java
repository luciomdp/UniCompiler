package lexicalanalyzer.semanticactions.sa;

import java.security.SecureRandom;
import java.util.UUID;
import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.enums.EUse;

public class SA_17 implements ISemanticAction{
    private final Long LONGITUD_CADENA = 8L;
    //AS. 17
    @Override
    public void execute(SAParam params) {
        
        ConfigurationParams.symbolTable.insert(generarCadenaRandom(),new SymbolTableItem(ETokenType.STRING_CONST,EDataType.STRING, EUse.CONST,params.getLexema().toString()));
    }

    private String generarCadenaRandom() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder cadenaRandom = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < LONGITUD_CADENA; i++) {
            int indice = random.nextInt(caracteres.length());
            cadenaRandom.append(caracteres.charAt(indice));
        }

        return cadenaRandom.toString();
    }
    
}
