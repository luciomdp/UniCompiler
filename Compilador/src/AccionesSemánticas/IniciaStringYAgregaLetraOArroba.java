package AccionesSemánticas;

public class IniciaStringYAgregaLetraOArroba implements AcciónSemántica{

    /*
     A.S 2:
        - Inicializar string (se reserva 25 caracteres para identificadores)
        - Agregar letra o arroba al string
     */
    @Override
    public Character ejecutar(AccionSemáticaParametros params) {
        // se me ocurre que podríamos manejar todo desde params. En este caso, que lexema se ponga vació, le agregamos el primer caracter y también inicializamos el contador de cantidad de caracteres en 0 (o 1)
        params.setLexema("");
        params.setLexema(params.getLexema()+params.getUltimoCaracterLeido());
        params.setCantidadCaracteresLexema(1);
        return params.getUltimoCaracterLeido();
    }
    
}
