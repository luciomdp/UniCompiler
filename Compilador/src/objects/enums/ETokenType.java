package objects.enums;

import java.util.HashMap;
import java.util.Map;

public enum ETokenType {
    
    FIN_DE_ARCHIVO (-1),
    ERROR (0),
    IDENTIFICADOR (1),
    CONSTANTE_ENTERA (2),
    CONSTANTE_ULONGINT(3),
    CONSTANTE_CADENA (4),
    MAS (5),
    MENOS (6),
    MULTIPLICACION (7),
    DIVISION (8),
    ASIGNACION (9),
    MAYOR_IGUAL (10),
    MENOR_IGUAL (11),
    MAYOR_QUE (12),
    MENOR_QUE (13),
    IGUAL (14),
    NO_IGUAL (15),
    PARENTESIS_IZQ (16),
    PARENTESIS_DER (17),
    COMA (18),
    PUNTO_Y_COMA (19),
    PALABRA_RESERVADA (20);

    private int value;
    private static final Map<Integer, ETokenType> valueToTokenMap = new HashMap<>();
    
    private ETokenType(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }

    static {
        for (ETokenType token : ETokenType.values()) {
            valueToTokenMap.put(token.getValue(), token);
        }
    }

    public static ETokenType fromValue(int value) {
        return valueToTokenMap.get(value);
    }

}

