package objects.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    NUMERIC_CONST (258, "Numeric"),
    STRING_CONST (259, "String"),
    ASIGNACION (260, "Asignacion"),
    GREATER_EQUAL (261, "Greater equal"),
    LESS_EQUAL (262, "Less equal"),
    NOT_EQUAL (263, "Not equal"),
    IF (264, "if"),
    THEN (265, "then"),
    ELSE (266, "else"),
    BEGIN (267, "begin"),
    END (268, "end"),
    END_IF (269, "end_if"),
    PRINT (270, "print"),
    WHILE (271, "while"),
    DO (272, "do"),
    FUN (273, "fun"),
    RETURN (274, "return"),
    ITOUL (275, "itoul"),
    INTEGER(276,"integer"),
    ULONGINT(277,"ulongint")
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

    public static List<Long> getLexemeTokenTypes() {
        return Arrays.asList(ETokenType.ID.getValue(),ETokenType.STRING_CONST.getValue(),ETokenType.NUMERIC_CONST.getValue(),ETokenType.NUMERIC_CONST.getValue()).stream().map(element -> element.longValue()).toList();
    }

}

