package objects.enums;

import java.util.HashMap;
import java.util.Map;

public enum ETokenType {
    
    END_OF_FILE (-1, "EOF"),
    ID (1, "Id"),
    INT_CONST (2, "Int"),
    ULONGINT_CONST (3, "Ulongint"),
    STRING_CONST (4, "String"),
    PLUS (5, "Plus"),
    MINUS (6, "Minus"),
    MULTIPLICATION (7, "Multiplication"),
    DIVISION (8, "Division"),
    ASIGNACION (9, "Asignacion"),
    GREATER_EQUAL (10, "Greater equal"),
    LESS_EQUAL (11, "Less equal"),
    GREATER_THAN (12, "Greater than"),
    LESS_THAN (13, "Less than"),
    EQUAL (14, "Equal"),
    NOT_EQUAL (15, "Not equal"),
    LEFT_PARENTHESIS (16, "Left parenthesis"),
    RIGHT_PARENTHESIS (17, "Right parenthesis"),
    COMMA (18, "Comma"),
    SEMICOLON (19, "Semicolon"),
    IF (20, "If"),
    THEN (21, "Then"),
    ELSE (22, "Else"),
    BEGIN (23, "Begin"),
    END (24, "End"),
    END_IF (25, "End if"),
    PRINT (26, "Print"),
    WHILE (27, "While"),
    DO (28, "Do"),
    FUN (29, "Fun"),
    RETURN (30, "Return"),
    ITOUL (31, "ITOUL"), 
    IGNORE (32, "Ignore"),
    ;
    private int value;
    private String description;
    private static final Map<Integer, ETokenType> valueToTokenMap = new HashMap<>();
    
    private ETokenType(int value, String description) {
        this.value=value;
        this.description = description;
    }
    
    public int getValue() {
        return value;
    }

    public static String getDescription(int value) {
       return valueToTokenMap.get(value).description;
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

