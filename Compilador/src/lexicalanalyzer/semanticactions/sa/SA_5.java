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
        params.getLexema().append(params.getLastReadedCharacter()); 
        switch(params.getLexema().toString()) {
            case "+": params.setTokenType(ETokenType.PLUS);
            break;
            case "-": params.setTokenType(ETokenType.MINUS);
            break;
            case "/": params.setTokenType(ETokenType.DIVISION);
            break;
            case "*": params.setTokenType(ETokenType.MULTIPLICATION);
            break;
            case "(": params.setTokenType(ETokenType.LEFT_PARENTHESIS);
            break;
            case ")": params.setTokenType(ETokenType.RIGHT_PARENTHESIS);
            break;
            case ",": params.setTokenType(ETokenType.COMMA);
            break;
            case ";": params.setTokenType(ETokenType.SEMICOLON);
            break;
            case "=": params.setTokenType(ETokenType.EQUAL);
            break;
            default: params.setTokenType(ETokenType.ERROR);
        }
        params.setReadNewCharacter(true);
    }
 
}
