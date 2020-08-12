import java.io.File;

import interpreter.Program;

public class Main {

	public static void main(String[] args) {

		String path = "../src/interpreter/test/hello.bf";

		Program p = new Program(new File(path));
		p.run();
		p.print();
	}

}
