package objects;
import java.util.HashMap;
import java.util.Map;

import objects.enums.ETokenType;

public class SymbolTable { 
    // Lexema, tipo de dato, uso (Donde se usa ese identificador), eso todavía no lo ponemos
    private Map<String, SymbolTableItem> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<String, SymbolTableItem>();
        //Agregamos palabras reservadas a la tabla de símbolos
        symbolTable.put("if", new SymbolTableItem(ETokenType.IF, null, 0L));
        symbolTable.put("then", new SymbolTableItem(ETokenType.THEN, null, 0L));
        symbolTable.put("else", new SymbolTableItem(ETokenType.ELSE, null, 0L));
        symbolTable.put("begin", new SymbolTableItem(ETokenType.BEGIN, null, 0L));
        symbolTable.put("end", new SymbolTableItem(ETokenType.END, null, 0L));
        symbolTable.put("end_if", new SymbolTableItem(ETokenType.END_IF, null, 0L));
        symbolTable.put("print", new SymbolTableItem(ETokenType.PRINT, null, 0L));
        symbolTable.put("while", new SymbolTableItem(ETokenType.WHILE, null, 0L));
        symbolTable.put("do", new SymbolTableItem(ETokenType.DO, null, 0L));
        symbolTable.put("fun", new SymbolTableItem(ETokenType.FUN, null, 0L));
        symbolTable.put("return", new SymbolTableItem(ETokenType.RETURN, null, 0L));
        symbolTable.put("itoul", new SymbolTableItem(ETokenType.ITOUL, null, 0L));
        symbolTable.put("integer", new SymbolTableItem(ETokenType.INTEGER, null, 0L));
        symbolTable.put("ulongint", new SymbolTableItem(ETokenType.ULONGINT, null, 0L));
        
        
    }

    public void insert(String lexeme, SymbolTableItem tokenType) {
        symbolTable.put(lexeme, tokenType);
        ConfigurationParams.updateView();
    }

    public void addEntryCount(String lexeme) {
        SymbolTableItem item = symbolTable.get(lexeme);
        item.setItemEntryCount(item.getItemEntryCount()+1);
        ConfigurationParams.updateView();
    }

    public SymbolTableItem lookup(String lexeme) {
        return symbolTable.get(lexeme);
    }

    public SymbolTableItem remove(String lexeme) {
        return symbolTable.remove(lexeme);
    }

    public boolean contains(String lexeme) {
        return symbolTable.containsKey(lexeme);
    }

    public String[][] generateDataForTable () {
        String data[][] = new String[symbolTable.size()][4];
        final int[] rowIndex = {0};
        symbolTable.entrySet().stream().forEach(entry -> {
            String key = entry.getKey();
            SymbolTableItem value = entry.getValue();
            data[rowIndex[0]][0] = key;
            data[rowIndex[0]][1] = value.getTokenType()!=null?value.getTokenType().name():"-";
            data[rowIndex[0]][2] = value.getDataType()!=null?value.getDataType().name():"-";
            data[rowIndex[0]][3] = String.valueOf(value.getItemEntryCount()!=null?value.getItemEntryCount():0); // Opcional si lo necesitas
            rowIndex[0]++;
        });
        return data;
    }
}

