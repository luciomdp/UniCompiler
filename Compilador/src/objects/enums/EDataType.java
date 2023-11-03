package objects.enums;

public enum EDataType {
    INTEGER(1),
    ULONGINT(2),
    STRING(3);

    private int value;
    
    private EDataType(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }
}
