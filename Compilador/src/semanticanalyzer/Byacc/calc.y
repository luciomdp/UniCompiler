
/* --------------- DECLARACION --------------- */
%{
import lexicalanalyzer.*;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.ReversePolishStructure;
import components.*;
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
            ID BEGIN sentencias END {ConfigurationParams.mainView.getSemanticViewer().appendData("------------------------------ << Fin del análisis sintáctico >> ------------------------------");
                                                            ConfigurationParams.ReversePolishStructure.add($1.sval);
                                    }
;
sentencias : 
            sentencias sentencia
        |   sentencia
;
sentencia :  
            declaracion
        |   asignacion {ConfigurationParams.mainView.getSemanticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   impresion {ConfigurationParams.mainView.getSemanticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   iteracion {ConfigurationParams.mainView.getSemanticViewer().appendData("fin de iteracion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   seleccion {ConfigurationParams.mainView.getSemanticViewer().appendData("fin de seleccion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   error ';'   {ConfigurationParams.mainView.getSemanticViewer().appendError("Error de sentencia \n");}
;
sentencias_ejecutables : 
            sentencias_ejecutables sentencia_ejecutable
        |   sentencia_ejecutable
;
sentencia_ejecutable :  
            declaracion_variables {ConfigurationParams.mainView.getSemanticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   asignacion {ConfigurationParams.mainView.getSemanticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   impresion {ConfigurationParams.mainView.getSemanticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   iteracion {ConfigurationParams.mainView.getSemanticViewer().appendData("fin de iteracion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   seleccion {ConfigurationParams.mainView.getSemanticViewer().appendData("fin de seleccion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   error ';'   {ConfigurationParams.mainView.getSemanticViewer().appendError("Error de sentencia ejecutable\n");}
;
/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion :   
            tipo variables ';' {ConfigurationParams.mainView.getSemanticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
        |   tipo FUN ID '(' tipo ID ')' bloque_funciones {
                                                            ConfigurationParams.mainView.getSemanticViewer().appendData("declaracion de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                                                            ConfigurationParams.ReversePolishStructure.add("fun");
                                                            ConfigurationParams.ReversePolishStructure.add($3.sval);
                                                            ConfigurationParams.ReversePolishStructure.add($6.sval);
                                                        }
        |   tipo FUN ID '('  ')' bloque_funciones {ConfigurationParams.mainView.getSemanticViewer().appendData("fin declaracion de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                                                            ConfigurationParams.ReversePolishStructure.add("fun");
                                                            ConfigurationParams.ReversePolishStructure.add($3.sval);                                                       
                                                  }
;
declaracion_variables :   
            tipo variables ';'
;
bloque_funciones :   
            BEGIN sentencias retorno END
;
tipo :       
            INTEGER  {ConfigurationParams.ReversePolishStructure.add("integer");
        |   ULONGINT {ConfigurationParams.ReversePolishStructure.add("ulongint");
variables : 
            variables ',' variable {ConfigurationParams.ReversePolishStructure.add(",");
        |   variable
;
variable : 
            ID
;

/* ----- SENTENCIAS EJECUTABLES ----- */
/* ASIGNACION */
asignacion : 
            ID ASIGNACION expresion ';' {ConfigurationParams.ReversePolishStructure.add(":=");
                                        ConfigurationParams.ReversePolishStructure.add($1.sval);}
;

expresion : 
            termino
        |   expresion '+' termino {ConfigurationParams.ReversePolishStructure.add("+");}
        |   expresion '-' termino {ConfigurationParams.ReversePolishStructure.add("-");}
;

termino :   
            factor
        |   termino '*' factor {ConfigurationParams.ReversePolishStructure.add("*");}
        |   termino '/' factor {ConfigurationParams.ReversePolishStructure.add("/");}
;

factor :    
            ID  {ConfigurationParams.ReversePolishStructure.add($1.sval);}
        |   NUMERIC_CONST {ConfigurationParams.ReversePolishStructure.add($1.sval);}
        |   invocacion
        |   '-' NUMERIC_CONST {
                                    String lexema = $2.sval;
                                    ConfigurationParams.ReversePolishStructure.add("-"+lexema);
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
        |   ITOUL '(' expresion ')' {ConfigurationParams.ReversePolishStructure.add("itoul");}
;

invocacion: 
            ID '(' parametros ')' {ConfigurationParams.ReversePolishStructure.add($1.sval);}
        |   ID '('')' {ConfigurationParams.ReversePolishStructure.add($1.sval);}
;
parametros:
            ID {ConfigurationParams.ReversePolishStructure.add($1.sval);}
        |   NUMERIC_CONST {ConfigurationParams.ReversePolishStructure.add($1.sval);}
        |   '-' NUMERIC_CONST {
                                    String lexema = $2.sval;
                                    ConfigurationParams.ReversePolishStructure.add("-"+lexema);
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

/* ----- OTRAS ----- */
/* IMPRESION */
impresion : 
            PRINT '(' STRING_CONST ')' ';'{ConfigurationParams.ReversePolishStructure.add(Arrays.asList($3.sval, "print"))}
;
/* ITERACION */
iteracion: 
            WHILE '(' condicion ')' DO bloque_ejecutables 
        |   WHILE '(' condicion ')' bloque_ejecutables {ConfigurationParams.mainView.getSemanticViewer().appendError("Error: te olvidaste el DO linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
;
/* SELECCION */
seleccion: 
            IF '(' condicion ')' THEN bloque_ejecutables ELSE bloque END_IF
        |   IF '(' condicion ')' THEN bloque_ejecutables END_IF
;

condicion:
            expresion comparador expresion
;
comparador:
            GREATER_EQUAL {ConfigurationParams.ReversePolishStructure.add(">=")} 
        |   LESS_EQUAL {ConfigurationParams.ReversePolishStructure.add("<=")} 
        |   NOT_EQUAL {ConfigurationParams.ReversePolishStructure.add("<>")} 
        |   '>' {ConfigurationParams.ReversePolishStructure.add(">")}
        |   '<' {ConfigurationParams.ReversePolishStructure.add("<")}
        |   '=' {ConfigurationParams.ReversePolishStructure.add("=")}
;
bloque_ejecutables:
        BEGIN sentencias_ejecutables END        
;
/* RETORNO */
retorno: 
            RETURN '(' expresion ')' ';'
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
    ConfigurationParams.mainView.getSemanticViewer().appendError(s + ", en la línea"+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
}
