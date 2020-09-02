package interpreter;

// Executer executs the given tokenized program and updates 
// its program 
public class Executer {

    // program variable that will be updated along with the 
    // execution of the actions
    private Program program; 

    private String output;

    // private String code; 

    public Executer(Program program) {
        this.program = program;
        // this.code = this.program.getCode();
        this.output = "";

    }

    // Execute the program's set of instructions 
    public Program Exec() {
        this.run();
        this.program.setOutput(this.output);
        return this.program;
    }

	// run the program
	// -> iterate over the set of the instructions
	// and modify the cells
	private void run() {
		// initiate the 0th cell
		this.program.addCell(1);

		if (this.program.getOptions().printInstructions()) {
			for (Action action : this.program.getInstructions().getInstructions()) {
				System.out.print(action.getProcedure());
				System.out.print(" ");
				System.out.println(action.getIterations());
			}
		}

		// run the private run method and pass the
		// classe's instructions
		this.run(this.program.getInstructions(), 0);

		// print the generated output
		// System.out.println(this.output);

		if (this.program.getOptions().printCells()) {
			this.program.print();
		}
	}

	// run the given instructions
	private void run(Instructions instructions, int globalI) {
		// iterate over the instructions
		for (int i = 0; i < instructions.size(); i++) {

			// current action as a shotcut for cleaner code
			Action currentAction = instructions.get(i);

			// determine what to do
			switch (currentAction.getProcedure()) {
				case INCREASE_CELL_VALUE:

					// get the current cell and
					this.program.getCurrentCell().increase(currentAction.getIterations());
					break;
				case DECREASE_CELL_VALUE:
					this.program.getCurrentCell().decrease(currentAction.getIterations());

					// check the value of the current cell
					if (this.program.getCurrentCell().getValue() < 0) {
						// Helper.printErrorMessage(code, Helper.NEGATIVE_CELL_VALUE_STRING, i + globalI);
					
						if (!this.program.getOptions().allowNegativeValues()) {
							Helper.panic(Helper.NEGATIVE_CELL_VALUE_STRING);
						}
					}

					break;
				case MOVE_POINTER_UP:
					this.program.increasePointer(currentAction.getIterations());

					// if the pointer is higher than the size of the
					// cells list -> add new cell
					if (this.program.getPointer() >= this.program.getCells().size()) {

						// add the difference between the pointer and the size
						this.program.addCell(this.program.getPointer() - this.program.getCells().size()+1);
					}
					break;
				case MOVE_POINTER_DOWN:
					this.program.decreasePointer(currentAction.getIterations());

					// check if the pointer gets below zero
					if (this.program.getPointer() < 0) {
						// Helper.printErrorMessage(code, Helper.NEGATIVE_POINTER_STRING, i + globalI);
						Helper.panic(Helper.NEGATIVE_POINTER_STRING);
					
					}
					break;
				case PRINT:

					// print the cell's value dependent on the options
					this.output += this.program.getOptions().printCell(this.program.getCurrentCell());
					
					// if only numbers should be printed 
					// add a space between each number
					if (this.program.getOptions().printInteger()) {
						this.output += " ";
					}
					break;
				case READ:

                    this.program.getCurrentCell().read(this.program.getOptions().printInputChar());
					break;
				case START_LOOP:

					// get the pointer of the loop's cell
					int loopsCellIndex = this.program.getPointer();

					// find the index of the closing loop
					//
					// for every opening loop the loop index will increase
					// and for the closing loop, the index will decrease
					//
					// if a closing loop is found and the index is equal to 0
					// the end of the loop is found
					//
					// -> go from the current position until the
					// closing loop is found
					int loopIndex = 0;
					// the index at which the loop ends
					int endOfTheLoopIndex = 0;
					for (int j = i; j < instructions.size(); j++) {
						if (instructions.get(j).getProcedure() == Procedures.START_LOOP) {
							loopIndex++;
						} else if (instructions.get(j).getProcedure() == Procedures.END_LOOP) {
							loopIndex--;
						}

						// if the absolute end of the loop is found
						if (loopIndex == 0 && instructions.get(j).getProcedure() == Procedures.END_LOOP) {
							endOfTheLoopIndex = j;
							break;
						}
					}

					// Get the loop content
					// go from i+1 to endOfTheLoopIndex so that the loop endings and starts are
					// excluded
					Instructions loop = instructions.getInterval(i + 1, endOfTheLoopIndex);

					// number of the iterations of the loop
					int numberOfIterations = 0;

					// run the private run function for as long as the value of the loop's cell
					// is not equal 0
					while (this.program.getCells().get(loopsCellIndex).getValue() != 1) {

						// run the private run functions with the loop's instructions
						this.run(loop, i);
						numberOfIterations++;
						Helper.checkForEndlessLoop(numberOfIterations);
					}

					break;
				case SQUASHED_LOOP:

					// multiply the iterations of the 
					// next procedure by the 
					// value of the current cell 
					// and then set the value of the current 
					// cell to 0
					int tempVal = this.program.getCurrentCell().getValue();

					// multiply the next iterations 
					this.program.getInstructions().get(i+1).setIterations(this.program.getInstructions().get(i+1).getIterations()*tempVal);
					
					// set current cell's value to 0 
					this.program.getCurrentCell().decrease(tempVal);
					break;
				case END_LOOP:
					break;
				case BLANK:
					break;
			}
			globalI++;
		}
	}
}