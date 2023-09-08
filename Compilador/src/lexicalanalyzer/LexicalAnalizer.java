package lexicalanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import lexicalanalyzer.semanticactions.SAParam;
import lexicalanalyzer.semanticactions.sa.SA_1;
import lexicalanalyzer.semanticactions.sa.SA_10;
import lexicalanalyzer.semanticactions.sa.SA_11;
import lexicalanalyzer.semanticactions.sa.SA_12;
import lexicalanalyzer.semanticactions.sa.SA_13;
import lexicalanalyzer.semanticactions.sa.SA_14;
import lexicalanalyzer.semanticactions.sa.SA_2;
import lexicalanalyzer.semanticactions.sa.SA_3;
import lexicalanalyzer.semanticactions.sa.SA_4;
import lexicalanalyzer.semanticactions.sa.SA_5;
import lexicalanalyzer.semanticactions.sa.SA_6;
import lexicalanalyzer.semanticactions.sa.SA_7;
import lexicalanalyzer.semanticactions.sa.SA_8;
import lexicalanalyzer.semanticactions.sa.SA_9;
import lexicalanalyzer.semanticactions.ISemanticAction;
import objects.SymbolTable;
import objects.enums.ECharacterType;
import objects.enums.ELexicalAnalizerState;
import objects.enums.ETokenType;

public class LexicalAnalizer {

    private final int stateTable [][] = {
        {1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7, 6, 0, 0, 0, 3, 5, 1},
        {1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1},
        {-1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0},
        {4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8, -1, -1, -1, -1, -1, -1, -1},
        {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 8, 8, 8, 8, 8, 8},
        {8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, -1, 8, 8, 8, 8, 8, 8}
    };
    private final ISemanticAction semanticActionsTable [][] = {
        {new SA_3(),new SA_3(),new SA_5(),new SA_5(),new SA_5(),new SA_5(),new SA_5(),new SA_5(),new SA_5(),new SA_5(),new SA_5(),new SA_3(),new SA_3(),null,null,null,null,new SA_3(),new SA_3()},
        {new SA_2(),new SA_2(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_1(),new SA_2()},
        {new SA_4(), new SA_3(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4(), new SA_4()},
        {new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(), null,new SA_6(),new SA_6()},
        {null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null},
        {new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_14(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6(),new SA_6()},
        {new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_7(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8(),new SA_8()},
        {new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_10(),new SA_11(),new SA_10(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9(),new SA_9()},
        {new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),null,new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12(),new SA_12()},
        {new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),null,new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13(),new SA_13()}
    };
    private SymbolTable symbolTable;

    private BufferedReader sourceCode;

    private int currentCharacter;
    private boolean readNewCharacter;

    public LexicalAnalizer (String fileName) throws FileNotFoundException{
        
        File file = new File (fileName);
        sourceCode = new BufferedReader(new FileReader(file));
        symbolTable = new SymbolTable();     
        readNewCharacter = true;
    }
    
    public long getToken () {

        int currentState = ELexicalAnalizerState.INITIAL.getValue(); //Iniciamos en el estado inicial (0)
        SAParam SAParam = new SAParam(symbolTable); //Inicializamos la semantic action param con nuestra tabla de símbolos
        ISemanticAction semanticAction;
        try {
            if (readNewCharacter)
                currentCharacter = sourceCode.read(); //n
            while (currentState != ELexicalAnalizerState.FINAL.getValue() && currentCharacter!=-1){ // -1 es EOF
                
                //Busco la acción semántica correspondiente a la transición, para el estado en el que estoy y el tipo de caracter que consumo
                semanticAction = semanticActionsTable[currentState][ECharacterType.fromChar((char)currentCharacter).getValue()];// 3 2
                if(semanticAction != null) {
                    SAParam.setLastReadedCharacter((char)currentCharacter);
                    semanticAction.execute(SAParam);
                }
                //Calculo próximo estado en base al estado en el que estoy y el tipo de caracter que consumí
                currentState = stateTable [currentState][ECharacterType.fromChar((char)currentCharacter).getValue()];
                //El if es necesario aca, porque cuando ejecuta una acción léxica que va al estado final sin el if lee el siguiente caracter
                if (SAParam.isReadNewCharacter())
                    currentCharacter = sourceCode.read(); 
            }
            readNewCharacter = SAParam.isReadNewCharacter();
            return SAParam.getTokenType().getValue();

        } catch (IOException e) {
        
            return ETokenType.ERROR.getValue();
        }
    }

}