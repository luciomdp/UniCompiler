
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
import objects.SemanticParserActions;
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
            nombre_programa BEGIN sentencias END {SemanticParserActions.ON_bloque1_End();}
;
nombre_programa : 
            ID {
                SemanticParserActions.ON_nombre_programa1_End($1.sval);
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
        |   asignacion {SemanticParserActions.ON_sentencia_ejecutable2_End();}
        |   impresion {SemanticParserActions.ON_sentencia_ejecutable3_End();}
        |   iteracion 
        |   seleccion 
        |   error   {SemanticParserActions.ON_sentencia_ejecutable6_End();}
;
/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion_funcion :   
        cabecera_funcion inicio_funcion cuerpo_funcion fin_funcion
        |   cabecera_funcion_parametro inicio_funcion cuerpo_funcion fin_funcion 
;
cabecera_funcion_parametro : 
            tipo_funcion token_fun ID '(' tipo ID ')' {
                SemanticParserActions.ON_cabecera_funcion_parametro1_End($3.sval, $6.sval);
            }
            | error {
                 SemanticParserActions.ON_cabecera_funcion_parametro2_End();
            }
;
cabecera_funcion : 
            tipo_funcion token_fun ID '(' ')' {
                SemanticParserActions.ON_cabecera_funcion1_End($3.sval);
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
                SemanticParserActions.ON_fin_funcion1_End();
            }
;
declaracion_variables :   
            tipo variables ';'{
                                SemanticParserActions.ON_declaracion_variables1_End();
                            }
;

tipo :       
            INTEGER {SemanticParserActions.ON_tipo1_End();} 
        |   ULONGINT {SemanticParserActions.ON_tipo2_End();}
;
tipo_funcion :       
            INTEGER {SemanticParserActions.ON_tipo_funcion1_End();} 
        |   ULONGINT {SemanticParserActions.ON_tipo_funcion2_End();}
;
variables : 
            variables ',' variable
        |   variable
;
variable : 
            ID {
                // Debo primero verificar que no sea existente, de serlo arrojar un error. 
                SemanticParserActions.ON_variable1_End($1.sval);
            }
;

/* ----- SENTENCIAS EJECUTABLES ----- */
/* ASIGNACION */
asignacion : 
            ID ASIGNACION expresion ';' { 
                                            SemanticParserActions.ON_asignacion1_End($1.sval);
                                        }
;

expresion : 
            termino
        |   expresion '+' termino {SemanticParserActions.ON_expresion2_End();}
        |   expresion '-' termino {SemanticParserActions.ON_expresion3_End();}
;

termino :   
            factor
        |   termino '*' factor {SemanticParserActions.ON_termino2_End();}
        |   termino '/' factor {SemanticParserActions.ON_termino3_End();}
;

factor :    
            ID  {
                    SemanticParserActions.ON_factor1_End($1.sval);
                }
        |   NUMERIC_CONST {SemanticParserActions.ON_factor2_End($1.sval);}
        |   invocacion {SemanticParserActions.ON_factor3_End();}
        |   '-' NUMERIC_CONST {
                                    SemanticParserActions.ON_factor4_End($2.sval);
                                }
        |   ITOUL '(' expresion ')' {SemanticParserActions.ON_factor4_End();}
;

invocacion: 
            ID '(' parametros ')' {
                    SemanticParserActions.ON_invocacion1_End($1.sval);
                }
        |   ID '('')' {
                    SemanticParserActions.ON_invocacion2_End($1.sval);
                }
;
parametros:
            parametros ',' parametro {SemanticParserActions.ON_parametros1_End();}
        |   parametro
;
parametro:
            ID {SemanticParserActions.ON_parametro1_End($1.sval);}
        |   NUMERIC_CONST {SemanticParserActions.ON_parametro2_End($1.sval);}
        |   '-' NUMERIC_CONST {SemanticParserActions.ON_parametro3_End($2.sval);}
;

/* --------------------------------------------------------------------------IMPRESION -------------------------------------------------------------------------------*/
impresion : 
            PRINT '(' STRING_CONST ')' ';'{SemanticParserActions.ON_impresion1_End($3.sval);}
;
/* -------------------------------------------------------------------- ITERACION Y SELECCIÓN ------------------------------------------------------------------------*/
iteracion : 
            inicio_while '(' condicion_while ')' DO bloque_ejecutables_while 
        |   inicio_while '(' condicion_while ')' bloque_ejecutables_while {SemanticParserActions.ON_iteracion2_End();}
;
seleccion : 
            inicio_if '(' condicion_if ')' THEN bloque_ejecutables_if_con_else ELSE bloque_ejecutables_else END_IF
        |   inicio_if '(' condicion_if ')' THEN bloque_ejecutables_if_sin_else END_IF
;
inicio_if :
         IF {SemanticParserActions.ON_inicio_if1_End();}
;
inicio_while :
        WHILE {SemanticParserActions.ON_inicio_while_End();}
;
bloque_ejecutables_if_con_else :
        BEGIN sentencias_ejecutables END {SemanticParserActions.ON_bloque_ejecutables_if_con_else1_End();}   
;
bloque_ejecutables_if_sin_else :
        BEGIN sentencias_ejecutables END {SemanticParserActions.ON_bloque_ejecutables_if_sin_else1_End();}   
;
bloque_ejecutables_else :
        BEGIN sentencias_ejecutables END {SemanticParserActions.ON_bloque_ejecutables_else1_End();}      
;
bloque_ejecutables_while :
    BEGIN sentencias_ejecutables END {SemanticParserActions.ON_bloque_ejecutables_while1_End();} 
    
;
condicion_if :
            expresion GREATER_EQUAL expresion {SemanticParserActions.ON_condicion_if1_End();} 
        |   expresion LESS_EQUAL expresion {SemanticParserActions.ON_condicion_if2_End();}
        |   expresion NOT_EQUAL expresion {SemanticParserActions.ON_condicion_if3_End();}
        |   expresion '>' expresion {SemanticParserActions.ON_condicion_if4_End();}
        |   expresion '<' expresion {SemanticParserActions.ON_condicion_if5_End();}
        |   expresion '=' expresion {SemanticParserActions.ON_condicion_if6_End();}
;
condicion_while :
            expresion GREATER_EQUAL expresion {SemanticParserActions.ON_condicion_while1_End();} 
        |   expresion LESS_EQUAL expresion {SemanticParserActions.ON_condicion_while2_End();} 
        |   expresion NOT_EQUAL expresion {SemanticParserActions.ON_condicion_while3_End();}
        |   expresion '>' expresion {SemanticParserActions.ON_condicion_while4_End();}
        |   expresion '<' expresion {SemanticParserActions.ON_condicion_while5_End();}
        |   expresion '=' expresion {SemanticParserActions.ON_condicion_while6_End();}
;

/* -----------------------------------------------------------------------------RETORNO -----------------------------------------------------------------------------*/
retorno : 
            RETURN '(' expresion ')' ';' {SemanticParserActions.ON_retorno1_End();}
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
