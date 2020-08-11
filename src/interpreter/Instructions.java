package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Instructions {
    private List<Actions> instructions;

    // when no array is provided 
    // -> init an empty array
    public Instructions() {
        this.instructions = new ArrayList<Actions>();
    }

    public Instructions(List<Actions> instructions) {
        this.instructions = instructions;
    }

    public Actions get(int i) {
        return this.instructions.get(i);
    }

    public List<Actions> getInstructions() {
        return this.instructions;
    }

    public int size() {
        return this.instructions.size();
    }

    // return the section of the list 
    // with withing the given interval
    public Instructions getInterval(int a, int b) {

        Instructions temp = new Instructions();

        // iterate from a to b and add elements to temp 
        for (int i = a; i < b; i++) {
            temp.add(this.instructions.get(i));
        }

        return temp;
    }

    public void add(Actions action) {
        this.instructions.add(action);
    }

}