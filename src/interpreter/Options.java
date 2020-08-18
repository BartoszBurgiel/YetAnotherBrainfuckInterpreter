package interpreter;

import java.io.File;
import java.util.Scanner;

// Contains execute options for a program
public class Options {

	// contents of the options file
	private String optionsFileContent;

	// Tell if the interpreter should print cell's values
	// as ASCII characters or as integers
	private boolean printInteger;

	// tell if all cells of the program should be printed
	private boolean printCells;

	// tell if the build and interpreting status should be
	// printed
	private boolean printInfo;

	// tell if program's optimised instructions should be printed
	private boolean printInstructions;

	// tell if an input 'notifier' should be displayed
	// -> when an input occours a ':' is printed like this:
	//
	// (some program output)
	// :<inut>
	// (some program output)
	private boolean inputChar;

	public Options(String content) {
		this.optionsFileContent = content;

		// process optionsFIle content
		// -> split to lines
		String[] lines = this.optionsFileContent.split("\n");

		this.initFromLines(lines);
	}

	// constructor that reads an .opt file for the
	// execution
	public Options(File file) {
		this.optionsFileContent = getContentFromAFile(file);

		// remove any whitespace from optinsFileContent
		this.optionsFileContent = this.optionsFileContent.replaceAll("[^\\S\\r\\n]", "");

		// process optionsFIle content
		// -> split to lines
		String[] lines = this.optionsFileContent.split("\n");
		this.initFromLines(lines);
	}

	// basic constructor
	public Options() {
		this.inputChar = true;
		this.printCells = true;
		this.printInfo = false;
		this.printInteger = false;
		this.printInstructions = false;
	}

	// Prints either the cell's integer value or the
	// ASCII value dependent on the option
	public String printCell(Cell c) {
		if (this.printInteger) {
			return String.valueOf(c.getValue());
		}
		return c.toString();

	}

	public boolean printInputChar() {
		return this.inputChar;
	}

	public boolean printCells() {
		return this.printCells;
	}

	public boolean printInstructions() {
		return this.printInstructions;
	}

	private String getContentFromAFile(File file) {

		// Define a scanner
		Scanner scanner = null;

		// create a scanner for the file
		try {
			scanner = new Scanner(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// read the file's content into the string
		String content = scanner.useDelimiter("\\A").next();

		// close the scanner
		scanner.close();

		return content;
	}

	// initiate the variables from the provided lines
	private void initFromLines(String[] lines) {
		// iterate over lines and set the variables
		for (String line : lines) {

			// split each line by the equals sign and set the private variables
			String[] lineDiv = line.split("=");

			// check the 0th member (variable)
			// and check 1st member (value)
			switch (lineDiv[0]) {
				case "printInteger":
					this.printInteger = Boolean.valueOf(lineDiv[1]);
					break;
				case "inputChar":
					this.inputChar = Boolean.valueOf(lineDiv[1]);
					break;
				case "printCells":
					this.printCells = Boolean.valueOf(lineDiv[1]);
					break;
				case "printInfo":
					this.printInfo = Boolean.valueOf(lineDiv[1]);
					break;
				case "printInstructions":
					this.printInstructions = Boolean.valueOf(lineDiv[1]);
					break;
				default:
					Helper.panic("Illegal keyword found in the options\n\t-> " + line);
			}
		}

		// if print info was set to true ->
		// print all options
		if (this.printInfo) {
			System.out.println("printInteger\t= " + this.printInteger);
			System.out.println("inputChar\t= " + this.inputChar);
			System.out.println("printCells\t= " + this.printCells);
			System.out.println("printInfo\t= " + this.printInfo);
			System.out.println("printInstructions\t= " + this.printInstructions);
		}
	}
}