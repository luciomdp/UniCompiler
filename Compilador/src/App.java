import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import lexicalanalyzer.LexicalAnalizer;

public class App {

    private static JFileChooser fileChooser = new JFileChooser();
    private static String path;

    private static LexicalAnalizer lexicalAnalizer;

    public static void main(String[] args) throws Exception {

        do {
            fileChooser.setDialogTitle("Elegí el archivo a compilar");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            //fileChooser.setCurrentDirectory(new File("C:\\Programacion\\Trabajo"));
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

        System.out.println(lexicalAnalizer.getToken());

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
  
}
