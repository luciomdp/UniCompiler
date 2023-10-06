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
    0,    1,    2,    2,    4,    4,    3,    3,    5,    5,
    5,    5,    5,    5,    6,    6,   13,    7,   14,   12,
   12,   15,   15,   17,    8,   18,   18,   18,   19,   19,
   19,   20,   20,   20,   20,   20,   21,   21,   22,   22,
   22,    9,   10,   10,   11,   11,   26,   23,   28,   30,
   29,   25,   27,   27,   27,   27,   27,   27,   24,   24,
   24,   24,   24,   24,   16,
};
final static short yylen[] = {                            2,
    1,    4,    2,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    8,    6,    1,    3,    4,    1,
    1,    3,    1,    1,    4,    1,    3,    3,    1,    3,
    3,    1,    1,    1,    2,    4,    4,    3,    1,    1,
    2,    5,    6,    5,    9,    7,    1,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,    0,   47,    0,   48,   20,
   21,    0,    4,    8,    7,    9,   10,   11,   12,   13,
    0,    0,    0,   14,    0,    0,    2,    3,   24,   17,
    0,    0,   23,    0,    0,    0,   33,    0,    0,    0,
    0,   29,   34,    0,    0,   18,    0,    0,    0,    0,
    0,    0,    0,   35,   25,    0,    0,    0,    0,    0,
    0,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   39,   40,   38,    0,
    0,    0,    0,    0,   30,   31,   42,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   44,    0,    0,
    0,    0,    0,    0,    0,   41,   37,   36,    0,   16,
    0,    0,    6,    0,   43,    0,    0,    0,    0,    0,
   52,    5,    0,    0,   46,    0,    0,   15,    0,    0,
    0,    0,   19,    0,   45,    0,   51,    0,   65,
};
final static short yydgoto[] = {                          2,
    3,   12,   13,  112,   14,   15,   16,   17,   18,   19,
   20,  114,   31,  110,   32,  127,   33,   40,   41,   42,
   43,   81,   22,   49,   98,   23,   51,  117,  131,  118,
};
final static short yysindex[] = {                      -240,
 -249,    0,    0,  -90,  -35, -217,    0,   26,    0,    0,
    0, -164,    0,    0,    0,    0,    0,    0,    0,    0,
 -238,   33,   40,    0,  -44, -170,    0,    0,    0,    0,
 -163,    4,    0,  -44,  -44,   56,    0,   62, -150,   52,
   23,    0,    0,   75,   87,    0, -139,   -4,   89,   -1,
   90,    6,  -44,    0,    0,  -44,  -44,  -44,  -44,   74,
  -41,    0,  -44,  -44,  -44,  -44,  -44,  -44, -200,  -44,
  -44,  -44,  -44,  -44,  -44, -129,    0,    0,    0, -121,
  105,   58,   23,   23,    0,    0,    0, -120, -108,   45,
   45,   45,   45,   45,   45,  -90, -114,    0,   45,   45,
   45,   45,   45,   45, -111,    0,    0,    0,  -90,    0,
  116, -142,    0, -139,    0,  -90, -107, -100, -132, -120,
    0,    0, -116, -104,    0,  128,  -96,    0,    0,  -90,
  -94,  -44,    0, -106,    0,   78,    0,  114,    0,
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
    0,    0,  -29,   -7,    0,    0,    0,    0,    0,  135,
   75,  136,  137,  138,  141,    0,    0,    0,  142,  143,
  144,  147,  148,  149,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -229,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   82,   -2, -101,  -25,    0,    0,    0,    0,    0,
    0,    1,    0,   72,    0,    0,  146,   11,   12,   -9,
    0,    0,    0,    0,   97,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=264;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         88,
   39,   32,   32,   32,   21,   32,   26,   32,   26,   28,
   26,   27,   21,   27,  123,   27,    1,    4,   29,   32,
   32,   32,   32,   24,   26,   26,   26,   26,  134,   27,
   27,   27,   27,   28,   30,   28,   49,   28,   56,   50,
   57,   56,   25,   57,   48,   50,   79,   47,   85,   86,
   80,   28,   28,   28,   28,   67,   68,   66,   74,   75,
   73,   89,   46,   82,   58,   26,   96,   83,   84,   59,
  113,   97,   34,   90,   91,   92,   93,   94,   95,   35,
   99,  100,  101,  102,  103,  104,  122,   56,   44,   57,
  113,    5,    6,   45,   56,   52,   57,  122,  108,    7,
   56,   53,   57,   27,  113,    8,    9,   54,  122,   21,
   55,   10,   11,    5,    6,   60,   28,   29,  138,   21,
   56,    7,   57,    5,    6,  121,   61,    8,    9,   69,
   76,    7,   87,   10,   11,  105,  106,    8,    9,    5,
    6,  126,  136,   10,   11,  107,  109,    7,  111,    5,
    6,  129,   96,    8,    9,  116,  120,    7,  124,   10,
   11,  137,  130,    8,    9,    5,    6,  132,  125,   10,
   11,  133,  139,    7,  135,   59,   61,   62,   63,    8,
    9,   64,   53,   54,   55,   10,   11,   56,   57,   58,
  119,  128,   62,  115,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   36,   37,    0,    0,    0,    0,    0,    0,
    0,   32,   32,   32,    0,    0,   26,   26,   26,    0,
   38,   27,   27,   27,   10,   11,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   28,   28,   28,   63,   64,   65,   70,
   71,   72,   77,   78,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,    4,   45,   41,   47,   43,   12,
   45,   41,   12,   43,  116,   45,  257,  267,  257,   59,
   60,   61,   62,   59,   59,   60,   61,   62,  130,   59,
   60,   61,   62,   41,  273,   43,  266,   45,   43,  269,
   45,   43,  260,   45,   34,   35,   41,   44,   58,   59,
   45,   59,   60,   61,   62,   60,   61,   62,   60,   61,
   62,   61,   59,   53,   42,   40,  267,   56,   57,   47,
   96,  272,   40,   63,   64,   65,   66,   67,   68,   40,
   70,   71,   72,   73,   74,   75,  112,   43,  259,   45,
  116,  256,  257,  257,   43,   40,   45,  123,   41,  264,
   43,   40,   45,  268,  130,  270,  271,  258,  134,  109,
   59,  276,  277,  256,  257,   41,  119,  257,   41,  119,
   43,  264,   45,  256,  257,  268,   40,  270,  271,   41,
   41,  264,   59,  276,  277,  265,  258,  270,  271,  256,
  257,  274,  132,  276,  277,   41,  267,  264,  257,  256,
  257,  268,  267,  270,  271,  267,   41,  264,  266,  276,
  277,  268,  267,  270,  271,  256,  257,   40,  269,  276,
  277,  268,   59,  264,  269,   41,   41,   41,   41,  270,
  271,   41,   41,   41,   41,  276,  277,   41,   41,   41,
  109,  120,   47,   97,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,   -1,   -1,   -1,   -1,   -1,   -1,
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
"bloque : ID BEGIN sentencias END",
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
"declaracion_funcion : tipo inicio_funcion ID '(' tipo ID ')' bloque_funciones",
"declaracion_funcion : tipo inicio_funcion ID '(' ')' bloque_funciones",
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

//#line 289 "calc.y"

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
//#line 371 "Parser.java"
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
case 9:
//#line 44 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 10:
//#line 45 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("asignacion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 11:
//#line 46 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("impresion linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 14:
//#line 49 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error de sentencia ejecutable\n");}
break;
case 15:
//#line 53 "calc.y"
{
                                                            ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                                                            ConfigurationParams.reversePolishStructure.add("fun");
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(5).sval);
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(2).sval);
                                                        }
break;
case 16:
//#line 59 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("fin declaracion de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                                                            ConfigurationParams.reversePolishStructure.add("fun");
                                                            ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);                                                       
                                                  }
break;
case 17:
//#line 65 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("declaración de función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 18:
//#line 68 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("declaracion de variable linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 20:
//#line 74 "calc.y"
{ConfigurationParams.reversePolishStructure.add("integer");}
break;
case 21:
//#line 75 "calc.y"
{ConfigurationParams.reversePolishStructure.add("ulongint");}
break;
case 22:
//#line 77 "calc.y"
{ConfigurationParams.reversePolishStructure.add(",");}
break;
case 25:
//#line 87 "calc.y"
{   ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);
                                            ConfigurationParams.reversePolishStructure.add(":=");
                                        }
break;
case 27:
//#line 94 "calc.y"
{ConfigurationParams.reversePolishStructure.add("+");}
break;
case 28:
//#line 95 "calc.y"
{ConfigurationParams.reversePolishStructure.add("-");}
break;
case 30:
//#line 100 "calc.y"
{ConfigurationParams.reversePolishStructure.add("*");}
break;
case 31:
//#line 101 "calc.y"
{ConfigurationParams.reversePolishStructure.add("/");}
break;
case 32:
//#line 105 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 33:
//#line 106 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 34:
//#line 107 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("invocación función linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 35:
//#line 108 "calc.y"
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
case 36:
//#line 123 "calc.y"
{ConfigurationParams.reversePolishStructure.add("itoul");}
break;
case 37:
//#line 127 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(3).sval);}
break;
case 38:
//#line 128 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(2).sval);}
break;
case 39:
//#line 131 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 40:
//#line 132 "calc.y"
{ConfigurationParams.reversePolishStructure.add(val_peek(0).sval);}
break;
case 41:
//#line 133 "calc.y"
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
//#line 152 "calc.y"
{ConfigurationParams.reversePolishStructure.add(Arrays.asList(val_peek(2).sval, "print"));}
break;
case 44:
//#line 157 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendError("Error: te olvidaste el DO linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
case 47:
//#line 164 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("if linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
            }
break;
case 48:
//#line 169 "calc.y"
{
                ConfigurationParams.mainView.getSintacticViewer().appendData("while linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");
                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
            }
break;
case 49:
//#line 175 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex()+2, jumpPosition);
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }
break;
case 50:
//#line 184 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(), jumpPosition);
                                            }
break;
case 51:
//#line 190 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex(), jumpPosition);
                                            }
break;
case 52:
//#line 196 "calc.y"
{
                                                Integer jumpPosition = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.addInPosition(ConfigurationParams.reversePolishStructure.getNextIndex()+2, jumpPosition);
                                                Integer jumpPosition2 = ConfigurationParams.reversePolishStructure.popElementFromStack();
                                                ConfigurationParams.reversePolishStructure.add(jumpPosition2);
                                                ConfigurationParams.reversePolishStructure.add("JUMP"); 
                                            }
break;
case 53:
//#line 206 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 54:
//#line 212 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 55:
//#line 218 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 56:
//#line 224 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 57:
//#line 230 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 58:
//#line 236 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 59:
//#line 244 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 60:
//#line 250 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 61:
//#line 256 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<>"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 62:
//#line 262 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add(">"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 63:
//#line 268 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("<"); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 64:
//#line 274 "calc.y"
{
                                                ConfigurationParams.reversePolishStructure.add("="); 
                                                ConfigurationParams.reversePolishStructure.pushElementInStack(ConfigurationParams.reversePolishStructure.getNextIndex());
                                                ConfigurationParams.reversePolishStructure.add(""); 
                                                ConfigurationParams.reversePolishStructure.add("JNE"); 
                                            }
break;
case 65:
//#line 284 "calc.y"
{ConfigurationParams.mainView.getSintacticViewer().appendData("return linea "+ ConfigurationParams.lexicalAnalizer.getNewLineCount() +"\n");}
break;
//#line 835 "Parser.java"
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
