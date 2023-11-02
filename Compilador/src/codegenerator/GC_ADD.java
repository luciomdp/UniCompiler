package codegenerator;

public class GC_ADD implements IAssemblerCode {

    @Override
    public String generateCode(String operandA, String operandB, String variableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("MOV eax, "+operandA+"\n");
        sb.append("ADD eax, "+operandB+"\n");
        sb.append("MOV "+variableName+", eax \n");
        return sb.toString();
    }
}
