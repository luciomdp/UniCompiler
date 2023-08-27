package AccionesSemánticas;

public class IniciaStringYAgregaLetraOArroba implements AcciónSemántica{

    /*
     A.S 2:
        - Inicializar string (se reserva 25 caracteres para identificadores)
        - Agregar letra o arroba al string
     */
    @Override
    public void ejecutar(AccionSemáticaParametros params) {
        params.setLexema(params.getUltimoCaracterLeido().toString());
        params.setCantidadCaracteresLexema(1);
    }
    
}
