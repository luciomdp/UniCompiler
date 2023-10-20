package objects;

import objects.enums.EDataType;
import objects.enums.ETokenType;

public class SemanticParserActions {
    
    //---------------------- << bloque >> ----------------------
    // nombre_programa BEGIN sentencias END
    public void ON_bloque1_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("--------------------------- << Fin del análisis sintáctico >> ---------------------------");
    }

    //---------------------- << nombre_programa >> ----------------------
    // ID 
    public void ON_nombre_programa1_End(String id) {
        ConfigurationParams.addScope(id);
    }

    //---------------------- << sentencia_ejecutable >> ----------------------
    // asignacion 
    public void ON_sentencia_ejecutable2_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }
    // impresion 
    public void ON_sentencia_ejecutable3_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }
    // error 
    public void ON_sentencia_ejecutable6_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendError("Error de sentencia ejecutable\n");
    }

    //---------------------- << cabecera_funcion_parametro >> ----------------------
    // tipo token_fun ID '(' tipo ID ')'
    public void ON_cabecera_funcion_parametro1_End(String id, String param) {
        ConfigurationParams.reversePolishStructure.add(id);
        if (!ConfigurationParams.renameFunctionWithScope(id, true))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' ya fue declarada previamente en este ámbito \n");
        else{
            ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función con parametro linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
        }
        ConfigurationParams.addScope(id);
        ConfigurationParams.renameLexemaWithScope(param);
    }

    public void ON_cabecera_funcion_parametro2_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: declaración de función inválida \n");
    }

    //---------------------- << cabecera_funcion >> ----------------------
    // tipo token_fun ID '(' ')'
    public void ON_cabecera_funcion1_End(String id) {
        ConfigurationParams.reversePolishStructure.add(id);
        if (!ConfigurationParams.renameFunctionWithScope(id, false))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' ya fue declarada previamente en este ámbito \n");
        else{
            ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función sin parametro linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
        }
        ConfigurationParams.addScope(id);
    }

    //---------------------- << fin_funcion >> ----------------------
    // END 
    public void ON_fin_funcion1_End() {
        ConfigurationParams.removeScope();
    }

    //---------------------- << declaracion_variables >> ----------------------
    // tipo variables ';' 
    public void ON_declaracion_variables1_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }

    //---------------------- << variable >> ----------------------
    // ID 
    public void ON_variable1_End(String id) {
        // Debo primero verificar que no sea existente, de serlo arrojar un error. 
        if (!ConfigurationParams.renameLexemaWithScope(id))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' ya fue declarada previamente en este ámbito \n");
    }

    //---------------------- << asignacion >> ----------------------
    // ID ASIGNACION expresion ';'
    public void ON_asignacion1_End(String id) {
        ConfigurationParams.reversePolishStructure.add(id);
        ConfigurationParams.reversePolishStructure.add(":=");
        if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
    }

    //---------------------- << expresion >> ----------------------
    // expresion '+' termino
    public void ON_expresion2_End() {
        ConfigurationParams.reversePolishStructure.add("+");
    }
    // expresion '-' termino
    public void ON_expresion3_End() {
        ConfigurationParams.reversePolishStructure.add("-");
    }

    //---------------------- << termino >> ----------------------
    // termino '*' factor
    public void ON_termino2_End() {
        ConfigurationParams.reversePolishStructure.add("*");
    }
    // termino '/' factor
    public void ON_termino3_End() {
        ConfigurationParams.reversePolishStructure.add("/");
    }

    //---------------------- << factor >> ----------------------
    // ID  
    public void ON_factor1_End(String id) {
        ConfigurationParams.reversePolishStructure.add(id);
        if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
    }
    // NUMERIC_CONST 
    public void ON_factor2_End(String lexema) {
        ConfigurationParams.reversePolishStructure.add(lexema);
    }
    // invocacion  
    public void ON_factor3_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("invocación función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }
    // '-' NUMERIC_CONST 
    public void ON_factor4_End(String lexema) {
        ConfigurationParams.reversePolishStructure.add("-"+lexema);
        if (ConfigurationParams.symbolTable.contains("-"+lexema)){
            ConfigurationParams.symbolTable.lookup("-"+lexema).addOneItemEntry();
            ConfigurationParams.symbolTable.lookup(lexema).subtractOneItemEntry();
        }
        else if (ConfigurationParams.symbolTable.contains(lexema)){
            if (ConfigurationParams.symbolTable.lookup(lexema).getItemEntryCount() == 1)
                ConfigurationParams.symbolTable.remove(lexema);
            else
                ConfigurationParams.symbolTable.lookup(lexema).subtractOneItemEntry();;
        ConfigurationParams.symbolTable.insert("-"+lexema, new SymbolTableItem(ETokenType.INTEGER, EDataType.INTEGER));
        }
    }
    // ITOUL '(' expresion ')'
    public void ON_factor4_End() {
        ConfigurationParams.reversePolishStructure.add("itoul");
    }

    //---------------------- << invocacion >> ----------------------
    // ID '(' parametros ')' 
    public void ON_invocacion1_End(String id) {
        ConfigurationParams.reversePolishStructure.add(id);
        if (!ConfigurationParams.checkIfFunctionIsDeclared(id, true))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' no fue declarada previamente en este ámbito o no contiene parámetros \n");
    }
    // ID '('')' 
    public void ON_invocacion2_End(String id) {
        ConfigurationParams.reversePolishStructure.add(id);
        if (!ConfigurationParams.checkIfFunctionIsDeclared(id, false))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' no fue declarada previamente en este ámbito o contiene parámetros \n");
    }

    //---------------------- << parametros >> ----------------------
    // parametros ',' parametro 
    public void ON_parametros1_End(String id) {
        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: no puede haber mas de un parámetro en la invocación a una función \n");
    }

    //---------------------- << parametro >> ----------------------
    // ID
    public void ON_parametro1_End(String id) {
        ConfigurationParams.reversePolishStructure.add(id);
        if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
            ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
    }
    // NUMERIC_CONST 
    public void ON_parametro2_End(String lexema) {
        ConfigurationParams.reversePolishStructure.add(lexema);
    }
    // '-' NUMERIC_CONST 
    public void ON_parametro3_End(String lexema) {
        ConfigurationParams.reversePolishStructure.add("-"+lexema);
        if (ConfigurationParams.symbolTable.contains("-"+lexema)){
            ConfigurationParams.symbolTable.lookup("-"+lexema).addOneItemEntry();
            ConfigurationParams.symbolTable.lookup(lexema).subtractOneItemEntry();
        }
        else if (ConfigurationParams.symbolTable.contains(lexema)){
            if (ConfigurationParams.symbolTable.lookup(lexema).getItemEntryCount() == 1)
                ConfigurationParams.symbolTable.remove(lexema);
            else
                ConfigurationParams.symbolTable.lookup(lexema).subtractOneItemEntry();;
        ConfigurationParams.symbolTable.insert("-"+lexema, new SymbolTableItem(ETokenType.INTEGER, EDataType.INTEGER));
        }
    }

    //---------------------- << impresion >> ----------------------
    // PRINT '(' STRING_CONST ')' ';'
    public void ON_impresion1_End(String comment) {
        ConfigurationParams.reversePolishStructure.add(comment);
    }

    //---------------------- << iteracion >> ----------------------
    // inicio_while '(' condicion_while ')' bloque_ejecutables_while
    public void ON_iteracion2_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: te olvidaste el DO linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }

    //---------------------- << inicio_if >> ----------------------
    // IF 
    public void ON_inicio_if1_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("if linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }

    //---------------------- << inicio_if >> ----------------------
    // WHILE 
    public void ON_inicio_while_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("while linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
    }

    //---------------------- << bloque_ejecutables_if_con_else >> ----------------------
    // BEGIN sentencias_ejecutables END 
    public void ON_bloque_ejecutables_if_con_else1_End() {
        Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
        ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope())+2, jumpPosition);
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JUMP"); 
    }

    //---------------------- << bloque_ejecutables_if_sin_else >> ----------------------
    // BEGIN sentencias_ejecutables END 
    public void ON_bloque_ejecutables_if_sin_else1_End() {
        Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
        ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()), jumpPosition);
    }

    //---------------------- << bloque_ejecutables_else >> ----------------------
    // BEGIN sentencias_ejecutables END 
    public void ON_bloque_ejecutables_else1_End() {
        Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
        ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()), jumpPosition);
    }

    //---------------------- << bloque_ejecutables_while >> ----------------------
    // BEGIN sentencias_ejecutables END
    public void ON_bloque_ejecutables_while1_End() {
        Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
        ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope())+2, jumpPosition);
        Integer jumpPosition2 = ConfigurationParams.reversePolishStructure.popElementFromStack();
        ConfigurationParams.reversePolishStructure.add(jumpPosition2);
        ConfigurationParams.reversePolishStructure.add("JUMP"); 
    }

    //---------------------- << condicion_if >> ----------------------
    // expresion GREATER_EQUAL expresion
    public void ON_condicion_if1_End() {
        ConfigurationParams.reversePolishStructure.add(">="); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE"); 
    }
    // expresion LESS_EQUAL expresion
    public void ON_condicion_if2_End() {
        ConfigurationParams.reversePolishStructure.add("<="); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE"); 
    }
    // expresion NOT_EQUAL expresion
    public void ON_condicion_if3_End() {
        ConfigurationParams.reversePolishStructure.add("<>"); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE"); 
    }
    // expresion '>' expresion
    public void ON_condicion_if4_End() {
        ConfigurationParams.reversePolishStructure.add(">"); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE");
    }
    // expresion '<' expresion
    public void ON_condicion_if5_End() {
        ConfigurationParams.reversePolishStructure.add("<"); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE");
    }
    // expresion '=' expresion
    public void ON_condicion_if6_End() {
        ConfigurationParams.reversePolishStructure.add("="); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE");
    }

    //---------------------- << condicion_while  >> ----------------------
    // expresion GREATER_EQUAL expresion
    public void ON_condicion_while1_End() {
        ConfigurationParams.reversePolishStructure.add(">="); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE"); 
    }
    // expresion LESS_EQUAL expresion
    public void ON_condicion_while2_End() {
        ConfigurationParams.reversePolishStructure.add("<="); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE"); 
    }
    // expresion NOT_EQUAL expresion
    public void ON_condicion_while3_End() {
        ConfigurationParams.reversePolishStructure.add("<>"); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE"); 
    }
    // expresion '>' expresion
    public void ON_condicion_while4_End() {
        ConfigurationParams.reversePolishStructure.add(">"); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE");
    }
    // expresion '<' expresion
    public void ON_condicion_while5_End() {
        ConfigurationParams.reversePolishStructure.add("<"); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE");
    }
    // expresion '=' expresion
    public void ON_condicion_while6_End() {
        ConfigurationParams.reversePolishStructure.add("="); 
        ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
        ConfigurationParams.reversePolishStructure.add(""); 
        ConfigurationParams.reversePolishStructure.add("JNE");
    }

    //---------------------- << retorno  >> ----------------------
    // RETURN '(' expresion ')' ';'
    public void ON_retorno1_End() {
        ConfigurationParams.mainView.getSintacticViewer().appendData("return linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
    }
}
