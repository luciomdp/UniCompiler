package sintacticalanalyzer.byacc;

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
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    9,    9,   10,   10,   10,   10,   10,   10,    4,    4,
    4,   13,   11,   14,   12,   12,   15,   15,   17,    5,
   18,   18,   18,   19,   19,   19,   20,   20,   20,   20,
   20,   21,   21,   22,   22,   22,    6,    7,    7,    8,
    8,   26,   23,   28,   30,   29,   25,   27,   27,   27,
   27,   27,   27,   24,   24,   24,   24,   24,   24,   16,
};
final static short yylen[] = {                            2,
    1,    4,    2,    1,    1,    1,    1,    1,    1,    2,
    2,    1,    1,    1,    1,    1,    1,    2,    2,    8,
    6,    1,    3,    4,    1,    1,    3,    1,    1,    4,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    2,
    4,    4,    3,    1,    1,    2,    5,    6,    5,    9,
    7,    1,    1,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,    0,   52,    0,   53,   25,
   26,    0,    4,    5,    6,    7,    8,    9,    0,    0,
    0,    0,   10,    0,    0,    2,    3,   19,   29,   22,
    0,    0,   28,    0,    0,    0,   38,    0,    0,    0,
    0,   34,   39,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,   40,   30,    0,    0,    0,    0,    0,
    0,   27,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   44,   45,   43,    0,
    0,    0,    0,    0,   35,   36,   47,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   49,    0,    0,
    0,    0,    0,    0,    0,   46,   42,   41,    0,   21,
    0,    0,   14,   15,   16,   17,    0,   12,   13,    0,
   48,    0,    0,    0,    0,    0,   18,   57,   11,    0,
    0,   51,    0,    0,   20,    0,    0,    0,    0,   24,
    0,   50,    0,   56,    0,   70,
};
final static short yydgoto[] = {                          2,
    3,   12,   13,   14,  113,  114,  115,  116,  117,  118,
  119,  120,   31,  110,   32,  134,   33,   40,   41,   42,
   43,   81,   21,   49,   98,   22,   51,  123,  138,  124,
};
final static short yysindex[] = {                      -240,
 -201,    0,    0,  -81,   14, -184,    0,   54,    0,    0,
    0, -165,    0,    0,    0,    0,    0,    0,   37, -180,
   67,   87,    0,  -44, -164,    0,    0,    0,    0,    0,
 -137,   80,    0,  -44,  -44,   88,    0,   89, -128,   55,
  -23,    0,    0,   92,   96,    0, -116,   -4,  101,   -1,
  105,    6,  -44,    0,    0,  -44,  -44,  -44,  -44,   90,
  -41,    0,  -44,  -44,  -44,  -44,  -44,  -44, -202,  -44,
  -44,  -44,  -44,  -44,  -44, -118,    0,    0,    0, -110,
  111,  167,  -23,  -23,    0,    0,    0, -114,  -95,   24,
   24,   24,   24,   24,   24,  -70, -102,    0,   24,   24,
   24,   24,   24,   24, -101,    0,    0,    0,  -81,    0,
  129,  113,    0,    0,    0,    0, -155,    0,    0, -116,
    0,  -70,  -89,  -91, -139, -114,    0,    0,    0, -113,
  -86,    0,  142,  -80,    0,    0,  -70,  -78,  -44,    0,
  -97,    0,  174,    0,  133,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -39,    0,    0,    0,    0,
  -34,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -29,   -7,    0,    0,    0,    0,    0,  152,
  156,  157,  158,  161,  101,    0,    0,    0,  162,  163,
   92,  164,  168,  170,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -251,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  107,   -2,    0,   25,   31,   36,   59,  -18,  -33,
   60,    1,    0,   94,    0,    0,  171,   15,  -11,   16,
    0,    0,    0,    0,  124,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         88,
   39,   37,   37,   37,   20,   37,   31,   37,   31,   27,
   31,   32,   20,   32,   54,   32,    1,   55,   58,   37,
   37,   37,   37,   59,   31,   31,   31,   31,   15,   32,
   32,   32,   32,   33,   16,   33,   15,   33,   56,   17,
   57,   56,   16,   57,   83,   84,   79,   17,   48,   50,
   80,   33,   33,   33,   33,   67,   68,   66,   74,   75,
   73,   89,   18,   19,   96,    4,   56,   82,   57,   97,
   18,   19,   23,   85,   86,   24,   29,   90,   91,   92,
   93,   94,   95,  129,   99,  100,  101,  102,  103,  104,
    5,    6,   30,   25,   44,   28,  129,   56,    7,   57,
  112,    6,   26,  130,    8,    9,   34,  129,    7,   20,
   10,   11,  128,   55,    8,    9,    5,    6,  141,   45,
   10,   11,   27,   47,    7,   20,   35,   52,   53,   54,
    8,    9,   60,   15,  133,   61,   10,   11,   46,   16,
   29,   69,  112,    6,   17,   76,  105,  106,   87,   15,
    7,  107,  109,  143,  136,   16,    8,    9,  112,    6,
   17,  111,   10,   11,   96,  122,    7,   18,   19,  126,
  144,  127,    8,    9,    5,    6,  131,  132,   10,   11,
  137,  139,    7,   18,   19,  112,    6,  140,    8,    9,
  142,  146,   64,    7,   10,   11,   65,   66,   67,    8,
    9,   68,   58,   59,   61,   10,   11,  108,   62,   56,
   63,   57,   36,   37,  145,  125,   56,   62,   57,  135,
  121,   37,   37,   37,    0,    0,   31,   31,   31,    0,
   38,   32,   32,   32,   10,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   33,   33,   33,   63,   64,   65,   70,
   71,   72,   77,   78,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,    4,   45,   41,   47,   43,   12,
   45,   41,   12,   43,  266,   45,  257,  269,   42,   59,
   60,   61,   62,   47,   59,   60,   61,   62,    4,   59,
   60,   61,   62,   41,    4,   43,   12,   45,   43,    4,
   45,   43,   12,   45,   56,   57,   41,   12,   34,   35,
   45,   59,   60,   61,   62,   60,   61,   62,   60,   61,
   62,   61,    4,    4,  267,  267,   43,   53,   45,  272,
   12,   12,   59,   58,   59,  260,  257,   63,   64,   65,
   66,   67,   68,  117,   70,   71,   72,   73,   74,   75,
  256,  257,  273,   40,  259,   59,  130,   43,  264,   45,
  256,  257,  268,  122,  270,  271,   40,  141,  264,  109,
  276,  277,  268,   59,  270,  271,  256,  257,  137,  257,
  276,  277,  125,   44,  264,  125,   40,   40,   40,  258,
  270,  271,   41,  109,  274,   40,  276,  277,   59,  109,
  257,   41,  256,  257,  109,   41,  265,  258,   59,  125,
  264,   41,  267,  139,  268,  125,  270,  271,  256,  257,
  125,  257,  276,  277,  267,  267,  264,  109,  109,   41,
  268,   59,  270,  271,  256,  257,  266,  269,  276,  277,
  267,   40,  264,  125,  125,  256,  257,  268,  270,  271,
  269,   59,   41,  264,  276,  277,   41,   41,   41,  270,
  271,   41,   41,   41,   41,  276,  277,   41,   41,   43,
   41,   45,  257,  258,   41,  109,   43,   47,   45,  126,
   97,  261,  262,  263,   -1,   -1,  261,  262,  263,   -1,
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
"bloque : ID BEGIN sentencias END",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : declaracion",
"sentencia : asignacion",
"sentencia : impresion",
"sentencia : iteracion",
"sentencia : seleccion",
"sentencia : error ';'",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencia_ejecutable : declaracion_variables",
"sentencia_ejecutable : asignacion",
"sentencia_ejecutable : impresion",
"sentencia_ejecutable : iteracion",
"sentencia_ejecutable : seleccion",
"sentencia_ejecutable : error ';'",
"declaracion : declaracion_variables ';'",
"declaracion : tipo inicio_funcion ID '(' tipo ID ')' bloque_funciones",
"declaracion : tipo inicio_funcion ID '(' ')' bloque_funciones",
"inicio_funcion : FUN",
"declaracion_variables : tipo variables ';'",
"bloque_funciones : BEGIN sentencias retorno END",
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
"parametros : ID",
"parametros : NUMERIC_CONST",
"parametros : '-' NUMERIC_CONST",
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

//#line 294 "calc.y"

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
//#line 379 "Parser.java"
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
{ConfigurationParams.mainView.getSintacticViewer().appendData("--------------------------- << Fin del análisis sintáctico >> ---------------------------");
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);
                                    }
break;
case 6:
//#line 37 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 7:
//#line 38 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 10:
//#line 41 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error de sentencia \n");}
break;
case 13:
//#line 48 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 14:
//#line 49 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 15:
//#line 50 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 18:
//#line 53 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error de sentencia ejecutable\n");}
break;
case 20:
//#line 58 "calc.y"
{
                                                            ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                                                            ConfigurationParams.reversePolishStructure.add("fun");
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(5).sval);
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(2).sval);
                                                        }
break;
case 21:
//#line 64 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("fin declaracion de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                                                            ConfigurationParams.reversePolishStructure.add("fun");
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);                                                       
                                                  }
break;
case 22:
//#line 70 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("declaración de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 23:
//#line 73 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 25:
//#line 79 "calc.y"
{ConfigurationParams.reversePolishStructure.add("integer");}
break;
case 26:
//#line 80 "calc.y"
{ConfigurationParams.reversePolishStructure.add("ulongint");}
break;
case 27:
//#line 82 "calc.y"
{ConfigurationParams.reversePolishStructure.add(",");}
break;
case 30:
//#line 92 "calc.y"
{   ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);
                                            ConfigurationParams.reversePolishStructure.add(":=");
                                        }
break;
case 32:
//#line 99 "calc.y"
{ConfigurationParams.reversePolishStructure.add("+");}
break;
case 33:
//#line 100 "calc.y"
{ConfigurationParams.reversePolishStructure.add("-");}
break;
case 35:
//#line 105 "calc.y"
{ConfigurationParams.reversePolishStructure.add("*");}
break;
case 36:
//#line 106 "calc.y"
{ConfigurationParams.reversePolishStructure.add("/");}
break;
case 37:
//#line 110 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 38:
//#line 111 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 39:
//#line 112 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("invocación función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 40:
//#line 113 "calc.y"
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
case 41:
//#line 128 "calc.y"
{ConfigurationParams.reversePolishStructure.add("itoul");}
break;
case 42:
//#line 132 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);}
break;
case 43:
//#line 133 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(2).sval);}
break;
case 44:
//#line 136 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 45:
//#line 137 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 46:
//#line 138 "calc.y"
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
case 47:
//#line 157 "calc.y"
{ConfigurationParams.reversePolishStructure.add(Arrays.asList(val_peek(2).sval, "print"));}
break;
case 49:
//#line 162 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error: te olvidaste el DO linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 52:
//#line 169 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("if linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
            }
break;
case 53:
//#line 174 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("while linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
            }
break;
case 54:
//#line 180 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex()+2, jumpPosition);
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }
break;
case 55:
//#line 189 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(), jumpPosition);
                                            }
break;
case 56:
//#line 195 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(), jumpPosition);
                                            }
break;
case 57:
//#line 201 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex()+2, jumpPosition);
                                                Integer jumpPosition2 = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.add(jumpPosition2);
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }
break;
case 58:
//#line 211 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 59:
//#line 217 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 60:
//#line 223 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 61:
//#line 229 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 62:
//#line 235 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 63:
//#line 241 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 64:
//#line 249 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 65:
//#line 255 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 66:
//#line 261 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 67:
//#line 267 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 68:
//#line 273 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 69:
//#line 279 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 70:
//#line 289 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("return linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
//#line 855 "Parser.java"
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
