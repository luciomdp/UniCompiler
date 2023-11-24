package objects;

import java.util.Map.Entry;
import java.util.Optional;
import codegenerator.GenerateCodeComponent;
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
    public static GenerateCodeComponent generateCodeComponent;
    private static String outputFileName;
    private static Long errorCount;
    private static Long labelCount;

    public ConfigurationParams (Boolean production) {
        if(production){
            VIEW_TOKEN_NUMBER = true;
        }
        symbolTable = new SymbolTable();    
        lexicalAnalizer = new LexicalAnalizer();
        generateCodeComponent = new GenerateCodeComponent();
        reversePolishStructure = new ReversePolishStructure();
        currentScope = new StringBuilder();
        mainView = new MainView();
        errorCount = 0L;
        labelCount = 1L;
    }

    public static void updateSymbolTableView() {
        mainView.getSymbolTableViewer().updateTable();
    }
    public static void updateReversePolishView(String reversePolish,String newVal,int index) {
        mainView.getReversePolishViewer().updateTable(reversePolish,newVal,index);
    }
    public static void addScope(String newScope) {
        StringBuilder newCurrentScope = new StringBuilder(newScope);
        if (!currentScope.toString().equals(""))
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
        String scope = getFullCurrentScope();
        String[] wordsInId = scope.split("\\.");
        for (int i=0; i < wordsInId.length-1 ; i++){
            if (symbolTable.contains(id+scope)){
                symbolTable.addEntryCount(id+scope);
                symbolTable.remove(id);
                reversePolishStructure.add(id+scope);
                if (operand != null)
                    reversePolishStructure.add(operand);
                return true;
            }
            int index = scope.indexOf("."+wordsInId[i]);
            int endIndex = index + wordsInId[i+1].length()+1;
            scope = scope.substring(endIndex, scope.length());
        }
        reversePolishStructure.add(id+getFullCurrentScope());
        if (operand != null)
            reversePolishStructure.add(operand);
        return false;
    }
    public static boolean checkIfFunctionIsDeclared (String id, boolean params){
        String scope = getFullCurrentScope();
        String[] wordsInId = scope.split("\\.");
        for (int i=0; i < wordsInId.length-1 ; i++){
            if (symbolTable.contains(id+scope)){
                symbolTable.addEntryCount(id+scope);
                symbolTable.remove(id);
                if ((symbolTable.lookup(id+scope).getUse() == EUse.FUNCTION_PARAM && !params) ||(symbolTable.lookup(id+scope).getUse() == EUse.FUNCTION && params)){
                    reversePolishStructure.add(id+getFullCurrentScope());
                    reversePolishStructure.add("CALL");
                    return false;
                }
                reversePolishStructure.add(id+scope);
                reversePolishStructure.add("CALL");
                return true;
            }
            int index = scope.indexOf("."+wordsInId[i]);
            int endIndex = index + wordsInId[i+1].length()+1;
            scope = scope.substring(endIndex, scope.length());
        }
        reversePolishStructure.add(id+getFullCurrentScope());
        reversePolishStructure.add("CALL");
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
        Optional<Entry<String, SymbolTableItem>> function =symbolTable.getSymbolTable().entrySet().stream().filter(k -> (k.getKey().startsWith(functionName) && k.getValue().getUse() == EUse.FUNCTION_PARAM)).findFirst();
        String parameter = "";
        if (function.isPresent()){
            String functionNameWithScope = function.get().getKey();
            Entry<String, SymbolTableItem> item = symbolTable.getSymbolTable().entrySet().stream().filter(k -> (k.getKey().endsWith(functionNameWithScope) && k.getValue().getUse() == EUse.PARAMETER)).findFirst().get();
            parameter = item == null? "-": item.getKey();
        }
        return parameter;
    }

    public static void setOutputFileName(String name) {
        outputFileName = name;
    }

    public static String getOutputFileName() {
        return outputFileName;
    }
    public static void addError (){
        errorCount++;
    }
    public static boolean areErrors (){
        if (errorCount > 0)
            return true;
        else
            return false;
    }
    public static Long getLabelCount() {
        return labelCount;
    }
    public static void increaseLabelCount() {
        labelCount = labelCount+1;
    }
    
}
