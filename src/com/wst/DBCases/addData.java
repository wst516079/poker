package com.wst.DBCases;

import com.wst.player;

import java.util.List;

public interface addData {
    String addGameTable(String name, String playerTableId, String roundTableId);
    void addPlayerTable(String tableName, player p);
    void addRoundTable(String tableName, String chip);
    void addCardTable(String tableName, Integer value, String suit);
}
