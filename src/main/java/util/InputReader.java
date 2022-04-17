package util;

import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints out a message to console, and returns the next line
     *
     * @param message to be printed
     * @return String nextLine
     */
    public String getNextText(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
