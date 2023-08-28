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
        symbolTable.put("if", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("then", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("else", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("begin", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("end", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("end_if", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("print", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("while", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("do", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("fun", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("return", ETokenType.PALABRA_RESERVADA);
        symbolTable.put("itoul", ETokenType.PALABRA_RESERVADA);
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

