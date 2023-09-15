import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import lexicalanalyzer.LexicalAnalizer;
import lexicalanalyzer.TokenViewer;

public class App {

    private static ;
    private static String path;
    private static LexicalAnalizer lexicalAnalizer;

    public static void main(String[] args) throws Exception {
        initialiceLexicalAnalicer();
        //TokenViewer parser = new TokenViewer(lexicalAnalizer);
        //parser.beginToParse();
        Parser parser = new Parser(true);
        parser.yyparse();
    }

    public static int yylex() {
        int token = lexicalAnalizer.yylex();
        //yyval = new ParserVal(lexicalAnalizer.getLexema());
        return token;
    }

}
