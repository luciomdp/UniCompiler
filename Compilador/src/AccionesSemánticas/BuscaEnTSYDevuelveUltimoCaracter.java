package AccionesSemánticas;

import java.util.HashSet;
import java.util.Set;

import objects.TipoToken;

public class BuscaEnTSYDevuelveUltimoCaracter implements AcciónSemántica{

    public BuscaEnTSYDevuelveUltimoCaracter (){

    }
    //  A.S 1:
    // - Devolver a la entrada el último carácter leído
    // - Buscar en la TS
    //     * Si está, +Si es PR, devolver la Palabra Reservada +Si no, Devolver ID + Punt TS
    //     * Si no está, +Alta en la TS
    // -Devolver ID + Punt TS
    @Override
    public Character ejecutar(AccionSemáticaParametros params) {
        if (params.getTablaPalabrasReservadas().esPalabraReservada(params.getLexema())){
            // la palabra reservada que debería devolver es el lexema
        }
        else {
            if (!params.getTablaSimbolos().contains(params.getLexema())) 
                params.getTablaSimbolos().insert(params.getLexema(), TipoToken.IDENTIFICADOR);
        }
        params.setTipoToken(TipoToken.IDENTIFICADOR);
        return params.getUltimoCaracterLeido();
    }
    
}
