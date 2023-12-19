package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_ITOUL implements IAssemblerCode {
    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     MOV ax, 0\n");
        sb.append("     CMP ax, "+operandA+"\n");
        sb.append("     JGE NegativeItoul \n");
        sb.append("     MOV eax, 0 \n");
        sb.append("     MOV ax, "+operandA+" \n");
        sb.append("     MOV "+variableName+", eax \n");
        return sb.toString();
    }
}
