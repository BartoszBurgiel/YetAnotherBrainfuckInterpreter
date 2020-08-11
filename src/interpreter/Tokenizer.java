package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    
    // the brainfuck code 
    private String code;

    // the list of actions 
    // -> the stack of the program
    private Instructions instructions;
    
    public Tokenizer(String code) {

        // set the member variable
        this.code = code;

        // initialize actions
        this.instructions = new Instructions();

        // Iterate over code and tokenize it
        for (char token : code.toCharArray()) {
            
            switch (token) {
                case '+':
                instructions.add(Actions.INCREASE_CELL_VALUE);
                break;
                case '-':
                instructions.add(Actions.DECREASE_CELL_VALUE);
                break;
                case '>':
                instructions.add(Actions.MOVE_POINTER_UP);
                break;
                case '<':
                instructions.add(Actions.MOVE_POINTER_DOWN);
                break;
                case '.':
                instructions.add(Actions.PRINT);
                break;
                case ',':
                instructions.add(Actions.READ);
                break;
                case '[':
                instructions.add(Actions.START_LOOP);
                break;
                case ']':
                instructions.add(Actions.END_LOOP);
                break;
                default:
            }
        }
    }


    public Instructions getInstructions() {
        return this.instructions;
    }

    public String getCode() {
        return this.code;
    }
}