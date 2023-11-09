package codegenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import codegenerator.actions.GC_ADD;
import codegenerator.actions.GC_DIV;
import codegenerator.actions.GC_EQUAL;
import codegenerator.actions.GC_ASIGN;
import codegenerator.actions.GC_GREATER;
import codegenerator.actions.GC_GREATER_EQUAL;
import codegenerator.actions.GC_ITOUL;
import codegenerator.actions.GC_JUMP;
import codegenerator.actions.GC_LESS;
import codegenerator.actions.GC_LESS_EQUAL;
import codegenerator.actions.GC_MUL;
import codegenerator.actions.GC_NOT_EQUAL;
import codegenerator.actions.GC_PRINT;
import codegenerator.actions.GC_SUB;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.enums.EUse;

public class GenerateCodeComponent {

    private List <String> binaryOperands;
    private List<String> unaryOperands;
    private Map<String, IAssemblerCode> mapAssemblerCode;
    private Long count;
    private File fileGenerated;
    private StringBuilder sbHeader;
    private StringBuilder sbData;
    private StringBuilder sbCode;
    private boolean errorOcurred = false;

    public GenerateCodeComponent () {
        binaryOperands = new ArrayList<>();
        unaryOperands = new ArrayList<>();
        binaryOperands.addAll(Arrays.asList("+", "-", "*", "/", ":="));
        unaryOperands.addAll(Arrays.asList("itoul", "print"));
        count=0L;
        mapAssemblerCode = new HashMap <String, IAssemblerCode>();
        mapAssemblerCode.put("+", new GC_ADD());
        mapAssemblerCode.put(":=", new GC_ASIGN());
        mapAssemblerCode.put("-", new GC_SUB());
        mapAssemblerCode.put("*", new GC_MUL());
        mapAssemblerCode.put("/", new GC_DIV());
        mapAssemblerCode.put("itoul", new GC_ITOUL());
        mapAssemblerCode.put("print", new GC_PRINT());
        mapAssemblerCode.put("<", new GC_LESS());
        mapAssemblerCode.put("<=", new GC_LESS_EQUAL());
        mapAssemblerCode.put(">", new GC_GREATER());
        mapAssemblerCode.put(">=", new GC_GREATER_EQUAL());
        mapAssemblerCode.put("<>", new GC_NOT_EQUAL());
        mapAssemblerCode.put("=", new GC_EQUAL());
        mapAssemblerCode.put("JUMP", new GC_JUMP());
        sbHeader = new StringBuilder("");
        sbData = new StringBuilder("");
        sbCode = new StringBuilder("");
    }

    public void generateAssemblerCode() {
        //Cabecera  
        generateHeader();
        //Código
        if (!ConfigurationParams.areErrors())
            generateCode();
        else {
            errorOcurred = true;
            sbCode = new StringBuilder("No se ha podido generar el codigo debido a la ocurrencia de errores en el codigo fuente ");
        }   
        //Declaración de variables (Va después del code así las variables están cargadas en la TS)
        generateVariableDeclaration();
        try {
            fileGenerated = new File("Files/CodeGenerated/" + ConfigurationParams.getOutputFileName().replace(".txt", ".asm"));
            if (fileGenerated.exists()) 
                fileGenerated.delete();
            fileGenerated.createNewFile();
                
            FileWriter fileWriter = new FileWriter(fileGenerated, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            if(!errorOcurred) {
                ConfigurationParams.mainView.getFinalCodeViewer().appendData(sbHeader.toString());
                ConfigurationParams.mainView.getFinalCodeViewer().appendData(sbData.toString());
                ConfigurationParams.mainView.getFinalCodeViewer().appendData(sbCode.toString());
                bufferedWriter.write(sbHeader.toString());
                bufferedWriter.write(sbData.toString());
            }else {
                ConfigurationParams.mainView.getFinalCodeViewer().appendError(sbCode.toString());
            }
            
            bufferedWriter.write(sbCode.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }

    private void generateHeader(){
        sbHeader.append(".586\n");
        sbHeader.append(".model flat, stdcall\n");
        sbHeader.append("option casemap :none\n");
        sbHeader.append("include \\masm32\\include\\windows.inc\n");
        sbHeader.append("include \\masm32\\include\\kernel32.inc\n");
        sbHeader.append("include \\masm32\\include\\user32.inc\n");
        sbHeader.append("includelib \\masm32\\lib\\kernel32.lib\n");
        sbHeader.append("includelib \\masm32\\lib\\user32.lib\n");
    }

    private void generateVariableDeclaration(){
        sbData.append(".data\n");
        //Acá iría la declaración de todas las variables de la tabla de símbolos.
        ConfigurationParams.symbolTable.getSymbolTable().entrySet().forEach(entry -> {
            //Si es STRING.
            if(entry.getValue().getDataType() != null && entry.getValue().getDataType().equals(EDataType.STRING))
                sbData.append("     " + entry.getKey() + " db \"" + entry.getValue().getValue() + "\", 0\n");
            //Si es variable
            if(entry.getValue().getUse() != null && entry.getValue().getUse().equals(EUse.VARIABLE)) {
                //Si es entero
                if(entry.getValue().getDataType().equals(EDataType.INTEGER))
                    sbData.append("     " + "_"+entry.getKey() + " dw ?\n");
                //Si es ulongint
                if(entry.getValue().getDataType().equals(EDataType.ULONGINT))
                    sbData.append("     " + "_"+entry.getKey() + " dd ?\n");
            }
            if(entry.getValue().getUse() != null && entry.getValue().getUse().equals(EUse.VARIABLE_ASSEMBLER)) {
                //Si es entero
                if(entry.getValue().getDataType().equals(EDataType.INTEGER))
                    sbData.append("     " + entry.getKey() + " dw ?\n");
                //Si es ulongint
                if(entry.getValue().getDataType().equals(EDataType.ULONGINT))
                    sbData.append("     " + entry.getKey() + " dd ?\n");
            }            
        });
    }

    private void generateCode() {
        Stack<String> stack = new Stack<>();

        sbCode.append(".code\n");
        sbCode.append("start:\n");
        for (String e: ConfigurationParams.reversePolishStructure.merge()){
            String operandA;
            String operandB;
            String stackitem;
            if (binaryOperands.contains(e)){
                operandA = stack.pop();
                operandB = stack.pop();
                stackitem = createAssemblerCode(operandB, operandA, e);
                if (stackitem == null)
                    return;
                stack.push(stackitem);
            }
            else if (unaryOperands.contains(e)){
                operandA = stack.pop();
                stackitem = createAssemblerCode(operandA, null, e);
                if (stackitem == null)
                    return;
                stack.push(stackitem);
            }
            else if (e.equals("JUMP")){
                String label = "Label_"+stack.pop();
                createAssemblerCodeForConditions(null, null, e, label);
            }
            else if (e.equals("BF")){
                // nro de label al que debemos saltar
                String labelName = "Label_"+stack.pop();
                String comparativeOperator = stack.pop();
                operandA = stack.pop();
                operandB = stack.pop();
                // aca habría que generar el codigo para los operadores condicionales
                stackitem = createAssemblerCodeForConditions(operandB, operandA, comparativeOperator, labelName);
                if (stackitem == null)
                    return;
            }
            else if (e.startsWith("Label_")){
                writeLabelName(e);
            }
            else 
                stack.push(e);
        };

        sbCode.append("     invoke ExitProcess, 0\n");
        sbCode.append("end start\n");
    }

    private String createAssemblerCode (String operandA, String operandB, String operator){
        count++;
        String variableName = "@aux"+count;
        
        SymbolTableItem symbolTableItemOperandA, symbolTableItemOperandB, symbolTableItemVariable = new SymbolTableItem(null, null);
        symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
        operandA = renameOperand(operandA, symbolTableItemOperandA);
        if (operandB != null){
            symbolTableItemOperandB = ConfigurationParams.symbolTable.lookup(operandB);
            operandB = renameOperand(operandB, symbolTableItemOperandB);
            boolean is32BitOperation = false;
            if (symbolTableItemOperandA.getDataType() == symbolTableItemOperandB.getDataType()) {
                if(symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()){
                    symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, EDataType.INTEGER, EUse.VARIABLE_ASSEMBLER);
                    is32BitOperation = true;
                }else{
                    symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, EDataType.ULONGINT, EUse.VARIABLE_ASSEMBLER);
                    is32BitOperation = false;
                }
                    
                writeCode(operator, operandA, operandB!=null?operandB:null, variableName, is32BitOperation);
            }
            else{
                errorOcurred = true;
                sbCode = new StringBuilder("Error: incompatibilidad en los tipos de datos de las variables "+ operandA + " y "+ operandB);
                return null;
            }
        }
        else {          
            if(symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()) 
                symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, EDataType.ULONGINT, EUse.VARIABLE);
            else if (symbolTableItemOperandA.getDataType().getValue() == EDataType.STRING.getValue())
                symbolTableItemVariable = new SymbolTableItem(ETokenType.STRING_CONST, EDataType.STRING, EUse.VARIABLE);
            else{
                errorOcurred = true;
                sbCode = new StringBuilder("Error: no se puede convertir el tipo de dato ULONGINT");
                return null;                
            }     
            writeCode(operator, operandA, operandB, variableName, false);
        }
    
        ConfigurationParams.symbolTable.insert(variableName, symbolTableItemVariable);
        return variableName;
    }
    private String renameOperand(String operand, SymbolTableItem symbolTableItem) {
        if (symbolTableItem.getUse() == EUse.VARIABLE)
            return "_"+operand;
        else
            return operand;
    }

    private String createAssemblerCodeForConditions (String operandA, String operandB, String operator, String label){
        count++;
        String variableName = "@aux"+count;
        SymbolTableItem symbolTableItemOperandA, symbolTableItemOperandB, symbolTableItemVariable = new SymbolTableItem(null, null);
        if (operandA != null && operandB != null){
            symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
            symbolTableItemOperandB = ConfigurationParams.symbolTable.lookup(operandB);
            
            if (symbolTableItemOperandA.getDataType() == symbolTableItemOperandB.getDataType()) 
                writeCodeForCondicions(operator, operandA, operandB, label);
            else {
                errorOcurred = true;
                sbCode = new StringBuilder("Error: incompatibilidad en los tipos de datos de las variables "+ operandA + " y "+ operandB);
                return null;
            }
        }
        else 
            writeCodeForCondicions(operator, null, null, label);

        return variableName;
    }
    private void writeCode (String operator, String operandA, String operandB, String variableName, boolean is32BitOperation){
        String assemblerCode = "";
        assemblerCode = mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName, is32BitOperation, null); //El tab es para identar el código
        sbCode.append(assemblerCode + "\n");
    }
    private void writeCodeForCondicions (String operator, String operandA, String operandB, String label){
        String assemblerCode = "";
        assemblerCode = mapAssemblerCode.get(operator).generateCode(operandA, operandB, null, false, label); //El tab es para identar el código
        sbCode.append(assemblerCode + "\n");
    }
    private void writeLabelName(String label) {
        sbCode.append(label + "\n");
    }
}
