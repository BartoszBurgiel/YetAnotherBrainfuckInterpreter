package interpreter;

public class Linter {

    // the old code that will be formatted
    private String oldCode;

    // formatted code
    private String newCode;

    // LIMITS

    // limit size of a chunk of any legal character
    // -> if the limit is 5, code will be formatted like this
    // ++++++++ -> +++++ +++
    private final int CHAR_CHUNK_LIMIT = 5;

    public Linter() {
        System.out.println("elo 320");
    }

    public void elo() {
        System.out.println("elo 320 hehe");

    }
}
