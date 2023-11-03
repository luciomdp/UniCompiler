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
import codegenerator.actions.GC_MUL;
import codegenerator.actions.GC_SUB;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;

public class GenerateCodeComponent {

    public static List<String> binaryOperands;
    public static List<String> unaryOperands;
    private static Map<String, IAssemblerCode> mapAssemblerCode;
    private static Long count;
    private static File fileGenerated;
    private static StringBuilder sbHeader;
    private static StringBuilder sbData;
    private static StringBuilder sbCode;

    public GenerateCodeComponent () {
        binaryOperands = new ArrayList<>();
        unaryOperands = new ArrayList<>();
        binaryOperands.addAll(Arrays.asList("+", "-", "*", "/", ":=", "<", ">", ">=", "<="));
        unaryOperands.addAll(Arrays.asList("itoul"));
        count=0L;
        mapAssemblerCode = new HashMap <String, IAssemblerCode>();
        mapAssemblerCode.put("+", new GC_ADD());
        mapAssemblerCode.put(":=", new GC_EQUAL());
        mapAssemblerCode.put("-", new GC_SUB());
        mapAssemblerCode.put("*", new GC_MUL());
        mapAssemblerCode.put("/", new GC_DIV());
        sbHeader = new StringBuilder("");
        sbData = new StringBuilder("");
        sbCode = new StringBuilder("");
    }

    public static void generateAssemblerCode() {
        //Cabecera  
        generateHeader();
        //Código
        generateCode();
        //Declaración de variables (Va después del code así las variables están cargadas en la TS)
        generateVariableDeclaration();
        try {
            fileGenerated = new File("Files/CodeGenerated/finalCode.txt");
            if (!fileGenerated.exists()) 
                fileGenerated.createNewFile();
            FileWriter fileWriter = new FileWriter(fileGenerated, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(sbHeader.toString());
            bufferedWriter.write(sbData.toString());
            bufferedWriter.write(sbCode.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConfigurationParams.mainView.getFinalCodeViewer().appendData(sbHeader.toString());
        ConfigurationParams.mainView.getFinalCodeViewer().appendData(sbData.toString());
        ConfigurationParams.mainView.getFinalCodeViewer().appendData(sbCode.toString());
    }

    private static void generateHeader(){
        sbHeader.append(".586\n");
        sbHeader.append(".model flat, stdcall\n");
        sbHeader.append("option casemap :none\n");
        sbHeader.append("include \\masm32\\include\\windows.inc\n");
        sbHeader.append("include \\masm32\\include\\kernel32.inc\n");
        sbHeader.append("include \\masm32\\include\\user32.inc\n");
        sbHeader.append("includelib \\masm32\\lib\\kernel32.lib\n");
        sbHeader.append("includelib \\masm32\\lib\\user32.lib\n");
    }

    private static void generateVariableDeclaration(){
        sbData.append(".data\n");
        //Acá iría la declaración de todas las variables de la tabla de símbolos.
        ConfigurationParams.symbolTable.getSymbolTable().entrySet().forEach(entry -> {
            //Si la entrada a la tabla es una variable o constante
            if(entry.getValue().getDataType() != null){
                //Si es constante string. Donde está entry.getValue().toString() va el valor de la cadena
                if(entry.getValue().getDataType().equals(EDataType.STRING))
                    sbData.append(entry.getKey() + " db \"" + entry.getValue().toString() + "\", 0\n");
                //Si es entero
                if(entry.getValue().getDataType().equals(EDataType.INTEGER))
                    sbData.append(entry.getKey() + "dw ?\n");
                //Si es ulongint
                if(entry.getValue().getDataType().equals(EDataType.ULONGINT))
                    sbData.append(entry.getKey() + "dd ?\n");
            }
        });
    }

    private static void generateCode() {
        Stack<String> stack = new Stack<>();

        sbCode.append(".code\n");
        sbCode.append("start:\n");

        ConfigurationParams.reversePolishStructure.getReversePolishList().forEach(e -> {
            String operandA;
            String operandB;
            if (binaryOperands.contains(e)){
                operandA = stack.pop();
                operandB = stack.pop();
                stack.push(createAssemblerCode("_"+operandB, "_"+operandA, e));
            }
            else if (unaryOperands.contains(e))
                operandA = stack.pop();
            else 
                stack.push(e);
        });

        sbCode.append("end start\n");
    }

    private static String createAssemblerCode (String operandA, String operandB, String operator){
        count++;
        String variableName = "@aux"+count;
        ConfigurationParams.symbolTable.insert(variableName, null);
        String assemblerCode = "";
        
        SymbolTableItem symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
        SymbolTableItem symbolTableItemOperandB = ConfigurationParams.symbolTable.lookup(operandB);
        boolean is32BitOperation = false;
        if (operandB != null && symbolTableItemOperandA.getDataType() == symbolTableItemOperandB.getDataType()){
            if(symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue())
                is32BitOperation = true;
            else
                is32BitOperation = false;
            assemblerCode = "    " + mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName, is32BitOperation); //El tab es para identar el código
            sbCode.append(assemblerCode + "\n");
        } 
    
        return variableName;
    }
}
