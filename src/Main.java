import interpreter.Program;

public class Main {

	public static void main(String[] args) {


		String test = "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++.+++.--.++++++.--------.";

		String loopTest = String.format("%s\n%s\n%s", "+++++ ", "+++3++[",">+++[>++<-]<-]>>+++++.");

		Program p = new Program(loopTest);
	
		p.run();
		p.print();
	}

}
