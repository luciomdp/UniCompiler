package objects;

import java.util.HashMap;


public class TablaPalabrasReservadas {
    private HashMap<String, Long> palabrasReservadas;

    public TablaPalabrasReservadas (){

        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("if", 20L);
        palabrasReservadas.put("then", 21L);
        palabrasReservadas.put("else", 22L);
        palabrasReservadas.put("begin", 23L);
        palabrasReservadas.put("end", 24L);
        palabrasReservadas.put("end_if", 25L);
        palabrasReservadas.put("print", 26L);
        palabrasReservadas.put("while", 27L);
        palabrasReservadas.put("do", 28L);
        palabrasReservadas.put("fun", 29L);
        palabrasReservadas.put("return", 30L);
    }

    public boolean esPalabraReservada(String palabra) {
        return palabrasReservadas.get(palabra)==null?false:true;
    }

    public Long getPalabraReservadaId(String palabraReservada){
        return palabrasReservadas.get(palabraReservada);
    }
    
}

