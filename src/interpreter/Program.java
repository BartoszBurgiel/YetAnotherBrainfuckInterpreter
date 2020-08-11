package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Program {

	// all cells of the program 
	private List<Cell> cells;
	

	// the brainfuck code
	private String code; 

	// the value of the pointer 
	// -> the cell that the pointer points to 
	private int pointer; 
	
	// interpreted code, ready to execute
	private List<Actions> actions;

	// Basic constructor
	public Program() {
		this.cells = new ArrayList<Cell>();

	}


	// Constructor where the brainfuck code is provided
	public Program(String code) {

		// set the code member variable 
		this.code = code;

		// initialize cells 
		this.cells = new ArrayList<Cell>();


		// tokenize the code 
		Tokenizer t = new Tokenizer(code);

		// set the actions member variable
		this.actions = t.getActions();
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

		// iterate over the instructions
		for (int i = 0; i < this.actions.size(); i++) {
			
			
			// determine what to do 
			switch (this.actions.get(i)) {
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
				this.addCell();
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
				int endOfTheLoopIndex;
				for (int j = i; j < this.actions.size(); j++) {
					if (this.actions.get(j) == Actions.START_LOOP) {
						loopIndex++;
					} else if (this.actions.get(j) == Actions.END_LOOP) {
						loopIndex--;
					}

					// if the absolute end of the loop is found
					if (loopIndex == 0 && this.actions.get(j) == Actions.END_LOOP) {
						endOfTheLoopIndex = j;
						System.out.println(endOfTheLoopIndex);
						break;
					}
				}


				break;
				case END_LOOP:
				break;
			}
		}
		System.out.println("");
	}

	public void visualize() {
		
	}
}
