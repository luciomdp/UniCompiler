package objects.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ETokenType {
    
    END_OF_FILE (-1, "EOF"),
    IGNORE(0,""),
    PLUS (43, "+"),
    MINUS (45, "-"),
    MULTIPLICATION (42, "*"),
    DIVISION (47, "/"),
    GREATER_THAN (62, ">"),
    LESS_THAN (60, "<"),
    EQUAL (61, "="),
    LEFT_PARENTHESIS (40, "("),
    RIGHT_PARENTHESIS (41, ")"),
    COMMA (44, ","),
    SEMICOLON (59, ";"),

    ID (257, "Id"),
    NUMERIC_CONST (258, "Const. Numerica"),
    STRING_CONST (259, "Const. Texto"),
    ASIGNACION (260, ":="),
    GREATER_EQUAL (261, ">="),
    LESS_EQUAL (262, "<="),
    NOT_EQUAL (263, "<>"),
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

