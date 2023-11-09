package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_ASIGN implements IAssemblerCode{

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        sb.append("     MOV eax, "+operandA+"\n");
        sb.append("     MOV "+operandB+", eax \n");
        return sb.toString();
    }
    
}
