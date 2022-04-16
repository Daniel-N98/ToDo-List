package main.util;

import java.util.Scanner;

public class InputReader {

    private final Scanner scanner;

    public InputReader(){
        this.scanner = new Scanner(System.in);
    }

    public String getNextText(String message){
        System.out.println(message);
        return scanner.nextLine();
    }
}
