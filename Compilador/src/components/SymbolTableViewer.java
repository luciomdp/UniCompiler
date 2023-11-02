package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import objects.ConfigurationParams;

public class SymbolTableViewer extends JPanel{
    
    private static final String title = "Tabla de símbolos";
    private static final Color BACKGROUND_PANEL = new Color(225,249,255,255), BACKGROUND_COMPONENTS = new Color(194,232,239,255), BACKGROUND_HEADER = new Color(44,221,221,255);
    private static final String header[] = { "Lexema", "Token", "Tipo de dato", "Uso", "Entradas"};
    
    private JTable symbolTable;
    private JScrollPane panelSymbolTable;

    public SymbolTableViewer() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_PANEL);
        setBorder(new LineBorder(BACKGROUND_PANEL, 5));
        setVisible(true);
        setPreferredSize(new Dimension(700, 515));

        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lblTitle = new JLabel("<< " + title + " >>");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        JPanel panelTitle = new JPanel();
        panelTitle.setLayout(new BorderLayout());
        panelTitle.setBackground(new Color(178, 223, 250,150));
        panelTitle.setPreferredSize(new Dimension(700, 15));
        panelTitle.add(lblTitle,BorderLayout.CENTER);

        panelSymbolTable = new JScrollPane();

        add(panelTitle);
        add(Box.createRigidArea(new Dimension(0, 10)));  

        updateTable();
    }

    public void updateTable() {
        remove(panelSymbolTable);

        symbolTable = new JTable(ConfigurationParams.symbolTable.generateDataForTable(), header);
        symbolTable.getTableHeader().setBackground(BACKGROUND_HEADER);
        symbolTable.setFillsViewportHeight(true);
        symbolTable.setEnabled(false);
        symbolTable.setVisible(true);
        symbolTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        symbolTable.setBackground(BACKGROUND_COMPONENTS);

        panelSymbolTable = new JScrollPane(symbolTable);
        panelSymbolTable.setPreferredSize(new Dimension(700, 500));

        add(panelSymbolTable);
        revalidate();
        repaint();
    }

}
