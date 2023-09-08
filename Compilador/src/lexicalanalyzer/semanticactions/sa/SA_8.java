package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_8 implements ISemanticAction{
    /*
        A.S 8:
            - Consumir caracter y devolverlo (ya que ese caracter debe ser leído al 
            arrancar el análisis del próximo token, y no formaría parte del actual)
     */
    @Override
    public void execute(SAParam params) {
        if (params.getLastReadedCharacter() == '=')
            params.setTokenType(ETokenType.ERROR);
        else
            params.setTokenType(ETokenType.MAYOR_QUE);
        params.setReadNewCharacter(false);
    }

}
