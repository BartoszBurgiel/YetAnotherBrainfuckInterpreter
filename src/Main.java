import interpreter.Program;

public class Main {

	public static void main(String[] args) {


		String test = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+++.--.++++++.--------.";

		String loopTest = "[[[[++++---]]]---]";
		Program p = new Program(loopTest);
	
		p.run();
		p.print();
	}

}
