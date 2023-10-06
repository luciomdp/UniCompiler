package objects;

import components.MainView;
import lexicalanalyzer.LexicalAnalizer;

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
    public static void updateReversePolishView(String newVal,int index) {
        mainView.getReversePolishViewer().updateTable(newVal,index);
    }
    public static void addScope(String newScope) {
        currentScope.append(".");
        currentScope.append(newScope);
        reversePolishStructure.generateNewReversePolish(newScope);
    }
    public static void removeScope() {
        if (currentScope.lastIndexOf(".") != -1) 
           currentScope.setLength(currentScope.lastIndexOf("."));
    }

    public static String getCurrentScope() {
        return (currentScope.lastIndexOf(".") != -1) ? currentScope.substring(currentScope.lastIndexOf(".") + 1) : currentScope.toString();
    }

    public static String renameLexemaWithScope (String id){
        if (symbolTable.contains(id)){
            // agregar scope al id
            SymbolTableItem sti = symbolTable.lookup(id);
            symbolTable.remove(id);
            symbolTable.insert(id+"."+ConfigurationParams.getCurrentScope(), sti);
            return "";
        }
        else if (symbolTable.contains(id+"."+ConfigurationParams.getCurrentScope())){
            // ya existe, lanzar error de declaración
            return "ERROR: la variable "+id+" ya fue declarada en el scope "+ getCurrentScope();
        }
        return "";
            
    }
}
