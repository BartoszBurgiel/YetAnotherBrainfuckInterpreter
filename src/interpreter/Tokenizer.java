package interpreter;

public class Tokenizer {

	// the brainfuck code
	private String code;

	// current line that the tokenizer is at
	private int currentLine;

	// the list of actions
	// -> the stack of the program
	private Instructions instructions;

	public Tokenizer(String code) {

		// initialize instructions
		this.instructions = new Instructions();

		// initialize the current counter
		this.currentLine = 1;

		// set the member variable
		this.code = code;

		// temporary variable to quick access
		char[] tempArr = code.toCharArray();

		// Iterate over code and tokenize it
		for (int i = 0; i < code.toCharArray().length; i++) {

			char token = tempArr[i];

			// increment the counters
			if (token == '\n') {
				this.currentLine++;

			}

			switch (token) {
				case '+':
					this.instructions.add(new Action(Procedures.INCREASE_CELL_VALUE, this.currentLine));
					break;
				case '-':
					this.instructions.add(new Action(Procedures.DECREASE_CELL_VALUE, this.currentLine));
					break;
				case '>':
					this.instructions.add(new Action(Procedures.MOVE_POINTER_UP, this.currentLine));
					break;
				case '<':
					this.instructions.add(new Action(Procedures.MOVE_POINTER_DOWN, this.currentLine));
					break;
				case '.':
					this.instructions.add(new Action(Procedures.PRINT, this.currentLine));
					break;
				case ',':
					this.instructions.add(new Action(Procedures.READ, this.currentLine));
					break;
				case '[':
					this.instructions.add(new Action(Procedures.START_LOOP, this.currentLine));
					break;
				case ']':
					this.instructions.add(new Action(Procedures.END_LOOP, this.currentLine));
					break;
				default:

			}
		}

		// add a blank instruction to this.instructions so that the 
		// optimising algorithm won't skip last compressed elements 
		this.instructions.add(new Action(Procedures.BLANK, 0));

		// Optimise and compress the procedures into actions
		// Compress only the adding and subtracting
		Instructions tempInstructions = new Instructions();

		// iterate over the instructions and involve the iterations
		for (int i = 0; i < this.instructions.size(); i++) {

			// current procedure for easier and more effective access
			Procedures currentProcedure = this.instructions.get(i).getProcedure();

			// check if the instruction is either adding of subrtacting
			if (currentProcedure == Procedures.INCREASE_CELL_VALUE
					|| currentProcedure == Procedures.DECREASE_CELL_VALUE) {

				// check how many times this procedure appears
				for (int j = i; j < this.instructions.size(); j++) {

					Procedures currentProcedureInCheck = this.instructions.get(j).getProcedure();

					// if the procedure is different than the current procedure
					// -> add the difference between i and j to the iterations
					if (currentProcedureInCheck != currentProcedure) {
						tempInstructions
								.add(new Action(currentProcedure, this.instructions.get(j).getLinePosition(), j - i));

						// skip to the next different procedure
						i = j -1;
						break;
					}
				}
			} else {
				tempInstructions.add(new Action(currentProcedure, this.instructions.get(i).getLinePosition()));
			}
		}

		// print the length of the tempInstructions

		// replace this.instructions with tempInstructions
		this.instructions = tempInstructions;
	}

	public Instructions getInstructions() {
		return this.instructions;
	}

	public String getCode() {
		return this.code;
	}
}