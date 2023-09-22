import lexicalanalyzer.LexicalAnalizer;

public class App {
    private static LexicalAnalizer lexicalAnalizer;
    private static MainView mainView;

    public static void main(String[] args) throws Exception {
        lexicalAnalizer = new LexicalAnalizer();
        mainView = new MainView(lexicalAnalizer);
        mainView.getPanelTokenViewer().parseAll();
        Parser parser = new Parser(true);
        parser.yyparse(); 
    }

}
