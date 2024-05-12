package com.example.springboot;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Minimax {
    public static void printBoard(char[] board) {
        for (int i = 0; i < 9; i++) {
            System.out.print(board[i] + " ");
            if (i % 3 == 2) {
                System.out.print("\n");
            }
        }
    }

    public static int playRandom(char[] board) {
        int rand;
        do {
            rand = (int) (Math.random() * 9);
        } while(board[rand] != ' ');
        return rand;
    }


    public static int getWinningLines(char[] board, char a) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            int j = i * 3;
            int[] vert = {j, j + 1, j + 2};
            if (isWinnableLine(board, vert, a)) count++;

            int[] hor = {i, i + 3, i + 6};
            if (isWinnableLine(board, hor, a)) count++;
        }

        int[] diag0 = {0, 4, 8};
        if (isWinnableLine(board, diag0, a)) count++;

        int[] diag1 = {2, 4, 6};
        if (isWinningLine(board, diag1, a)) return count++;

        return count;
    }

    public static boolean isWinnableLine(char[] board, int[] ind, char a) {
        char b = a == 'x' ? 'o' : 'x';
        int i0 = ind[0];
        int i1 = ind[1];
        int i2 = ind[2];
        if (board[i0] != b && board[i1] != b && board[i2] != b) {
            return board[i0] == a || board[i1] == a || board[i2] == a;
        }
        return false;
    }

    public static int evaluate(char[] board, char winner) {
        System.out.println("evaluating: ");
        printBoard(board);
        int res;
        if (winner == ' ') {
            int countX = getWinningLines(board, 'x');
            int countO = getWinningLines(board, 'o');
            res = countX - countO;
        }
        else {
            if (winner == 'o') res = -100;
            else res = 100;
        }
        System.out.println("res: " + res);
        return res;
    }

    public static boolean isWinningLine(char[] board, int[] ind, char a) {
        int i0 = ind[0];
        int i1 = ind[1];
        int i2 = ind[2];
        return board[i0] == a && board[i1] == a && board[i2] == a;
    }

    //true if is winnable for a
    public static char getWinner(char[] board) {
        char x = 'x';
        char o = 'o';

        for (int i = 0; i < 3; i++) {
            int j = i * 3;
            int[] vert = {j, j + 1, j + 2};
            if (isWinningLine(board, vert, x)) return x;
            if (isWinningLine(board, vert, o)) return o;

            int[] hor = {i, i + 3, i + 6};
            if (isWinningLine(board, hor, x)) return x;
            if (isWinningLine(board, hor, o)) return o;
        }

        int[] diag0 = {0, 4, 8};
        if (isWinningLine(board, diag0, x)) return x;
        if (isWinningLine(board, diag0, o)) return o;

        int[] diag1 = {2, 4, 6};
        if (isWinningLine(board, diag1, x)) return x;
        if (isWinningLine(board, diag1, o)) return o;

        return ' '; //no winner
    }

    //x is playing
    public static int maxChildEval(char[] board, int depth, int plays) {
        char winner = getWinner(board);
        if (plays == 9 || winner != ' ' || depth == 0) return evaluate(board, winner);
        else {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] != ' ') continue;

                board[i] = 'x';
                int eval = minChildEval(board, depth - 1, plays + 1);
                board[i] = ' ';

                maxEval = max(maxEval, eval);
            }
            return maxEval;
        }
    }

    //o is playing
    public static int minChildEval(char[] board, int depth, int plays) {
        char winner = getWinner(board);
        if (plays == 9 || winner != ' ' || depth == 0) return evaluate(board, winner);
        else {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] != ' ') continue;

                board[i] = 'o';
                int eval = maxChildEval(board, depth - 1, plays + 1);
                board[i] = ' ';

                minEval = min(minEval, eval);
            }
            return minEval;
        }
    }

    public static int minimaxHelper(char[] board, char turn, int depth, int plays) {
        int idx = 0;
        if (turn == 'x') { //max of next min children
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] != ' ') continue;

                board[i] = 'x';
                int eval = minChildEval(board, depth - 1, plays + 1);
                board[i] = ' ';

                if (maxEval < eval) {
                    maxEval = eval;
                    idx = i;
                }
            }
        }
        else { //min of next max children
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] != ' ') continue;

                board[i] = 'o';
                int eval = maxChildEval(board, depth - 1, plays + 1);
                board[i] = ' ';

                if (minEval > eval) {
                    minEval = eval;
                    idx = i;
                }
            }
        }
        return idx;
    }


    public static int minimaxPlay(char[] board, char turn, int plays) {
        final int depth = 10;
        return minimaxHelper(board, turn, depth, plays);
    }

    public static int greedyPlay(char[] board, char turn, int plays) {
        final int depth = 1;
        return minimaxHelper(board, turn, depth, plays);
    }

    public static int play(char[] board, char turn, int plays) { //returns the index of play
        return minimaxPlay(board, turn, plays);
    }

    public static void test() {
        System.out.println("test from minimax");
    }
}
