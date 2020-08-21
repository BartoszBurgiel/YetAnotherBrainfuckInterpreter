import java.io.File;

import interpreter.Cli;
import interpreter.Program;

public class Main {

	public static void main(String[] args) {
		String path = "../hello/main.bf";

		Program p = new Program(new File(path));
		p.run();
		p.printOutput();
	}
}
