package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_DIV implements IAssemblerCode {
    /*
     Este es para ulongint
     */
    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation, String label) {
        StringBuilder sb = new StringBuilder();
        if (is32BitOperation){
            sb.append("     MOV edx, 0 \n");
            sb.append("     MOV eax, "+operandA+"\n");
            sb.append("     CMP "+operandB+", edx \n");
            sb.append("     JE ZeroDivision \n"); 
            sb.append("     DIV "+operandB+"\n");
            sb.append("     MOV "+variableName+", eax \n");
        }
        else {
             sb.append("     MOV edx, 0 \n");
            sb.append("     MOV ax, "+operandA+"\n");
            sb.append("     CWD \n");
            sb.append("     CMP "+operandB+", edx \n");
            sb.append("     JE ZeroDivision \n"); 
            sb.append("     IDIV "+operandB+"\n");
            sb.append("     MOV "+variableName+", ax \n");
        }
        return sb.toString();
    }
}
