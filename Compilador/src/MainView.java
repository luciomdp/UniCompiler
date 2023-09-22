import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import lexicalanalyzer.LexicalAnalizer;
import lexicalanalyzer.TokenViewer;

public class MainView  extends JFrame{

    private JTabbedPane tab;
    private TokenViewer panelTokenViewer; 
    
    public MainView(LexicalAnalizer lexicalAnalizer) {
        tab = new JTabbedPane(JTabbedPane.TOP);
        panelTokenViewer = new TokenViewer(lexicalAnalizer);        
       
        tab.addTab("Visualizador de Tokens", generateTokenIcon(), panelTokenViewer, null);

        this.setIconImage(new ImageIcon("Compilador/src/images/logo.png").getImage());
		setTitle("FasTALC (FASTA Language Compiler)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(tab);
		setContentPane(contentPane);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);

        panelTokenViewer.appendData("--------------------------- << Comienzo del análisis léxico >> ---------------------------\n");
        panelTokenViewer.appendData("------------- << [Lexema,Token] >> -------------\n");
    }

    private Icon generateTokenIcon() {
        try {
            BufferedImage originalImage = ImageIO.read(new File("Compilador/src/images/token.png"));
            Image scaledImage = originalImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
           return null;
        }
    }

    public TokenViewer getPanelTokenViewer() {
        return panelTokenViewer;
    }

    public void setPanelTokenViewer(TokenViewer panelTokenViewer) {
        this.panelTokenViewer = panelTokenViewer;
    }
    
}
