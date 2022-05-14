package com.wst;

import com.wst.DBCases.implement.addDataImpl;
import com.wst.DBCases.implement.createTableImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class createNewGame extends JFrame implements ActionListener {
    private JLabel playerNum = new JLabel("Number of Player");
    private JLabel roundNum = new JLabel("Number of Rounds");
    private JLabel gameName = new JLabel("game name");
    private JTextField input1 = new JTextField();
    private JTextField input2 = new JTextField();
    private JTextField input3 = new JTextField();
    private JButton submit = new JButton("submit");
    private JButton back = new JButton("back");
    private JButton setPlayerInfo = new JButton("set players' name and chip amount");
    private int numOfPlayer;
    private int numOfRound;

    private game G = new game();
    public createNewGame() {
        setLayout(null);
        this.setSize(1920, 1080);
        this.setTitle("create new game");
        this.setVisible(true);
        submit.addActionListener(this);
        back.addActionListener(this);
        setPlayerInfo.addActionListener(this);
        playerNum.setBounds(0,0,200,20);
        roundNum.setBounds(0,30,200,20);
        gameName.setBounds(0,60,200,20);
        input1.setBounds(200,0,200,20);
        input2.setBounds(200,30,200,20);
        input3.setBounds(200,60,200,20);
        submit.setBounds(0,90,200,20);
        back.setBounds(0,120,200,20);
        setPlayerInfo.setBounds(0,150,200,20);
        this.add(playerNum);
        this.add(roundNum);
        this.add(gameName);
        this.add(input1);
        this.add(input2);
        this.add(input3);
        this.add(submit);
        this.add(back);
        this.add(setPlayerInfo);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        SQLiteJDBC conn = new SQLiteJDBC();
        conn.connect();
        createTableImpl s = new createTableImpl();
        s.createGameTable();
        addDataImpl d = new addDataImpl();
        if (e.getSource() == back) {
            this.dispose();
            new gamePanel();
        }
        else if (e.getSource() == submit){
                                 //indexing
            G.setName(input3.getText());
            try{
                int numofplayer = Integer.parseInt(input1.getText());   //make input1 to be an int
                int numofround = Integer.parseInt(input2.getText());
                numOfPlayer = numofplayer;
                numOfRound = numofround;
                G.setPlayerList(numofplayer);
                G.setRoundList(numofround);
                //add to db, check dup and null
                d.addGameTable(G.getName(),G.getName(),G.getName());

                //s.createRoundTable(G.getName());
                for (int i = 0; i < numofround; i++){
                    s.createRoundTable(G.getName()+String.valueOf(i));
                    s.createPlayerTable(G.getName()+String.valueOf(i));
                }
                //this.dispose();
                new createNewRecord(numOfPlayer,numOfRound, input3.getText());
            } catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }
        else if (e.getSource() == setPlayerInfo){
            int numofplayer = Integer.parseInt(input1.getText());   //make input1 to be an int
            numOfPlayer = numofplayer;
            int numofround = Integer.parseInt(input2.getText());   //make input2 to be an int
            numOfRound = numofround;
            new setPlayerInfo(numOfPlayer,numOfRound, input3.getText());


        }
    }
    public static void main(String[] args) {

        createNewGame createNewGame=new createNewGame();
    }
}
