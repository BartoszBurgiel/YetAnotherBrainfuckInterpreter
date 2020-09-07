package interpreter;

// Helper contains the error messages as well as 
// limitations, constants and helper functions 
// to format and detect illegal characters
public class Helper {

	/*
	 * CONSTANTS
	 */

	// array of all legal characters
	public static final char[] LEGAL_CHARACTERS = { '-', '+', '>', '<', '.', ',', '[', ']' };

	// Manual
	public static final String MAN_STRING = "YetAnotherBrainfuckInterpreter\n\nAll rights preserved to Bartosz Burgiel.\nhttps://github.com/BartoszBurgiel\n\n\nUsage:\n$ YABI init <name>\n\tInitiate a sample brainfuck project that is suitable for the YABI's project standards.\n\n$ YABI exec <path-to-the-file>\n\tExecute a single brainfuck file.\n\n$ YABI run <path-to-the-project>\n\tExecute a project directory.";

	/*
	 * ERROR MESSAGES
	 */

	// No file found
	public static final String NO_FILE_FOUND_IN_PROJECT = "Unfortunately one of the necessery project files (main.bf or options) were not found in the project directory.";

	// Too few arguments
	public static final String TOO_FEW_ARGUMENTS_STRING = "Too few arguments provided. To see what's missing run\nYABF --help";

	// No arguments provided at all
	public static final String NO_ARGUMENTS_STRING = "No arguments were provided.";

	// Negative values
	public static final String NEGATIVE_CELL_VALUE_STRING = "A cell can not contain any negative values.\n";

	// Negative pointer
	public static final String NEGATIVE_POINTER_STRING = "A pointer can not be negative.\n";

	// Endless loops
	public static final int ENDLESS_LOOP_LIMIT_INT = 100000000;
	public static final String ENDLESS_LOOP_STRING = String
			.format("Endless loop detected -> more than %d iterations occured", ENDLESS_LOOP_LIMIT_INT);

	/*
	 * CHECKERS
	 */

	// Check for illegal characters
	public static void checkForIllegalCharacters(String code) {

		// line count shows at what line the checker currently is
		int lineCount = 0;

		// temporary variable to store the entire line for the error message
		String line = "";

		// iterate over the code and find the illegal characters
		for (int i = 0; i < code.length(); i++) {

			char c = code.charAt(i);

			// increase the counters
			line = line + c;

			// if next line
			if (c == '\n') {
				lineCount++;

				// clear line
				line = "";
			}

			// check if the current char is illegal
			if (!isLegalCharacter(c)) {

				// compose the panic message
				printErrorMessage(code, String.format("Illegal character found at line %d\n\n", lineCount + 1),
						lineCount);

			}
		}
	}

	static void printErrorMessage(String code, String message, int lineCount) {

		// line that will be returned
		String line = "";

		// Divide the code to lines
		String[] lines = code.split("\n");

		// if the number of lines is greater than 2
		// print the fancy line display like:
		// 1 | +++++
		// 2 | +++3++[
		// ^
		// 3 | >+++[
		//
		// else -> print just the affected line

		// check the number of the lines
		if (lines.length > 2) {

			// if the line where the illigal character is at
			// is not the first, nor the last
			// -> display it as the middle line
			if (lineCount != 0 && lineCount != lines.length - 1) {

				// prepare the fancy line display
				// format to add the space margin for the msg row
				String fancyLineFormat = String.format("%%-3d| %%s\n%%-3d| %%s\n%%-3d| %%s");

				line = String.format(fancyLineFormat, lineCount, lines[lineCount - 1], lineCount + 1, lines[lineCount],
						lineCount + 2, lines[lineCount + 1]);
			}
			if (lineCount == 0) {
				// if the illegal character is in the 1st line

				// prepare the fancy line display
				// format to add the space margin for the msg row
				String fancyLineFormat = String.format("%%-3d| %%s\n%%-3d| %%s\n%%-3d| %%s");
				line = String.format(fancyLineFormat, lineCount + 1, lines[lineCount], lineCount + 2,
						lines[lineCount + 1], lineCount + 3, lines[lineCount + 2]);
			}
			if (lineCount == lines.length - 1) {
				// it the illegal character is in the last line

				// prepare the fancy line display
				// format to add the space margin for the msg row
				String fancyLineFormat = String.format("%%-3d| %%s\n%%-3d| %%s\n%%-3d| %%s\n");
				line = String.format(fancyLineFormat, lineCount - 1, lines[lineCount - 2], lineCount,
						lines[lineCount - 1], lineCount + 1, lines[lineCount]);
			}
		}

		// add a line break after the lines
		line += "\n";

		// add the error message
		message += line;

		panic(message);

	}

	// panic method to print the message and end the program
	public static void panic(String msg) {
		System.out.println(msg);
		System.exit(0);
	}

	// iterate over the array of all legal characters
	// and if c matches one of the legal characters
	// -> return true
	static boolean isLegalCharacter(char c) {
		// it the char is whitespace
		// -> return true
		if (Character.isWhitespace(c)) {
			return true;
		}

		// iterate over all legal characters and
		// if c is equal to any of them
		// return true
		for (char lC : LEGAL_CHARACTERS) {
			if (lC == c) {
				return true;
			}
		}
		return false;
	}

	static void checkForEndlessLoop(int i) {
		if (i > ENDLESS_LOOP_LIMIT_INT) {

			panic(ENDLESS_LOOP_STRING);
		}
	}

	// remove all comments from the source code 
	static String removeComments(String code) {
		// append a \n at the end of the code 
		// to support the regex 
		code = code + "\n";
		return code.replaceAll("\\/\\/.*?\\n", "");
	}
}