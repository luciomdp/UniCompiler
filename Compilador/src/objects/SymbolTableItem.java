package objects;

import objects.enums.EDataType;
import objects.enums.ETokenType;

public class SymbolTableItem {
    private ETokenType tokenType;
    private EDataType dataType;

    public SymbolTableItem(ETokenType tokenType, EDataType dataType) {
        this.tokenType = tokenType;
        this.dataType = dataType;
    }

    public ETokenType getTokenType() {
        return tokenType;
    }
    public void setTokenType(ETokenType tokenType) {
        this.tokenType = tokenType;
    }
    public EDataType getDataType() {
        return dataType;
    }
    public void setDataType(EDataType dataType) {
        this.dataType = dataType;
    }

    
}
