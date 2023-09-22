package objects.enums;

public enum ECharacterType {
    LOWERCASE (0),
    UPPERCASE (1),
    DIGIT (2),
    SLASH(3), // "/"
    ASTERISK(4), // "*"
    PLUS(5), // "+"
    HYPHEN(6), // "-"
    OPEN_PARENTHESIS(7), // "("
    CLOSE_PARENTHESIS(8), // ")"
    COMMA(9),// ","
    SEMICOLON(10),// ";"
    EQUAL(11),// "="
    LESS_THAN(12),// "<"
    GREATER_THAN(13),// ">"
    SPACE(14),// " "
    TAB(15),// "    "
    NEW_LINE(16),// "nl",
    HASHTAG(17),// "#"
    COLON(18),// ":"
    AT(19),// "@"
    OTHERS(20),
    UNDERSCORE(21);

    private int value;
    
    private ECharacterType(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }

    public static ECharacterType fromChar(char character) {
        switch (character) {
            case '_':
                return ECharacterType.UNDERSCORE;
            case '/':
                return ECharacterType.SLASH;
            case '*':
                return ECharacterType.ASTERISK;
            case '+':
                return ECharacterType.PLUS;
            case '-':
                return ECharacterType.HYPHEN;
            case '=':
                return ECharacterType.EQUAL;
            case '<':
                return ECharacterType.LESS_THAN;
            case '>':
                return ECharacterType.GREATER_THAN;
            case '#':
                return ECharacterType.HASHTAG;
            case ':':
                return ECharacterType.COLON;
            case '@':
                return ECharacterType.AT;
            case ' ':
                return ECharacterType.SPACE;
            case '\t':
                return ECharacterType.TAB;
            case '\n':
                return ECharacterType.NEW_LINE;
            case 13:
                return ECharacterType.NEW_LINE; 
            case '(':
                return ECharacterType.OPEN_PARENTHESIS;
            case ')':
                return ECharacterType.CLOSE_PARENTHESIS;
            case ',':
                return ECharacterType.COMMA;
            case ';':
                return ECharacterType.SEMICOLON;
            default:
                if (Character.isLowerCase(character)) {
                    return ECharacterType.LOWERCASE;
                } else if (Character.isUpperCase(character)) {
                    return ECharacterType.UPPERCASE;
                } else if (Character.isDigit(character)) {
                    return ECharacterType.DIGIT;
                } else {
                    return ECharacterType.OTHERS;
                }
        }
    }    

}

