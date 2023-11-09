package objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ReversePolishStructure {

    //Para agregar un elemento a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(value)
    //Para agregar varios elementos a la lista, tendremos que hacer o ConfigurationParams.ReversePolishStructure.add(Arrays.asList(values[]))

    private Map<String,List<String>> reversePolishList;
    private Stack<Integer> stack;

    public ReversePolishStructure() {
        reversePolishList = new LinkedHashMap<String,List<String>>();
        stack = new Stack<>();
    }

    public void generateNewReversePolish(String id) {
        if(!reversePolishList.containsKey(id)){
            reversePolishList.put(id, new ArrayList<String>());
        }
    }

    public void add (String value) {
        reversePolishList.get(ConfigurationParams.getCurrentScope()).add(value);
        ConfigurationParams.updateReversePolishView(ConfigurationParams.getCurrentScope(),value,getNextIndex(ConfigurationParams.getCurrentScope())-1);
    }
    public void addInPosition (Integer value, Integer position) {
        reversePolishList.get(ConfigurationParams.getCurrentScope()).set(position, value.toString());
        ConfigurationParams.updateReversePolishView(ConfigurationParams.getCurrentScope(),value.toString(),position);
    }    
    public void add (Integer value) {
        reversePolishList.get(ConfigurationParams.getCurrentScope()).add(value.toString());
        ConfigurationParams.updateReversePolishView(ConfigurationParams.getCurrentScope(),value.toString(),getNextIndex(ConfigurationParams.getCurrentScope())-1);
    }

    public void add(List<Object> values) {
        
        values.stream().forEach(value -> {
            if(value instanceof String)
                add((String)value);
            if(value instanceof Integer)
                add((Integer)value);  
        });
    }
    public Integer getNextIndex(String scope) {    
        return reversePolishList.get(scope).size();
    }

    public Integer popElementFromStack(){
        return stack.pop();
    }
    public void pushElementInStack(Integer value){
        stack.push(value);
    }

    public List<String> getReversePolishList() {
        return reversePolishList.get(ConfigurationParams.getCurrentScope());
    }

    public void setReversePolishList(List<String> reversePolishList) {
        this.reversePolishList.put(ConfigurationParams.getCurrentScope(),reversePolishList);
    }

    public List<String> removePolish(String key) {
        return reversePolishList.remove(key);
    }

    public String getNextKey() {
        return reversePolishList.keySet().iterator().hasNext()?reversePolishList.keySet().iterator().next():null;
    }

}


