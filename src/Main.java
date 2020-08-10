import interpreter.Program;

public class Main {

	public static void main(String[] args) {

		Program p = new Program();
		
		// add some cells 
		for (int i = 0; i < 30; i++) {
			p.addCell();
		}
		p.print();		
	}

}
