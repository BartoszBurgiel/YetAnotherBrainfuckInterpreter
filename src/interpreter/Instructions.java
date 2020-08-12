package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Instructions {
    private List<Action> instructions;

    // when no array is provided
    // -> init an empty array
    public Instructions() {
        this.instructions = new ArrayList<Action>();
    }

    public Instructions(List<Action> instructions) {
        this.instructions = instructions;
    }

    public Action get(int i) {
        return this.instructions.get(i);
    }

    public List<Action> getInstructions() {
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

    public void add(Action action) {
        this.instructions.add(action);
    }

    public void print() {

        // iterate over all actions
        for (Action action : instructions) {
            System.out.print("Procedure: ");
            System.out.println(action.getProcedure());

            System.out.print("Iterations: ");
            System.out.println(action.getIterations());

            System.out.print("On line: ");
            System.out.println(action.getLinePosition());
        }
    }

}