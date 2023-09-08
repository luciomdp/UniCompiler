package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_9 implements ISemanticAction{
    /*
        A.S 9:
            - Consumir caracter distinto de ">","=","<"  y devolverlo 
            (ya que ese caracter debe ser leído al arrancar el análisis del próximo token, 
            y no formaría parte del actual)
     */
    @Override
    public void execute(SAParam params) {
        params.setReadNewCharacter(false);
        params.setTokenType(ETokenType.MENOR_QUE);
    }  
      
}
