package com.brainfuckc;

public class Error {
    String message;

    public Error(String message) {
        this.message = message;
    }

    public void printError() {
        System.out.println('\n' + message);
        System.exit(0);
    }
}
