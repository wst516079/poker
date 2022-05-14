package com.wst;
import com.wst.DBCases.implement.addDataImpl;
import com.wst.DBCases.implement.createTableImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class setPlayerInfo extends JFrame implements ActionListener
{

    private JButton submit = new JButton("submit");
    private List<List<String>> playerInfo = new ArrayList<>();
    private List<JTextField> name = new ArrayList<>();
    private List<JTextField> chip = new ArrayList<>();
    private List<JLabel> labelName = new ArrayList<>();
    private List<JLabel> labelChip = new ArrayList<>();
    private int numOfPlayer;
    private int numOfRound;
    private String GameName;
    public setPlayerInfo(int numofplayer,int numofround, String gameName)
    {
        GameName = gameName;
        numOfPlayer = numofplayer;
        numOfRound = numofround;
        setLayout(null);
        this.setTitle("set players' name and chip amount");    //set title
        this.setSize(1920,1080);    //set size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //set closeable
        setVisible(true);    //set visiable
        int i = 0;
        for (i = 0; i < numofplayer; i++){
            JTextField tempName = new JTextField();
            tempName.setBounds(200,i*30,200,20);
            name.add(tempName);
            JTextField tempChip = new JTextField();
            tempChip.setBounds(600,i*30,200,20);
            chip.add(tempChip);
            JLabel tempNameL = new JLabel("player's name");
            tempNameL.setBounds(0,i*30,200,20);
            labelName.add(tempNameL);
            JLabel tempChipL = new JLabel("player's chip");
            tempChipL.setBounds(400,i*30,200,20);
            labelChip.add(tempChipL);
        }
        for (int j = 0; j < numofplayer; j++){
            this.add(name.get(j));
            this.add(chip.get(j));
            this.add(labelChip.get(j));
            this.add(labelName.get(j));
        }
        submit.setBounds(0,i*30,200,20);
        submit.addActionListener(this);
        this.add(submit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SQLiteJDBC conn = new SQLiteJDBC();
        conn.connect();
        setPlayerInfo(playerInfo,name,chip);
        addDataImpl d = new addDataImpl();
        createTableImpl s = new createTableImpl();

        for (int j = 0; j < numOfRound; j++){
            s.createPlayerTable(GameName+String.valueOf(j));
        }
        if (e.getSource() == submit){
            for (int i = 0; i < playerInfo.size(); i++){
                player p = new player();
                p.initList();
                p.setName(name.get(i).getText());
                p.setChip(Integer.parseInt(chip.get(i).getText()));
                p.setBlind(i==0);
                for (int j = 0; j < numOfRound; j++){
                    d.addPlayerTable(GameName+String.valueOf(j),p);
                }

            }
        }
        this.dispose();
    }



    public void main(String[] args) {
        setPlayerInfo setPlayerInfo=new setPlayerInfo(this.numOfPlayer,this.numOfRound,this.GameName);
    }

    public void setPlayerInfo(List<List<String>> playerInfotemp, List<JTextField> name, List<JTextField> chip) {
        for (int i = 0; i < name.size(); i++){
            List<String> temp = new ArrayList<>();
            temp.add(name.get(i).getText());
            temp.add(chip.get(i).getText());
            playerInfotemp.add(temp);
        }
    }
}

