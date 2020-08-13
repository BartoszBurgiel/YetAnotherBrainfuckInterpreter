package interpreter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Utils contains helper methods usefull across the
// package  
public class Utils {
    protected static String getContentFromAFile(File file) {

        // Define a scanner
        Scanner scanner = null;

        // create a scanner for the file
        try {
            scanner = new Scanner(file);
        } catch (Exception e) {

            // check if the exception is that
            // no file was found
            if (e instanceof FileNotFoundException) {
                Helper.panic(Helper.NO_FILE_FOUND_IN_PROJECT);
            }
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

    static protected void createAndWriteToFile(File file, String content) {
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
}