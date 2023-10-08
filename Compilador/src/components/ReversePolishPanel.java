package components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import objects.ConfigurationParams;

public class ReversePolishPanel extends JPanel{
    private static final Color BACKGROUND_BORDER = new Color(216,210,222,255),BACKGROUND_PANEL = new Color(157,142,173,255), BACKGROUND_COMPONENTS = new Color(118,97,141,255), BACKGROUND_HEADER = new Color(241,171,192,255);

    public static final Double pageSize = Double.valueOf(9);
    public static Double currentPage = Double.valueOf(0);

    private JPanel verticalLayoutContainer;
    private List<JTable> tables;
    

    public ReversePolishPanel(String title) {

        setLayout(new BorderLayout());

        setBackground(BACKGROUND_BORDER);
        setBorder(new LineBorder(BACKGROUND_BORDER, 5,true));
        setVisible(true);
        verticalLayoutContainer = new JPanel();
        verticalLayoutContainer.setBackground(BACKGROUND_PANEL);
        verticalLayoutContainer.setLayout(new DynamicHeightLayoutManager());

        JLabel lblTitle = new JLabel("_______________________<< " + title + " >>_______________________");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(JLabel.CENTER);

        verticalLayoutContainer.add(lblTitle, BorderLayout.CENTER);
        verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 10)));  
        
        tables = new ArrayList<JTable>();
        tables.add(getNewTable());

        add(verticalLayoutContainer,BorderLayout.CENTER);

    }

    public void updateTable(String newVal,int index) {

        if(createNewPage()){
            verticalLayoutContainer.add(Box.createRigidArea(new Dimension(0, 15)));  
            tables.add(getNewTable());
            verticalLayoutContainer.revalidate();
            verticalLayoutContainer.repaint();
        }
        tables.get(getTableForIndex(index)).setValueAt(newVal, 0, getTableIndex(index));
        revalidate();
        repaint();

    }

    private int getTableIndex(int index) {
        int val = index - getTableForIndex(index)*pageSize.intValue();
        return val;
    }

    private int getTableForIndex(int index) {
        int val = Double.valueOf(Math.floor(Double.valueOf(index)/pageSize)).intValue();
        return val;
    }

    private JTable getNewTable() {
        DefaultTableModel model = new DefaultTableModel(1, pageSize.intValue());
        JTable newTable = new JTable(model);
        
        JTableHeader tableHeader = newTable.getTableHeader();
        DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
        tableRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tableRenderer.setForeground(Color.WHITE);

        int x=0;
        for (Double i = currentPage * pageSize; i < currentPage * pageSize + pageSize; i++) {
            tableHeader.getColumnModel().getColumn(x).setHeaderValue(i.intValue());
            tableHeader.getColumnModel().getColumn(x).setCellRenderer(tableRenderer);
            x++;
        }
        tableHeader.setBackground(BACKGROUND_HEADER);
        tableHeader.setVisible(true);
        tableHeader.setAutoscrolls(false);
        newTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        newTable.setFillsViewportHeight(true);
        newTable.setEnabled(false);
        newTable.setVisible(true);
        
        newTable.setBackground(BACKGROUND_COMPONENTS);

        verticalLayoutContainer.add(tableHeader);
        verticalLayoutContainer.add(newTable);   
        return newTable;
    }


    public Boolean createNewPage() {
        if(tables.size()*pageSize <= ConfigurationParams.reversePolishStructure.getReversePolishList().size()){
            currentPage++;
            return true;
        }
        return false;
    }

    class DynamicHeightLayoutManager implements LayoutManager {
        private int totalHeight = 0;

        @Override
        public void addLayoutComponent(String name, Component comp) {
            totalHeight += comp.getPreferredSize().height;
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            int maxWidth = 0;

            for (Component c : parent.getComponents()) {
                Dimension prefSize = c.getPreferredSize();
                maxWidth = Math.max(maxWidth, prefSize.width);
            }

            return new Dimension(maxWidth, totalHeight);
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }

        @Override
        public void layoutContainer(Container parent) {
            int y = 0;

            for (Component c : parent.getComponents()) {
                int width = parent.getWidth();
                int height = c.getPreferredSize().height;

                c.setBounds(0, y, width, height);

                y += height;
            }
        }


        @Override
        public void removeLayoutComponent(Component comp) {
            totalHeight -= comp.getPreferredSize().height;
        }
    }
}
