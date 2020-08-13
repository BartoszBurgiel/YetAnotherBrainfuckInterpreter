import java.io.File;

import interpreter.Linter;
import interpreter.Program;
import interpreter.Project;

public class Main {

	public static void main(String[] args) {

		// String path =
		// "/home/bartosz/dev/java/brainheck/src/interpreter/helloWorld/main.bf";
		// // String optionsPath = "../src/interpreter/test/hello.options";

		// Program p = new Program(new File(path));
		// p.run();

		// Linter l = new Linter(
		// "++++++++[>+++++++++<-]>.---.<+++[>++<-]>+..+++.>>++++++[<+++++>-]<++.<[>>+>+<<<-]>>>[<<<+>>>-]<<<<+++[>+++<-]>-.>>.+++.------.>++++[<-->-]<.");

		// create a project
		Project.create("helloWorld", "/home/bartosz/dev/java/brainheck/src/interpreter");
		Project p = new Project("/home/bartosz/dev/java/brainheck/src/interpreter/helloWorld");
		p.run();

	}

}
