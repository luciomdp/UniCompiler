package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_SUB implements IAssemblerCode{

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        if (is32BitOperation) {
            sb.append("     MOV eax, "+operandA+"\n");
            sb.append("     SUB eax, "+operandB+"\n");
            sb.append("     MOV "+variableName+", eax \n");
        }
        else {
            sb.append("     MOV ax, "+operandA+"\n");
            sb.append("     SUB ax, "+operandB+"\n");
            sb.append("     MOV "+variableName+", ax \n");            
        }
        return sb.toString();
    }

}
