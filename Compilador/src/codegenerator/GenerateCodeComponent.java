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
    }

    public static void generateAssemblerCode() {
        try {
            fileGenerated = new File("Files/CodeGenerated/finalCode.txt");
            if (!fileGenerated.exists()) 
                fileGenerated.createNewFile();
            FileWriter fileWriter = new FileWriter(fileGenerated, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            //Cabecera  
            generateHeader(bufferedWriter);

            //Declaración de variables
            generateVariableDeclaration(bufferedWriter);

            //Código
            generateCode(bufferedWriter);

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private static void generateHeader(BufferedWriter writer) throws IOException {
        writer.write(".586");
        writer.newLine();

        writer.write(".model flat, stdcall");
        writer.newLine();

        writer.write("option casemap :none");
        writer.newLine();

        writer.write("include \\masm32\\include\\windows.inc");
        writer.newLine();

        writer.write("include \\masm32\\include\\kernel32.inc");
        writer.newLine();

        writer.write("include \\masm32\\include\\user32.inc");
        writer.newLine();

        writer.write("includelib \\masm32\\lib\\kernel32.lib");
        writer.newLine();

        writer.write("includelib \\masm32\\lib\\user32.lib");
        writer.newLine();
    }

    private static void generateVariableDeclaration(BufferedWriter writer) throws IOException {
        writer.write(".data");
        writer.newLine();

        //Acá iría la declaración de todas las variables de la tabla de símbolos.
        ConfigurationParams.symbolTable.getSymbolTable().entrySet().forEach(entry -> {
            //Si la entrada a la tabla es una variable o constante
            if(entry.getValue().getDataType() != null){
                try {
                    //Si es constante string. Donde está entry.getValue().toString() va el valor de la cadena
                    if(entry.getValue().getDataType().equals(EDataType.STRING))
                        writer.write(entry.getKey() + " db \"" + entry.getValue().toString() + "\", 0");
                    //Si es entero
                    if(entry.getValue().getDataType().equals(EDataType.INTEGER))
                        writer.write(entry.getKey() + "dw ?");
                    //Si es ulongint
                    if(entry.getValue().getDataType().equals(EDataType.ULONGINT))
                        writer.write(entry.getKey() + "dd ?");
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void generateCode(BufferedWriter writer) throws IOException {
        Stack<String> stack = new Stack<>();

        writer.write(".code");
        writer.newLine();

        writer.write("start:");
        writer.newLine();

        ConfigurationParams.reversePolishStructure.getReversePolishList().forEach(e -> {
            String operandA;
            String operandB;
            if (binaryOperands.contains(e)){
                operandA = stack.pop();
                operandB = stack.pop();
                stack.push(createAssemblerCode("_"+operandB, "_"+operandA, e, writer));
            }
            else if (unaryOperands.contains(e)){
                operandA = stack.pop();
            }
            else {
                stack.push(e);
            }
        });

        writer.write("end start");
    }

    private static String createAssemblerCode (String operandA, String operandB, String operator, BufferedWriter writer){
        count++;
        String variableName = "@aux"+count;
        ConfigurationParams.symbolTable.insert(variableName, null);
        String assemblerCode = "";
        try {
            SymbolTableItem symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
            SymbolTableItem symbolTableItemOperandB = ConfigurationParams.symbolTable.lookup(operandB);
            boolean is32BitOperation = false;
            if (operandB != null && symbolTableItemOperandA.getDataType() == symbolTableItemOperandB.getDataType()){
                if(symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue())
                    is32BitOperation = true;
                else
                    is32BitOperation = false;
                
                assemblerCode = "    " + mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName, is32BitOperation); //El tab es para identar el código
                writer.write(assemblerCode);
                writer.newLine();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return variableName;
    }
}
