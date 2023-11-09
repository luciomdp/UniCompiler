package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_ITOUL implements IAssemblerCode {
    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     CMP "+operandA+", 0\n");
        sb.append("     JL err_por_negativo \n");
        sb.append("     MOV eax, 0 \n");
        sb.append("     MOV ax, "+operandA+" \n");
        sb.append("     MOV "+variableName+", eax \n");
        return sb.toString();
    }
}
