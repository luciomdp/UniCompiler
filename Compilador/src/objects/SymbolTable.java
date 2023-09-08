package objects;
import java.util.HashMap;
import java.util.Map;

import objects.enums.ETokenType;

public class SymbolTable { 
    // Lexema, tipo de dato, uso (Donde se usa ese identificador), eso todavía no lo ponemos
    private Map<String, ETokenType> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<String, ETokenType>();
        //Agregamos palabras reservadas a la tabla de símbolos
        symbolTable.put("if", ETokenType.IF);
        symbolTable.put("then", ETokenType.THEN);
        symbolTable.put("else", ETokenType.ELSE);
        symbolTable.put("begin", ETokenType.BEGIN);
        symbolTable.put("end", ETokenType.END);
        symbolTable.put("end_if", ETokenType.END_IF);
        symbolTable.put("print", ETokenType.PRINT);
        symbolTable.put("while", ETokenType.WHILE);
        symbolTable.put("do", ETokenType.DO);
        symbolTable.put("fun", ETokenType.FUN);
        symbolTable.put("return", ETokenType.RETURN);
        symbolTable.put("itoul", ETokenType.ITOUL);
    }

    public void insert(String lexeme, ETokenType tokenType) {
        symbolTable.put(lexeme, tokenType);
    }

    public ETokenType lookup(String lexeme) {
        return symbolTable.get(lexeme);
    }

    public boolean contains(String lexeme) {
        return symbolTable.containsKey(lexeme);
    }
}

