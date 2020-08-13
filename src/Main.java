import interpreter.Cli;

public class Main {

	public static void main(String[] args) {
		Cli cli = new Cli(args);
		cli.execute();
	}
}
