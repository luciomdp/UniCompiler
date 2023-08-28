package lexicalanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lexicalanalyzer.semanticactions.SAParam;
import lexicalanalyzer.semanticactions.ISemanticAction;
import objects.SymbolTable;
import objects.enums.ECharacterType;
import objects.enums.ELexicalAnalizerState;
import objects.enums.ETokenType;

public class LexicalAnalizer {

    private final int stateTable [][] = {};
    private final ISemanticAction semanticActionsTable [][] = {};
    private SymbolTable symbolTable;

    private BufferedReader sourceCode;

    private int currentCharacter;
    private boolean readNewCharacter;

    public LexicalAnalizer (String fileName) {
        try {
            File file = new File (fileName);
            sourceCode = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
        symbolTable = new SymbolTable();     
        readNewCharacter = true;
    }

    public long getToken () {

        int currentState = ELexicalAnalizerState.INITIAL.getValue(); //Iniciamos en el estado inicial (0)
        SAParam SAParam = new SAParam(symbolTable); //Inicializamos la semantic action param con nuestra tabla de símbolos
        ISemanticAction semanticAction;
        try {
            if (readNewCharacter)
                currentCharacter = sourceCode.read();
            while (currentState != ELexicalAnalizerState.FINAL.getValue() && currentCharacter!=-1){ // -1 es EOF
                
                //Busco la acción semántica correspondiente a la transición, para el estado en el que estoy y el tipo de caracter que consumo
                semanticAction = semanticActionsTable[currentState][ECharacterType.fromChar((char)currentCharacter).getValue()];
                if(semanticAction != null) {
                    SAParam.setLastReadedCharacter((char)currentCharacter);
                    semanticAction.execute(SAParam);
                }
                //Calculo próximo estado en base al estado en el que estoy y el tipo de caracter que consumí
                currentState = stateTable [currentState][ECharacterType.fromChar((char)currentCharacter).getValue()];
                currentCharacter = sourceCode.read();
            }

            readNewCharacter = SAParam.isReadNewCharacter();
            return SAParam.getTokenType().getValue();

        } catch (IOException e) {
        
            return ETokenType.ERROR.getValue();
        }
    }

}