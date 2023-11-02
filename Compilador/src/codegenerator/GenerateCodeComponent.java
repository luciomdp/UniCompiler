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
        String operandA = "";
        String operandB = "";
        for (String element : ConfigurationParams.reversePolishStructure.getReversePolishList()){
            if (binaryOperands.contains(element)){
                operandA = stack.pop();
                operandB = stack.pop();
                stack.push(createAssemblerCode("_"+operandB, "_"+operandA, element));
            }
            else if (unaryOperands.contains(element)){
                operandA = stack.pop();
            }
            else {
                stack.push(element);
            } 
        }
    }
    public static String createAssemblerCode (String operandA, String operandB, String operator){
        count++;
        String variableName = "@var"+count;

        String fileName = "C:\\Programacion\\Propio\\CompiladorUNI\\Compilador\\src\\codegenerator\\assemblycode.txt";
        try {
            File file = new File(fileName);
            // Si el archivo no existe, lo crea
            if (!file.exists()) {
                file.createNewFile();
            }
            // Abre el archivo en modo de escritura (al final del archivo)
            FileWriter fileWriter = new FileWriter(file, true);
            // Crea un BufferedWriter para escribir en el archivo
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Contenido que deseas agregar al final del archivo
            String assemblerCode = mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName);
            // Escribe el contenido en el archivo
            bufferedWriter.write(assemblerCode);
            bufferedWriter.newLine(); // Agrega una nueva línea

            // Cierra el BufferedWriter y FileWriter
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return variableName;
    }
}
