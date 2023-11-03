package components;
import java.awt.*;

import javax.swing.text.StyleConstants;

public class SintacticViewer extends AbstractPanelViewer {

    private static final String title = "Visualizador sintáctico";
    private static final Color BACKGROUND_PANEL = new Color(109,128,214,255), BACKGROUND_COMPONENTS = new Color(81,105,206,255);

    public SintacticViewer() {
        super(title,BACKGROUND_PANEL,BACKGROUND_COMPONENTS,StyleConstants.ALIGN_CENTER);
        appendData("--------------------------- << Comienzo del análisis sintáctico >> ---------------------------\n");
    }
}