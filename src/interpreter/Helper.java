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

                // Divide the code to lines
                String[] lines = code.split("\n");

                // if the number of lines is greater than 2 
                // print the fancy line display like:
                // 1 | +++++
                // 2 | +++3++[
                //        ^
                // 3 | >+++[
                //
                // else -> print just the affected line

                // check the number of the lines 
                if (lines.length > 2) {
                    
                    // margin so that the pointer to the illegal character will be displayed right 
                    int margin = 5;
                    
                    // if the line where the illigal character is at
                    // is not the first, nor the last 
                    // -> display it as the middle line 
                    if (lineCount != 0 && lineCount != lines.length-1) {
    
                        // prepare the fancy line display 
                        // format to add the space margin for the msg row
                        String fancyLineFormat = String.format("%%-3d| %%s\n%%-3d| %%s\n%%%ds\n%%-3d| %%s", rowCount+margin);

                        line = String.format(fancyLineFormat, 
                                        lineCount, lines[lineCount-1], 
                                        lineCount+1, lines[lineCount], 
                                        "^",
                                        lineCount+2, lines[lineCount+1]);
                    } if (lineCount == 0) {
                        // if the illegal character is in the 1st line 

                        // prepare the fancy line display 
                        // format to add the space margin for the msg row
                        String fancyLineFormat = String.format("%%-3d| %%s\n%%%ds\n%%-3d| %%s\n%%-3d| %%s", rowCount+margin);
                        line = String.format(fancyLineFormat, 
                                    lineCount+1, lines[lineCount], 
                                    "^",
                                    lineCount+2, lines[lineCount+1], 
                                    lineCount+3, lines[lineCount+2]);
                    } if (lineCount == lines.length-1) {
                        // it the illegal character is in the last line

                        // prepare the fancy line display 
                        // format to add the space margin for the msg row
                        String fancyLineFormat = String.format("%%-3d| %%s\n%%-3d| %%s\n%%-3d| %%s\n%%%ds\n", rowCount+margin);
                        line = String.format(fancyLineFormat, 
                                    lineCount-1, lines[lineCount-2], 
                                    lineCount, lines[lineCount-1], 
                                    lineCount+1, lines[lineCount],
                                    "^");
                    }
                } else {

                    // complete the line 
                    for (int i = charCount; i < code.length(); i++) {
                        line = line + code.charAt(i);

                        // if a line break or end of code is found 
                        // -> break
                        if (code.charAt(i) == '\n') {
                            break;
                        }
                    }

                    // add the pointer to the illegal character
                    String tempPointerFormat = String.format("\n%%%ds", rowCount);
                    line += String.format(tempPointerFormat, "^");
                }

                // compose the panic message
                String msg = String.format("Illegal character found at \nLine %d, row %d\n\n%s\n", lineCount, rowCount, line);

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