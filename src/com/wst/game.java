package com.wst;

import java.util.ArrayList;
import java.util.List;

public class game {
    private int id = 0;
    private String name = "";
    private List<player> playerList= new ArrayList<>();
    private List<round> roundList= new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<player> getPlayerList() {
        return playerList;
    }

    public List<round> getRoundList() {
        return roundList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayerList(int num) {
        for (int i = 0; i < num; i++){
            player p = new player();
            p.setId(i);
            this.playerList.add(p);
        }
    }

    public void setRoundList(int num) {
        for (int i = 0; i < num; i++){
            round r = new round();
            r.setRoundId(i);
            this.roundList.add(r);
        }
    }
}
