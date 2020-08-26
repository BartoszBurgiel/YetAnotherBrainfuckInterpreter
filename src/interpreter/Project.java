package interpreter;

import java.io.File;

// Project prepresents a brainfuck project 
// with the source code for a HelloWorld program and
// a basic .options file
public class Project {

    // Prepared program created from the project's contents
    private Program program;

    // The contents of the main.bf file
    private String mainContents;

    // the contents of the options file
    private String optionsContent;

    // source code for a hello world program
    // one liner, that will be formatted by the linter later on
    static String helloWorld = "++++++++[>+++++++++<-]>.---.<+++[>++<-]>+..+++.>>++++++[<+++++>-]<++.<[>>+>+<<<-]>>>[<<<+>>>-]<<<<+++[>+++<-]>-.>>.+++.------.>++++[<-->-]<.";

    // basic options file to kickstart a program
    static String optionsFileContent = new StringBuilder().append("printInfo=true\n").append("printInteger=false\n")
            .append("inputChar=true\n").append("printCells=true\n").append("printInstructions=false\n").toString();

    // Initiate a project from a project directory
    public Project(String path) {
        this.optionsContent = Utils.getContentFromAFile(new File(path + "/options"));
        this.mainContents = Utils.getContentFromAFile(new File(path + "/main.bf"));

        // initiate options instance based on the options file
        Options o = new Options(this.optionsContent);

        // initiate a program
        this.program = new Program(this.mainContents, o);
    }

    // initiate a project in the given path
    //
    // a project is a directory with a main.bf source code
    // file and an .options file
    public static void create(String name, String path) {
        path += "/" + name;

        // create project's directory
        new File(path).mkdirs();

        // format the helloWorld template
        Linter l = new Linter(helloWorld);

        // create the main file
        File mainFile = new File(path + "/" + "main.bf");

        // create the options file
        File optionsFile = new File(path + "/options");

        // create and write contents to the files
        Utils.createAndWriteToFile(mainFile, l.getNewCode());
        Utils.createAndWriteToFile(optionsFile, optionsFileContent);

    }

    public void run() {
        this.program.run();
        this.program.printOutput();
    }
}