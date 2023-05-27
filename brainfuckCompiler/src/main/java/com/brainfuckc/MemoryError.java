package com.brainfuckc;

public class MemoryError extends Error {
    public MemoryError(String message) {
        super("Memory Error: " + message);
    }
}
