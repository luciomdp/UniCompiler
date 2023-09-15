
/* --------------- DECLARACION --------------- */

%token ID INT_CONST ULONGINT_CONST STRING_CONST ASIGNACION GREATER_EQUAL LESS_EQUAL NOT_EQUAL IF THEN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN ITOUL INTEGER ULONGINT GREATER_THAN LESS_THAN EQUAL

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
        |   retorno
        |   error ';'   {System.out.println("Error en sentencia");}
;

/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion :   
            tipo variables ';' //Declaracion de dato
        |   tipo FUN ID '(' tipo ID ')' bloque //Declaracion de funcion con 1 parametro
        |   tipo FUN ID '('  ')' bloque //Declaracion de funcion con 0 parametro
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
        |   INT_CONST  
        |   ULONGINT_CONST
        |   invocacion
;

invocacion: 
            ID '(' parametros ')' ';'
;
parametros:
            ID
        |   INT_CONST
        |   ULONGINT_CONST
;

/* ----- OTRAS ----- */
/* IMPRESION */
impresion : 
            PRINT '(' STRING_CONST ')' ';'
;
/* ITERACION */
iteracion: 
            WHILE '(' condicion ')' DO bloque
        |   WHILE '(' condicion ')' bloque {System.out.println("ERROR: te olvidaste el "DO")}
;
/* SELECCION */
seleccion: 
            IF '(' condicion ')' THEN bloque ELSE bloque se END_IF
        |   IF '(' condicion ')' THEN bloque END_IF
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
/* RETORNO */
retorno: 
            RETURN '(' expresion ')' ';'
;

/* --------------- CODIGO --------------- */
%%