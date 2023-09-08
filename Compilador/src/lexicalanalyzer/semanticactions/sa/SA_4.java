package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
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
                params.getSymbolTable().insert(params.getLexema().toString(), ETokenType.INT_CONST);
                params.setTokenType(ETokenType.INT_CONST);
            }else{// es ulongint
                params.getSymbolTable().insert(params.getLexema().toString(), ETokenType.ULONGINT_CONST);
                params.setTokenType(ETokenType.ULONGINT_CONST);
            }  
        }
        
        params.setReadNewCharacter(false);
    }
  
}
