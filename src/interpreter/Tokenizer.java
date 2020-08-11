package interpreter;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    
    // the brainfuck code 
    private String code;

    // the list of actions 
    // -> the stack of the program
    private List<Actions> actions;
    
    public Tokenizer(String code) {

        // set the member variable
        this.code = code;

        // initialize actions
        this.actions = new ArrayList<>();

        // Iterate over code and tokenize it
        for (char token : code.toCharArray()) {
            
            switch (token) {
                case '+':
                actions.add(Actions.INCREASE_CELL_VALUE);
                break;
                case '-':
                actions.add(Actions.DECREASE_CELL_VALUE);
                break;
                case '>':
                actions.add(Actions.MOVE_POINTER_UP);
                break;
                case '<':
                actions.add(Actions.MOVE_POINTER_DOWN);
                break;
                case '.':
                actions.add(Actions.PRINT);
                break;
                case ',':
                actions.add(Actions.READ);
                break;
                case '[':
                actions.add(Actions.START_LOOP);
                break;
                case ']':
                actions.add(Actions.END_LOOP);
                break;
                default:
            }
        }
    }


    public List<Actions> getActions() {
        return this.actions;
    }

    public String getCode() {
        return this.code;
    }
}