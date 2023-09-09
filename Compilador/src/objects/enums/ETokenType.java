package objects.enums;

import java.util.HashMap;
import java.util.Map;

public enum ETokenType {
    
    END_OF_FILE (-1, "EOF"),
    IGNORE(0,""),
    PLUS (43, "Plus"),
    MINUS (45, "Minus"),
    MULTIPLICATION (42, "Multiplication"),
    DIVISION (47, "Division"),
    GREATER_THAN (62, "Greater than"),
    LESS_THAN (60, "Less than"),
    EQUAL (61, "Equal"),
    LEFT_PARENTHESIS (40, "Left parenthesis"),
    RIGHT_PARENTHESIS (41, "Right parenthesis"),
    COMMA (44, "Comma"),
    SEMICOLON (59, "Semicolon"),

    ID (257, "Id"),
    INT_CONST (258, "Int"),
    ULONGINT_CONST (259, "Ulongint"),
    STRING_CONST (260, "String"),
    ASIGNACION (261, "Asignacion"),
    GREATER_EQUAL (262, "Greater equal"),
    LESS_EQUAL (263, "Less equal"),
    NOT_EQUAL (264, "Not equal"),
    IF (265, "If"),
    THEN (266, "Then"),
    ELSE (267, "Else"),
    BEGIN (268, "Begin"),
    END (269, "End"),
    END_IF (270, "End if"),
    PRINT (271, "Print"),
    WHILE (272, "While"),
    DO (273, "Do"),
    FUN (274, "Fun"),
    RETURN (275, "Return"),
    ITOUL (276, "ITOUL")
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

