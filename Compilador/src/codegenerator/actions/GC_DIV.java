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
            sb.append("     MOV ecx, 0\n");
            sb.append("     MOV ecx, "+operandB+" \n");
            sb.append("     MOV edx, 0 \n");
            sb.append("     MOV eax, "+operandA+"\n");
            sb.append("     CMP edx, ecx \n");
            sb.append("     JE ZeroDivision \n"); 
            sb.append("     DIV ecx \n");
            sb.append("     MOV "+variableName+", eax \n");
        }
        else {
            sb.append("     MOV ecx, 0\n");
            sb.append("     MOV cx, "+operandB+" \n");
            sb.append("     MOV edx, 0 \n");
            sb.append("     MOV ax, "+operandA+"\n");
            sb.append("     CMP dx, cx \n");
            sb.append("     JE ZeroDivision \n"); 
            sb.append("     DIV cx\n");
            sb.append("     MOV "+variableName+", ax \n");
        }
        return sb.toString();
    }
}
