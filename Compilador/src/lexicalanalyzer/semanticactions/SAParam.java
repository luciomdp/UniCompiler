package lexicalanalyzer.semanticactions;

import objects.enums.ETokenType;

public class SAParam {
    
    private Character lastReadedCharacter;
    private StringBuilder lexema;
    private String messageError;
    private String messageWarning;
    private ETokenType tokenType;
    
    //Para las acciones semánticas que sean de transición a estado final, se define esta variable que indicará si el caracter leído es parte del token actual, (rnc=true) o si debería comenzar la lectura del próximo token en el último caracter consumido (rnc=false)
    private boolean readNewCharacter;


    public SAParam() {
        lexema = new StringBuilder();
        readNewCharacter = true;
        messageError = "";
        messageWarning = "";
        tokenType = ETokenType.IGNORE;
    }

    public ETokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(ETokenType tipoToken) {
        this.tokenType = tipoToken;
    }

    public Character getLastReadedCharacter() {
        return lastReadedCharacter;
    }

    public void setLastReadedCharacter(Character ultimoCaracterLeido) {
        this.lastReadedCharacter = ultimoCaracterLeido;
    }

    public boolean isReadNewCharacter() {
        return readNewCharacter;
    }

    public void setReadNewCharacter(boolean readNewCharacter) {
        this.readNewCharacter = readNewCharacter;
    }

    public StringBuilder getLexema() {
        return lexema;
    }

    public void setLexema(StringBuilder lexema) {
        this.lexema = lexema;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getMessageWarning() {
        return messageWarning;
    }

    public void setMessageWarning(String messageWarning) {
        this.messageWarning = messageWarning;
    }
    
    
}
