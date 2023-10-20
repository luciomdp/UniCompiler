package objects.enums;

public enum EUse {
    FUNCTION(1),
    FUNCTION_PARAM(2),
    VARIABLE(3),
    CONST(4),
    RESERVED_WORD(5);

    private int value;
    private EUse(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }
}
