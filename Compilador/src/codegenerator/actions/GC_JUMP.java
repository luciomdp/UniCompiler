package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_JUMP implements IAssemblerCode{
    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     JMP "+label+"\n");
        return sb.toString();
    }
    
}
