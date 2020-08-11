import interpreter.Program;

public class Main {

	public static void main(String[] args) {


		String test = "+++++++++++++++++++++++++++++++++++3+++++++++++++++++++++++++++++++.+++.--.++++++.--------.";

		// String loopTest = String.format("%s\n%s\n%s", "+++++ ", "+++++[",">+++[>++<-]<-]>>3+++++.");
		// Program p = new Program(loopTest);
		// p.run();
		// p.print();


		// loopTest = String.format("%s\n%s\n%s", "+++++ ", "++++3+[",">+++[>++<-]<-]>>+++++.");
		// p = new Program(loopTest);
		// p.run();
		// p.print();

		// loopTest = String.format("%s\n%s\n%s", "+++1++ ", "+++++[",">+++[>++<-]<-]>>+++++.");
		// p = new Program(loopTest);
		// p.run();
		// p.print();

		Program p = new Program(test);
		p.run();
		p.print();
	}

}
