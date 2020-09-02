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

		Optimizer opt = new Optimizer(this.instructions);

		// replace this.instructions with optimized instructions
		this.instructions = opt.run();
	}

	public Instructions getInstructions() {
		return this.instructions;
	}

	public String getCode() {
		return this.code;
	}
}