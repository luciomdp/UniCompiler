package objects.enums;

public enum EDataType {
    INTEGER(1),
    ULONGINT(2);

    private int value;
    
    private EDataType(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }
}
