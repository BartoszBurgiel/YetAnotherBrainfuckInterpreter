package interpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Program {

	// all cells of the program
	private List<Cell> cells;

	// the source code of the program
	private String code;

	// the string output of the program
	private String output;

	// the value of the pointer
	// -> the cell that the pointer points to
	private int pointer;

	// Options of how the program should execute
	private Options options;

	// interpreted code, ready to execute
	private Instructions instructions;

	// constructor with the options file
	public Program(File file, File optionsFile) {
		this.output = "";
		this.options = new Options(optionsFile);

		this.code = this.getContentFromAFile(file);

		this.prepareCode();

		// create a new tokenizer
		Tokenizer t = new Tokenizer(this.code);

		// set the actions member variable
		this.instructions = t.getInstructions();

		// initiate the cells
		this.cells = new ArrayList<Cell>();
	}

	// Basic constructor
	public Program(File file) {
		this.output = "";

		this.options = new Options();

		this.code = this.getContentFromAFile(file);

		this.prepareCode();

		// create a new tokenizer
		Tokenizer t = new Tokenizer(this.code);

		// set the actions member variable
		this.instructions = t.getInstructions();

		// initiate the cells
		this.cells = new ArrayList<Cell>();

	}

	// Constructor where the brainfuck code is provided
	public Program(String code, Options options) {
		this.output = "";
		this.options = options;
		this.code = code;

		this.prepareCode();

		// initialize cells
		this.cells = new ArrayList<Cell>();

		// tokenize the code
		Tokenizer t = new Tokenizer(code);

		// set the actions member variable
		this.instructions = t.getInstructions();
	}

	// print all program's data
	public void print() {

		System.out.println("");
		for (int i = 0; i < cells.size(); i++) {

			cells.get(i).print(i);

			// print pointer
			if (i == this.pointer) {
				System.out.println("<");
			} else {
				System.out.println("");
			}
		}
	}

	// add an empty cell to the program
	public void addCell() {
		this.cells.add(new Cell());
	}

	// get the cell at which the pointer points
	public Cell getCurrentCell() {
		return this.cells.get(this.pointer);
	}

	// run the program
	// -> iterate over the set of the instructions
	// and modify the cells
	public void run() {
		// initiate the 0th cell
		this.addCell();

		// run the private run method and pass the
		// classe's instructions
		this.run(this.instructions, 0);

		// print the generated output
		System.out.println(this.output);

		if (this.options.printCells()) {
			this.print();
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
					this.getCurrentCell().increase(currentAction.getIterations());
					break;
				case DECREASE_CELL_VALUE:
					this.getCurrentCell().decrease(currentAction.getIterations());

					// check the value of the current cell
					if (this.getCurrentCell().getValue() < 0) {
						Helper.printErrorMessage(code, Helper.NEGATIVE_CELL_VALUE_STRING, i + globalI);
					}

					break;
				case MOVE_POINTER_UP:
					this.pointer++;

					// if the pointer is higher than the size of the
					// cells list -> add new cell
					if (this.pointer >= this.cells.size()) {
						this.addCell();
					}
					break;
				case MOVE_POINTER_DOWN:
					this.pointer--;

					// check if the pointer gets below zero
					if (this.pointer < 0) {
						Helper.printErrorMessage(code, Helper.NEGATIVE_POINTER_STRING, i + globalI);
					}
					break;
				case PRINT:

					// print the cell's value dependent on the options
					this.output += this.options.printCell(this.getCurrentCell());
					break;
				case READ:

					if (this.options.printInputChar()) {
						System.out.print(":");
					}

					// get the input
					Scanner in = new Scanner(System.in);
					int num = in.nextInt();

					// write num to the cell
					this.getCurrentCell().increase(num);

					break;
				case START_LOOP:

					// get the pointer of the loop's cell
					int loopsCellIndex = this.pointer;

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
					while (this.cells.get(loopsCellIndex).getValue() != 1) {

						// run the private run functions with the loop's instructions
						this.run(loop, i);
						numberOfIterations++;
						Helper.checkForEndlessLoop(numberOfIterations);
					}

					break;
				case END_LOOP:
					break;
			}
			globalI++;
		}
	}

	// prepareCode removes all whitespace and check for illegal characters
	private void prepareCode() {
		Helper.checkForIllegalCharacters(this.code);
	}

	private String getContentFromAFile(File file) {

		// Define a scanner
		Scanner scanner = null;

		// create a scanner for the file
		try {
			scanner = new Scanner(file);
		} catch (Exception e) {

			e.printStackTrace();
		}

		// read the file's content into the string
		String content = "";
		try {
			content = scanner.useDelimiter("\\A").next();
		} catch (Exception e) {
			// handle exception for the case when the file is empty
			if (e instanceof NoSuchElementException) {
				Helper.panic("The brainfuck file can not be empty.\n" + file.getAbsolutePath());
			}
		}

		// close the scanner
		scanner.close();

		return content;
	}

	public void visualize() {

	}
}
