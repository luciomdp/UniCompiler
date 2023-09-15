
/* --------------- DECLARACION --------------- */

%token ID INT_CONST ULONGINT_CONST STRING_CONST ASIGNACION GREATER_EQUAL LESS_EQUAL NOT_EQUAL IF THEN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN  ITOUL INTEGER ULONGINT

/* --------------- GRAMATICA --------------- */
%%
/* -----  SENTENCIA START -----  */

programa : bloque
;

/* -----  INICIO -----  */

bloque :   
            BEGIN sentencias END
;
sentencias : 
            sentencia sentencias
        |   sentencia
;
sentencia :  
            declaracion
        |   asignacion
        |   impresion
        |   iteracion
        |   retorno
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
            variable ',' variables
        |   variable
;
variable : 
            ID
;

/* ----- SENTENCIAS EJECUTABLES ----- */
asignacion : ID ASIGNACION expresion ';'
        |   error ';'   {System.out.println("Error en asignación")}
;

expresion : expresion '+' termino 
        |   expresion '-' termino   
        |   termino
        |   ITOUL '(' expresion ')'
;
termino :   termino '*' factor 
        |   termino '/' factor   
        |   factor
;
factor :    ID 
        |   INT_CONST  
        |   ULONGINT_CONST
;
/* ----- OTRAS ----- */
/* IMPRESION */
impresion : PRINT '(' STRING_CONST ')' ';'
;
/* ITERACION */
retorno: 
            WHILE '(' condicion ')' DO bloque
        |   WHILE '(' condicion ')' bloque {System.out.println("ERROR: te olvidaste el "DO")}
;
/* RETORNO */
retorno: 
            RETURN expresion ';'
        |   RETURN expresion ';' sentencias  {System.out.println("WARNING return debería ser la última línea de una sentencia")}
;

/* --------------- CODIGO --------------- */
%%