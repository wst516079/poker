package com.wst;

public class cards {
    private int value = 0;     //0~12: deuce to ace
    private int suit = 0;     //club 0; diamond 1; heart 2; spades 3;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSuit() {
        return suit;
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }
}
