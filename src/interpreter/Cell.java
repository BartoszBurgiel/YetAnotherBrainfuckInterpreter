package interpreter;

public class Cell {
	private int value;
	
	// Constructor to initiate the cell 
	// and set the content to 0 
	public Cell() {
		this.value = 0;
	}
	
	// increase the value of the content
	public void increase() {
		this.value++;
	}
	
	// decrease the value of the content 
	// if it gets below 0 -> throw exception
	public void decrease() throws Exception {
		this.value--;
		
		if (this.value < 0) {
			throw new Exception("Cell can not have a value that is lesser than 0");
		}
	}
	
	// value's getter
	public int getValue() {
		return this.value;
	}
}
