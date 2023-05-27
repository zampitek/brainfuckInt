package com.brainfuckc;

import java.util.*;

public class Parser {
    String code;

    public Parser(String code) {
        this.code = code;
    }

    private boolean checkBrackets(String[] tokens) {
        ArrayList<Boolean> tokensClosedList = new ArrayList<Boolean>();

        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals(Lexer.START_LOOP)) {
                tokensClosedList.add(false);
            }
        }

        boolean[] tokensClosed = new boolean[tokensClosedList.size()];
        for (int i = 0; i < tokensClosedList.size(); i++) {
            tokensClosed[i] = tokensClosedList.get(i);
        }

        int j = 0;
        for (int i = 0; i < tokens.length; i++) {
            if(tokens[i].equals(Lexer.STOP_LOOP)) {
                tokensClosed[j] = true;
                j++;
            }
        }

        boolean flag = true;
        for (boolean b : tokensClosed) {
            if(b == false) flag = false;
        }

        if (flag) return true;

        return false;

    }

    public String[] getOrderedTokens() {
        Lexer lexer = new Lexer(this.code);
        String[] tokens = lexer.getTokens();
        String[] voidArray = {};

        if (checkBrackets(tokens)) return tokens;

        SyntaxError error = new SyntaxError("a loop opened was never closed");
        error.printError();
        return voidArray;
    }
}
