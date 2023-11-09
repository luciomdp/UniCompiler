package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_LESS implements IAssemblerCode{

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     CMP "+operandA+", "+operandB+"\n");
        sb.append("     JGE "+label+"\n");
        return sb.toString();
    }
    
}
