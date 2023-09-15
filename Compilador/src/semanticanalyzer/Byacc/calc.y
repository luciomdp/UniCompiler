
/* --------------- DECLARACION --------------- */
%{
    import lexicalanalyzer.LexicalAnalizer;
    import javax.swing.JFileChooser;
%}

%token ID INT_CONST ULONGINT_CONST STRING_CONST ASIGNACION GREATER_EQUAL LESS_EQUAL NOT_EQUAL IF THEN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN ITOUL INTEGER ULONGINT GREATER_THAN LESS_THAN EQUAL

/* --------------- GRAMATICA --------------- */
%%
/* -----  SENTENCIA START -----  */

programa : 
            bloque
;

/* -----  INICIO -----  */

bloque :   
            BEGIN sentencias END
;
sentencias : 
            sentencias sentencia
        |   sentencia
;
sentencia :  
            declaracion
        |   asignacion
        |   impresion
        |   iteracion
        |   retorno
        |   seleccion
        |   error ';'   {System.out.println("Error en sentencia");}
;

/* ----- SENTENCIAS DECLARATIVAS ----- */
declaracion :   
            tipo variables ';' //Declaracion de dato
        |   tipo FUN ID '(' tipo ID ')' bloque //Declaracion de funcion con 1 parametro
        |   tipo FUN ID '('  ')' bloque //Declaracion de funcion con 0 parametro
;
tipo :       
            INTEGER
        |   ULONGINT
variables : 
            variables ',' variable 
        |   variable
;
variable : 
            ID
;

/* ----- SENTENCIAS EJECUTABLES ----- */
/* ASIGNACION */
asignacion : 
            ID ASIGNACION expresion ';'
;

expresion : 
            termino
        |   expresion '+' termino 
        |   expresion '-' termino   
;

termino :   
            factor
        |   termino '*' factor 
        |   termino '/' factor 
    ;

factor :    
            ID 
        |   INT_CONST  
        |   ULONGINT_CONST
        |   invocacion
;

invocacion: 
            ID '(' parametros ')' ';'
;
parametros:
            ID
        |   INT_CONST
        |   ULONGINT_CONST
;

/* ----- OTRAS ----- */
/* IMPRESION */
impresion : 
            PRINT '(' STRING_CONST ')' ';'
;
/* ITERACION */
iteracion: 
            WHILE '(' condicion ')' DO bloque
        |   WHILE '(' condicion ')' bloque {System.out.println("ERROR: te olvidaste el "DO"");}
;
/* SELECCION */
seleccion: 
            IF '(' condicion ')' THEN bloque ELSE bloque END_IF
        |   IF '(' condicion ')' THEN bloque END_IF
;

condicion:
            expresion comparador expresion
;
comparador:
            GREATER_EQUAL
        |   LESS_EQUAL
        |   NOT_EQUAL
        |   '>'
        |   '<'
        |   '='
;
/* RETORNO */
retorno: 
            RETURN '(' expresion ')' ';'
;

/* --------------- CODIGO --------------- */
%%
private static JFileChooser fileChooser = new JFileChooser();
private static String path;
private static LexicalAnalizer lexicalAnalizer;

public static void main(String[] args) throws Exception {
    initialiceLexicalAnalicer();
    //TokenViewer parser = new TokenViewer(lexicalAnalizer);
    //parser.beginToParse();
    Parser parser = new Parser(true);
    parser.yyparse();
}

public int yylex() {
    int token = lexicalAnalizer.yylex();
    yyval = new ParserVal(lexicalAnalizer.getLexema());
    return token;
}

public void initialiceLexicalAnalicer() {
    do {
        fileChooser.setDialogTitle("Elegí el archivo a compilar");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(new File("TestUnits"));
        try {
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
                path = fileChooser.getSelectedFile().getAbsolutePath();
            else
                Thread.currentThread().stop();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null,(String)"No se ha seleccionado ningún archivo");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }while(path == null || !readFiles(path));
}

    public static boolean readFiles(String path) {
    try {
        lexicalAnalizer = new LexicalAnalizer(path);
        return true;
    }catch(FileNotFoundException e) {
        e.printStackTrace();
        return false;
    }
}

public void yyerror(String s) {
    System.out.println(s);
}
