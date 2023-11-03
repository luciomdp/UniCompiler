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

import objects.ConfigurationParams;

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
        mapAssemblerCode.put("*", new GC_IMUL());
        mapAssemblerCode.put("/", new GC_IDIV());
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
        String variableName = "@var"+count;
        ConfigurationParams.symbolTable.insert(variableName, null);
        try { 
            String assemblerCode = "    " + mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName); //El tab es para identar el código
            writer.write(assemblerCode);
            writer.newLine(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return variableName;
    }
}
