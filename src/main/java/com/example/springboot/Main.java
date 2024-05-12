package com.example.springboot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        char[] board = new char[9];
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
        }

        int plays = 0;
        boolean playerTurn = true;
        while (true) {
            if (playerTurn) {
                Minimax.printBoard(board);
                System.out.print("give input (row col): ");
                String input = reader.readLine();
                if (input.equals("exit")) break;

                int r = input.charAt(0) - '0';
                int c = input.charAt(2) - '0';
                board[r * 3 + c] = 'x';
            }
            else {
                int i = Minimax.play(board, 'o', plays);
                board[i] = 'o';
            }
            plays++;

            char winner = Minimax.getWinner(board);
            if (winner != ' ') {
                Minimax.printBoard(board);
                System.out.println(winner + " won the game");
                break;
            }
            if (plays == 9) {
                Minimax.printBoard(board);
                System.out.println("game was a tie");
                break;
            }

            playerTurn = !playerTurn;
        }
    }
}
