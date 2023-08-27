package objects;

import java.util.HashMap;
import java.util.Map;

public enum TipoToken {
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
    PALABRA_RESERVADA_IF (20),
    PALABRA_RESERVADA_THEN (21),
    PALABRA_RESERVADA_ELSE (22),
    PALABRA_RESERVADA_BEGIN (23),
    PALABRA_RESERVADA_END (24),
    PALABRA_RESERVADA_END_IF (25),
    PALABRA_RESERVADA_PRINT (26),
    PALABRA_RESERVADA_WHILE (27),
    PALABRA_RESERVADA_DO (28),
    PALABRA_RESERVADA_FUN (29),
    PALABRA_RESERVADA_RETURN (30),
    FIN_DE_ARCHIVO (31),
    ERROR (32);

    private long value;
    private static final Map<Long, TipoToken> valueToTokenMap = new HashMap<>();
    
    private TipoToken(long value) {
        this.value=value;
    }
    
    public long getValue() {
        return value;
    }

    static {
        for (TipoToken token : TipoToken.values()) {
            valueToTokenMap.put(token.getValue(), token);
        }
    }

    public static TipoToken fromValue(long value) {
        return valueToTokenMap.get(value);
    }

}

