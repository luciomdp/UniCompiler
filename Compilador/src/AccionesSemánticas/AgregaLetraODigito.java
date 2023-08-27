package AccionesSemánticas;

public class AgregaLetraODigito implements AcciónSemántica{

    /*
     A.S 3:
        - Agregar letra o digito o arroba al string
     */
    @Override
    public void ejecutar(AccionSemáticaParametros params) {
        if (params.getUltimoCaracterLeido() < 25){
            params.setLexema(params.getLexema()+params.getUltimoCaracterLeido());
            params.setCantidadCaracteresLexema(params.getCantidadCaracteresLexema()+1);
        }
    }
    
}
