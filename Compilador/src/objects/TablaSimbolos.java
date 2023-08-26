package objects;
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, TipoToken> tablaSimbolos;

    public TablaSimbolos() {
        tablaSimbolos = new HashMap<>();
    }

    public void insert(String lexeme, TipoToken tokenType) {
        tablaSimbolos.put(lexeme, tokenType);
    }

    public TipoToken lookup(String lexeme) {
        return tablaSimbolos.get(lexeme);
    }

    public boolean contains(String lexeme) {
        return tablaSimbolos.containsKey(lexeme);
    }
}

