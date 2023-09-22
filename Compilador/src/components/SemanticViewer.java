package components;
import java.awt.*;

public class SemanticViewer extends AbstractPanelViewer {

    private static final Color BACKGROUND_PANEL = new Color(34,138,181,255), BACKGROUND_COMPONENTS = new Color(42,172,226,255);

    public SemanticViewer() {
        super(BACKGROUND_PANEL,BACKGROUND_COMPONENTS);
        appendData("--------------------------- << Comienzo del análisis semántico >> ---------------------------\n");
    }
}