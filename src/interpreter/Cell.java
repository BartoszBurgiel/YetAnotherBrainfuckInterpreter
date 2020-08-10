package interpreter;

public class Cell {
	
	// value of the cell 
	private int value;
	
	// print themplate 
	private String template;
	
	// Constructor to initiate the cell 
	// and set the content to 0 
	public Cell() {
		this.value = 0;
		
		// declare the template 
		this.template = "%d: Value: %d\n";
	}
	
	// increase the value of the content bu the given amount
	public void increase(int n) {
		this.value+=n;
	}
	
	// decrease the value of the content by the given amount
	// if it gets below 0 -> throw exception
	public void decrease(int n) throws Exception {
		this.value-=n;
		
		if (this.value < 0) {
			throw new Exception("Cell can not have a value that is lesser than 0");
		}
	}
	
	// value's getter
	public int getValue() {
		return this.value;
	}
	
	// Print the cell's data
	public void print(int index) {
		System.out.printf(this.template, index, this.value);
	}
}
