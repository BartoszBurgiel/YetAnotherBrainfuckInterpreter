package interpreter;

import java.io.File;

// Cli is the command line interface handler
public class Cli {

    private String[] args;

    public Cli(String[] args) {
        this.args = args;
    }

    public void execute() {
        // check if the args are empty
        if (this.args.length == 0) {
            Helper.panic(Helper.NO_ARGUMENTS_STRING);
        }

        // first argument determines the main action
        switch (this.args[0]) {

            // Initialize a project in the current directory
            case "init":

                String projectName = "";
                try {

                    // the 1st argument is the name of the project
                    projectName = this.args[1];
                } catch (Exception e) {

                    if (e instanceof ArrayIndexOutOfBoundsException) {
                        Helper.panic(Helper.TOO_FEW_ARGUMENTS_STRING);
                    }
                }

                Project.create(projectName, ".");
                break;
            case "run":

                String pathToTheProject = "";
                try {

                    // the 1st argument is the name of the project
                    pathToTheProject = this.args[1];
                } catch (Exception e) {

                    if (e instanceof ArrayIndexOutOfBoundsException) {
                        Helper.panic(Helper.TOO_FEW_ARGUMENTS_STRING);
                    }
                }

                // create a project instance from the provided path
                Project p = new Project(pathToTheProject);
                p.run();
                break;
            case "exec":
                String pathToTheFile = "";
                try {

                    // the 1st argument is the name of the project
                    pathToTheFile = this.args[1];
                } catch (Exception e) {

                    if (e instanceof ArrayIndexOutOfBoundsException) {
                        Helper.panic(Helper.TOO_FEW_ARGUMENTS_STRING);
                    }
                }

                // Create the program instance off the *.bf file
                Program program = new Program(new File(pathToTheFile));
                program.run();

            case "--help":
                System.out.println(Helper.MAN_STRING);
                break;
            default:
                System.out.println("Unknown argument provided.");
                System.out.println(Helper.MAN_STRING);
        }
    }
}