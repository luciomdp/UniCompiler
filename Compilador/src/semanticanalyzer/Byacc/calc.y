
/* --------------- DECLARACION --------------- */

%token ID INT_CONST ULONGINT_CONST STRING_CONST ASIGNACION GREATER_EQUAL LESS_EQUAL NOT_EQUAL IF THEN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN  ITOUL INTEGER ULONGINT

/* --------------- GRAMATICA --------------- */
%%

/* INICIO */
programa :   
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
        |   retorno
;

/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion :   
            tipo variables ';' //Declaracion de dato
        |   tipo FUN ID '(' tipo ID ')' BEGIN sentencias END //Declaracion de funcion con 1 parametro
        |   tipo FUN ID '('  ')' BEGIN sentencias END //Declaracion de funcion con 0 parametro
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

/* IMPRESION */
impresion : PRINT '(' STRING_CONST ')' ';'
;
/* RETORNO */
retorno: 
            RETURN expresion ';'
        |   RETURN expresion ';' sentencias  {System.out.println("WARNING return debería ser la última línea de una sentencia")}
;

/* --------------- CODIGO --------------- */
%%