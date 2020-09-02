package interpreter;

// Optimizer for the tokenizer 
public class Optimizer {

    // set of instructions that will be optimized
    private Instructions instructions;

    // optimized instructions
    private Instructions optimized;

    public Optimizer(Instructions instructions) {
        this.instructions = instructions;
        this.optimized = new Instructions();
    }

    // run the initial instructions and returns
    // the optimized set of of instructions
    public Instructions run() {
        squash();
        destroyLoops();
        return this.optimized;
    }

    // squash the instructions and introduce
    // iterations
    private void squash() {

        // Optimise and compress the procedures into actions
        // Compress only the adding and subtracting
        Instructions tempInstructions = new Instructions();
        // iterate over the instructions and involve the iterations
        for (int i = 0; i < this.instructions.size(); i++) {

            // current procedure for easier and more effective access
            Procedures currentProcedure = this.instructions.get(i).getProcedure();

            // check if the instruction is either adding of subrtacting
            if (currentProcedure == Procedures.INCREASE_CELL_VALUE || currentProcedure == Procedures.DECREASE_CELL_VALUE
                    || currentProcedure == Procedures.MOVE_POINTER_DOWN
                    || currentProcedure == Procedures.MOVE_POINTER_UP) {

                // check how many times this procedure appears
                for (int j = i; j < this.instructions.size(); j++) {

                    Procedures currentProcedureInCheck = this.instructions.get(j).getProcedure();

                    // if the procedure is different than the current procedure
                    // -> add the difference between i and j to the iterations
                    if (currentProcedureInCheck != currentProcedure) {
                        tempInstructions
                                .add(new Action(currentProcedure, this.instructions.get(j).getLinePosition(), j - i));

                        // skip to the next different procedure
                        i = j - 1;
                        break;
                    }
                }
            } else {
                tempInstructions.add(new Action(currentProcedure, this.instructions.get(i).getLinePosition()));
            }
        }

        // print the length of the tempInstructions
        // for (Action action : tempInstructions.getInstructions()) {
        // System.out.print(action.getProcedure());
        // System.out.print(" ");
        // System.out.println(action.getIterations());
        // }

        this.optimized = tempInstructions;
    }

    // find all loops and resolve them
    //
    // set the loop cell's value to 0 
    // and multiply the iterations of the value altering 
    // procedure by the initial value of the loop cell
    private void destroyLoops() {

        // get all loops
        

    }
}
