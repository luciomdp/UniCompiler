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
   19,   14,   15,   15,   16,    7,   20,   20,   18,   18,
   22,   22,   23,    8,   24,   24,   24,   25,   25,   25,
   26,   26,   26,   26,   26,   27,   27,   28,   28,   29,
   29,   29,    9,   10,   10,   11,   11,   33,   30,   35,
   37,   36,   32,   34,   34,   34,   34,   34,   34,   31,
   31,   31,   31,   31,   31,   21,
};
final static short yylen[] = {                            2,
    1,    4,    1,    2,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    4,    4,    7,    1,    5,
    1,    1,    2,    1,    1,    3,    1,    1,    1,    1,
    3,    1,    1,    4,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    2,    4,    4,    3,    3,    1,    1,
    1,    2,    5,    6,    5,    9,    7,    1,    1,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    5,
};
final static short yydefred[] = {                         0,
    3,    0,    1,    0,    0,    0,    0,   58,    0,   59,
    0,    0,    0,    5,    8,    9,   10,   11,   12,   13,
   14,    0,    0,    0,    0,    0,    0,    0,    0,    2,
    4,   22,    0,    0,   21,    0,   33,    0,   32,    0,
    0,    0,   42,    0,    0,    0,    0,   38,   43,    0,
    0,    0,    0,   24,    0,    0,   26,    0,    0,    0,
    0,    0,    0,    0,   44,   34,    0,    0,    0,    0,
    0,    0,   23,   25,   16,   17,    0,   31,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   50,   51,   47,    0,    0,   49,    0,    0,
    0,   39,   40,   53,    0,   27,   28,   20,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,    0,    0,
    0,    0,    0,    0,    0,   52,   46,    0,   45,    0,
    0,    0,    7,   54,    0,    0,    0,   48,   76,   18,
   63,    6,    0,    0,   57,    0,    0,    0,    0,   56,
   62,
};
final static short yydgoto[] = {                          2,
    3,    4,   52,   14,  132,   15,   16,   17,   18,   19,
   20,   21,   22,   33,   53,   75,   23,   24,   36,   25,
   54,   38,   39,   46,   47,   48,   49,   97,   98,   26,
   60,  118,   27,   62,  136,  148,  137,
};
final static short yysindex[] = {                      -220,
    0,    0,    0, -224,  -90,    0, -192,    0,   33,    0,
    0,    0, -143,    0,    0,    0,    0,    0,    0,    0,
    0, -185, -185, -183, -151,   70,   76,  -44, -135,    0,
    0,    0, -134, -134,    0, -131,    0,   19,    0,  -44,
  -44,   89,    0,   91, -126,  -30,   23,    0,    0,   94,
  101, -134, -124,    0, -124,  105,    0, -151,   -4,  106,
   -1,  110,    6,  -44,    0,    0,  -44,  -44,  -44,  -44,
   95,  -44,    0,    0,    0,    0,  -41,    0,  -44,  -44,
  -44,  -44,  -44,  -44, -201,  -44,  -44,  -44,  -44,  -44,
  -44, -110,    0,    0,    0, -101,   39,    0,   31,   23,
   23,    0,    0,    0,   58,    0,    0,    0,  -96,   57,
   57,   57,   57,   57,   57,  -90,  -97,    0,   57,   57,
   57,   57,   57,   57,  -94,    0,    0,  -40,    0,  112,
  134, -118,    0,    0,  -90,  -89,  -87,    0,    0,    0,
    0,    0, -108,  -84,    0,    0,  -90,  -81,  -92,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0, -159,    0,    0,    0,    0,
 -238, -233,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,    0,    0,    0,  -34,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -29,
   -7,    0,    0,    0,    0,    0,    0,    0,    0,  148,
   94,  149,  150,  151,  152,    0,    0,    0,  153,  154,
  155,  156,  157,  158,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -162,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
    0,    0,  195,   -3,  -85,  -68,    0,    0,    0,    0,
    0,    0,    0,  178,  168,  160,    0,    0,    0,  126,
  159,    0,  146,    5,  -50,   50,    0,    0,   77,    0,
    0,   90,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        108,
   45,   41,   41,   41,   96,   41,   35,   41,   35,   31,
   35,   36,   67,   36,   68,   36,  100,  101,   27,   41,
   41,   41,   41,   28,   35,   35,   35,   35,   66,   36,
   36,   36,   36,   37,   29,   37,    1,   37,   67,   30,
   68,   67,    5,   68,   59,   61,   95,  133,   31,  143,
   96,   37,   37,   37,   37,   83,   84,   82,   90,   91,
   89,  149,   58,  142,   69,  116,  133,   28,   99,   70,
  117,  129,   29,   67,  142,   68,  105,   57,  133,  127,
  142,   32,  128,  110,  111,  112,  113,  114,  115,   35,
  119,  120,  121,  122,  123,  124,   15,   15,  130,   67,
   67,   68,   68,   60,   15,   37,   61,   19,   15,   40,
   15,   15,    6,    7,   15,   41,   15,   15,  102,  103,
    8,    6,    7,   50,   30,   56,    9,   10,   63,    8,
   64,   65,   11,   12,   71,    9,   10,    6,    7,   51,
   72,   11,   12,   74,   77,    8,   85,    6,    7,  141,
   92,    9,   10,  104,  125,    8,  126,   11,   12,  146,
  131,    9,   10,    6,    7,    6,    7,   11,   12,  116,
  139,    8,  135,    8,  140,  151,  144,    9,   10,    9,
   10,  145,  147,   11,   12,   11,   12,  150,   70,   72,
   73,   74,   75,   64,   65,   66,   67,   68,   69,   13,
   34,   55,  109,   78,  138,    0,  134,    0,    0,    0,
   73,    0,   42,   43,   76,    0,   93,   94,    0,    0,
    0,   41,   41,   41,    0,    0,   35,   35,   35,    0,
   44,   36,   36,   36,  106,  107,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   37,   37,   37,   79,   80,   81,   86,
   87,   88,   93,   94,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,   45,   45,   41,   47,   43,   13,
   45,   41,   43,   43,   45,   45,   67,   68,  257,   59,
   60,   61,   62,  257,   59,   60,   61,   62,   59,   59,
   60,   61,   62,   41,  273,   43,  257,   45,   43,  273,
   45,   43,  267,   45,   40,   41,   41,  116,   52,  135,
   45,   59,   60,   61,   62,   60,   61,   62,   60,   61,
   62,  147,   44,  132,   42,  267,  135,  260,   64,   47,
  272,   41,   40,   43,  143,   45,   72,   59,  147,   41,
  149,  267,   44,   79,   80,   81,   82,   83,   84,  273,
   86,   87,   88,   89,   90,   91,  256,  257,   41,   43,
   43,   45,   45,  266,  264,  257,  269,  267,  268,   40,
  270,  271,  256,  257,  274,   40,  276,  277,   69,   70,
  264,  256,  257,  259,  268,  257,  270,  271,   40,  264,
   40,  258,  276,  277,   41,  270,  271,  256,  257,  274,
   40,  276,  277,  268,   40,  264,   41,  256,  257,  268,
   41,  270,  271,   59,  265,  264,  258,  276,  277,  268,
  257,  270,  271,  256,  257,  256,  257,  276,  277,  267,
   59,  264,  267,  264,   41,  268,  266,  270,  271,  270,
  271,  269,  267,  276,  277,  276,  277,  269,   41,   41,
   41,   41,   41,   41,   41,   41,   41,   41,   41,    5,
   23,   34,   77,   58,  128,   -1,  117,   -1,   -1,   -1,
   52,   -1,  257,  258,   55,   -1,  257,  258,   -1,   -1,
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
"cuerpo_funcion : retorno",
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

//#line 213 "calc.y"

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
//#line 387 "Parser.java"
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
case 25:
//#line 78 "calc.y"
{
                SemanticParserActions.ON_fin_funcion1_End();
            }
break;
case 26:
//#line 83 "calc.y"
{
                                SemanticParserActions.ON_declaracion_variables1_End();
                            }
break;
case 27:
//#line 89 "calc.y"
{SemanticParserActions.ON_tipo1_End();}
break;
case 28:
//#line 90 "calc.y"
{SemanticParserActions.ON_tipo2_End();}
break;
case 29:
//#line 93 "calc.y"
{SemanticParserActions.ON_tipo_funcion1_End();}
break;
case 30:
//#line 94 "calc.y"
{SemanticParserActions.ON_tipo_funcion2_End();}
break;
case 33:
//#line 101 "calc.y"
{
                /* Debo primero verificar que no sea existente, de serlo arrojar un error. */
                SemanticParserActions.ON_variable1_End(val_peek(0).sval);
            }
break;
case 34:
//#line 110 "calc.y"
{ 
                                            SemanticParserActions.ON_asignacion1_End(val_peek(3).sval);
                                        }
break;
case 36:
//#line 117 "calc.y"
{SemanticParserActions.ON_expresion2_End();}
break;
case 37:
//#line 118 "calc.y"
{SemanticParserActions.ON_expresion3_End();}
break;
case 39:
//#line 123 "calc.y"
{SemanticParserActions.ON_termino2_End();}
break;
case 40:
//#line 124 "calc.y"
{SemanticParserActions.ON_termino3_End();}
break;
case 41:
//#line 128 "calc.y"
{
                    SemanticParserActions.ON_factor1_End(val_peek(0).sval);
                }
break;
case 42:
//#line 131 "calc.y"
{SemanticParserActions.ON_factor2_End(val_peek(0).sval);}
break;
case 43:
//#line 132 "calc.y"
{SemanticParserActions.ON_factor3_End();}
break;
case 44:
//#line 133 "calc.y"
{
                                    SemanticParserActions.ON_factor4_End(val_peek(0).sval);
                                }
break;
case 45:
//#line 136 "calc.y"
{SemanticParserActions.ON_factor4_End();}
break;
case 46:
//#line 140 "calc.y"
{
                    SemanticParserActions.ON_invocacion1_End(val_peek(3).sval);
                }
break;
case 47:
//#line 143 "calc.y"
{
                    SemanticParserActions.ON_invocacion2_End(val_peek(2).sval);
                }
break;
case 48:
//#line 148 "calc.y"
{SemanticParserActions.ON_parametros1_End();}
break;
case 50:
//#line 152 "calc.y"
{SemanticParserActions.ON_parametro1_End(val_peek(0).sval);}
break;
case 51:
//#line 153 "calc.y"
{SemanticParserActions.ON_parametro2_End(val_peek(0).sval);}
break;
case 52:
//#line 154 "calc.y"
{SemanticParserActions.ON_parametro3_End(val_peek(0).sval);}
break;
case 53:
//#line 159 "calc.y"
{SemanticParserActions.ON_impresion1_End(val_peek(2).sval);}
break;
case 55:
//#line 164 "calc.y"
{SemanticParserActions.ON_iteracion2_End();}
break;
case 58:
//#line 171 "calc.y"
{SemanticParserActions.ON_inicio_if1_End();}
break;
case 59:
//#line 174 "calc.y"
{SemanticParserActions.ON_inicio_while_End();}
break;
case 60:
//#line 177 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_if_con_else1_End();}
break;
case 61:
//#line 180 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_if_sin_else1_End();}
break;
case 62:
//#line 183 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_else1_End();}
break;
case 63:
//#line 186 "calc.y"
{SemanticParserActions.ON_bloque_ejecutables_while1_End();}
break;
case 64:
//#line 190 "calc.y"
{SemanticParserActions.ON_condicion_if1_End();}
break;
case 65:
//#line 191 "calc.y"
{SemanticParserActions.ON_condicion_if2_End();}
break;
case 66:
//#line 192 "calc.y"
{SemanticParserActions.ON_condicion_if3_End();}
break;
case 67:
//#line 193 "calc.y"
{SemanticParserActions.ON_condicion_if4_End();}
break;
case 68:
//#line 194 "calc.y"
{SemanticParserActions.ON_condicion_if5_End();}
break;
case 69:
//#line 195 "calc.y"
{SemanticParserActions.ON_condicion_if6_End();}
break;
case 70:
//#line 198 "calc.y"
{SemanticParserActions.ON_condicion_while1_End();}
break;
case 71:
//#line 199 "calc.y"
{SemanticParserActions.ON_condicion_while2_End();}
break;
case 72:
//#line 200 "calc.y"
{SemanticParserActions.ON_condicion_while3_End();}
break;
case 73:
//#line 201 "calc.y"
{SemanticParserActions.ON_condicion_while4_End();}
break;
case 74:
//#line 202 "calc.y"
{SemanticParserActions.ON_condicion_while5_End();}
break;
case 75:
//#line 203 "calc.y"
{SemanticParserActions.ON_condicion_while6_End();}
break;
case 76:
//#line 208 "calc.y"
{SemanticParserActions.ON_retorno1_End();}
break;
//#line 769 "Parser.java"
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
