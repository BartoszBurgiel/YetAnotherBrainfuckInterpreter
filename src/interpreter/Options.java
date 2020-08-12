package interpreter;

import java.io.File;

// Contains execute options for a program
public class Options {

    // Tell if the interpreter should print cell's values
    // as ASCII characters or as integers
    private boolean printInteger;

    // tell if an input 'notifier' should be displayed
    // -> when an input occours a ':' is printed like this:
    //
    // (some program output)
    // :<inut>
    // (some program output)
    private boolean inputChar;

    // constructor that reads an .opt file for the
    // execution
    public Options(File file) {

    }

    // manually set all options
    public Options(boolean printInteger, boolean inputChar) {
        this.printInteger = printInteger;
        this.inputChar = inputChar;
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
}