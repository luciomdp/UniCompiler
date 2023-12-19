package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_GREATER implements IAssemblerCode{

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        if (is32BitOperation){
            sb.append("     MOV eax, "+operandA+"\n");
            sb.append("     CMP eax, "+operandB+"\n");
        }
        else {
            sb.append("     MOV ax, "+operandA+"\n");
            sb.append("     CMP ax, "+operandB+"\n");
        }
        sb.append("     JLE "+label+"\n");
        return sb.toString();
    }
    
}
