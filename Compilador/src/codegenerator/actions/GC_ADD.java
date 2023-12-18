package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_ADD implements IAssemblerCode {

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        if (is32BitOperation) {
            sb.append("     MOV eax, "+operandA+"\n");
            sb.append("     ADD eax, "+operandB+"\n");
            sb.append("     JC Overflow\n");
            sb.append("     MOV "+variableName+", eax \n");
        }
        else {
            sb.append("     MOV ax, "+operandA+"\n");
            sb.append("     ADD ax, "+operandB+"\n");
            sb.append("     JO Overflow\n");
            sb.append("     MOV "+variableName+", ax \n");            
        }
        return sb.toString();
    }
}
