package interpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	// executer of the instructions 
	private Executer executer;

	// constructor with the options file
	public Program(File file, File optionsFile) {
		this.output = "";
		this.options = new Options(optionsFile);

		this.code = Utils.getContentFromAFile(file);

		this.prepareCode();

		// create a new tokenizer
		Tokenizer t = new Tokenizer(this.code);

		// set the actions member variable
		this.instructions = t.getInstructions();

		// initiate the cells
		this.cells = new ArrayList<>();
		this.executer = new Executer(this);
	}

	// Basic constructor
	public Program(File file) {
		this.output = "";

		this.options = new Options();

		this.code = Utils.getContentFromAFile(file);

		this.prepareCode();

		// create a new tokenizer
		Tokenizer t = new Tokenizer(this.code);

		// set the actions member variable
		this.instructions = t.getInstructions();

		// initiate the cells
		this.cells = new ArrayList<>();
		this.executer = new Executer(this);

	}

	// Constructor where the brainfuck code is provided
	public Program(String code, Options options) {
		this.output = "";
		this.options = options;
		this.code = code;

		this.prepareCode();

		// initialize cells
		this.cells = new ArrayList<>();

		// tokenize the code
		Tokenizer t = new Tokenizer(this.code);

		// set the actions member variable
		this.instructions = t.getInstructions();

		this.executer = new Executer(this);
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

	public void printOutput() {
		System.out.println(this.output);
	}

	// add an empty cell to the program
	public void addCell() {
		this.cells.add(new Cell());
	}

	// get the cell at which the pointer points
	public Cell getCurrentCell() {
		return this.cells.get(this.pointer);
	}

	// Run the program by calling the executor 
	// and update this' variables
	public void run() {
		Program temp = this.executer.Exec();
		this.setCells(temp.getCells());
		this.output = temp.getOutput();
		this.pointer = temp.getPointer();
	}


	// Getter and setter for the member variables
	public List<Cell> getCells() {
		return this.cells;
	}

	public int getPointer() {
		return this.pointer;
	}

	public String getCode() {
		return this.code;
	}

	public String getOutput() {
		return this.output;
	}

	public Options getOptions() {
		return this.options;
	}

	public Instructions getInstructions() {
		return this.instructions;
	}

	public void increasePointer() {
		this.pointer++;
	}

	public void setCells(List<Cell> cells) {
		this.cells = cells;
	}

	public void setPointer(int pointer) {
		this.pointer = pointer;
	}

	public void decreasePointer() {
		this.pointer--;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	// prepareCode removes all whitespace and check for illegal characters
	private void prepareCode() {
		this.code = Helper.removeComments(this.code);
		Helper.checkForIllegalCharacters(this.code);
	}


}
