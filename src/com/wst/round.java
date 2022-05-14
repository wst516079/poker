package com.wst;

import java.util.ArrayList;
import java.util.List;

public class round {
    private int roundId = 0;
    private List<cards> cardsList = new ArrayList<>();
    private int chips;
    private boolean ishole = false;
    private boolean isflop = false;
    private boolean isturn = false;
    private boolean isriver = false;
    public void initList(){
        for (int i = 0; i < 5; i++){
            cards temp1 = new cards();
            temp1.setSuit(4);
            temp1.setValue(13);
            this.cardsList.add(temp1);
        }
    }
    public int getRoundId() {
        return roundId;
    }

    public List<cards> getCardsList() {
        return cardsList;
    }

    public int getChips() {
        return chips;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public void setCardsList(cards c, int pos) {
        this.cardsList.get(pos).setSuit(c.getSuit());
        this.cardsList.get(pos).setValue(c.getValue());
    }
    public void setCardsList(List<cards> cardsList) {
        this.cardsList = cardsList;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public boolean isIshole() {
        return ishole;
    }

    public void setIshole(boolean ishole) {
        this.ishole = ishole;
    }

    public boolean isIsflop() {
        return isflop;
    }

    public void setIsflop(boolean isflop) {
        this.isflop = isflop;
    }

    public boolean isIsturn() {
        return isturn;
    }

    public void setIsturn(boolean isturn) {
        this.isturn = isturn;
    }

    public boolean isIsriver() {
        return isriver;
    }

    public void setIsriver(boolean isriver) {
        this.isriver = isriver;
    }


}
