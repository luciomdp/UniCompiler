import lexicalanalyzer.LexicalAnalizer;

public class App {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Por favor, proporciona el nombre del archivo de texto como argumento.");
            return;
        }
        String archivoTexto = args[0];
        LexicalAnalizer lexicalAnalizer = new LexicalAnalizer(archivoTexto);

    }
}
