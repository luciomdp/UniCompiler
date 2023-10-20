package objects.enums;

public enum EUse {
    FUNCTION(1),
    FUNCTION_PARAM(2);

    private int value;
    private EUse(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }
}
