package com.brainfuckc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
    String text, toPrint;
    String[] instructionsSetArray;
    int[] pointers;
    byte currentPointer;
    ArrayList<String> instructionsSet;
    boolean inLoop;


    public Interpreter(String text) {
        this.text = text;
        this.pointers = new int[30000];
        this.currentPointer = 0;

        Parser parser = new Parser(this.text);
        this.instructionsSetArray = parser.getOrderedTokens();

        this.instructionsSet = new ArrayList<>(Arrays.asList(this.instructionsSetArray));
        this.toPrint = "";

        this.inLoop = false;
    }

    private void executeLoopCode(String[] code) {
        for (String token : code) {
            identifyInstruction(token);
        }
    }

    private void incrementPointer() {
        if(this.currentPointer >= 29999) {
            MemoryError error = new MemoryError("Impossible to increment the pointer (29.999), max pointer (29.999)");
            error.printError();
        }

        this.currentPointer++;
        if (!this.inLoop) {
            this.instructionsSet.remove(0);
        }
    }

    private void decrementPointer() {
        if(this.currentPointer > 0) {
            this.currentPointer--;
            if (!this.inLoop) this.instructionsSet.remove(0);
        }
        else {
            MemoryError error = new MemoryError("Impossible to decrement the pointer: current pointer (0), min pointer (0)");
            error.printError();
        }
    }

    private void incrementValue() {
        if(this.pointers[this.currentPointer] < 127) {
            this.pointers[this.currentPointer]++;
            if (!this.inLoop) this.instructionsSet.remove(0);
        } else {
            ValueError error = new ValueError("Impossible to increment the value of the current pointer (" + String.valueOf(this.currentPointer) + "), current value (127), max value (127)");
            error.printError();
        }
    }

    private void decrementValue() {
        if(this.pointers[this.currentPointer] > 0) {
            this.pointers[this.currentPointer]--;
            if (!this.inLoop) this.instructionsSet.remove(0);
        } else {
            ValueError error = new ValueError("Impossible to decrement the value of the current pointer (" + String.valueOf(this.currentPointer) + "), current value (" + String.valueOf(this.pointers[this.currentPointer]) + "), min value (0)");
            error.printError();
        }
    }

    private void loop() {
        this.inLoop = true;
        int pos = 0;
        while(instructionsSet.get(pos) != Lexer.STOP_LOOP) pos++;

        List<String> list = this.instructionsSet.subList(1, pos);
        String[] loopCode = list.toArray(new String[list.size()]);

        while (this.pointers[currentPointer] != 0) {
            executeLoopCode(loopCode);
        }

        for (int i = 0; i < loopCode.length + 2; i++) {
            instructionsSet.remove(0);
        }

        this.inLoop = false;
    }

    private void input() {
        Scanner scanner = new Scanner(System.in);

        int charGot = scanner.next().charAt(0);
        this.pointers[this.currentPointer] = charGot;

        if (!this.inLoop) this.instructionsSet.remove(0);

        scanner.close();
    }

    private void output() {
        char thisChar = (char)this.pointers[this.currentPointer];
        this.toPrint += thisChar;

        if (!this.inLoop) this.instructionsSet.remove(0);
    }

    private void identifyInstruction(String token) {
        switch (token) {
            case Lexer.P_INCREMENT:
                incrementPointer();
                break;
            case Lexer.P_DECREMENT:
                decrementPointer();
                break;
            case Lexer.V_INCREMENT:
                incrementValue();
                break;
            case Lexer.V_DECREMENT:
                decrementValue();
                break;
            case Lexer.START_LOOP:
                loop();
                break;
            case Lexer.STOP_LOOP:
                instructionsSet.remove(0);
                break;
            case Lexer.INPUT:
                input();
                break;
            case Lexer.OUTPUT:
                output();
                break;
        }
    }

    public void executeCode() {
        for (int i = 0; i < 30000; i++) {
            this.pointers[i] = 0;
        }

        while(!this.instructionsSet.isEmpty()) {
            identifyInstruction(this.instructionsSet.get(0));
        }

        System.out.println(this.toPrint + "\n");
    }
}

