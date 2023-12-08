package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_ASIGN implements IAssemblerCode{

    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        if (is32BitOperation){
            sb.append("     MOV eax, "+operandA+"\n");
            sb.append("     MOV "+operandB+", eax \n");
        }
        else {
            sb.append("     MOV ax, "+operandA+"\n");
            sb.append("     MOV "+operandB+", ax \n");            
        }
        return sb.toString();
    }
    
}
