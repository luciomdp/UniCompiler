package AccionesSemánticas;

public class IniciaStringYAgregaDigito implements AcciónSemántica{
    /*
     A.S 4:
        - Inicializar string para la constante
        - Agregar dígito al string
     */
    @Override
    public void ejecutar(AccionSemáticaParametros params) {
        params.setLexema(params.getUltimoCaracterLeido().toString());    
    }
    
}
