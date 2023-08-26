import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import AccionesSemánticas.AccionSemáticaParametros;
import AccionesSemánticas.AcciónSemántica;
import objects.TipoToken;

public class AnalizadorLexico {
    Set<Character> conjuntoDigitos;
    Set<Character> conjuntoLetras;
    HashMap<Character, Long> caracters;

    int matrizEstados [][];
    AcciónSemántica matrizAccionesSemanticas [][];

    BufferedReader code;

    public AnalizadorLexico () {
        conjuntoDigitos = new HashSet<>();
        for (char c = 'a'; c <= 'z'; c++) {
            caracters.put(c, Long.valueOf(0));
            caracters.put(Character.toUpperCase(c), Long.valueOf(0));
        }
        for (Character c = '0'; c <= '9'; c++) 
            caracters.put(c, Long.valueOf(1));
        
            caracters.put('/', Long.valueOf(2));
            caracters.put('*', Long.valueOf(3));
            caracters.put('+', Long.valueOf(4));
            caracters.put('-', Long.valueOf(5));
            caracters.put('=', Long.valueOf(6));
            caracters.put('<', Long.valueOf(7));
            caracters.put('>', Long.valueOf(8));
            caracters.put(' ', Long.valueOf(9)); //otros
            caracters.put(' ', Long.valueOf(10));//blanco
            caracters.put(' ', Long.valueOf(11));//tab
            caracters.put(' ', Long.valueOf(12)); //nl
            caracters.put('#', Long.valueOf(13));
            caracters.put(':', Long.valueOf(14));
            caracters.put('@', Long.valueOf(15));

        

    }

    public TipoToken getToken () {
        int estadoActual = 0;
        AcciónSemántica as;
        AccionSemáticaParametros accionSematicaParametros = new AccionSemáticaParametros();
        while (estadoActual != -1){
            try {
                int caracterActual = code.read();
                // si es -1 implica final de flujo de entrada (creo que es final de archivo también)
                if (caracterActual != -1){
                    as = matrizAccionesSemanticas[estadoActual][caracters.get((char)caracterActual).intValue()];
                    as.ejecutar(accionSematicaParametros);
                    estadoActual = matrizEstados [estadoActual][caracters.get((char)caracterActual).intValue()];
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Tipo de token podría ser un enum como los que hacemos en medere que tienen el value
        return accionSematicaParametros.getTipoToken();
    }
}