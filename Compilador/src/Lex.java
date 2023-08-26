import java.io.BufferedReader;
import java.io.IOException;

import AccionesSemánticas.AcciónSemántica;


public class Lex {
    int matrizEstados [][];
    AcciónSemántica matrizAccionesSemanticas [][];
    BufferedReader code;

    public int getToken () {
        int tokenId = -1, estadoActual = 0, caracterActual = -1;
        AcciónSemántica as;
        while (estadoActual != -1){
            try {
                caracterActual = code.read();
                as = matrizAccionesSemanticas[estadoActual][caracterActual];
                tokenId = as.ejecutar(null);
                estadoActual = matrizEstados[estadoActual][caracterActual];
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tokenId;
    }
}