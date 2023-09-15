
/* --------------- DECLARACION --------------- */

%token ID INT_CONST ULONGINT_CONST STRING_CONST ASIGNACION GREATER_EQUAL LESS_EQUAL NOT_EQUAL IF THEN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN  ITOUL

/* --------------- GRAMATICA --------------- */
%%

/* INICIO */
programa : BEGIN sentencias END
;
sentencias : sentencia sentencias
        |    sentencia
;
sentencia : asignacion
        |  impresion
;

/* ASIGNACION */
asignacion : ID ASIGNACION expresion ';'
        |   error ';'   {System.out.println("Error en asignación")}
;
expresion : expresion '+' termino 
        |   expresion '-' termino   
        |   termino
;
termino :   termino '*' factor 
        |   termino '/' factor   
        |   factor
;
factor :    ID {
    if()
}
        |   INT_CONST  
        |   ULONGINT_CONST
;

/* IMPRESION */
impresion : PRINT '(' STRING_CONST ')' ';'
;

/* --------------- CODIGO --------------- */
%%