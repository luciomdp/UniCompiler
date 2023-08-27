package AccionesSemánticas;

import java.math.BigInteger;

import objects.TipoToken;

public class VerificaRangoCteYAltaEnTs implements AcciónSemántica{

    /*
     A.S 6:
        - Devolver a la entrada el último carácter leído
        - Verificar rango de la constante
        - Alta en la TS
        - Devolver CTE (lexico) + Punt TS (id token)
     */
    @Override
    public void ejecutar(AccionSemáticaParametros params) {
        //-32768 y 32767 --> integer
        // 4294967295 --> ulongint
        BigInteger digit = new BigInteger(params.getLexema());

        BigInteger ulongintMaxValue = new BigInteger("4294967295");
        if (digit.compareTo(BigInteger.valueOf(-32768)) >= 0 && digit.compareTo(ulongintMaxValue) <= 0){
            if (digit.compareTo(BigInteger.valueOf(32767)) <= 0){
                // es integer
                params.getTablaSimbolos().insert(params.getLexema(), TipoToken.CONSTANTE_ENTERA);
            }
            else{
                // es ulongint
                params.getTablaSimbolos().insert(params.getLexema(), TipoToken.CONSTANTE_ULONGINT);
            }
        }
        else{
            // no está en el rango de valores posibles
        }
    }

    
}
