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

    public static void startGeneratingCode() {
        Stack<String> stack = new Stack<>();
        try {
            fileGenerated = new File("Files/CodeGenerated/finalCode.txt");
            if (!fileGenerated.exists()) 
                fileGenerated.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        ConfigurationParams.reversePolishStructure.getReversePolishList().forEach(e -> {
            String operandA;
            String operandB;
            if (binaryOperands.contains(e)){
                operandA = stack.pop();
                operandB = stack.pop();
                stack.push(createAssemblerCode("_"+operandB, "_"+operandA, e));
            }
            else if (unaryOperands.contains(e)){
                operandA = stack.pop();
            }
            else {
                stack.push(e);
            }
        });
    }
    public static String createAssemblerCode (String operandA, String operandB, String operator){
        count++;
        String variableName = "@var"+count;
        ConfigurationParams.symbolTable.insert(variableName, null);
        try {
            FileWriter fileWriter = new FileWriter(fileGenerated, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String assemblerCode = mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName);
            bufferedWriter.write(assemblerCode);
            bufferedWriter.newLine(); 
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return variableName;
    }
}
