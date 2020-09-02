package interpreter;

// Procedures includes all possible procedures that 
// can be run
public enum Procedures {
    MOVE_POINTER_UP, MOVE_POINTER_DOWN, INCREASE_CELL_VALUE, DECREASE_CELL_VALUE, START_LOOP, END_LOOP, PRINT, READ, BLANK,

    // procedures used for the optimization 
    SQUASHED_LOOP
}