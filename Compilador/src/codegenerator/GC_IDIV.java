package codegenerator;

public class GC_IDIV implements IAssemblerCode {
    @Override
    public String generateCode(String operandA, String operandB, String variableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("MOV eax, "+operandA+"\n");
        sb.append("MOV ebx, "+operandB+"\n");
        sb.append("IDIV eax, ebx\n");
        sb.append("MOV "+variableName+", eax \n");
        return sb.toString();
    }
}