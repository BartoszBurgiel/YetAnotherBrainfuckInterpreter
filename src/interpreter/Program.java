package interpreter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {

	// all cells of the program 
	private List<Cell> cells;
	
	private String code;


	// the value of the pointer 
	// -> the cell that the pointer points to 
	private int pointer; 
	
	// interpreted code, ready to execute
	private Instructions instructions;

	// Basic constructor
	public Program(File file) {

		// Define a scanner 
		Scanner scanner = null;

		// create a scanner for the file
		try{
		scanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the file's content into the string
		String content = scanner.useDelimiter("\\A").next();

		// close the scanner
		scanner.close(); 

		// initiate code 
		this.code = content;

		// create a new tokenizer
		Tokenizer t = new Tokenizer(this.code);

		// set the actions member variable
		this.instructions = t.getInstructions();

		// initiate the cells
		this.cells = new ArrayList<Cell>();

	}


	// Constructor where the brainfuck code is provided
	public Program(String code) {

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
	
		// prepare the template 
		String template = new String("Pointer is on the: %d\nTotal number of the cells: %d\n\n");
		System.out.printf(template, this.pointer, this.cells.size());
		
		// print the values of the cells 
		System.out.println("Values of each cells");
		
		for (int i = 0; i < cells.size(); i++) {
			
			cells.get(i).print(i);
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
		this.run(this.instructions);

		
		System.out.println("");
	}

	// run the given instructions
	private void run(Instructions instructions) {
		// iterate over the instructions
		for (int i = 0; i < instructions.size(); i++) {
			
			
			// determine what to do 
			switch (instructions.get(i)) {
				case INCREASE_CELL_VALUE:

				// get the current cell and 
				this.getCurrentCell().increase(1);
				break;
				case DECREASE_CELL_VALUE:

				try {
					this.getCurrentCell().decrease(1);
				} catch (Exception e) {
					e.printStackTrace();
					break;
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
					System.out.println("The pointer value is less than zero.");
					System.exit(0);
				}
				break;
				case PRINT:

				// print the cell's value 
				//
				// the print method is used for debugging
				System.out.print((char)this.getCurrentCell().getValue());
				break;
				case READ:
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
				int endOfTheLoopIndex =0;
				for (int j = i; j < instructions.size(); j++) {
					if (instructions.get(j) == Actions.START_LOOP) {
						loopIndex++;
					} else if (instructions.get(j) == Actions.END_LOOP) {
						loopIndex--;
					}

					// if the absolute end of the loop is found
					if (loopIndex == 0 && instructions.get(j) == Actions.END_LOOP) {
						endOfTheLoopIndex = j;
						break;
					}
				}

				// Get the loop content 
				// go from i+1 to endOfTheLoopIndex so that the loop endings and starts are excluded
				Instructions loop = instructions.getInterval(i+1, endOfTheLoopIndex);

				
				// number of the iterations of the loop 
				int numberOfIterations = 0;

				// run the private run function for as long as the value of the loop's cell 
				// is not equal 0 
				while (this.cells.get(loopsCellIndex).getValue() != 1) {

					// run the private run functions with the loop's instructions 
					this.run(loop);
					numberOfIterations++;

					if (numberOfIterations > 1000000) {
						System.out.println("Endless loop detected -> more than 1000000 iterations; aborting.");
						System.exit(0);
					}
				}

				break;
				case END_LOOP:
				break;
			}
		}
	}

	// prepareCode removes all whitespace and check for illegal characters
	private void prepareCode() {
		Helper.checkForIllegalCharacters(this.code);
	}

	public void visualize() {
		
	}
}
