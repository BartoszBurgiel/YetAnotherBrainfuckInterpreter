package interpreter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
            .append("inputChar=true\n").append("printCells=true\n").toString();

    // Initiate a project from a project directory
    public Project(String path) {
        this.optionsContent = getContentFromAFile(new File(path + "/options"));
        this.mainContents = getContentFromAFile(new File(path + "/main.bf"));

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
        createAndWriteToFile(mainFile, l.getNewCode());
        createAndWriteToFile(optionsFile, optionsFileContent);

    }

    static void createAndWriteToFile(File file, String content) {
        try {
            if (file.createNewFile()) {
                System.out.printf("The %s file has been successfully created.\n", file.getName());
            } else {
                System.out.printf("The %s file already exists.\n", file.getName());
                return;
            }
        } catch (IOException e) {
            System.out.printf("An error occurred while creating the %s file.\n", file.getName());
            e.printStackTrace();
        }

        // write into the main.bf file
        try {
            FileWriter mainFileWriter = new FileWriter(file);
            mainFileWriter.write(content);
            mainFileWriter.close();
            System.out.printf("Successfully a sample program has been written into the %s file.\n", file.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
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
        String content = "";
        try {
            content = scanner.useDelimiter("\\A").next();
        } catch (Exception e) {
            // handle exception for the case when the file is empty
            if (e instanceof NoSuchElementException) {
                Helper.panic("The brainfuck file can not be empty.\n" + file.getAbsolutePath());
            }
        }

        // close the scanner
        scanner.close();

        return content;
    }

    public void run() {
        this.program.run();
    }
}