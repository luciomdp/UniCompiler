import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


import AccionesSemánticas.AccionSemáticaParametros;
import AccionesSemánticas.AcciónSemántica;
import objects.TablaPalabrasReservadas;
import objects.TablaSimbolos;


public class AnalizadorLexico {
    private HashMap<Character, Long> caracters;
    private int matrizEstados [][];
    private AcciónSemántica matrizAccionesSemanticas [][];
    private BufferedReader codeReader;
    private TablaSimbolos tablaSimbolos;
    private TablaPalabrasReservadas tablaPalabrasReservadas;
    private int caracterActual;
    private boolean leerNuevoCaracter;

    public AnalizadorLexico (String fileName) {
        try {
            File file = new File (fileName);
            codeReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        caracters = new HashMap<Character, Long>();
        tablaPalabrasReservadas = new TablaPalabrasReservadas();
        tablaSimbolos = new TablaSimbolos();
        leerNuevoCaracter = true;
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

    public long getToken () {
        int estadoActual = 0;
        char charCaracterActual;
        AcciónSemántica as;
        // que sea variable local  nos permite no depender de que las acciones semánticas que dan fin a un proceso de getToken nos dejen las variables vacias (lexema, cantidadcaracteresleidos, tipotoken). 
        AccionSemáticaParametros accionSematicaParametros = new AccionSemáticaParametros();
        accionSematicaParametros.setTablaPalabrasReservadas(tablaPalabrasReservadas);
        accionSematicaParametros.setTablaSimbolos(tablaSimbolos);
        while (estadoActual != -1){
            try {
                 // sin este if, cuando hay que leer el caracter que me llevó al fin del autómata, lo va a borrar y va a leer el siguiente a ese
                if (leerNuevoCaracter)
                    caracterActual = codeReader.read();
                // si es -1 implica final de flujo de entrada (creo que es final de archivo también)
                if (caracterActual != -1){
                    charCaracterActual = (char)caracterActual;
                    accionSematicaParametros.setUltimoCaracterLeido(charCaracterActual);
                    as = matrizAccionesSemanticas[estadoActual][caracters.get(charCaracterActual).intValue()];
                    estadoActual = matrizEstados [estadoActual][caracters.get(charCaracterActual).intValue()];
                    as.ejecutar(accionSematicaParametros);
                    leerNuevoCaracter = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        leerNuevoCaracter = false;
        // Tipo de token podría ser un enum como los que hacemos en medere que tienen el value
        return accionSematicaParametros.getTipoToken().getValue();
    }
}