package objects.enums;

public enum ELexicalAnalizerState {
    
    INITIAL(0),
    _1(1),
    _2(2),
    _3(3),
    _4(4),
    _5(5),
    _6(6),
    _7(7),
    _8(8),
    _9(9),
    FINAL(-1);

    private int value;
    
    private ELexicalAnalizerState(int value) {
        this.value=value;
    }
    
    public int getValue() {
        return value;
    }
}

