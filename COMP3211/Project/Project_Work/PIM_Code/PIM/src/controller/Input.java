package controller;

import java.util.Scanner;

public class Input {
    private static String input;

    /**
     * Input Contract
     * @param input: the input of user
     */
    public Input(String input) {
        this.input = input;
    }

    /**
     * Get Input Function
     * @return String: the input of user
     */
    public static String getInput() {
        return input;
    }

    /**
     * Set Input Function
     */
    public static void setInput() {
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();
    }
}