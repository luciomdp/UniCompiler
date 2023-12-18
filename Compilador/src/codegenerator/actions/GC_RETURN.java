package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_RETURN implements IAssemblerCode {

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        if (is32BitOperation)
            sb.append("     MOV edx, "+operandA+"\n");
        else{
            sb.append("     MOV edx, 0 \n");
            sb.append("     MOV dx, "+operandA+"\n");
        }
        return sb.toString();
    }
}
