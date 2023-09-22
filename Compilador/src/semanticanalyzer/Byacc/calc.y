
/* --------------- DECLARACION --------------- */
%{
    import lexicalanalyzer.*;
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
            BEGIN sentencias END {mainView.getSemanticViewer().appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");}
;
sentencias : 
            sentencias sentencia
        |   sentencia
;
sentencia :  
            declaracion {mainView.getSemanticViewer().appendData("declaracion\n");}
        |   asignacion {mainView.getSemanticViewer().appendData("asignacion\n");}
        |   impresion {mainView.getSemanticViewer().appendData("impresion\n");}
        |   iteracion {mainView.getSemanticViewer().appendData("iteracion\n");}
        |   seleccion {mainView.getSemanticViewer().appendData("seleccion\n");}
        |   error ';'   {mainView.getSemanticViewer().appendError("Error de sentencia\n");}
;
sentencias_ejecutables : 
            sentencias_ejecutables sentencia_ejecutable
        |   sentencia_ejecutable
;
sentencia_ejecutable :  
            declaracion_variables {mainView.getSemanticViewer().appendData("declaracion variables\n");}
        |   asignacion {mainView.getSemanticViewer().appendData("asignacion\n");}
        |   impresion {mainView.getSemanticViewer().appendData("impresion\n");}
        |   iteracion {mainView.getSemanticViewer().appendData("iteracion\n");}
        |   seleccion {mainView.getSemanticViewer().appendData("seleccion\n");}
        |   error ';'   {mainView.getSemanticViewer().appendError("Error de sentencia ejecutable\n");}
;
/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion :   
            tipo variables ';'
        |   tipo FUN ID '(' tipo ID ')' bloque_funciones
        |   tipo FUN ID '('  ')' bloque_funciones
;
declaracion_variables :   
            tipo variables ';'
;
bloque_funciones :   
            BEGIN sentencias retorno END
;
tipo :       
            INTEGER
        |   ULONGINT
variables : 
            variables ',' variable 
        |   variable
;
variable : 
            ID
;

/* ----- SENTENCIAS EJECUTABLES ----- */
/* ASIGNACION */
asignacion : 
            ID ASIGNACION expresion ';'
;

expresion : 
            termino
        |   expresion '+' termino 
        |   expresion '-' termino    
;

termino :   
            factor
        |   termino '*' factor 
        |   termino '/' factor 
    ;

factor :    
            ID  
        |   NUMERIC_CONST
        |   invocacion
        |   '-' NUMERIC_CONST
        |   ITOUL '(' expresion ')'
;

invocacion: 
            ID '(' parametros ')'
        |   ID '('')'
;
parametros:
            ID
        |   NUMERIC_CONST 
        |   '-' NUMERIC_CONST
;

/* ----- OTRAS ----- */
/* IMPRESION */
impresion : 
            PRINT '(' STRING_CONST ')' ';'
;
/* ITERACION */
iteracion: 
            WHILE '(' condicion ')' DO bloque_ejecutables
        |   WHILE '(' condicion ')' bloque_ejecutables {mainView.getSemanticViewer().appendError("Error: te olvidaste el DO\n");}
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
            GREATER_EQUAL
        |   LESS_EQUAL
        |   NOT_EQUAL
        |   '>'
        |   '<'
        |   '='
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
private static LexicalAnalizer lexicalAnalizer;
private static MainView mainView;

public static void main(String[] args) throws Exception {
    lexicalAnalizer = new LexicalAnalizer();
    mainView = new MainView(lexicalAnalizer);
    Parser parser = new Parser(true);
    parser.yyparse(); 
}

public int yylex() {
    int token = lexicalAnalizer.yylex();
    mainView.getPanelTokenViewer().printToken(token);
    yyval = new ParserVal(lexicalAnalizer.getLexema());
    return token;
}
public void yyerror(String s) {
    mainView.getSemanticViewer().appendError(s + ", en la línea"+ lexicalAnalizer.getNewLineCount() +"\n");
}
