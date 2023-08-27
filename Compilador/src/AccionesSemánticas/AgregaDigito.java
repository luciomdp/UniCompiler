package AccionesSemánticas;

public class AgregaDigito implements AcciónSemántica{
    //A.S 5:
    // - Agregar dígito al string
    @Override
    public void ejecutar(AccionSemáticaParametros params) {
        //¿¿¿¿Deberíamos controlar que no se pase de los límites el digito????
        params.setLexema(params.getLexema()+params.getUltimoCaracterLeido());        
    }
    
}
