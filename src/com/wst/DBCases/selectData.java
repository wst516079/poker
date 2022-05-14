package com.wst.DBCases;

import com.wst.game;
import com.wst.player;
import com.wst.round;

import java.sql.ResultSet;
import java.util.List;

public interface selectData {
    List<player> selectPlayerData(String DBname);
    round selectRoundData(String DBname, String pos);
    List<String> selectGameData();
    int countRounds(String gameName);
    int countPlayers(String gameName);
}
