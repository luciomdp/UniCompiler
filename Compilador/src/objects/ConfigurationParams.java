package objects;

public class ConfigurationParams {

    //De TokenViewer
    public static Boolean VIEW_TOKEN_NUMBER = true; // muestro el número de token

    public ConfigurationParams (Boolean production) {
        if(production){
            VIEW_TOKEN_NUMBER = true;
        }
    }
}
