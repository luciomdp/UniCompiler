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
    public void ejecutar(AccionSemáticaParametros params) {
        if (!params.getTablaPalabrasReservadas().esPalabraReservada(params.getLexema())){
            if (!params.getTablaSimbolos().contains(params.getLexema())) 
                params.getTablaSimbolos().insert(params.getLexema(), TipoToken.IDENTIFICADOR);
            params.setTipoToken(TipoToken.IDENTIFICADOR);
        }
        else
            //nos devuelve el de la palabra reservada (hay que ver si se puede mejorar)
            params.setTipoToken(TipoToken.fromValue(params.getTablaPalabrasReservadas().getPalabraReservadaId(params.getLexema())));
    }
    
}
