package objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReversePolishStructure {

    //Para agregar un elemento a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(value)
    //Para agregar varios elementos a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(Arrays.asList(values[]))

    private List<String> reversePolishList;
    private Stack<Integer> stack;

    public ReversePolishStructure() {
        reversePolishList = new ArrayList<String>();
        stack = new Stack<>();
    }

    public void add (String value) {
        reversePolishList.add(value);
        ConfigurationParams.updateReversePolishView(value,getNextIndex()-1);
    }
    public void addInPosition (Integer value, Integer position) {
        reversePolishList.set(position, value.toString());
        ConfigurationParams.updateReversePolishView(value.toString(),position);
    }    
    public void add (Integer value) {
        reversePolishList.add(value.toString());
        ConfigurationParams.updateReversePolishView(value.toString(),getNextIndex()-1);
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

    public Integer popElementFromStack(){
        return stack.pop();
    }
    public void pushElementInStack(Integer value){
        stack.push(value);
    }

    public List<String> getReversePolishList() {
        return reversePolishList;
    }

    public void setReversePolishList(List<String> reversePolishList) {
        this.reversePolishList = reversePolishList;
    }

    

}


