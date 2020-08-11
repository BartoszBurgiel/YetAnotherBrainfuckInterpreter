package interpreter;

// Helper contains the error messages as well as 
// limitations, constants and helper functions 
// to format and detect illegal characters
public class Helper {

    /*
        CONSTANTS
    */

    // array of all legal characters
    final static public char[] LEGAL_CHARACTERS = {'-', '+', '>', '<', '.', ',', '[', ']'}; 

    /*
        ERROR MESSAGES 
    */


    // Endless loops
    final static public int ENDLESS_LOOP_LIMIT_INT = 1000000;
    final static public String ENDLESS_LOOP_STRING = String.format("Endless loop detected -> more than %d iterations occured", ENDLESS_LOOP_LIMIT_INT); 

    // Check for illegal characters
    static public void checkForIllegalCharacters(String code) {
        
        // line count shows at what line the checker currently is
        int lineCount = 0;

        // row count shows at what row the checer currently is 
        int rowCount = 0;

        // number of the checked characters
        int charCount =0;

        // temporary variable to store the entire line for the error message
        String line = "";


        // iterate over the code and find the illegal characters
        for (char c : code.toCharArray()) {
            
            // increase the counters
            rowCount++;
            charCount++;
            line = line + c;

            // if next line 
            if (c == '\n') {
                lineCount++;

                // set row count to 0 
                rowCount=0;

                // clear line 
                line = "";
            }

            // check if the current char is illegal 
            if (!isLegalCharacter(c)) {

                // complete the line 
                // -> get to the end of it 
                for (int i = charCount; code.charAt(i) != '\n'; i++) {
                    line = line + code.charAt(i);

                    // if i is equal to the length of the code 
                    // abort -> no new line will be found
                    if (i == code.length()-1) {
                        break;
                    }
                } 

                // compose the panic message
                String msg = String.format("Illegal character found at \nLine %d, row %d\n\n%s\n", lineCount, rowCount, line);

                // get to the row where the illegal character is at
                // and set a pointer to the char
                for (int i = 0; i < rowCount-1; i++) {
                    msg = msg + " ";
                }
                msg += "^";
                panic(msg);
            }
        }
    }

    // panic method to print the message and end the program 
    static void panic(String msg) {
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

        for (char lC : LEGAL_CHARACTERS) {
            if (lC == c) {
                return true;
            }
        }
        return false;
    }
}