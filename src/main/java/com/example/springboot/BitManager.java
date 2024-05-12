package com.example.springboot;

public class BitManager {
    //board is 3x3, so 9 unit spots
    //each spot can be 3 things, 'x', 'o', ' ', so each thing can be stored with 2 bits
    //need then 18 bits
    //int is 32 bits
    //use first 18 bits

    //shift bits of interest to be first 2 bits
    //extract by and 110000... only first 2 are let through
    public int getVal(int board, int i) {
        i *= 2;
        return (board >> i) & 0b11;
    }

    //returns new board
    //sets area 0
    //sets area val
    public int setEqual(int board, int i, int val) {
        board = setZero(board, i);
        return setVal(board, i, val);
    }

    //returns new board
    //assumes board val there is already 0
    public int setVal(int board, int i, int val) {
        i *= 2;
        return board | (val << i);
    }

    //returns new board
    //and with 111011... where
    //1 & x = x, so reset is retained
    //1 & 0 = 0, so specific is 0
    public int setZero(int board, int i) {
        i *= 2;
        return board & ~(1 << i);
    }
}
