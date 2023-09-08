package lexicalanalyzer.semanticactions.sa;

import lexicalanalyzer.semanticactions.ISemanticAction;
import lexicalanalyzer.semanticactions.SAParam;
import objects.enums.ETokenType;

public class SA_5 implements ISemanticAction{
    /*
     A.S 5:
        - Inicializar string
        - Agregar caracter unitario (+ - / * ( ) , ; =)
     */
    @Override
    public void execute(SAParam params) {
        switch(params.getLexema().toString()) {
            case "+": params.setTokenType(ETokenType.MAS);
            break;
            case "-": params.setTokenType(ETokenType.MENOS);
            break;
            case "/": params.setTokenType(ETokenType.DIVISION);
            break;
            case "*": params.setTokenType(ETokenType.MULTIPLICACION);
            break;
            case "(": params.setTokenType(ETokenType.PARENTESIS_IZQ);
            break;
            case ")": params.setTokenType(ETokenType.PARENTESIS_DER);
            break;
            case ",": params.setTokenType(ETokenType.COMA);
            break;
            case ";": params.setTokenType(ETokenType.PUNTO_Y_COMA);
            break;
            case "=": params.setTokenType(ETokenType.IGUAL);
            break;
            default: params.setTokenType(ETokenType.ERROR);
        }
        params.setReadNewCharacter(true);
    }
 
}
