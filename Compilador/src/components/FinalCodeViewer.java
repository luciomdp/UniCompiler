package components;
import java.awt.*;

import javax.swing.text.StyleConstants;

public class FinalCodeViewer extends AbstractPanelViewer {

    private static final String title = "Visualizador de código";
    private static final Color BACKGROUND_PANEL = new Color(135,195,241), BACKGROUND_COMPONENTS = new Color(108,116,121,255);

    public FinalCodeViewer() {
        super(title,BACKGROUND_PANEL,BACKGROUND_COMPONENTS,StyleConstants.ALIGN_LEFT);
    }
}
