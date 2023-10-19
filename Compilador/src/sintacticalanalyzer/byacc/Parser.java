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
import lexicalanalyzer.*;
import objects.ConfigurationParams;
import objects.SymbolTableItem;
import objects.enums.EDataType;
import objects.enums.ETokenType;
import objects.ReversePolishStructure;
import components.*;
import java.util.Arrays;
//#line 26 "Parser.java"




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
    0,    1,    2,    3,    3,    5,    5,    4,    4,    6,
    6,    6,    6,    6,    6,    7,    7,   17,   17,   13,
   19,   14,   15,   16,    8,   18,   18,   21,   21,   22,
    9,   23,   23,   23,   24,   24,   24,   25,   25,   25,
   25,   25,   26,   26,   27,   27,   28,   28,   28,   10,
   11,   11,   12,   12,   32,   29,   34,   36,   35,   31,
   33,   33,   33,   33,   33,   33,   30,   30,   30,   30,
   30,   30,   20,
};
final static short yylen[] = {                            2,
    1,    4,    1,    2,    1,    2,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    4,    4,    7,    1,    5,
    1,    1,    2,    1,    3,    1,    1,    3,    1,    1,
    4,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    2,    4,    4,    3,    3,    1,    1,    1,    2,    5,
    6,    5,    9,    7,    1,    1,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    5,
};
final static short yydefred[] = {                         0,
    3,    0,    1,    0,    0,    0,    0,   55,    0,   56,
   26,   27,    0,    5,    9,    8,   10,   11,   12,   13,
   14,    0,    0,    0,    0,    0,   15,    0,    0,    2,
    4,   22,    0,    0,   30,   21,    0,    0,   29,    0,
    0,    0,   39,    0,    0,    0,    0,   35,   40,    0,
    0,    0,    0,    0,   25,    0,    0,    0,    0,    0,
    0,    0,   41,   31,    0,    0,    0,    0,    0,    0,
   23,   24,   16,   17,    0,   28,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   47,   48,   44,    0,    0,   46,    0,    0,    0,   36,
   37,   50,    0,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   52,    0,    0,    0,    0,    0,    0,
    0,   49,   43,    0,   42,    0,    0,    0,    0,    7,
    0,   51,    0,    0,    0,   45,    0,   18,   60,    6,
    0,    0,   54,   73,    0,    0,    0,    0,   53,   59,
};
final static short yydgoto[] = {                          2,
    3,    4,   51,   14,  129,   15,   16,   17,   18,   19,
   20,   21,   22,   33,   52,   73,   23,  131,   37,   71,
   38,   39,   46,   47,   48,   49,   95,   96,   25,   58,
  114,   26,   60,  134,  147,  135,
};
final static short yysindex[] = {                      -239,
    0,    0,    0, -218,  -83,   14, -181,    0,   48,    0,
    0,    0, -158,    0,    0,    0,    0,    0,    0,    0,
    0, -164, -164, -233,   67,   71,    0,  -44, -142,    0,
    0,    0,  -83,  -83,    0,    0, -134,   18,    0,  -44,
  -44,   75,    0,   84, -132,  -30,   23,    0,    0,   86,
 -136, -139, -139,   90,    0, -124,   -4,   95,   -1,   96,
    6,  -44,    0,    0,  -44,  -44,  -44,  -44,   83,  104,
    0,    0,    0,    0,  -41,    0,  -44,  -44,  -44,  -44,
  -44,  -44, -201,  -44,  -44,  -44,  -44,  -44,  -44, -116,
    0,    0,    0, -108,   34,    0,   31,   23,   23,    0,
    0,    0,  -44,    0, -104,  -26,  -26,  -26,  -26,  -26,
  -26,  -81, -113,    0,  -26,  -26,  -26,  -26,  -26,  -26,
 -111,    0,    0,  -40,    0,   59,  119,   14, -125,    0,
 -124,    0,  -81, -103, -105,    0,  107,    0,    0,    0,
 -109,  -97,    0,    0,    0,  -81,  -90,  -99,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,  -87,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,    0,    0,    0,  -34,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -29,   -7,    0,
    0,    0,    0,    0,    0,  141,  143,   86,  144,  145,
  150,    0,    0,    0,  151,  156,  157,  158,  159,  160,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -144,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,  197,   -3,  -96,  -32,    0,    0,    0,    0,
    0,    0,    0,  180,  170,  152,    0,   30,    0,    0,
    0,  153,    5,    3,   28,    0,    0,   82,    0,    0,
   94,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        104,
   45,   38,   38,   38,   94,   38,   32,   38,   32,   31,
   32,   33,   65,   33,   66,   33,   65,    1,   66,   38,
   38,   38,   38,   35,   32,   32,   32,   32,   64,   33,
   33,   33,   33,   34,   24,   34,  141,   34,   65,   36,
   66,   65,   24,   66,   57,   59,   93,   31,    5,  148,
   94,   34,   34,   34,   34,   81,   82,   80,   88,   89,
   87,   56,   24,   24,   67,  112,   97,   98,   99,   68,
  113,  125,   27,   65,  123,   66,   55,  124,   28,  130,
   24,  106,  107,  108,  109,  110,  111,   29,  115,  116,
  117,  118,  119,  120,  100,  101,  140,    6,    7,  137,
  130,   65,   32,   66,  105,    8,   40,  126,  140,   30,
   41,    9,   10,  130,   61,  140,   50,   11,   12,    6,
    7,   57,   54,   62,   58,   63,   69,    8,   72,   75,
  128,    7,   35,    9,   10,   83,   90,   70,    8,   11,
   12,  102,  139,  103,    9,   10,  128,    7,  121,  122,
   11,   12,  127,  112,    8,  133,  128,    7,  145,  138,
    9,   10,  142,  143,    8,  144,   11,   12,  150,  146,
    9,   10,    6,    7,  128,    7,   11,   12,  149,   19,
    8,   67,    8,   68,   70,   71,    9,   10,    9,   10,
   72,   61,   11,   12,   11,   12,   62,   63,   64,   65,
   66,   13,   34,   53,   74,  136,  132,    0,   76,    0,
    0,    0,   42,   43,    0,    0,   91,   92,    0,    0,
    0,   38,   38,   38,    0,    0,   32,   32,   32,    0,
   44,   33,   33,   33,   11,   12,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   34,   34,   34,   77,   78,   79,   84,
   85,   86,   91,   92,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,   45,   45,   41,   47,   43,   13,
   45,   41,   43,   43,   45,   45,   43,  257,   45,   59,
   60,   61,   62,  257,   59,   60,   61,   62,   59,   59,
   60,   61,   62,   41,    5,   43,  133,   45,   43,  273,
   45,   43,   13,   45,   40,   41,   41,   51,  267,  146,
   45,   59,   60,   61,   62,   60,   61,   62,   60,   61,
   62,   44,   33,   34,   42,  267,   62,   65,   66,   47,
  272,   41,   59,   43,   41,   45,   59,   44,  260,  112,
   51,   77,   78,   79,   80,   81,   82,   40,   84,   85,
   86,   87,   88,   89,   67,   68,  129,  256,  257,   41,
  133,   43,  267,   45,   75,  264,   40,  103,  141,  268,
   40,  270,  271,  146,   40,  148,  259,  276,  277,  256,
  257,  266,  257,   40,  269,  258,   41,  264,  268,   40,
  256,  257,  257,  270,  271,   41,   41,  274,  264,  276,
  277,   59,  268,   40,  270,  271,  256,  257,  265,  258,
  276,  277,  257,  267,  264,  267,  256,  257,  268,   41,
  270,  271,  266,  269,  264,   59,  276,  277,  268,  267,
  270,  271,  256,  257,  256,  257,  276,  277,  269,  267,
  264,   41,  264,   41,   41,   41,  270,  271,  270,  271,
   41,   41,  276,  277,  276,  277,   41,   41,   41,   41,
   41,    5,   23,   34,   53,  124,  113,   -1,   56,   -1,
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
"sentencia : declaracion_funcion",
"sentencia : sentencia_ejecutable",
"sentencia_ejecutable : declaracion_variables",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : impresion",
"sentencia_ejecutable : iteracion",
"sentencia_ejecutable : seleccion",
"sentencia_ejecutable : error ';'",
"declaracion_funcion : cabecera_funcion inicio_funcion cuerpo_funcion fin_funcion",
"declaracion_funcion : cabecera_funcion_parametro inicio_funcion cuerpo_funcion fin_funcion",
"cabecera_funcion_parametro : tipo token_fun ID '(' tipo ID ')'",
"cabecera_funcion_parametro : error",
"cabecera_funcion : tipo token_fun ID '(' ')'",
"token_fun : FUN",
"inicio_funcion : BEGIN",
"cuerpo_funcion : sentencias retorno",
"fin_funcion : END",
"declaracion_variables : tipo variables ';'",
"tipo : INTEGER",
"tipo : ULONGINT",
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

//#line 358 "calc.y"

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
//#line 386 "Parser.java"
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
//#line 27 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("--------------------------- << Fin del análisis sintáctico >> ---------------------------");
            }
break;
case 3:
//#line 32 "calc.y"
{
                ConfigurationParams.addScope(val_peek(0).sval);
            }
break;
case 11:
//#line 50 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 12:
//#line 51 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 15:
//#line 54 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error de sentencia ejecutable\n");}
break;
case 18:
//#line 62 "calc.y"
{
                String id = val_peek(4).sval;
                String param = val_peek(1).sval;
                ConfigurationParams.reversePolishStructure.add(id);
                if (!ConfigurationParams.renameFunctionWithScope(id, true))
                    ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' ya fue declarada previamente en este ámbito \n");
                else{
                    ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función con parametro linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                }
                ConfigurationParams.addScope(val_peek(4).sval);
                ConfigurationParams.renameLexemaWithScope(param);
            }
break;
case 19:
//#line 74 "calc.y"
{
                 {ConfigurationParams.mainView.getSintacticViewer().appendError("Error: declaración de función inválida \n");}
            }
break;
case 20:
//#line 79 "calc.y"
{
                String id = val_peek(2).sval;
                ConfigurationParams.reversePolishStructure.add(id);
                if (!ConfigurationParams.renameFunctionWithScope(id, false))
                    ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' ya fue declarada previamente en este ámbito \n");
                else{
                    ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función sin parametro linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                }
                ConfigurationParams.addScope(val_peek(2).sval);
            }
break;
case 24:
//#line 100 "calc.y"
{
                ConfigurationParams.removeScope();
            }
break;
case 25:
//#line 105 "calc.y"
{
                                ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                            }
break;
case 30:
//#line 118 "calc.y"
{
                /* Debo primero verificar que no sea existente, de serlo arrojar un error. */
                String id = val_peek(0).sval;
                if (!ConfigurationParams.renameLexemaWithScope(id))
                    ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' ya fue declarada previamente en este ámbito \n");
            }
break;
case 31:
//#line 129 "calc.y"
{   ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);
                                            ConfigurationParams.reversePolishStructure.add(":=");
                                            String id = val_peek(3).sval;
                                            if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
                                                ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
                                        }
break;
case 33:
//#line 139 "calc.y"
{ConfigurationParams.reversePolishStructure.add("+");}
break;
case 34:
//#line 140 "calc.y"
{ConfigurationParams.reversePolishStructure.add("-");}
break;
case 36:
//#line 145 "calc.y"
{ConfigurationParams.reversePolishStructure.add("*");}
break;
case 37:
//#line 146 "calc.y"
{ConfigurationParams.reversePolishStructure.add("/");}
break;
case 38:
//#line 150 "calc.y"
{
                    String id = val_peek(0).sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
                }
break;
case 39:
//#line 156 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 40:
//#line 157 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("invocación función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 41:
//#line 158 "calc.y"
{
                                    String lexema = val_peek(0).sval;
                                    ConfigurationParams.reversePolishStructure.add("-"+lexema);
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
break;
case 42:
//#line 173 "calc.y"
{ConfigurationParams.reversePolishStructure.add("itoul");}
break;
case 43:
//#line 177 "calc.y"
{
                    String id = val_peek(3).sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfFunctionIsDeclared(id, true))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' no fue declarada previamente en este ámbito o no contiene parámetros \n");
                }
break;
case 44:
//#line 183 "calc.y"
{
                    String id = val_peek(2).sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfFunctionIsDeclared(id, false))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la función '"+ id + "' no fue declarada previamente en este ámbito o contiene parámetros \n");
                }
break;
case 45:
//#line 191 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error: no puede haber mas de un parámetro en la invocación a una función \n");}
break;
case 47:
//#line 195 "calc.y"
{
                    String id = val_peek(0).sval;
                    ConfigurationParams.reversePolishStructure.add(id);
                    if (!ConfigurationParams.checkIfLexemaIsDeclared(id))
                        ConfigurationParams.mainView.getSintacticViewer().appendError("Error: la variable '"+ id + "' no fue declarada previamente en este ámbito \n");
                }
break;
case 48:
//#line 201 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 49:
//#line 202 "calc.y"
{
                                    String lexema = val_peek(0).sval;
                                    ConfigurationParams.reversePolishStructure.add("-"+lexema);
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
break;
case 50:
//#line 221 "calc.y"
{ConfigurationParams.reversePolishStructure.add(Arrays.asList(val_peek(2).sval, "print"));}
break;
case 52:
//#line 226 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error: te olvidaste el DO linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 55:
//#line 233 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("if linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
            }
break;
case 56:
//#line 238 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("while linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
            }
break;
case 57:
//#line 244 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope())+2, jumpPosition);
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }
break;
case 58:
//#line 253 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()), jumpPosition);
                                            }
break;
case 59:
//#line 259 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()), jumpPosition);
                                            }
break;
case 60:
//#line 265 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope())+2, jumpPosition);
                                                Integer jumpPosition2 = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.add(jumpPosition2);
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }
break;
case 61:
//#line 275 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 62:
//#line 281 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 63:
//#line 287 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 64:
//#line 293 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 65:
//#line 299 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 66:
//#line 305 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 67:
//#line 313 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 68:
//#line 319 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 69:
//#line 325 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 70:
//#line 331 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 71:
//#line 337 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 72:
//#line 343 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex(ConfigurationParams.getCurrentScope()));
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 73:
//#line 353 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("return linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
//#line 898 "Parser.java"
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
