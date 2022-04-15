package util;

import java.util.Scanner;

public class InputReader {

    private Scanner scanner;

    public InputReader(){
        this.scanner = new Scanner(System.in);
    }

    public String getNextText(String message){
        System.out.println(message);
        return scanner.next();
    }
}
