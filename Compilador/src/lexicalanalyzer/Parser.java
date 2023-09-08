package lexicalanalyzer;
import java.awt.*;

import java.io.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import objects.enums.ETokenType;

public class Parser extends JFrame{
    private PanelParserInfo panel;

    private JFileChooser fileChooser = new JFileChooser();
    private String path;

    private LexicalAnalizer lexicalAnalizer;

    public Parser() {
        panel = new PanelParserInfo();
        add(panel);
		setTitle("FasTALC (FASTA Language Compiler)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("../TestUnits").getImage());
        setVisible(true);
        setResizable(false);

        do {
            fileChooser.setDialogTitle("Elegí el archivo a compilar");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setCurrentDirectory(new File("C:\\Programacion\\Facultad\\CompiladorUNI\\TestUnits"));
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
        panel.appendData("--------------------------- << Comienzo del análisis léxico >> ---------------------------\n");
        panel.appendData("------------- << Tokens en orden >> -------------\n");
        do {
            currentToken = lexicalAnalizer.getToken();
            panel.appendData(currentToken.toString() + "(" + ETokenType.getDescription(currentToken.intValue()) + ")\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while(currentToken != -1);
        
        panel.appendData("------------------------------ << Fin del análisis léxico >> ------------------------------");
    }

    private class  PanelParserInfo extends JPanel{

        private final Color BACKGROUND_PANEL = new Color(90,75,181,255),BACKGROUND_COMPONENTS = new Color(47,159,241,255);
        private JTextPane txtArea;
        private StringBuilder str;

        public PanelParserInfo (){
            str = new StringBuilder("");
            txtArea = new JTextPane();
            txtArea.setPreferredSize(new Dimension(700, 500));
            txtArea.setEditable(false);
            txtArea.setBackground(BACKGROUND_COMPONENTS);
            Font f = new Font(Font.MONOSPACED, Font.BOLD, 12);
            txtArea.setFont(f);
            StyledDocument doc = txtArea.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
            JScrollPane panel = new JScrollPane(txtArea);
            add(panel);
            setBackground(BACKGROUND_PANEL);
            setResizable(false);
        }

        public void appendData(String data) {
            str.append(data);
            txtArea.setText(str.toString());
        }
    }
}
