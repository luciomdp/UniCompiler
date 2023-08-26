package objects;

import java.util.HashSet;
import java.util.Set;

public class TablaPalabrasReservadas {
    private static Set<String> palabrasReservadas;

    public TablaPalabrasReservadas (){
        palabrasReservadas = new HashSet<>();
        palabrasReservadas.add("if");
        palabrasReservadas.add("then");
        palabrasReservadas.add("else");
        palabrasReservadas.add("begin");
        palabrasReservadas.add("end");
        palabrasReservadas.add("end_if");
        palabrasReservadas.add("print");
        palabrasReservadas.add("while");
        palabrasReservadas.add("do");
        palabrasReservadas.add("fun");
        palabrasReservadas.add("return");
    }

    public static boolean esPalabraReservada(String palabra) {
        return palabrasReservadas.contains(palabra);
    }
}

