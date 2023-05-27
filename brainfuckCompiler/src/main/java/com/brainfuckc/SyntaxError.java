package com.brainfuckc;

public class SyntaxError extends Error {
    public SyntaxError(String message) {
        super("Syntax Error: " + message);
    }
}
