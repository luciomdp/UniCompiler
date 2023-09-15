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
    import lexicalanalyzer.LexicalAnalizer;
//#line 19 "Parser.java"




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
public final static short INT_CONST=258;
public final static short ULONGINT_CONST=259;
public final static short STRING_CONST=260;
public final static short ASIGNACION=261;
public final static short GREATER_EQUAL=262;
public final static short LESS_EQUAL=263;
public final static short NOT_EQUAL=264;
public final static short IF=265;
public final static short THEN=266;
public final static short ELSE=267;
public final static short BEGIN=268;
public final static short END=269;
public final static short END_IF=270;
public final static short PRINT=271;
public final static short WHILE=272;
public final static short DO=273;
public final static short FUN=274;
public final static short RETURN=275;
public final static short ITOUL=276;
public final static short INTEGER=277;
public final static short ULONGINT=278;
public final static short IGNORE=279;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    9,    9,    9,    9,    9,    9,    9,    4,    4,    4,
   10,   13,   11,   11,   12,   12,   15,    5,   16,   16,
   16,   16,   17,   17,   17,   18,   18,   18,   18,   18,
   18,   19,   20,   20,   20,   20,   20,    6,    7,    7,
    8,    8,   21,   23,   23,   23,   23,   23,   23,   22,
   14,
};
final static short yylen[] = {                            2,
    1,    3,    2,    1,    1,    1,    1,    1,    1,    2,
    0,    1,    1,    1,    1,    1,    2,    3,    8,    6,
    3,    4,    1,    1,    3,    1,    1,    4,    1,    3,
    3,    4,    1,    3,    3,    1,    1,    1,    1,    2,
    2,    5,    1,    1,    1,    2,    2,    5,    6,    5,
    9,    7,    3,    1,    1,    1,    1,    1,    1,    3,
    5,
};
final static short yydefred[] = {                         0,
    0,    0,    1,    0,    0,    0,    0,    0,   23,   24,
    0,    4,    5,    6,    7,    8,    9,    0,   10,    0,
    0,    0,    0,    2,    3,   27,    0,    0,   26,    0,
   37,   38,    0,    0,    0,    0,   33,   39,    0,    0,
    0,    0,    0,   18,    0,    0,    0,   40,   41,   28,
    0,    0,    0,    0,   54,   55,   56,   57,   58,   59,
    0,    0,    0,    0,    0,   25,   43,   44,   45,    0,
    0,    0,    0,    0,   34,   35,    0,    0,   48,    0,
    0,   50,    0,    0,   46,   47,    0,   32,    0,    0,
   13,   14,   15,   16,    0,   12,    0,   49,    0,   20,
    0,   42,    0,   52,   17,   60,    0,    0,    0,    0,
   21,    0,    0,   19,   51,    0,   22,    0,    0,   61,
};
final static short yydgoto[] = {                          2,
    3,   11,   12,   13,   14,   15,   16,   17,   95,   96,
   18,   28,  100,  113,   29,   39,   36,   37,   38,   71,
   40,   82,   61,
};
final static short yysindex[] = {                      -224,
 -169,    0,    0,    9, -189,   38,   44,   50,    0,    0,
 -196,    0,    0,    0,    0,    0,    0, -211,    0,  -44,
  -44, -150,  -44,    0,    0,    0, -162,    3,    0,   61,
    0,    0,   71, -192,   -8,  -23,    0,    0,   -4,   74,
   75,   76,   81,    0, -134,  -40,  -44,    0,    0,    0,
  -16,  -16,  -16,  -16,    0,    0,    0,    0,    0,    0,
  -44, -141,   67, -225,  -41,    0,    0,    0,    0, -165,
   86,  -28,  -23,  -23,    0,    0,   -3, -140,    0, -159,
 -140,    0, -139, -127,    0,    0,   72,    0, -193,   73,
    0,    0,    0,    0, -136,    0, -134,    0, -169,    0,
   93,    0, -224,    0,    0,    0,    5, -186, -139, -135,
    0,   96, -132,    0,    0,  -44,    0,   79,   80,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -39,
    0,    0,    0,    0,    0,  -34,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -29,   -7,    0,    0,   97,    0,    0, -129,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   39,   42,   -1,    0,   63,   64,   65,   66,    0,    0,
  -15,   52,   41,    0,  102,   -2,   48,   51,    0,    0,
  125,    2,    0,
};
final static int YYTABLESIZE=260;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
   34,   36,   36,   36,   70,   36,   29,   36,   29,   25,
   29,   30,   88,   30,   51,   30,   52,   35,   53,   36,
   36,   36,   36,   54,   29,   29,   29,   29,   34,   30,
   30,   30,   30,   31,   51,   31,   52,   31,   51,   51,
   52,   52,   80,    1,   72,   26,   45,   81,   45,   84,
   50,   31,   31,   31,   31,   59,   60,   58,   77,    4,
    5,   44,   27,  111,   97,   48,   49,   19,    6,    4,
    5,   20,   24,  103,    7,    8,  104,   21,    6,   89,
    9,   10,   98,   22,    7,    8,    4,    5,  112,   23,
    9,   10,   85,   86,   43,    6,   90,    5,   73,   74,
   46,    7,    8,   75,   76,    6,   25,    9,   10,   41,
   47,    7,    8,  118,   62,   63,   64,    9,   10,  119,
   65,   51,   26,   52,   78,   79,   87,   80,   99,  101,
  102,  105,  106,  109,  115,  116,  117,   53,  120,   11,
  108,  110,   91,   92,   93,   94,   66,   42,  107,  114,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   30,   31,   32,    0,   67,   68,   69,    0,
    0,    0,   36,   36,   36,    0,    0,   29,   29,   29,
    0,   33,   30,   30,   30,    9,   10,    0,    0,    0,
   30,   31,   32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   31,   31,   31,   55,   56,   57,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   45,   41,   42,   43,   45,   45,   41,   47,   43,   11,
   45,   41,   41,   43,   43,   45,   45,   20,   42,   59,
   60,   61,   62,   47,   59,   60,   61,   62,   45,   59,
   60,   61,   62,   41,   43,   43,   45,   45,   43,   43,
   45,   45,  268,  268,   47,  257,   44,  273,   44,   65,
   59,   59,   60,   61,   62,   60,   61,   62,   61,  256,
  257,   59,  274,   59,   80,  258,  259,   59,  265,  256,
  257,  261,  269,  267,  271,  272,  270,   40,  265,   78,
  277,  278,   81,   40,  271,  272,  256,  257,  275,   40,
  277,  278,  258,  259,  257,  265,  256,  257,   51,   52,
   40,  271,  272,   53,   54,  265,  108,  277,  278,  260,
   40,  271,  272,  116,   41,   41,   41,  277,  278,   41,
   40,   43,  257,   45,  266,   59,   41,  268,  268,  257,
   59,   59,  269,   41,  270,   40,  269,   41,   59,  269,
   99,  103,   80,   80,   80,   80,   45,   23,   97,  109,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,   -1,  257,  258,  259,   -1,
   -1,   -1,  262,  263,  264,   -1,   -1,  262,  263,  264,
   -1,  276,  262,  263,  264,  277,  278,   -1,   -1,   -1,
  257,  258,  259,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  262,  263,  264,  262,  263,  264,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=279;
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
null,null,null,null,null,null,"ID","INT_CONST","ULONGINT_CONST","STRING_CONST",
"ASIGNACION","GREATER_EQUAL","LESS_EQUAL","NOT_EQUAL","IF","THEN","ELSE",
"BEGIN","END","END_IF","PRINT","WHILE","DO","FUN","RETURN","ITOUL","INTEGER",
"ULONGINT","IGNORE",
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
"sentencias_ejecutables :",
"sentencias_ejecutables : declaracion_variables",
"sentencias_ejecutables : asignacion",
"sentencias_ejecutables : impresion",
"sentencias_ejecutables : iteracion",
"sentencias_ejecutables : seleccion",
"sentencias_ejecutables : error ';'",
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
"expresion : ITOUL '(' expresion ')'",
"termino : factor",
"termino : termino '*' factor",
"termino : termino '/' factor",
"factor : ID",
"factor : INT_CONST",
"factor : ULONGINT_CONST",
"factor : invocacion",
"factor : '-' INT_CONST",
"factor : '-' ULONGINT_CONST",
"invocacion : ID '(' parametros ')' ';'",
"parametros : ID",
"parametros : INT_CONST",
"parametros : ULONGINT_CONST",
"parametros : '-' INT_CONST",
"parametros : '-' ULONGINT_CONST",
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

//#line 141 "calc.y"
private static LexicalAnalizer lexicalAnalizer;

public static void main(String[] args) throws Exception {
    lexicalAnalizer = new LexicalAnalizer();
    Parser parser = new Parser(true);
    parser.yyparse(); 
}

public int yylex() {
    int token = lexicalAnalizer.yylex();
    yyval = new ParserVal(lexicalAnalizer.getLexema());
    return token;
}
public void yyerror(String s) {
    System.out.println(s);
}
//#line 353 "Parser.java"
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
case 10:
//#line 32 "calc.y"
{System.out.println("Error en sentencia");}
break;
case 17:
//#line 40 "calc.y"
{System.out.println("Error en sentencia ejecutable");}
break;
case 50:
//#line 112 "calc.y"
{System.out.println("ERROR: te olvidaste el DO");}
break;
//#line 514 "Parser.java"
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
