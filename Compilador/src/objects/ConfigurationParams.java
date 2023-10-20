package objects;

import java.util.Arrays;

import components.MainView;
import lexicalanalyzer.LexicalAnalizer;
import objects.enums.EUse;

public class ConfigurationParams {

    //De TokenViewer
    public static Boolean VIEW_TOKEN_NUMBER = true; // muestro el número de token
    public static LexicalAnalizer lexicalAnalizer;
    public static MainView mainView;
    public static SymbolTable symbolTable;
    public static ReversePolishStructure reversePolishStructure;

    public static StringBuilder currentScope;

    public ConfigurationParams (Boolean production) {
        if(production){
            VIEW_TOKEN_NUMBER = true;
        }
        symbolTable = new SymbolTable();    
        lexicalAnalizer = new LexicalAnalizer();
        reversePolishStructure = new ReversePolishStructure();
        currentScope = new StringBuilder();
        mainView = new MainView();
    }

    public static void updateSymbolTableView() {
        mainView.getSymbolTableViewer().updateTable();
    }
    public static void updateReversePolishView(String reversePolish,String newVal,int index) {
        mainView.getReversePolishViewer().updateTable(reversePolish,newVal,index);
    }
    public static void addScope(String newScope) {
        currentScope.append(".");
        currentScope.append(newScope);
        reversePolishStructure.generateNewReversePolish(newScope);
        mainView.getReversePolishViewer().addNewStructure(newScope);
    }
    public static void removeScope() {
        if (currentScope.lastIndexOf(".") != -1) 
           currentScope.setLength(currentScope.lastIndexOf("."));
    }

    public static String getFullCurrentScope() {
        return currentScope.toString();
    }

    public static String getCurrentScope() {
        int index = currentScope.toString().lastIndexOf('.');
        return currentScope.toString().substring(index + 1);
    }

    /*
     * Este método se llama cada vez que se declara una variable ya sea en el programa como en la cabecera de una función. Les agrega el scope completo. También arroja falso si ya existe la variable en ese scope.

     */
    public static boolean renameLexemaWithScope (String id){
        // agregar scope al id
        SymbolTableItem sti = symbolTable.lookup(id);
        symbolTable.remove(id);
        String newId = id+ConfigurationParams.getFullCurrentScope();
        if (symbolTable.contains(newId))
            return false;
        symbolTable.insert(id+ConfigurationParams.getFullCurrentScope(), sti);
        return true;
    }
    /*
     * Cuando se declara una función se la renombra con el scope completo. Si ya existe devuelve false. Además, setea si es una función con o sin parámetros

     */
    public static boolean renameFunctionWithScope (String id, boolean params){
        if (renameLexemaWithScope(id)){
            SymbolTableItem sti = symbolTable.lookup(id+ConfigurationParams.getFullCurrentScope());
            if (params)
                sti.setUse(EUse.FUNCTION_PARAM);
            else
                sti.setUse(EUse.FUNCTION_PARAM);
        }
        else return false;

        return true;
    }
    public static boolean checkIfLexemaIsDeclared (String id){
        String idWithScope = id+getFullCurrentScope();
        String[] wordsInId = idWithScope.split("\\.");
        for (int i=wordsInId.length; i > 1 ; i--){
            if (symbolTable.contains(idWithScope)){
                symbolTable.addEntryCount(idWithScope);
                symbolTable.remove(id);
                return true;
            }
            int index = idWithScope.indexOf("."+wordsInId[i-1]);
            idWithScope = idWithScope.substring(0, index);
        }
        return false;
    }
    public static boolean checkIfFunctionIsDeclared (String id, boolean params){
        if (checkIfLexemaIsDeclared(id)){
            String idWithScope = id+getFullCurrentScope();
            if ((symbolTable.lookup(idWithScope).getUse() == EUse.FUNCTION_PARAM && !params) ||(symbolTable.lookup(idWithScope).getUse() == EUse.FUNCTION && params))
                return false;
        }  
        else return false;      
        return true;
    }

}
