
/* --------------- DECLARACION --------------- */
%{
import lexicalanalyzer.*;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.ReversePolishStructure;
import components.*;
import java.util.Arrays;
%}

%token ID NUMERIC_CONST STRING_CONST ASIGNACION GREATER_EQUAL LESS_EQUAL NOT_EQUAL IF THEN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN ITOUL INTEGER ULONGINT

/* --------------- GRAMATICA --------------- */
%%
/* -----  SENTENCIA START -----  */

programa : 
            bloque
;

/* -----  INICIO -----  */

bloque :   
            nombre_programa BEGIN sentencias END {
                ConfigurationParams.mainView.getSintacticViewer().appendData("--------------------------- << Fin del análisis sintáctico >> ---------------------------");
            }
;
nombre_programa : 
            ID {
                ConfigurationParams.addScope($1.sval);
            }
;
sentencias : 
            sentencias sentencia
        |   sentencia
;
sentencias_ejecutables : 
            sentencias_ejecutables sentencia_ejecutable
        |   sentencia_ejecutable
;
sentencia :  
            declaracion_funcion
        |   sentencia_ejecutable
;
sentencia_ejecutable :  
            declaracion_variables
        |   asignacion {ConfigurationParams.mainView.getSintacticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   impresion {ConfigurationParams.mainView.getSintacticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   iteracion 
        |   seleccion 
        |   error ';'   {ConfigurationParams.mainView.getSintacticViewer().appendError("Error de sentencia ejecutable\n");}
;
/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion_funcion :   
        cabecera_funcion inicio_funcion cuerpo_funcion fin_funcion
        |   cabecera_funcion_parametro inicio_funcion cuerpo_funcion fin_funcion 
;
cabecera_funcion_parametro : 
            tipo token_fun ID '(' tipo ID ')' {
                String id = $3.sval;
                String param = $6.sval;
                ConfigurationParams.reversePolishStructure.add(id);
                if (!ConfigurationParams.renameFunctionWithScope(id, true))
                    ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' ya fue declarada previamente en este ámbito \n");
                else{
                    ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función con parametro linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                }
                ConfigurationParams.addScope($3.sval);
                ConfigurationParams.renameLexemaWithScope(param);
            }
            | error {
                 {ConfigurationParams.mainView.getSintacticViewer().appendError("Error: declaración de función inválida \n");}
            }
;
cabecera_funcion : 
            tipo token_fun ID '(' ')' {
                String id = $3.sval;
                ConfigurationParams.reversePolishStructure.add(id);
                if (!ConfigurationParams.renameFunctionWithScope(id, false))
                    ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' ya fue declarada previamente en este ámbito \n");
                else{
                    ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función sin parametro linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                }
                ConfigurationParams.addScope($3.sval);
            }
;
token_fun :
            FUN
;
inicio_funcion :   
            BEGIN
;
cuerpo_funcion :   
            sentencias retorno
;
fin_funcion : 
            END {
                ConfigurationParams.removeScope();
            }
;
declaracion_variables :   
            tipo variables ';'{
                                ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                            }
;

tipo :       
            INTEGER  
        |   ULONGINT 
variables : 
            variables ',' variable
        |   variable
;
variable : 
            ID {
                // Debo primero verificar que no sea existente, de serlo arrojar un error. 
                String id = $1.sval;
                if (!ConfigurationParams.renameLexemaWithScope(id))
                    ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' ya fue declarada previamente en este ámbito \n");
            }
;

/* ----- SENTENCIAS EJECUTABLES ----- */
/* ASIGNACION */
asignacion : 
            ID ASIGNACION expresion ';' {   ConfigurationParams.reversePolishStructure.add($1.sval);
                                            ConfigurationParams.reversePolishStructure.add(":=");
                                            String id = $1.sval;
                                            if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
                                                ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
                                        }
;

expresion : 
            termino
        |   expresion '+' termino {ConfigurationParams.reversePolishStructure.add("+");}
        |   expresion '-' termino {ConfigurationParams.reversePolishStructure.add("-");}
;

termino :   
            factor
        |   termino '*' factor {ConfigurationParams.reversePolishStructure.add("*");}
        |   termino '/' factor {ConfigurationParams.reversePolishStructure.add("/");}
;

factor :    
            ID  {
                    String id = $1.sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
                }
        |   NUMERIC_CONST {ConfigurationParams.reversePolishStructure.add($1.sval);}
        |   invocacion {ConfigurationParams.mainView.getSintacticViewer().appendData("invocación función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   '-' NUMERIC_CONST {
                                    String lexema = $2.sval;
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
        |   ITOUL '(' expresion ')' {ConfigurationParams.reversePolishStructure.add("itoul");}
;

invocacion: 
            ID '(' parametros ')' {
                    String id = $1.sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfFunctionIsDeclared(id, true))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' no fue declarada previamente en este ámbito o no contiene parámetros \n");
                }
        |   ID '('')' {
                    String id = $1.sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfFunctionIsDeclared(id, false))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' no fue declarada previamente en este ámbito o contiene parámetros \n");
                }
;
parametros:
            parametros ',' parametro {ConfigurationParams.mainView.getSintacticViewer().appendError("Error: no puede haber mas de un parámetro en la invocación a una función \n");}
        |   parametro
;
parametro:
            ID {
                    String id = $1.sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
                }
        |   NUMERIC_CONST {ConfigurationParams.reversePolishStructure.add($1.sval);}
        |   '-' NUMERIC_CONST {
                                    String lexema = $2.sval;
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
;

/* --------------------------------------------------------------------------IMPRESION -------------------------------------------------------------------------------*/
impresion : 
            PRINT '(' STRING_CONST ')' ';'{ConfigurationParams.reversePolishStructure.add(Arrays.asList($3.sval, "print"));}
;
/* -------------------------------------------------------------------- ITERACION Y SELECCIÓN ------------------------------------------------------------------------*/
iteracion : 
            inicio_while '(' condicion_while ')' DO bloque_ejecutables_while 
        |   inicio_while '(' condicion_while ')' bloque_ejecutables_while {ConfigurationParams.mainView.getSintacticViewer().appendError("Error: te olvidaste el DO linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
;
seleccion : 
            inicio_if '(' condicion_if ')' THEN bloque_ejecutables_if_con_else ELSE bloque_ejecutables_else END_IF
        |   inicio_if '(' condicion_if ')' THEN bloque_ejecutables_if_sin_else END_IF
;
inicio_if :
         IF {
                ConfigurationParams.mainView.getSintacticViewer().appendData("if linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
            }
;
inicio_while :
        WHILE {
                ConfigurationParams.mainView.getSintacticViewer().appendData("while linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
            }
;
bloque_ejecutables_if_con_else :
        BEGIN sentencias_ejecutables END {
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope())+2, jumpPosition);
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }   
;
bloque_ejecutables_if_sin_else :
        BEGIN sentencias_ejecutables END {
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()), jumpPosition);
                                            }   
;
bloque_ejecutables_else :
        BEGIN sentencias_ejecutables END {
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()), jumpPosition);
                                            }      
;
bloque_ejecutables_while :
    BEGIN sentencias_ejecutables END {
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope())+2, jumpPosition);
                                                Integer jumpPosition2 = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.add(jumpPosition2);
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            } 
    
;
condicion_if :
            expresion GREATER_EQUAL expresion {
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            } 
        |   expresion LESS_EQUAL expresion {
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion NOT_EQUAL expresion {
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion '>' expresion {
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion '<' expresion {
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion '=' expresion {
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
;
condicion_while :
            expresion GREATER_EQUAL expresion {
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            } 
        |   expresion LESS_EQUAL expresion {
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            } 
        |   expresion NOT_EQUAL expresion {
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion '>' expresion {
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion '<' expresion {
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
        |   expresion '=' expresion {
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
;

/* -----------------------------------------------------------------------------RETORNO -----------------------------------------------------------------------------*/
retorno : 
            RETURN '(' expresion ')' ';' {ConfigurationParams.mainView.getSintacticViewer().appendData("return linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
;

/* --------------- CODIGO --------------- */
%%

public static void main(String[] args) throws Exception {
    ConfigurationParams globalParams = new ConfigurationParams(false);
    Parser parser = new Parser(true);
    parser.yyparse(); 
}

public int yylex() {
    int token = ConfigurationParams.lexicalAnalizer.yylex();
    ConfigurationParams.mainView.getPanelTokenViewer().printToken(token);
    if (token ==ETokenType.IGNORE.getValue())
      return yylex();
    yylval = new ParserVal(ConfigurationParams.lexicalAnalizer.getLexema());
    return token;
}
public void yyerror(String s) {
    ConfigurationParams.mainView.getSintacticViewer().appendError(s + ", en la línea"+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
}
