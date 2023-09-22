import objects.ConfigurationParams;

public class App {
    public static void main(String[] args) throws Exception {
        ConfigurationParams globalParams = new ConfigurationParams(false);
        ConfigurationParams.mainView.getPanelTokenViewer().parseAll();
    }

}
