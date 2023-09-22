package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
public class SA_4 implements ISemanticAction{
    //-32768 y 32767 --> integer
    // 4294967295 --> ulongint
    /*
     A.S 4:
        - Devolver a la entrada el último carácter leído
        - Verificar rango de la constante
        - Alta en la TS
        - Devolver CTE (lexico) + Punt TS (id token)
     */
    @Override
    public void execute(SAParam params) {
        Long longValue = Long.parseUnsignedLong(params.getLexema().toString());

        //Mientras no pasemos el límite máximo permitido para una cte de tipo ulongint (4294967295)
        //El negativo no importa porque no podemos definir en esta etapa si es positivo o negativo nuestro valor cte
        if(longValue <= Long.valueOf(4294967295L)) {
            if (longValue <= Long.valueOf(32767)){// es integer
                params.getSymbolTable().insert(params.getLexema().toString(), new SymbolTableItem(ETokenType.NUMERIC_CONST, EDataType.INTEGER));
                params.setTokenType(ETokenType.NUMERIC_CONST);
            }else{// es ulongint
                params.getSymbolTable().insert(params.getLexema().toString(), new SymbolTableItem(ETokenType.NUMERIC_CONST, EDataType.ULONGINT));
                params.setTokenType(ETokenType.NUMERIC_CONST);
            }  
        }else 
            params.setMessageError("ERROR: ulongint soporta hasta 4294967295");
        
        params.setReadNewCharacter(false);
    }
  
}
