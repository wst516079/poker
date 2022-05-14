package com.wst;

import com.wst.DBCases.implement.addDataImpl;
import com.wst.DBCases.implement.selectDataImpl;
import com.wst.DBCases.implement.updateDataImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class replayDetail extends JFrame implements ActionListener {
    private JPanel jp = new JPanel();
    private JPanel jp2 = new JPanel();

    private List<JTextField> playerName = new ArrayList<>();
    private List<JTextField> playerChip = new ArrayList<>();
    private List<JTextField> playerChipChange = new ArrayList<>();
    private List<JTextField> playerIsBlind = new ArrayList<>();
    private List<JTextField> playerAction = new ArrayList<>();

    private List<JLabel> playerCardValue1 = new ArrayList<>();
    private List<JLabel> playerCardSuit1 = new ArrayList<>();
    private List<JLabel> playerCardValue2 = new ArrayList<>();
    private List<JLabel> playerCardSuit2 = new ArrayList<>();
    private List<JLabel> cardImage = new ArrayList<>();
    private List<JLabel> cardImageS = new ArrayList<>();
    private JButton prevB = new JButton("<- previous turn");
    private JButton nextB = new JButton("next turn ->");
    private JButton prevRoundB = new JButton("<-previous round");
    private JButton nextRoundB = new JButton("next round->");
    private JTextField roundPot = new JTextField("0");
    private int numOfPlayer;
    private int numOfRound;
    private int blindPos = 1;
    private int roundNum = 0;
    private String GameName;
    private List<player> p = new ArrayList<>();
    private List<round> r = new ArrayList<>();
    private List<List<player>> pList= new ArrayList<>();
    private JTextField turnPot = new JTextField("0");
    private JTextField turnL = new JTextField("hole Turn");
    private JTextField roundL = new JTextField("Round: 0");
    private enum state {
        hole, flop, turn, river
    }
    private state st = state.hole;

    public replayDetail(int numofplayer, int numofround, String gameName) {
        for (int num = 0; num < numofplayer; num++){
            p.add(new player());
            p.get(num).initList();         //allocate space to avoid index out of range
        }
        for (int num = 0; num < numofround; num++){
            r.add(new round());
            r.get(num).initList();         //allocate space to avoid index out of range
        }

        GameName = gameName;
        numOfPlayer = numofplayer;
        numOfRound = numofround;
        setLayout(null);
        this.add(jp);
        this.add(jp2);
        this.setTitle("new record");    //set title
        this.setSize(1920,1080);    //set size
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //set closeable
        jp.setBounds(0,0,1920,600);
        jp2.setBounds(0,600,1920,400);


        jp.setLayout(null);
        jp2.setLayout(null);



        setPlayerInfoFromDB();
        int i = 0;
        for (i = 0; i < numofplayer; i++){
            JTextField tempNameL = new JTextField(String.valueOf(pList.get(roundNum).get(i).getName()));
            tempNameL.setBounds(0,20+i*20,200,20);
            tempNameL.setEditable(false);
            playerName.add(tempNameL);
            JTextField tempChipT = new JTextField(String.valueOf(pList.get(roundNum).get(i).getChip()));
            tempChipT.setBounds(200,20+i*20,100,20);
            tempChipT.setEditable(false);
            playerChip.add(tempChipT);
            JTextField tempChipC = new JTextField(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
            tempChipC.setBounds(300,20+i*20,100,20);
            tempChipC.setEditable(false);
            playerChipChange.add(tempChipC);
            JTextField tempBlindT = new JTextField();
            tempBlindT.setBounds(400,20+i*20,200,20);
            tempBlindT.setEditable(false);
            playerIsBlind.add(tempBlindT);


            JTextField tempAction = new JTextField();
            tempAction.setBounds(600,20+i*20,200,20);
            tempAction.setEditable(false);
            playerAction.add(tempAction);

            JLabel tempL = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempL.setBounds(800,20+i*20,60,20);
            playerCardValue1.add(tempL);
            JLabel tempLV = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempLV.setBounds(860,20+i*20,60,20);
            playerCardSuit1.add(tempLV);

            JLabel tempL2 = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempL2.setBounds(920,20+i*20,60,20);
            playerCardValue2.add(tempL2);
            JLabel tempLV2 = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempLV2.setBounds(980,20+i*20,60,20);
            playerCardSuit2.add(tempLV2);
        }
        JLabel nameL = new JLabel("player's name");
        nameL.setBounds(0,0,200,20);
        jp.add(nameL);
        JLabel chipL = new JLabel("player's chip");
        chipL.setBounds(200,0,200,20);
        jp.add(chipL);
        JLabel blindL = new JLabel("player's role");
        blindL.setBounds(400,0,200,20);
        jp.add(blindL);
        JLabel turnBetL = new JLabel("player's bet action");
        turnBetL.setBounds(600,0,200,20);
        jp.add(turnBetL);
        JLabel actionL = new JLabel("player's card");
        actionL.setBounds(800,0,200,20);
        jp.add(actionL);
        playerIsBlind.get(0).setText("dealer");
        playerIsBlind.get(1).setText("small blind");
        playerIsBlind.get(2).setText("big blind");
        pList.get(roundNum).get(blindPos).setBlind(true);
        for (int j = 0; j < numofplayer; j++){
            jp.add(playerName.get(j));
            jp.add(playerChip.get(j));
            jp.add(playerChipChange.get(j));
            jp.add(playerIsBlind.get(j));
            jp.add(playerAction.get(j));

            jp.add(playerCardSuit1.get(j));
            jp.add(playerCardValue1.get(j));
            jp.add(playerCardSuit2.get(j));
            jp.add(playerCardValue2.get(j));
        }
        for (int j = 0; j < 5; j++){

            JLabel tempI = new JLabel(new ImageIcon("src/image/defaultSuit.png"));
            JLabel tempS = new JLabel(new ImageIcon("src/image/defaultValue.png"));
            tempI.setBounds(j*384,110,384,384);
            tempS.setBounds(j*384,60,50,50);
            cardImage.add(tempI);
            cardImageS.add(tempS);



        }
        for (int j = 0; j < 5; j++){

            jp2.add(cardImage.get(j));
            jp2.add(cardImageS.get(j));
        }

        prevB.setBounds(0,20+i*20,200,20);
        nextB.setBounds(1720,20+i*20,200,20);
        prevB.addActionListener(this);
        nextB.addActionListener(this);
        jp.add(prevB);
        jp.add(nextB);

        prevRoundB.setBounds(0,0,200,20);
        nextRoundB.setBounds(1720,0,200,20);
        prevRoundB.addActionListener(this);
        nextRoundB.addActionListener(this);

        jp2.add(prevRoundB);
        jp2.add(nextRoundB);

        turnPot.setBounds(860,20+i*20,200,20);
        roundPot.setBounds(860,0,200,20);
        turnPot.setEditable(false);
        roundPot.setEditable(false);
        turnL.setEditable(false);
        turnL.setBounds(860,60+i*20,200,20);
        roundL.setBounds(1060,0,200,20);
        roundL.setEditable(false);
        jp2.add(roundL);
        jp2.add(roundPot);
        jp.add(turnL);
        jp.add(turnPot);
        jp2.setBackground(Color.CYAN);
        for (int y = 0; y < numOfPlayer; y++){
            playerCardValue1.get(y).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(y).getCardsList().get(0).getValue())+"s.png"));
            playerCardValue2.get(y).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(y).getCardsList().get(1).getValue())+"s.png"));
            playerCardSuit1.get(y).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(y).getCardsList().get(0).getSuit())+"s.png"));
            playerCardSuit2.get(y).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(y).getCardsList().get(1).getSuit())+"s.png"));
        }
        selectDataImpl s = new selectDataImpl();
        for (int y = 0; y < numofround; y++){
            r.get(y).setCardsList(s.selectRoundData("round"+gameName+y,"isriver").getCardsList());
        }
        for (int y = 0; y < 5; y++){
            cardImage.get(y).setIcon(new ImageIcon("src/image/"+num2Suit(r.get(roundNum).getCardsList().get(y).getSuit())+".png"));
            cardImageS.get(y).setIcon(new ImageIcon("src/image/"+num2Value(r.get(roundNum).getCardsList().get(y).getValue())+".png"));
        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        SQLiteJDBC conn = new SQLiteJDBC();
        conn.connect();
        updateDataImpl u = new updateDataImpl();
        addDataImpl d = new addDataImpl();
        selectDataImpl s = new selectDataImpl();
        helper h = new helper();
        if (e.getSource() == prevB){
            switch (st){
                case river:
                    st = state.turn;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                    }
                    break;
                case turn:
                    st = state.flop;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));
                    }
                    break;
                case flop:
                    st = state.hole;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));

                    }
                    break;
                case hole:
                    st = state.hole;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                    }
                    break;
            }
            turnL.setText(String.valueOf(st));
            turnPot.setText(String.valueOf(s.selectRoundData("round"+GameName+String.valueOf(roundNum),"is"+String.valueOf(st)).getChips()));

            for (int i = 0; i < numOfPlayer; i++){
                playerCardValue1.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(0).getValue())+"s.png"));
                playerCardValue2.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(1).getValue())+"s.png"));
                playerCardSuit1.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(0).getSuit())+"s.png"));
                playerCardSuit2.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(1).getSuit())+"s.png"));
            }
        }
        else if (e.getSource() == nextB){
            switch (st){
                case hole:
                    st = state.flop;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));
                    }
                    break;
                case flop:
                    st = state.turn;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                    }
                    break;
                case turn:
                    st = state.river;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                    }
                    break;
                case river:
                    st = state.river;
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                    }
                    break;

            }
            turnL.setText(String.valueOf(st));
            turnPot.setText(String.valueOf(s.selectRoundData("round"+GameName+String.valueOf(roundNum),"is"+String.valueOf(st)).getChips()));
            for (int i = 0; i < numOfPlayer; i++){
                playerCardValue1.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(0).getValue())+"s.png"));
                playerCardValue2.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(1).getValue())+"s.png"));
                playerCardSuit1.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(0).getSuit())+"s.png"));
                playerCardSuit2.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(1).getSuit())+"s.png"));
            }
        }

        if (e.getSource()==prevRoundB){

            if (roundNum!=0){
                if (blindPos==0){
                    blindPos = numOfPlayer-1;
                }
                else{
                    blindPos--;
                }
            }
            roundNum=roundNum==0?0:roundNum-1;
            for (int y = 0; y < numOfRound; y++){
                r.get(y).setCardsList(s.selectRoundData("round"+numOfRound+y,"isriver").getCardsList());
            }
            for (int y = 0; y < 5; y++){
                cardImage.get(y).setIcon(new ImageIcon("src/image/"+num2Suit(r.get(roundNum).getCardsList().get(y).getSuit())+".png"));
                cardImageS.get(y).setIcon(new ImageIcon("src/image/"+num2Value(r.get(roundNum).getCardsList().get(y).getValue())+".png"));
            }
            switch (st){
                case river:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                    }
                    break;
                case turn:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                    }
                    break;
                case flop:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));

                    }
                    break;
                case hole:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                    }
                    break;
            }
            turnL.setText(String.valueOf(st));
            turnPot.setText(String.valueOf(s.selectRoundData("round"+GameName+String.valueOf(roundNum),"is"+String.valueOf(st)).getChips()));
            int roundChipSum = 0;
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"ishole").getChips();
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"isflop").getChips();
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"isturn").getChips();
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"isriver").getChips();
            roundPot.setText(String.valueOf(roundChipSum));
            roundL.setText("Round: "+String.valueOf(roundNum));
            for (int i = 0; i < numOfPlayer; i++){
                playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                if (i==blindPos){
                    playerIsBlind.get(i).setText("small blind");
                    if (blindPos==numOfPlayer-1){
                        playerIsBlind.get(0).setText("big blind");
                        playerIsBlind.get(i-1).setText("dealer");
                    }
                    else if (blindPos==0){
                        playerIsBlind.get(1).setText("big blind");
                        playerIsBlind.get(numOfPlayer-1).setText("dealer");
                    }
                    else{
                        playerIsBlind.get(i+1).setText("big blind");
                        playerIsBlind.get(i-1).setText("dealer");
                    }
                }

            }
            for (int i = 0; i < numOfPlayer; i++){
                playerCardValue1.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(0).getValue())+"s.png"));
                playerCardValue2.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(1).getValue())+"s.png"));
                playerCardSuit1.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(0).getSuit())+"s.png"));
                playerCardSuit2.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(1).getSuit())+"s.png"));
            }
        }
        else if (e.getSource()==nextRoundB){

            if (roundNum!=numOfRound-1){
                if (blindPos==numOfPlayer-1){
                    blindPos=0;
                }
                else{
                    blindPos++;
                }
            }
            roundNum=roundNum==numOfRound-1?numOfRound-1:roundNum+1;
            for (int y = 0; y < numOfRound; y++){
                r.get(y).setCardsList(s.selectRoundData("round"+numOfRound+y,"isriver").getCardsList());
            }
            for (int y = 0; y < 5; y++){
                cardImage.get(y).setIcon(new ImageIcon("src/image/"+num2Suit(r.get(roundNum).getCardsList().get(y).getSuit())+".png"));
                cardImageS.get(y).setIcon(new ImageIcon("src/image/"+num2Value(r.get(roundNum).getCardsList().get(y).getValue())+".png"));
            }
            switch (st){
                case river:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                    }
                    break;
                case turn:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                    }
                    break;
                case flop:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));

                    }
                    break;
                case hole:
                    for (int i = 0; i < numOfPlayer; i++){
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        playerChip.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                    }
                    break;
            }
            turnL.setText(String.valueOf(st));
            turnPot.setText(String.valueOf(s.selectRoundData("round"+GameName+String.valueOf(roundNum),"is"+String.valueOf(st)).getChips()));
            int roundChipSum = 0;
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"ishole").getChips();
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"isflop").getChips();
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"isturn").getChips();
            roundChipSum+=s.selectRoundData("round"+GameName+String.valueOf(roundNum),"isriver").getChips();
            roundPot.setText(String.valueOf(roundChipSum));
            roundL.setText("Round: "+String.valueOf(roundNum));
            for (int i = 0; i < numOfPlayer; i++){
                playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                if (i==blindPos){
                    playerIsBlind.get(i).setText("small blind");
                    if (blindPos==numOfPlayer-1){
                        playerIsBlind.get(0).setText("big blind");
                        playerIsBlind.get(i-1).setText("dealer");
                    }
                    else if (blindPos==0){
                        playerIsBlind.get(1).setText("big blind");
                        playerIsBlind.get(numOfPlayer-1).setText("dealer");
                    }
                    else{
                        playerIsBlind.get(i+1).setText("big blind");
                        playerIsBlind.get(i-1).setText("dealer");
                    }
                }
            }
            for (int i = 0; i < numOfPlayer; i++){
                playerCardValue1.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(0).getValue())+"s.png"));
                playerCardValue2.get(i).setIcon(new ImageIcon("src/image/"+num2Value(pList.get(roundNum).get(i).getCardsList().get(1).getValue())+"s.png"));
                playerCardSuit1.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(0).getSuit())+"s.png"));
                playerCardSuit2.get(i).setIcon(new ImageIcon("src/image/"+num2Suit(pList.get(roundNum).get(i).getCardsList().get(1).getSuit())+"s.png"));
            }
        }


    }
    public void main(String[] args) {
        //gamePanel gamePanel=new gamePanel();
        replayDetail replayDetail = new replayDetail(this.numOfPlayer,this.numOfRound,this.GameName);
    }
    public void setPlayerInfoFromDB(){
        selectDataImpl s = new selectDataImpl();
        for (int i = 0; i < numOfRound; i++){
            p = s.selectPlayerData("player"+GameName+String.valueOf(i));
            pList.add(p);
        }
    }
    public void updateRoundCard(String tableName, List<cards> cardsList){
        updateDataImpl u = new updateDataImpl();
        u.updateRoundCardsData(tableName,cardsList);
    }
    public void updatePlayerCard(String tableName, player p){
        updateDataImpl u = new updateDataImpl();
        u.updatePlayerData(tableName,p);
    }
    public String num2Suit(int i){
        if (i==0){
            return "club";
        }
        else if (i==1){
            return "diamond";
        }
        else if(i==2){
            return "heart";
        }
        else if (i==3){
            return "spades";
        }
        else {
            return "default";
        }
    }
    public String num2Value(int i){
        if (i>=0&&i<=8){
            return String.valueOf(i+2);
        }
        else if (i==9){
            return "J";
        }
        else if (i==10){
            return "Q";}
        else if (i==11){
            return "K";}
        else if (i==12){
            return "A";}
        else{
            return "default";
        }
    }

}
