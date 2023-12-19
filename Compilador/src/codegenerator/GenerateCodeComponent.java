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
import codegenerator.actions.GC_CALL;
import codegenerator.actions.GC_GREATER;
import codegenerator.actions.GC_GREATER_EQUAL;
import codegenerator.actions.GC_ITOUL;
import codegenerator.actions.GC_JUMP;
import codegenerator.actions.GC_LESS;
import codegenerator.actions.GC_LESS_EQUAL;
import codegenerator.actions.GC_MUL;
import codegenerator.actions.GC_NOT_EQUAL;
import codegenerator.actions.GC_PRINT;
import codegenerator.actions.GC_RETURN;
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
    private File fileGeneratedDirectory;
    private File fileGenerated;
    private StringBuilder sbHeader;
    private StringBuilder sbData;
    private StringBuilder sbCode;
    private boolean errorOcurred = false;

    public GenerateCodeComponent () {
        binaryOperands = new ArrayList<>();
        unaryOperands = new ArrayList<>();
        binaryOperands.addAll(Arrays.asList("+", "-", "*", "/", ":="));
        unaryOperands.addAll(Arrays.asList("itoul", "print", "CALL", "return"));
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
        mapAssemblerCode.put("CALL", new GC_CALL());
        mapAssemblerCode.put("return", new GC_RETURN());
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
            String directoryPath = "Files/CodeGenerated/";
            fileGeneratedDirectory = new File(directoryPath);
            
            if (!fileGeneratedDirectory.exists()) 
                fileGeneratedDirectory.mkdirs();

            fileGenerated = new File(directoryPath+ ConfigurationParams.getOutputFileName().replace(".txt", ".asm"));
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
        //Agrego la definición del Título de los prints
        sbData.append("     TitPrint db \"Imprimir\", 0\n");
        sbData.append("     TitError db \"Error\", 0\n");
        sbData.append("     msgZeroDivision db \"Error: division por cero\", 0\n");
        sbData.append("     msgOverflow db \"Error: overflow\", 0\n");
        sbData.append("     msgNegativeItoul db \"Error: perdida de informacion por conversion\", 0\n");
        //Acá iría la declaración de todas las variables de la tabla de símbolos.
        ConfigurationParams.symbolTable.getSymbolTable().entrySet().forEach(entry -> {
            //Si es STRING.
            if(entry.getValue().getDataType() != null && entry.getValue().getDataType().equals(EDataType.STRING))
                sbData.append("     " + entry.getKey() + " db \"" + entry.getValue().getValue() + "\", 0\n");
            //Si es variable
            if(entry.getValue().getUse() != null && (entry.getValue().getUse().equals(EUse.VARIABLE) || entry.getValue().getUse().equals(EUse.PARAMETER))) {
                //Si es entero
                if(entry.getValue().getDataType().equals(EDataType.INTEGER))
                    sbData.append("     " + "_"+entry.getKey().replace(".", "_") + " dw ?\n");
                //Si es ulongint
                if(entry.getValue().getDataType().equals(EDataType.ULONGINT))
                    sbData.append("     " + "_"+entry.getKey().replace(".", "_") + " dd ?\n");
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
        sbCode.append(".code\n");
        String keyToRemove;
        //Escribo las funciones de atrás para adelante
        while(ConfigurationParams.reversePolishStructure.getReversePolishListSize() != 1) {
            keyToRemove = ConfigurationParams.reversePolishStructure.getLastKey();
            generateCode(keyToRemove, ConfigurationParams.reversePolishStructure.removePolish(keyToRemove));
        }  

        //Escribo el programa principal
        sbCode.append("start:\n");
        generateCode(null, ConfigurationParams.reversePolishStructure.removePolish(ConfigurationParams.reversePolishStructure.getLastKey()));
        if(!errorOcurred) {
            sbCode.append("     invoke ExitProcess, 0\n");
            //Códigos de error
            sbCode.append("ZeroDivision: \n");
            sbCode.append("     invoke MessageBox, NULL, addr msgZeroDivision , addr TitError, MB_OK \n");
            sbCode.append("     invoke ExitProcess, 0\n");
            sbCode.append("Overflow: \n");
            sbCode.append("     invoke MessageBox, NULL, addr msgOverflow , addr TitError, MB_OK \n");
            sbCode.append("     invoke ExitProcess, 0\n");
            sbCode.append("NegativeItoul: \n");
            sbCode.append("     invoke MessageBox, NULL, addr msgNegativeItoul , addr TitError, MB_OK \n");
            sbCode.append("     invoke ExitProcess, 0\n");

            //Fin del programa
            sbCode.append("end start\n");
        }        
    }

    private void generateCode(String fname, List<String> reversepolish) {
        Stack<String> stack = new Stack<>();
        if(fname != null) //Si no es funcion
            sbCode.append(fname + ":\n");
        for (String e: reversepolish){
            String operandA;
            String operandB;
            String stackitem;
            if (binaryOperands.contains(e)){
                operandA = stack.pop();
                operandB = stack.pop();
                stackitem = createAssemblerCodeForBinaryOperators(operandB, operandA, e);
                if (stackitem == null)
                    return;
                stack.push(stackitem);
            }
            else if (unaryOperands.contains(e)){
                operandA = stack.pop();
                stackitem = createAssemblerCodeForUnaryOperators(operandA, e);
                if (stackitem == null)
                    return;
                stack.push(stackitem);
            }
            else if (e.equals("JUMP")){
                String label = "Label_"+stack.pop();
                // pasa null en ambos operandos porque salta siempre, no debemos hacer una comparación y tomar la decisión
                writeCodeForCondicions(e, null, null, label, false);
            }
            else if (e.equals("BF")){
                // nro de label al que debemos saltar
                String labelName = "Label_"+stack.pop();
                String comparativeOperator = stack.pop();
                operandA = stack.pop();
                operandB = stack.pop();
                // pasa los dos operandos que van a ser comparados con el opreador, si se cumple salta al label
                stackitem = createAssemblerCodeForBF(operandB, operandA, comparativeOperator, labelName);
                if (stackitem == null)
                    return;
            }
            else if (e.startsWith("Label_")){
                // escribo directamente el nombre del label porque no hay operador de por medio
                writeLabelName(e+":");
            }
            else 
                stack.push(e);
        };
        if(fname != null) //Si no es funcion
            sbCode.append("ret \n");
    }

    private String createAssemblerCodeForUnaryOperators(String operandA, String operator) {
        count++;
        boolean is32BitOperation = false;
        String variableName = "@aux"+count;
        SymbolTableItem symbolTableItemOperandA, symbolTableItemOperandB, symbolTableItemVariable = new SymbolTableItem(null, null);
        symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
        operandA = renameOperand(operandA, symbolTableItemOperandA);
        if (operator.equals("itoul")){            
            if(symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()){ 
                symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, EDataType.ULONGINT, EUse.VARIABLE_ASSEMBLER);
            }
            else if (symbolTableItemOperandA.getDataType().getValue() == EDataType.STRING.getValue()){
                symbolTableItemVariable = new SymbolTableItem(ETokenType.STRING_CONST, EDataType.STRING, EUse.VARIABLE_ASSEMBLER);
            }
            else{
                errorOcurred = true;
                sbCode = new StringBuilder("Error: no se puede convertir el tipo de dato ULONGINT");
                return null;                
            }     
            writeCode(operator, operandA, null, variableName, true);
        }
        else if (operator.equals("CALL")){
            symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, symbolTableItemOperandA.getDataType(), EUse.VARIABLE_ASSEMBLER);
            operandA = getFunctionName(operandA);
            writeCode(operator, operandA, null, variableName, symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()?false:true);
        }
        else if (operator.equals("return")) {
            writeCode(operator, operandA, null, null, symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()?false:true );
        }
        else if (operator.equals("print")){
            writeCode(operator, operandA, null, null, false);
        }
        ConfigurationParams.symbolTable.insert(variableName, symbolTableItemVariable);
        return variableName;
    }


    private String getFunctionName(String operandA) {
        int indiceGuionBajo = operandA.indexOf('_');
        String resultado= "";
        if (indiceGuionBajo != -1) 
            resultado = operandA.substring(0, indiceGuionBajo);
        return resultado;
    }

    private String createAssemblerCodeForBinaryOperators (String operandA, String operandB, String operator){
        count++;
        String variableName = "@aux"+count;
        SymbolTableItem symbolTableItemOperandA, symbolTableItemOperandB, symbolTableItemVariable = new SymbolTableItem(null, null);
        symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
        symbolTableItemOperandB = ConfigurationParams.symbolTable.lookup(operandB);
        operandA = renameOperand(operandA, symbolTableItemOperandA);
        operandB = renameOperand(operandB, symbolTableItemOperandB);
        boolean is32BitOperation = false;
        if (symbolTableItemOperandA.getDataType() == symbolTableItemOperandB.getDataType()) {
            if(symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()){
                symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, EDataType.INTEGER, EUse.VARIABLE_ASSEMBLER);
                is32BitOperation = false;
            }else{
                symbolTableItemVariable = new SymbolTableItem(ETokenType.ID, EDataType.ULONGINT, EUse.VARIABLE_ASSEMBLER);
                is32BitOperation = true;
            }
                
            writeCode(operator, operandA, operandB, variableName, is32BitOperation);
        }
        else{
            errorOcurred = true;
            sbCode = new StringBuilder("Error: incompatibilidad en los tipos de datos de las variables "+ operandA + " y "+ operandB);
            return null;
        }
        
        ConfigurationParams.symbolTable.insert(variableName, symbolTableItemVariable);
        return variableName;
    }

    private String createAssemblerCodeForBF (String operandA, String operandB, String operator, String label){
        count++;
        String variableName = "@aux"+count;
        SymbolTableItem symbolTableItemOperandA, symbolTableItemOperandB = new SymbolTableItem(null, null);
        symbolTableItemOperandA = ConfigurationParams.symbolTable.lookup(operandA);
        symbolTableItemOperandB = ConfigurationParams.symbolTable.lookup(operandB);
        operandA = renameOperand(operandA, symbolTableItemOperandA);
        operandB = renameOperand(operandB, symbolTableItemOperandB);
        if (symbolTableItemOperandA.getDataType() == symbolTableItemOperandB.getDataType())
            writeCodeForCondicions(operator, operandA, operandB, label, symbolTableItemOperandA.getDataType().getValue() == EDataType.INTEGER.getValue()?false:true);
        else {
            errorOcurred = true;
            sbCode = new StringBuilder("Error: incompatibilidad en los tipos de datos de las variables "+ operandA + " y "+ operandB);
            return null;
        }
        return variableName;
    }

    private void writeCode (String operator, String operandA, String operandB, String variableName, boolean is32BitOperation){
        String assemblerCode = "";
        assemblerCode = mapAssemblerCode.get(operator).generateCode(operandA, operandB, variableName, is32BitOperation, null); //El tab es para identar el código
        sbCode.append(assemblerCode + "\n");
    }
    private void writeCodeForCondicions (String operator, String operandA, String operandB, String label, boolean is32BitOperation){
        String assemblerCode = "";
        assemblerCode = mapAssemblerCode.get(operator).generateCode(operandA, operandB, null, is32BitOperation, label); //El tab es para identar el código
        sbCode.append(assemblerCode + "\n");
    }
    private void writeLabelName(String label) {
        sbCode.append(label + "\n");
    }

    private String renameOperand(String operand, SymbolTableItem symbolTableItem) {
        if (symbolTableItem.getUse() == EUse.VARIABLE || symbolTableItem.getUse() == EUse.PARAMETER)
            return "_"+operand.replace(".", "_");
        else
            return operand.replace(".", "_");
    }
}
