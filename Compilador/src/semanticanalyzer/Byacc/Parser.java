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
    import components.*;
//#line 20 "Parser.java"




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
    4,   11,   14,   12,   12,   13,   13,   16,    5,   17,
   17,   17,   18,   18,   18,   19,   19,   19,   19,   19,
   20,   20,   21,   21,   21,    6,    7,    7,    8,    8,
   22,   24,   24,   24,   24,   24,   24,   23,   15,
};
final static short yylen[] = {                            2,
    1,    3,    2,    1,    1,    1,    1,    1,    1,    2,
    2,    1,    1,    1,    1,    1,    1,    2,    3,    8,
    6,    3,    4,    1,    1,    3,    1,    1,    4,    1,
    3,    3,    1,    3,    3,    1,    1,    1,    2,    4,
    4,    3,    1,    1,    2,    5,    6,    5,    9,    7,
    3,    1,    1,    1,    1,    1,    1,    3,    5,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,    0,    0,    0,   24,   25,
    0,    4,    5,    6,    7,    8,    9,    0,   10,    0,
    0,    0,    0,    2,    3,   28,    0,    0,   27,    0,
   37,    0,    0,    0,    0,   33,   38,    0,    0,    0,
    0,    0,   19,    0,    0,    0,   39,   29,    0,    0,
    0,    0,   52,   53,   54,   55,   56,   57,    0,    0,
    0,    0,    0,   26,   43,   44,   42,    0,    0,    0,
    0,    0,   34,   35,    0,    0,   46,    0,    0,   48,
    0,    0,   45,   41,   40,    0,    0,   14,   15,   16,
   17,    0,   12,   13,    0,   47,    0,   21,    0,    0,
   50,   18,   58,   11,    0,    0,    0,    0,   22,    0,
    0,   20,   49,    0,   23,    0,    0,   59,
};
final static short yydgoto[] = {                          2,
    3,   11,   12,   13,   14,   15,   16,   17,   92,   93,
   94,   18,   28,   98,  111,   29,   38,   35,   36,   37,
   69,   39,   80,   59,
};
final static short yysindex[] = {                      -257,
 -167,    0,    0,  -13, -213,   19,   27,   56,    0,    0,
 -195,    0,    0,    0,    0,    0,    0, -238,    0,  -44,
  -44, -144,  -44,    0,    0,    0, -140,    5,    0,   81,
    0,   82, -135,  -30,   -5,    0,    0,   -4,   83,   86,
   87,   89,    0, -127,    3,  -44,    0,    0,  -44,  -44,
  -44,  -44,    0,    0,    0,    0,    0,    0,  -44, -126,
   84, -174,  -41,    0,    0,    0,    0, -118,  100,   92,
   -5,   -5,    0,    0,   71, -125,    0, -145, -125,    0,
 -123, -112,    0,    0,    0, -226,   88,    0,    0,    0,
    0, -185,    0,    0, -127,    0, -167,    0,  105, -257,
    0,    0,    0,    0,    7, -169, -123, -121,    0,  109,
 -117,    0,    0,  -44,    0,   93,   91,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -39,
    0,    0,    0,    0,  -34,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -29,   -7,    0,    0,  111,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   53,   57,   -6,    0,   -8,    2,   21,   28,    0,   63,
    0,  -18,   61,   50,    0,  114,    4,  -32,   26,    0,
    0,  136,  -11,    0,
};
final static int YYTABLESIZE=261;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         81,
   33,   36,   36,   36,   25,   36,   30,   36,   30,    1,
   30,   31,   49,   31,   50,   31,   71,   72,   26,   36,
   36,   36,   36,   34,   30,   30,   30,   30,   48,   31,
   31,   31,   31,   32,   27,   32,   51,   32,   49,  100,
   50,   52,  101,   67,   82,   19,   20,   68,   44,   70,
   44,   32,   32,   32,   32,   57,   58,   56,   21,   95,
    4,    5,   75,   43,   86,  109,   22,   96,    6,   88,
   87,    5,   24,   95,    7,    8,   73,   74,    6,   89,
    9,   10,  103,   88,    7,    8,    4,    5,    4,    5,
    9,   10,   78,   89,    6,   23,    6,   79,   90,   25,
    7,    8,    7,    8,  110,   91,    9,   10,    9,   10,
   87,    5,   90,   49,   40,   50,   42,  116,    6,   91,
   45,   46,   47,   60,    7,    8,   61,   62,   63,   26,
    9,   10,   85,  117,   49,   49,   50,   50,   76,   83,
   84,   78,   77,   97,   99,  107,  102,  113,  114,  118,
  115,   51,  108,  106,  104,  105,  112,   64,   41,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   30,   31,    0,    0,    0,    0,    0,    0,
    0,   36,   36,   36,    0,    0,   30,   30,   30,    0,
   32,   31,   31,   31,    9,   10,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   32,   32,   32,   53,   54,   55,   65,
   66,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,   11,   45,   41,   47,   43,  267,
   45,   41,   43,   43,   45,   45,   49,   50,  257,   59,
   60,   61,   62,   20,   59,   60,   61,   62,   59,   59,
   60,   61,   62,   41,  273,   43,   42,   45,   43,  266,
   45,   47,  269,   41,   63,   59,  260,   45,   44,   46,
   44,   59,   60,   61,   62,   60,   61,   62,   40,   78,
  256,  257,   59,   59,   76,   59,   40,   79,  264,   78,
  256,  257,  268,   92,  270,  271,   51,   52,  264,   78,
  276,  277,  268,   92,  270,  271,  256,  257,  256,  257,
  276,  277,  267,   92,  264,   40,  264,  272,   78,  106,
  270,  271,  270,  271,  274,   78,  276,  277,  276,  277,
  256,  257,   92,   43,  259,   45,  257,  114,  264,   92,
   40,   40,  258,   41,  270,  271,   41,   41,   40,  257,
  276,  277,   41,   41,   43,   43,   45,   45,  265,  258,
   41,  267,   59,  267,  257,   41,   59,  269,   40,   59,
  268,   41,  100,   97,   92,   95,  107,   44,   23,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  261,  262,  263,   -1,   -1,  261,  262,  263,   -1,
  275,  261,  262,  263,  276,  277,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  261,  262,  263,  261,  262,  263,  257,
  258,
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
"bloque : BEGIN sentencias END",
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
"declaracion : tipo variables ';'",
"declaracion : tipo FUN ID '(' tipo ID ')' bloque_funciones",
"declaracion : tipo FUN ID '(' ')' bloque_funciones",
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
"iteracion : WHILE '(' condicion ')' DO bloque_ejecutables",
"iteracion : WHILE '(' condicion ')' bloque_ejecutables",
"seleccion : IF '(' condicion ')' THEN bloque_ejecutables ELSE bloque END_IF",
"seleccion : IF '(' condicion ')' THEN bloque_ejecutables END_IF",
"condicion : expresion comparador expresion",
"comparador : GREATER_EQUAL",
"comparador : LESS_EQUAL",
"comparador : NOT_EQUAL",
"comparador : '>'",
"comparador : '<'",
"comparador : '='",
"bloque_ejecutables : BEGIN sentencias_ejecutables END",
"retorno : RETURN '(' expresion ')' ';'",
};

//#line 143 "calc.y"
private static LexicalAnalizer lexicalAnalizer;
private static MainView mainView;

public static void main(String[] args) throws Exception {
    lexicalAnalizer = new LexicalAnalizer();
    mainView = new MainView(lexicalAnalizer);
    Parser parser = new Parser(true);
    parser.yyparse(); 
}

public int yylex() {
    int token = lexicalAnalizer.yylex();
    mainView.getPanelTokenViewer().printToken(token);
    yyval = new ParserVal(lexicalAnalizer.getLexema());
    return token;
}
public void yyerror(String s) {
    mainView.getSemanticViewer().appendError(s + ", en la línea"+ lexicalAnalizer.getNewLineCount() +"\n");
}
//#line 352 "Parser.java"
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
//#line 21 "calc.y"
{mainView.getSemanticViewer().appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");}
break;
case 5:
//#line 28 "calc.y"
{mainView.getSemanticViewer().appendData("declaracion\n");}
break;
case 6:
//#line 29 "calc.y"
{mainView.getSemanticViewer().appendData("asignacion\n");}
break;
case 7:
//#line 30 "calc.y"
{mainView.getSemanticViewer().appendData("impresion\n");}
break;
case 8:
//#line 31 "calc.y"
{mainView.getSemanticViewer().appendData("iteracion\n");}
break;
case 9:
//#line 32 "calc.y"
{mainView.getSemanticViewer().appendData("seleccion\n");}
break;
case 10:
//#line 33 "calc.y"
{mainView.getSemanticViewer().appendError("Error de sentencia\n");}
break;
case 13:
//#line 40 "calc.y"
{mainView.getSemanticViewer().appendData("declaracion variables\n");}
break;
case 14:
//#line 41 "calc.y"
{mainView.getSemanticViewer().appendData("asignacion\n");}
break;
case 15:
//#line 42 "calc.y"
{mainView.getSemanticViewer().appendData("impresion\n");}
break;
case 16:
//#line 43 "calc.y"
{mainView.getSemanticViewer().appendData("iteracion\n");}
break;
case 17:
//#line 44 "calc.y"
{mainView.getSemanticViewer().appendData("seleccion\n");}
break;
case 18:
//#line 45 "calc.y"
{mainView.getSemanticViewer().appendError("Error de sentencia ejecutable\n");}
break;
case 48:
//#line 114 "calc.y"
{mainView.getSemanticViewer().appendError("Error: te olvidaste el DO\n");}
break;
//#line 557 "Parser.java"
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
