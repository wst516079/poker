package com.wst.DBCases;

import com.wst.cards;
import com.wst.player;

import java.util.List;

public interface updateData {
    void updateRoundData(String tableName, Integer chip, String turn);
    void updatePlayerData(String tableName, player p);
    public void updateRoundCardsData(String tableName, List<cards> cardsList);
}
