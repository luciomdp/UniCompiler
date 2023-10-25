package objects;

import java.util.Arrays;
import java.util.Map.Entry;

import components.MainView;
import lexicalanalyzer.LexicalAnalizer;
import objects.enums.EDataType;
import objects.enums.EUse;

public class ConfigurationParams {

    //De TokenViewer
    public static Boolean VIEW_TOKEN_NUMBER = true; // muestro el número de token
    public static LexicalAnalizer lexicalAnalizer;
    public static MainView mainView;
    public static SymbolTable symbolTable;
    public static ReversePolishStructure reversePolishStructure;
    public static EDataType lastVariableDataType;
    public static EDataType lastFunctionDataType;
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
        StringBuilder newCurrentScope = new StringBuilder(newScope);
        if (!currentScope.isEmpty())
            newCurrentScope.append(".");
        newCurrentScope.append(currentScope);
        currentScope = newCurrentScope; 
        reversePolishStructure.generateNewReversePolish(newScope);
        mainView.getReversePolishViewer().addNewStructure(newScope);
    }
    public static void removeScope() {
        int index = currentScope.toString().indexOf('.');
        if (currentScope.indexOf(".") != -1) 
            currentScope = new StringBuilder(currentScope.toString().substring(index!=-1?index+1:currentScope.length()));
    }

    public static String getFullCurrentScope() {
        return "."+currentScope.toString();
    }

    public static String getCurrentScope() {
        int index = currentScope.toString().indexOf('.');
        String a = currentScope.toString().substring(0,index!=-1?index:currentScope.length());
        return a;
    }

    /*
     * Este método se llama cada vez que se declara una variable ya sea en el programa como en la cabecera de una función. Les agrega el scope completo. También arroja falso si ya existe la variable en ese scope.

     */
    public static boolean renameIdWithScopeAndSetDataType (String id, boolean param){
        // agregar scope al id
        SymbolTableItem sti = symbolTable.lookup(id);
        sti.setDataType(ConfigurationParams.getLastDataType());
        String newId = id+ConfigurationParams.getFullCurrentScope();
        if (symbolTable.contains(newId)) // ya existía antes?
            return false;
        if (param)
            sti.setUse(EUse.PARAMETER);
        else
            sti.setUse(EUse.VARIABLE);
        symbolTable.insert(id+ConfigurationParams.getFullCurrentScope(), sti);
        symbolTable.remove(id);
        return true;
    }
    /*
     * Cuando se declara una función se la renombra con el scope completo. Si ya existe devuelve false. Además, setea si es una función con o sin parámetros

     */
    public static boolean renameFunctionWithScope (String id, boolean params){
        // agregar scope al id
        String newId = id+ConfigurationParams.getFullCurrentScope();
        SymbolTableItem sti = symbolTable.lookup(id);
        sti.setDataType(ConfigurationParams.getLastFunctionDataType());
        if (params)
            sti.setUse(EUse.FUNCTION_PARAM);
        else
            sti.setUse(EUse.FUNCTION);
        if (symbolTable.contains(newId)) // ya existía antes?
            return false;
        symbolTable.insert(id+ConfigurationParams.getFullCurrentScope(), sti);
        symbolTable.remove(id);
        return true;
    }
    public static boolean checkIfLexemaIsDeclared (String id, String operand){
        String idWithScope = id+getFullCurrentScope();
        reversePolishStructure.add(idWithScope);
        if (operand != null)
            reversePolishStructure.add(operand);
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
        String idWithScope = id+getFullCurrentScope();
        reversePolishStructure.add(idWithScope);
        reversePolishStructure.add("CALL");
        String[] wordsInId = idWithScope.split("\\.");
        for (int i=wordsInId.length; i > 1 ; i--){
            if (symbolTable.contains(idWithScope)){
                symbolTable.addEntryCount(idWithScope);
                symbolTable.remove(id);
                if ((symbolTable.lookup(idWithScope).getUse() == EUse.FUNCTION_PARAM && !params) ||(symbolTable.lookup(idWithScope).getUse() == EUse.FUNCTION && params))
                    return false;
                return true;
            }
            int index = idWithScope.indexOf("."+wordsInId[i-1]);
            idWithScope = idWithScope.substring(0, index);
        }
        return false;
    }

    public static EDataType getLastDataType() {
        return lastVariableDataType;
    }

    public static void setLastDataType(EDataType lastVariableDataType) {
        ConfigurationParams.lastVariableDataType = lastVariableDataType;
    }

    public static EDataType getLastFunctionDataType() {
        return lastFunctionDataType;
    }

    public static void setLastFunctionDataType(EDataType lastFunctionDataType) {
        ConfigurationParams.lastFunctionDataType = lastFunctionDataType;
    }

    public static boolean checkIfIsFunction(String id) {
        id = id+"."+getCurrentScope();
        if (symbolTable.contains(id) && (symbolTable.lookup(id).getUse() == EUse.FUNCTION_PARAM || symbolTable.lookup(id).getUse() == EUse.FUNCTION))
            return true;
        else
            return false;
    }

    public static String getParameterNameFromFunction (String functionName){
        String functionNameWithScope = symbolTable.getSymbolTable().entrySet().stream().filter(k -> (k.getKey().startsWith(functionName) && k.getValue().getUse() == EUse.FUNCTION_PARAM)).findFirst().get().getKey();
        Entry<String, SymbolTableItem> item = symbolTable.getSymbolTable().entrySet().stream().filter(k -> (k.getKey().endsWith(functionNameWithScope) && k.getValue().getUse() == EUse.PARAMETER)).findFirst().get();
        String parameter = item == null? "": item.getKey();
        return parameter;
    }
    

}
