package objects.enums;

public enum ECharacterType {
    LOWERCASE (1),
    UPPERCASE (2),
    NUMBER (3),
    SLASH(4), // "/"
    ASTERISK(5), // "*"
    PLUS(6), // "+"
    HYPHEN(7),// "-"
    EQUAL(8),// "="
    LESS_THAN(9),// "<"
    GREATER_THAN(10),// ">"
    HASHTAG(11),// "#"
    COLON(12),// ":"
    AT(13),// "<"
    SPACE(14),// " "
    TAB(15),// "    "
    NEW_LINE(16),// "nl",
    OTHERS(17);

    private int value;
    
    private ECharacterType(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }

    public static ECharacterType fromChar(char character) {
        //Si no funciona, pasar un int y en el case poner el valor unicode para devolver el tipo de caracter
        switch (character) {
            case '/':
                return SLASH;
            case '*':
                return ASTERISK;
            case '+':
                return PLUS;
            case '-':
                return HYPHEN;
            case '=':
                return EQUAL;
            case '<':
                return LESS_THAN;
            case '>':
                return GREATER_THAN;
            case '#':
                return HASHTAG;
            case ':':
                return COLON;
            case '@':
                return AT;
            case ' ':
                return SPACE;
            case '\t':
                return TAB;
            case '\n':
                return NEW_LINE;
            default:
                if (Character.isLowerCase(character)) {
                    return LOWERCASE;
                } else if (Character.isUpperCase(character)) {
                    return UPPERCASE;
                } else if (Character.isDigit(character)) {
                    return NUMBER;
                } else {
                    return OTHERS;
                }
        }
    }



}

