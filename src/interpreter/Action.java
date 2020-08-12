package interpreter;

// Action represents the actions that are preformed by the run method
// in the Program class
public class Action {

    // Approximate position of the action
    // in the code
    private int linePosition;

    // the actual procedure that needs to be done
    private Procedures procedure;

    // the number of times that the procedure must be executed
    private int iterations;

    public Action(Procedures procedure, int line) {
        this.linePosition = line;
        this.iterations = 1;
        this.procedure = procedure;
    }

    public Action(Procedures procedure, int line, int iterations) {
        this.linePosition = line;
        this.iterations = iterations;
        this.procedure = procedure;
    }

    public int getLinePosition() {
        return this.linePosition;
    }

    public void setIterations(int n) {
        this.iterations = n;
    }

    public void increaseIterations() {
        this.iterations++;
    }

    public Procedures getProcedure() {
        return this.procedure;
    }

    public int getIterations() {
        return iterations;
    }
}