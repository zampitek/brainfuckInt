package com.brainfuckc;

public class ValueError extends Error {
    public ValueError(String message) {
        super("Value Error: " + message);
    }
}
