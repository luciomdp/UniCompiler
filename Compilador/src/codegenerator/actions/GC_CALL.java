package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_CALL implements IAssemblerCode {

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     CALL  "+operandA+"\n");
        if (is32BitOperation)
            sb.append("     MOV "+variableName+", edx\n");
        else
            sb.append("     MOV "+variableName+", dx\n");
        
        return sb.toString();
    }
}
