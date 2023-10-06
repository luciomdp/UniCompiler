package objects;

import java.util.ArrayList;
import java.util.List;

public class ReversePolishStructure {

    //Para agregar un elemento a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(value)
    //Para agregar varios elementos a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(Arrays.asList(values[]))
    public static final int pageSize = 40;

    private List<String> reversePolishList;

    public ReversePolishStructure() {
        reversePolishList = new ArrayList<String>();
    }

    public void add (String value) {
        reversePolishList.add(value);
        ConfigurationParams.updateReversePolishView();
    }
    public void add (Integer value) {
        reversePolishList.add(value.toString());
        ConfigurationParams.updateReversePolishView();
    }

    public void add(List<Object> values) {
        
        values.stream().forEach(value -> {
            if(value instanceof String)
                add((String)value);
            if(value instanceof Integer)
                add((Integer)value);  
        });
    }

    public List<String[][]> generateDataForMultipleTableInput() {
        List<String[][]> tablesData = new ArrayList<>();
        for(int i = 0; i < Math.ceil(reversePolishList.size()/pageSize);i++) {
            tablesData.add(generateDataForTablePage(i));
        }
        return tablesData;
    }

    public String[][] generateDataForTablePage (int page) {

        String data[][] = new String[2][pageSize];
        int x = 0;
        for (int i = page*pageSize;i<page*pageSize+pageSize;i++){
            data[x][0] = String.valueOf(i);
            data[x][1] = reversePolishList.get(i);
            x++;
        }
        return data;
    }
}


