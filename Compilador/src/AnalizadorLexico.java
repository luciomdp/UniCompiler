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
    private HashMap<Integer, Integer> caracters;
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
        //Este map nos sirve para saber que columna en las matrices es cada caracter (en columna 0 son letras, la 1 digitos)
        //La clave es integer, porque el read() nos devuelve el entero decimal que hace referencia a ese caracter en el unicode, por ej nl es 10
        //Ver archivo tabla unicode en documentación/analizador lexico
        caracters = new HashMap<Integer, Integer>();
        tablaPalabrasReservadas = new TablaPalabrasReservadas();
        tablaSimbolos = new TablaSimbolos();
        leerNuevoCaracter = true;
        for (int code = 97; code <= 122; code++) {
            caracters.put(code, 0); // letras desde 'a'- 'z'
            caracters.put((code - 32), 0); // letras desde 'A'- 'Z'
        }
        for (int code = 48; code <= 57; code++) 
            caracters.put(code, 1); // nros del 0-9
        
        caracters.put(47, 2);   // '/'
        caracters.put(42, 3);   // '*'
        caracters.put(43, 4);   // '+'
        caracters.put(45, 5);   // '-'
        caracters.put(61, 6);   // '='
        caracters.put(60, 7);   // '<'
        caracters.put(62, 8);   // '>'
        caracters.put(null, 9);   // otros
        caracters.put(32, 10);  // espacio
        caracters.put(9, 11);   // tab
        caracters.put(10, 12);  // nueva línea (nl)
        caracters.put(35, 13);  // '#'
        caracters.put(58, 14);  // ':'
        caracters.put(64, 15);  // '@'

    }

    public long getToken () {
        int estadoActual = 0;
        AcciónSemántica as;
        // que sea variable local  nos permite no depender de que las acciones semánticas que dan fin a un proceso de getToken nos dejen las variables vacias (lexema, cantidadcaracteresleidos, tipotoken). 
        AccionSemáticaParametros accionSematicaParametros = new AccionSemáticaParametros();
        accionSematicaParametros.setTablaPalabrasReservadas(tablaPalabrasReservadas);
        accionSematicaParametros.setTablaSimbolos(tablaSimbolos);
        while (estadoActual != -1 && caracterActual!=-1){
            try {
                 // sin este if, cuando hay que leer el caracter que me llevó al fin del autómata, lo va a borrar y va a leer el siguiente a ese
                if (leerNuevoCaracter)
                    caracterActual = codeReader.read();
                // si es -1 implica final de flujo de entrada (creo que es final de archivo también)
                if (caracterActual != -1){
                    accionSematicaParametros.setUltimoCaracterLeido((char)caracterActual);
                    as = matrizAccionesSemanticas[estadoActual][caracters.get(caracterActual)];
                    estadoActual = matrizEstados [estadoActual][caracters.get(caracterActual)];
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