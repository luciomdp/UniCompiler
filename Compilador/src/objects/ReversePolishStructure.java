package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReversePolishStructure {

    //Para agregar un elemento a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(value)
    //Para agregar varios elementos a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(Arrays.asList(values[]))
    public static final int pageSize = 40;

    private List<String> reversePolishList;
    private Stack<Integer> stack;

    public ReversePolishStructure() {
        reversePolishList = new ArrayList<String>();
        stack = new Stack<>();
    }

    public void add (String value) {
        reversePolishList.add(value);
        ConfigurationParams.updateReversePolishView();
    }
    public void addInPosition (Integer value, Integer position) {
        reversePolishList.set(position, value.toString());
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
    public Integer getNextIndex() {    
        return reversePolishList.size();
    }

    public List<ReversePolishDataTableElement> generateDataForMultipleTableInput() {
        List<ReversePolishDataTableElement> tablesData = new ArrayList<>();
        for(int i = 0; i < Math.ceil(reversePolishList.size()/pageSize);i++) {
            tablesData.add(generateDataForTablePage(i));
        }
        return tablesData;
    }

    public ReversePolishDataTableElement generateDataForTablePage (int page) {

        ReversePolishDataTableElement element = new ReversePolishDataTableElement();
        int x = 0;
        for (int i = page*pageSize;i<page*pageSize+pageSize;i++){ 
            element.addToHeader(x, String.valueOf(i));
            element.addToRow(x, reversePolishList.get(i));
            x++;
        }
        return element;
    }
    public class ReversePolishDataTableElement {
        private String[] header;
        private String[][] row;
        public ReversePolishDataTableElement() {
            header = new String[pageSize];
            row = new String[pageSize][1];
        }

        public String[] getHeader() {
            return header;
        }
        public void setHeader(String[] header) {
            this.header = header;
        }

        public String[][] getRow() {
            return row;
        }

        public void setRow(String[][] row) {
            this.row = row;
        }
        public void addToHeader(int position,String value) {
            header[position] = value;
        }
        public void addToRow(int position,String value) {
            row[position][0] = value;
        }
    }
    public Integer popElementFromStack(){
        return stack.pop();
    }
    public void pushElementInStack(Integer value){
        stack.push(value);
    }

}


