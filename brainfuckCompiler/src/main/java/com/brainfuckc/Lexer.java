package com.brainfuckc;

import java.util.*;

public class Lexer {
    // defining constants and variables
    String str;
    public static final String P_INCREMENT   = "P_INCREMENT";
    public static final String P_DECREMENT   = "P_DECREMENT";
    public static final String V_INCREMENT   = "V_INCREMENT";
    public static final String V_DECREMENT   = "V_DECREMENT";
    public static final String START_LOOP    = "START_LOOP";
    public static final String STOP_LOOP     = "STOP_LOOP";
    public static final String INPUT         = "INPUT";
    public static final String OUTPUT        = "OUTPUT";


    // initializing the class
    public Lexer(String str) {
        this.str = str;
    }

    // this method checks if the character passed as parameter is in '+-<>[].,' and it delete the character
    // if it's not in there
    private boolean isValidCharacter(char character) {
        String validChars = "+-<>[].,";
        if (!validChars.contains(String.valueOf(character))) return false;

        return true;
    }

    // this method analizes the character and return the correspondent
    private String getToken(char character) {
        switch(character) {
            case '>':
                return P_INCREMENT;
            case '<':
                return P_DECREMENT;
            case '+':
                return V_INCREMENT;
            case '-':
                return V_DECREMENT;
            case '[':
                return START_LOOP;
            case ']':
                return STOP_LOOP;
            case ',':
                return INPUT;
            case '.':
                return OUTPUT;
            default:                    // put this one just because the compiler would have given me error
                return "Invalid Token";
        }
    }

    // this method put all the tokens in a list, and returns it to the main function
    public String[] getTokens() {
        ArrayList<String> tokensSequenceList = new ArrayList<String>();

        for(int i = 0; i < this.str.length(); i++) {
            if(isValidCharacter(this.str.charAt(i))) {
                tokensSequenceList.add(getToken(this.str.charAt(i)));
            }
        }

        String[] tokensSequence = tokensSequenceList.toArray(new String[]{});

        return tokensSequence;
    }
}
