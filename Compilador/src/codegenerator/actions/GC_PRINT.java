package codegenerator.actions;

import codegenerator.IAssemblerCode;

public class GC_PRINT implements IAssemblerCode {
    @Override
    public String generateCode(String operandA, String operandB, String variableName, boolean is32BitOperation) {
        StringBuilder sb = new StringBuilder();
        sb.append("     invoke MessageBox, NULL, addr "+operandA+" , addr Print, MB_OK");
        return sb.toString();
    }
}

