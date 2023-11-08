import objects.ConfigurationParams;

public class App {
    public static void main(String[] args) throws Exception {
        new ConfigurationParams(false);
        ConfigurationParams.mainView.getPanelTokenViewer().parseAll();
    }

}
