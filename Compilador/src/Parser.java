import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import lexicalanalyzer.LexicalAnalizer;

public class Parser extends JFrame{
    private JPanel panel;
    private JTextArea txtArea;

    private JFileChooser fileChooser = new JFileChooser();
    private String path;

    private LexicalAnalizer lexicalAnalizer;

    public Parser() {
        panel = new JPanel();
        txtArea = new JTextArea(20,80);
        panel.add(txtArea);
        add(panel);
		setTitle("I18N Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("src/images/logo.png").getImage());

        do {
            fileChooser.setDialogTitle("Elegí el archivo a compilar");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setCurrentDirectory(new File("C:\\PROYECTOS\\PERSONAL\\CompiladorUNI\\TestUnits"));
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

    public boolean readFiles(String path) {
        try {
            lexicalAnalizer = new LexicalAnalizer(path);
            return true;
        }catch(FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void beginToParse() {
        Long currentToken;
        txtArea.append("--------------------------- << Comienzo del análisis léxico >> ---------------------------");
        do {
            currentToken = lexicalAnalizer.getToken();
            txtArea.append(currentToken.toString());
        }while(currentToken != -1);
        
        txtArea.append("--------------------------- << Fin del análisis léxico >> ---------------------------");
    }
    
}
