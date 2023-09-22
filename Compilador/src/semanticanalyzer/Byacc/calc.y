
/* --------------- DECLARACION --------------- */
%{
    import components.MainView;
    import lexicalanalyzer.LexicalAnalizer;
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
            BEGIN sentencias END
;
sentencias : 
            sentencias sentencia
        |   sentencia
;
sentencia :  
            declaracion
        |   asignacion
        |   impresion
        |   iteracion
        |   seleccion
        |   error ';'   {System.out.println("Error en sentencia");}
;

sentencias_ejecutables :  
            sentencias_ejecutables sentencia_ejecutable
        |   sentencia_ejecutable
;
sentencia_ejecutable :  
            declaracion_variables
        |   asignacion
        |   impresion
        |   iteracion
        |   seleccion
        |   error ';'   {System.out.println("Error en sentencia ejecutable");}
/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion :   
            tipo variables ';' //Declaracion de dato
        |   tipo FUN ID '(' tipo ID ')' bloque_funciones //Declaracion de funcion con 1 parametro
        |   tipo FUN ID '('  ')' bloque_funciones //Declaracion de funcion con 0 parametro
;
declaracion_variables :   
            tipo variables ';' //Declaracion de dato
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
            ID '(' parametros ')' ';'
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
        |   WHILE '(' condicion ')' bloque_ejecutables {System.out.println("ERROR: te olvidaste el DO");}
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
    mainView.getSemanticViewer().appendData(s + "\n");
}
public void debug(String msg)
{
  if (yydebug)
    mainView.getSemanticViewer().appendData(msg + "\n");
}
