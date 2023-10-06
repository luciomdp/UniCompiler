package components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;

import objects.ConfigurationParams;

public class SymbolTableViewer extends JPanel{
    private static final Color BACKGROUND_PANEL = new Color(225,249,255,255), BACKGROUND_COMPONENTS = new Color(194,232,239,255), BACKGROUND_HEADER = new Color(44,221,221,255);
    private static final String header[] = { "Lexema", "Token", "Tipo de dato", "Entradas"};
    private JTable symbolTable;

    public SymbolTableViewer() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_PANEL);
        setBorder(new LineBorder(BACKGROUND_PANEL, 5));
        setVisible(true);
        updateTable();
    }

    public void updateTable() {
        removeAll();
        symbolTable = new JTable(ConfigurationParams.symbolTable.generateDataForTable(), header);
        symbolTable.getTableHeader().setBackground(BACKGROUND_HEADER);
        symbolTable.setFillsViewportHeight(true);
        symbolTable.setEnabled(false);
        symbolTable.setVisible(true);
        symbolTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        symbolTable.setBackground(BACKGROUND_COMPONENTS);
        add(new JScrollPane(symbolTable), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
