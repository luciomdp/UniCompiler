//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 4 "calc.y"
import objects.enums.ETokenType;
import objects.ConfigurationParams;
import objects.SemanticParserActions;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short NUMERIC_CONST=258;
public final static short STRING_CONST=259;
public final static short ASIGNACION=260;
public final static short GREATER_EQUAL=261;
public final static short LESS_EQUAL=262;
public final static short NOT_EQUAL=263;
public final static short IF=264;
public final static short THEN=265;
public final static short ELSE=266;
public final static short BEGIN=267;
public final static short END=268;
public final static short END_IF=269;
public final static short PRINT=270;
public final static short WHILE=271;
public final static short DO=272;
public final static short FUN=273;
public final static short RETURN=274;
public final static short ITOUL=275;
public final static short INTEGER=276;
public final static short ULONGINT=277;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    3,    3,    5,    5,    4,    6,    6,
    6,    6,    6,    6,    6,   12,   12,   17,   17,   13,
   19,   14,   15,   16,    7,   20,   20,   18,   18,   22,
   22,   23,    8,   24,   24,   24,   25,   25,   25,   26,
   26,   26,   26,   26,   27,   27,   28,   28,   29,   29,
   29,    9,   10,   10,   11,   11,   33,   30,   35,   37,
   36,   32,   34,   34,   34,   34,   34,   34,   31,   31,
   31,   31,   31,   31,   21,
};
final static short yylen[] = {                            2,
    1,    4,    1,    2,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    4,    4,    7,    1,    5,
    1,    1,    2,    1,    3,    1,    1,    1,    1,    3,
    1,    1,    4,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    2,    4,    4,    3,    3,    1,    1,    1,
    2,    5,    6,    5,    9,    7,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    5,
};
final static short yydefred[] = {                         0,
    3,    0,    1,    0,    0,    0,    0,   57,    0,   58,
    0,    0,    0,    5,    8,    9,   10,   11,   12,   13,
   14,    0,    0,    0,    0,    0,    0,    0,    0,    2,
    4,   22,    0,    0,   21,    0,   32,    0,   31,    0,
    0,    0,   41,    0,    0,    0,    0,   37,   42,    0,
    0,    0,    0,    0,   25,    0,    0,    0,    0,    0,
    0,    0,   43,   33,    0,    0,    0,    0,    0,    0,
   23,   24,   16,   17,    0,   30,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   49,   50,   46,    0,    0,   48,    0,    0,    0,   38,
   39,   52,    0,   26,   27,   20,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   54,    0,    0,    0,    0,
    0,    0,    0,   51,   45,    0,   44,    0,    0,    0,
    7,   53,    0,    0,    0,   47,    0,   18,   62,    6,
    0,    0,   56,   75,    0,    0,    0,    0,   55,   61,
};
final static short yydgoto[] = {                          2,
    3,    4,   51,   14,  130,   15,   16,   17,   18,   19,
   20,   21,   22,   33,   52,   73,   23,   24,   36,   25,
   71,   38,   39,   46,   47,   48,   49,   95,   96,   26,
   58,  116,   27,   60,  134,  147,  135,
};
final static short yysindex[] = {                      -214,
    0,    0,    0, -218,  -83,    0, -184,    0,   40,    0,
    0,    0, -136,    0,    0,    0,    0,    0,    0,    0,
    0, -179, -179, -176, -156,   63,   67,  -44, -150,    0,
    0,    0,  -83,  -83,    0, -147,    0,   18,    0,  -44,
  -44,   74,    0,   77, -135,  -30,   21,    0,    0,   85,
 -127, -141, -141,   91,    0, -156,   -4,   92,   -1,   95,
    6,  -44,    0,    0,  -44,  -44,  -44,  -44,   79,   99,
    0,    0,    0,    0,  -41,    0,  -44,  -44,  -44,  -44,
  -44,  -44, -203,  -44,  -44,  -44,  -44,  -44,  -44, -123,
    0,    0,    0, -110,   37,    0,   29,   21,   21,    0,
    0,    0,  -44,    0,    0,    0, -106,   57,   57,   57,
   57,   57,   57,  -83, -115,    0,   57,   57,   57,   57,
   57,   57, -113,    0,    0,  -40,    0,   30,  117, -111,
    0,    0,  -83, -105, -107,    0,  105,    0,    0,    0,
 -101,  -99,    0,    0,    0,  -83,  -92,  -85,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -152,    0,    0,    0,    0,
 -238, -233,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,    0,    0,    0,  -34,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -29,   -7,    0,
    0,    0,    0,    0,    0,    0,    0,   85,  137,  139,
  141,  143,  148,    0,    0,    0,  149,  154,  155,  156,
  157,  158,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -170,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  195,   -3,  -96,  -35,    0,    0,    0,    0,
    0,    0,    0,  178,  168,  150,    0,    0,    0,  129,
    0,    0,  151,    5,  -48,   -2,    0,    0,   80,    0,
    0,   90,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        106,
   45,   40,   40,   40,   94,   40,   34,   40,   34,   31,
   34,   35,   65,   35,   66,   35,   98,   99,   26,   40,
   40,   40,   40,   27,   34,   34,   34,   34,   64,   35,
   35,   35,   35,   36,   28,   36,  141,   36,   65,   29,
   66,   65,    1,   66,   57,   59,   93,   31,    5,  148,
   94,   36,   36,   36,   36,   81,   82,   80,   88,   89,
   87,   56,   67,  114,  100,  101,   97,   68,  115,  127,
  137,   65,   65,   66,   66,   28,   55,  125,  131,   29,
  126,  108,  109,  110,  111,  112,  113,   32,  117,  118,
  119,  120,  121,  122,  140,   59,   35,  131,   60,   65,
   37,   66,   40,   15,   15,  140,   41,  128,   50,   54,
  131,   15,  140,   61,   19,   15,   62,   15,   15,    6,
    7,   15,   63,   15,   15,   69,   72,    8,    6,    7,
   75,   30,   83,    9,   10,   90,    8,  102,  103,   11,
   12,  123,    9,   10,    6,    7,   70,  124,   11,   12,
  129,  114,    8,  133,    6,    7,  139,  138,    9,   10,
  142,  143,    8,  144,   11,   12,  145,  146,    9,   10,
    6,    7,    6,    7,   11,   12,  149,   70,    8,   71,
    8,   72,  150,   73,    9,   10,    9,   10,   74,   63,
   11,   12,   11,   12,   64,   65,   66,   67,   68,   13,
   34,   53,   74,  107,  132,  136,   76,    0,    0,    0,
    0,    0,   42,   43,    0,    0,   91,   92,    0,    0,
    0,   40,   40,   40,    0,    0,   34,   34,   34,    0,
   44,   35,   35,   35,  104,  105,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   36,   36,   36,   77,   78,   79,   84,
   85,   86,   91,   92,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,   45,   45,   41,   47,   43,   13,
   45,   41,   43,   43,   45,   45,   65,   66,  257,   59,
   60,   61,   62,  257,   59,   60,   61,   62,   59,   59,
   60,   61,   62,   41,  273,   43,  133,   45,   43,  273,
   45,   43,  257,   45,   40,   41,   41,   51,  267,  146,
   45,   59,   60,   61,   62,   60,   61,   62,   60,   61,
   62,   44,   42,  267,   67,   68,   62,   47,  272,   41,
   41,   43,   43,   45,   45,  260,   59,   41,  114,   40,
   44,   77,   78,   79,   80,   81,   82,  267,   84,   85,
   86,   87,   88,   89,  130,  266,  273,  133,  269,   43,
  257,   45,   40,  256,  257,  141,   40,  103,  259,  257,
  146,  264,  148,   40,  267,  268,   40,  270,  271,  256,
  257,  274,  258,  276,  277,   41,  268,  264,  256,  257,
   40,  268,   41,  270,  271,   41,  264,   59,   40,  276,
  277,  265,  270,  271,  256,  257,  274,  258,  276,  277,
  257,  267,  264,  267,  256,  257,  268,   41,  270,  271,
  266,  269,  264,   59,  276,  277,  268,  267,  270,  271,
  256,  257,  256,  257,  276,  277,  269,   41,  264,   41,
  264,   41,  268,   41,  270,  271,  270,  271,   41,   41,
  276,  277,  276,  277,   41,   41,   41,   41,   41,    5,
   23,   34,   53,   75,  115,  126,   56,   -1,   -1,   -1,
   -1,   -1,  257,  258,   -1,   -1,  257,  258,   -1,   -1,
   -1,  261,  262,  263,   -1,   -1,  261,  262,  263,   -1,
  275,  261,  262,  263,  276,  277,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  261,  262,  263,  261,  262,  263,  261,
  262,  263,  257,  258,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=277;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"ID","NUMERIC_CONST","STRING_CONST","ASIGNACION",
"GREATER_EQUAL","LESS_EQUAL","NOT_EQUAL","IF","THEN","ELSE","BEGIN","END",
"END_IF","PRINT","WHILE","DO","FUN","RETURN","ITOUL","INTEGER","ULONGINT",
};
final static String yyrule[] = {
"$accept : programa",
"programa : bloque",
"bloque : nombre_programa BEGIN sentencias END",
"nombre_programa : ID",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencia : sentencia_ejecutable",
"sentencia_ejecutable : declaracion_variables",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : impresion",
"sentencia_ejecutable : iteracion",
"sentencia_ejecutable : seleccion",
"sentencia_ejecutable : declaracion_funcion",
"sentencia_ejecutable : error",
"declaracion_funcion : cabecera_funcion inicio_funcion cuerpo_funcion fin_funcion",
"declaracion_funcion : cabecera_funcion_parametro inicio_funcion cuerpo_funcion fin_funcion",
"cabecera_funcion_parametro : tipo_funcion token_fun ID '(' tipo ID ')'",
"cabecera_funcion_parametro : error",
"cabecera_funcion : tipo_funcion token_fun ID '(' ')'",
"token_fun : FUN",
"inicio_funcion : BEGIN",
"cuerpo_funcion : sentencias retorno",
"fin_funcion : END",
"declaracion_variables : tipo variables ';'",
"tipo : INTEGER",
"tipo : ULONGINT",
"tipo_funcion : INTEGER",
"tipo_funcion : ULONGINT",
"variables : variables ',' variable",
"variables : variable",
"variable : ID",
"asignacion : ID ASIGNACION expresion ';'",
"expresion : termino",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"termino : factor",
"termino : termino '*' factor",
"termino : termino '/' factor",
"factor : ID",
"factor : NUMERIC_CONST",
"factor : invocacion",
"factor : '-' NUMERIC_CONST",
"factor : ITOUL '(' expresion ')'",
"invocacion : ID '(' parametros ')'",
"invocacion : ID '(' ')'",
"parametros : parametros ',' parametro",
"parametros : parametro",
"parametro : ID",
"parametro : NUMERIC_CONST",
"parametro : '-' NUMERIC_CONST",
"impresion : PRINT '(' STRING_CONST ')' ';'",
"iteracion : inicio_while '(' condicion_while ')' DO bloque_ejecutables_while",
"iteracion : inicio_while '(' condicion_while ')' bloque_ejecutables_while",
"seleccion : inicio_if '(' condicion_if ')' THEN bloque_ejecutables_if_con_else ELSE bloque_ejecutables_else END_IF",
"seleccion : inicio_if '(' condicion_if ')' THEN bloque_ejecutables_if_sin_else END_IF",
"inicio_if : IF",
"inicio_while : WHILE",
"bloque_ejecutables_if_con_else : BEGIN sentencias_ejecutables END",
"bloque_ejecutables_if_sin_else : BEGIN sentencias_ejecutables END",
"bloque_ejecutables_else : BEGIN sentencias_ejecutables END",
"bloque_ejecutables_while : BEGIN sentencias_ejecutables END",
"condicion_if : expresion GREATER_EQUAL expresion",
"condicion_if : expresion LESS_EQUAL expresion",
"condicion_if : expresion NOT_EQUAL expresion",
"condicion_if : expresion '>' expresion",
"condicion_if : expresion '<' expresion",
"condicion_if : expresion '=' expresion",
"condicion_while : expresion GREATER_EQUAL expresion",
"condicion_while : expresion LESS_EQUAL expresion",
"condicion_while : expresion NOT_EQUAL expresion",
"condicion_while : expresion '>' expresion",
"condicion_while : expresion '<' expresion",
"condicion_while : expresion '=' expresion",
"retorno : RETURN '(' expresion ')' ';'",
};

//#line 212 "calc.y"

public static void main(String[] args) throws Exception {
    ConfigurationParams globalParams = new ConfigurationParams(false);
    Parser parser = new Parser(false);
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
    ConfigurationParams.mainView.getSintacticViewer().appendError(s + ", en la línea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
}
//#line 383 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 2:
//#line 22 "calc.y"
{SemanticParserActions.ON_bloque1_End();}
break;
case 3:
//#line 25 "calc.y"
{
                SemanticParserActions.ON_nombre_programa1_End(val_peek(0).sval);
            }
break;
case 10:
//#line 42 "calc.y"
{SemanticParserActions.ON_sentencia_ejecutable2_End();}
break;
case 11:
//#line 43 "calc.y"
{SemanticParserActions.ON_sentencia_ejecutable3_End();}
break;
case 15:
//#line 47 "calc.y"
{SemanticParserActions.ON_sentencia_ejecutable6_End();}
break;
case 18:
//#line 55 "calc.y"
{
                SemanticParserActions.ON_cabecera_funcion_parametro1_End(val_peek(4).sval, val_peek(1).sval);
            }
break;
case 19:
//#line 58 "calc.y"
{
                 SemanticParserActions.ON_cabecera_funcion_parametro2_End();
            }
break;
case 20:
//#line 63 "calc.y"
{
                SemanticParserActions.ON_cabecera_funcion1_End(val_peek(2).sval);
            }
break;
case 24:
//#line 77 "calc.y"
{
                SemanticParserActions.ON_fin_funcion1_End();
            }
break;
case 25:
//#line 82 "calc.y"
{
                                SemanticParserActions.ON_declaracion_variables1_End();
                            }
break;
case 26:
//#line 88 "calc.y"
{SemanticParserActions.ON_tipo1_End();}
break;
case 27:
//#line 89 "calc.y"
{SemanticParserActions.ON_tipo2_End();}
break;
case 28:
//#line 92 "calc.y"
{SemanticParserActions.ON_tipo_funcion1_End();}
break;
case 29:
//#line 93 "calc.y"
{SemanticParserActions.ON_tipo_funcion2_End();}
break;
case 32:
//#line 100 "calc.y"
{
                /* Debo primero verificar que no sea existente, de serlo arrojar un error. */
                SemanticParserActions.ON_variable1_End(val_peek(0).sval);
            }
break;
case 33:
//#line 109 "calc.y"
{ 
                                            SemanticParserActions.ON_asignacion1_End(val_peek(3).sval);
                                        }
break;
case 35:
//#line 116 "calc.y"
{SemanticParserActions.ON_expresion2_End();}
break;
case 36:
//#line 117 "calc.y"
{SemanticParserActions.ON_expresion3_End();}
break;
case 38:
//#line 122 "calc.y"
{SemanticParserActions.ON_termino2_End();}
break;
case 39:
//#line 123 "calc.y"
{SemanticParserActions.ON_termino3_End();}
break;
case 40:
//#line 127 "calc.y"
{
                    SemanticParserActions.ON_factor1_End(val_peek(0).sval);
                }
break;
case 41:
//#line 130 "calc.y"
{SemanticParserActions.ON_factor2_End(val_peek(0).sval);}
break;
case 42:
//#line 131 "calc.y"
{SemanticParserActions.ON_factor3_End();}
break;
case 43:
//#line 132 "calc.y"
{
                                    SemanticParserActions.ON_factor4_End(val_peek(0).sval);
                                }
break;
case 44:
//#line 135 "calc.y"
{SemanticParserActions.ON_factor4_End();}
break;
case 45:
//#line 139 "calc.y"
{
                    SemanticParserActions.ON_invocacion1_End(val_peek(3).sval);
                }
break;
case 46:
//#line 142 "calc.y"
{
                    SemanticParserActions.ON_invocacion2_End(val_peek(2).sval);
                }
break;
case 47:
//#line 147 "calc.y"
{SemanticParserActions.ON_parametros1_End();}
break;
case 49:
//#line 151 "calc.y"
{SemanticParserActions.ON_parametro1_End(val_peek(0).sval);}
break;
case 50:
//#line 152 "calc.y"
{SemanticParserActions.ON_parametro2_End(val_peek(0).sval);}
break;
case 51:
//#line 153 "calc.y"
{SemanticParserActions.ON_parametro3_End(val_peek(0).sval);}
break;
case 52:
//#line 158 "calc.y"
{SemanticParserActions.ON_impresion1_End(val_peek(2).sval);}
break;
case 54:
//#line 163 "calc.y"
{SemanticParserActions.ON_iteracion2_End();}
break;
case 57:
//#line 170 "calc.y"
{SemanticParserActions.ON_inicio_if1_End();}
break;
case 58:
//#line 173 "calc.y"
{SemanticParserActions.ON_inicio_while_End();}
break;
case 59:
//#line 176 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_if_con_else1_End();}
break;
case 60:
//#line 179 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_if_sin_else1_End();}
break;
case 61:
//#line 182 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_else1_End();}
break;
case 62:
//#line 185 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_while1_End();}
break;
case 63:
//#line 189 "calc.y"
{SemanticParserActions.ON_condicion_if1_End();}
break;
case 64:
//#line 190 "calc.y"
{SemanticParserActions.ON_condicion_if2_End();}
break;
case 65:
//#line 191 "calc.y"
{SemanticParserActions.ON_condicion_if3_End();}
break;
case 66:
//#line 192 "calc.y"
{SemanticParserActions.ON_condicion_if4_End();}
break;
case 67:
//#line 193 "calc.y"
{SemanticParserActions.ON_condicion_if5_End();}
break;
case 68:
//#line 194 "calc.y"
{SemanticParserActions.ON_condicion_if6_End();}
break;
case 69:
//#line 197 "calc.y"
{SemanticParserActions.ON_condicion_while1_End();}
break;
case 70:
//#line 198 "calc.y"
{SemanticParserActions.ON_condicion_while2_End();}
break;
case 71:
//#line 199 "calc.y"
{SemanticParserActions.ON_condicion_while3_End();}
break;
case 72:
//#line 200 "calc.y"
{SemanticParserActions.ON_condicion_while4_End();}
break;
case 73:
//#line 201 "calc.y"
{SemanticParserActions.ON_condicion_while5_End();}
break;
case 74:
//#line 202 "calc.y"
{SemanticParserActions.ON_condicion_while6_End();}
break;
case 75:
//#line 207 "calc.y"
{SemanticParserActions.ON_retorno1_End();}
break;
//#line 765 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
