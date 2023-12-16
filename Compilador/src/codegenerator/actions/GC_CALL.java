package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_CALL implements IAssemblerCode {

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     CALL  "+operandA+"\n");
        sb.append("     MOV "+variableName+", eax\n");
        return sb.toString();
    }
}
