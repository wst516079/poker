package com.wst;

import com.wst.DBCases.implement.createTableImpl;

public class Main {

    public static void main(String[] args) {
        gamePanel gamePanel=new gamePanel();

    }
    public void createGame(){
        game G = new game();
        G.setId(0);
        G.setName("test");
        G.setPlayerList(1);
    }
    public void createPlayer(){

    }
    public void createRound(){

    }
}
