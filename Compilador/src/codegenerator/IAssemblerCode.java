package codegenerator;

public interface IAssemblerCode {
    public String generateCode (String operandA, String operandB, String variableName, boolean is32BitOperation, String label);
}
