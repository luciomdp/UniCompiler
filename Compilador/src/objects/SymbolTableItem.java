package objects;

import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.enums.EUse;

public class SymbolTableItem {
    private ETokenType tokenType;
    private EDataType dataType;
    private Long itemEntryCount;
    private EUse use;

    public SymbolTableItem(ETokenType tokenType, EDataType dataType) {
        this.tokenType = tokenType;
        this.dataType = dataType;
        itemEntryCount = 1L;
    }
    public SymbolTableItem(ETokenType tokenType, EDataType dataType, Long itemEntryCount) {
        this.tokenType = tokenType;
        this.dataType = dataType;
        this.itemEntryCount = itemEntryCount;
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

    public Long getItemEntryCount() {
        return itemEntryCount;
    }

    public void setItemEntryCount(Long itemEntryCount) {
        this.itemEntryCount = itemEntryCount;
    }
    public void subtractOneItemEntry() {
        this.itemEntryCount = itemEntryCount-1;
    }
    public void addOneItemEntry() {
        this.itemEntryCount = itemEntryCount+1;
    }
    public EUse getUse() {
        return use;
    }
    public void setUse(EUse use) {
        this.use = use;
    }
    

}
