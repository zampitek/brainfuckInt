package com.brainfuckc;

import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;

class Main {

    static String readFile(String fileName) {
        if(!fileName.contains(".bf")) {
            System.out.println("File Error: The specified file is not a *.bf file");
             System.exit(0);
        }

        StringBuilder fileContent = new StringBuilder();
        try {
            FileReader file = new FileReader(fileName);
            int data = file.read();

            while (data != -1) {
                fileContent.append((char) data);
                data = file.read();
            }
            file.close();

            return fileContent.toString();
        } catch (IOException e) {
            System.out.println("File Error: The file specified was not found. Try creating a new one with 'bf -n filename.bf'");
            System.exit(0);
            return "";
        }

    }

    public static void main(String[] args) {
        if(args.length == 0) {
            // creating Scanner instance named input
            Scanner input = new Scanner(System.in);

            // printing header
            System.out.println("\nBrainfuck interpreter by Riccardo Zampieri.");
            System.out.println("\nUse \"bf --help\" to get help.\n\n");

            // creating a while loop to continue to ask the user to input something
            // if the input is 'q' the loop stops and the program ends
            // using the Lexer class and the returnText method it removes all the caracters except +-<>[].,
            while(true) {
                System.out.print("> ");
                String text = input.nextLine();

                if (text.equals("q")) {
                    System.out.println("Quitting...");
                    break;
                }

                Interpreter interpreter = new Interpreter(text);
                interpreter.executeCode();
            }

            input.close();
        } else {
            if (args[0].equals("-o")) {
                String content = readFile(args[1]);
                Interpreter interpreter = new Interpreter(content);
                interpreter.executeCode();
            } else {
                System.out.println(args[0] + "is not a valid option.");
            }
        }
    }
}
