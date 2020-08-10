package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Program {

	// all cells of the program 
	private List<Cell> cells;
	
	// the value of the pointer 
	// -> the cell that the pointer points to 
	private int pointer; 
	

	public Program() {
		this.cells = new ArrayList<Cell>();
		System.out.println("Hello, there is the list");
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
	
	public void visualize() {
		
	}
}
