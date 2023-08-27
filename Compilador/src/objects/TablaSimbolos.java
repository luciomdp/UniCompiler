package objects;
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, TipoToken> tablaSimbolos;

    public TablaSimbolos() {
        tablaSimbolos = new HashMap<>();
        /*
            ME PARECE QUE TENEMOS QUE INICIALIZAR TODAS ESTAS EN LA TABLA DE SÍMBOLOS (el nro entre paréntesis es el tipo de token)
                
                CONSTANTE_ENTERA (2),
                CONSTANTE_ULONGINT(3),
                CONSTANTE_CADENA (4),
                MAS (5),
                MENOS (6),
                MULTIPLICACION (7),
                DIVISION (8),
                ASIGNACION (9),
                MAYOR_IGUAL (10),
                MENOR_IGUAL (11),
                MAYOR_QUE (12),
                MENOR_QUE (13),
                IGUAL (14),
                NO_IGUAL (15),
                PARENTESIS_IZQ (16),
                PARENTESIS_DER (17),
                COMA (18),
                PUNTO_Y_COMA (19),
         */

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

