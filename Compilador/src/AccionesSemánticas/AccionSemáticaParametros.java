package AccionesSemánticas;

import objects.TablaPalabrasReservadas;
import objects.TablaSimbolos;
import objects.TipoToken;

public class AccionSemáticaParametros {
    private TablaSimbolos tablaSimbolos;
    private TablaPalabrasReservadas tablaPalabrasReservadas;
    private String lexema;
    private TipoToken tipoToken;
    private Character ultimoCaracterLeido;
    private int cantidadCaracteresLexema;


    public AccionSemáticaParametros() {
        lexema = "";
        cantidadCaracteresLexema = 0;
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }

    public void setTablaSimbolos(TablaSimbolos tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public TipoToken getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(TipoToken tipoToken) {
        this.tipoToken = tipoToken;
    }

    public Character getUltimoCaracterLeido() {
        return ultimoCaracterLeido;
    }

    public void setUltimoCaracterLeido(Character ultimoCaracterLeido) {
        this.ultimoCaracterLeido = ultimoCaracterLeido;
    }

    public TablaPalabrasReservadas getTablaPalabrasReservadas() {
        return tablaPalabrasReservadas;
    }

    public void setTablaPalabrasReservadas(TablaPalabrasReservadas tablaPalabrasReservadas) {
        this.tablaPalabrasReservadas = tablaPalabrasReservadas;
    }

    public int getCantidadCaracteresLexema() {
        return cantidadCaracteresLexema;
    }

    public void setCantidadCaracteresLexema(int cantidadCaracteresLexema) {
        this.cantidadCaracteresLexema = cantidadCaracteresLexema;
    }

    
}
