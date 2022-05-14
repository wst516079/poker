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

public class createNewRecord extends JFrame implements ActionListener {
    private JPanel jp = new JPanel();
    private JPanel jp2 = new JPanel();

    private List<JTextField> playerName = new ArrayList<>();
    private List<JTextField> playerChip = new ArrayList<>();
    private List<JTextField> playerChipChange = new ArrayList<>();
    private List<JTextField> playerIsBlind = new ArrayList<>();
    private List<JTextField> playerTurnBet = new ArrayList<>();
    private List<JTextField> playerAction = new ArrayList<>();
    private List<JButton> playerIsCheck = new ArrayList<>();
    private List<JButton> playerIsFold = new ArrayList<>();
    private List<JButton> playerIsCall = new ArrayList<>();
    private List<JButton> playerIsRaise = new ArrayList<>();
    private List<JButton> roundWinner = new ArrayList<>();
    private List<JLabel> playerCardValue1 = new ArrayList<>();
    private List<JLabel> playerCardSuit1 = new ArrayList<>();
    private List<JComboBox> playerCardValueCombo1 = new ArrayList<>();
    private List<JComboBox> playerCardSuitCombo1 = new ArrayList<>();
    private List<JLabel> playerCardValue2 = new ArrayList<>();
    private List<JLabel> playerCardSuit2 = new ArrayList<>();
    private List<JComboBox> playerCardValueCombo2 = new ArrayList<>();
    private List<JComboBox> playerCardSuitCombo2 = new ArrayList<>();
    private List<JComboBox> cardValue = new ArrayList<>();
    private List<JComboBox> cardSuit = new ArrayList<>();
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

    public createNewRecord(int numofplayer, int numofround, String gameName) {
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
            JTextField tempTurnBetT = new JTextField();
            tempTurnBetT.setBounds(600,20+i*20,100,20);
            playerTurnBet.add(tempTurnBetT);
            JButton tempWinnerB = new JButton("round winner");
            tempWinnerB.setBounds(700,20+i*20,100,20);
            tempWinnerB.addActionListener(this);
            roundWinner.add(tempWinnerB);
            JTextField tempAction = new JTextField();
            tempAction.setBounds(800,20+i*20,200,20);
            tempAction.setEditable(false);
            playerAction.add(tempAction);
            JButton tempCheckB = new JButton("check");
            tempCheckB.setBounds(1000,20+i*20,100,20);
            tempCheckB.addActionListener(this);
            playerIsCheck.add(tempCheckB);
            JButton tempFoldB = new JButton("fold");
            tempFoldB.setBounds(1100,20+i*20,100,20);
            tempFoldB.addActionListener(this);
            playerIsFold.add(tempFoldB);
            JButton tempCallB = new JButton("call");
            tempCallB.setBounds(1200,20+i*20,100,20);
            tempCallB.addActionListener(this);
            playerIsCall.add(tempCallB);
            JButton tempRaiseB = new JButton("raise");
            tempRaiseB.setBounds(1300,20+i*20,100,20);
            tempRaiseB.addActionListener(this);
            playerIsRaise.add(tempRaiseB);

            JComboBox tempJ = new JComboBox<>();
            JComboBox tempJ2 = new JComboBox<>();
            tempJ.setBounds(1400,20+i*20,60,20);
            tempJ2.setBounds(1520,20+i*20,60,20);
            for (int v = 2; v <= 10; v++){
                tempJ.addItem(String.valueOf(v));
            }
            tempJ.addItem("J");
            tempJ.addItem("Q");
            tempJ.addItem("K");
            tempJ.addItem("A");
            int finalI = i;
            tempJ.addItemListener(new ItemListener() {// 添加选项事件监听器
                public void itemStateChanged(ItemEvent e) {
                    int stateChange = e.getStateChange();// 获得事件类型
                    String item = e.getItem().toString();// 获得触发此次事件的选项
                    if (stateChange == ItemEvent.SELECTED) {// 查看是否由选中选项触发
                        playerCardValue1.get(finalI).setIcon(new ImageIcon("src/image/"+item+"s.png"));
                        cards tempCard = new cards();
                        int intValue = 13;

                        if (item=="J"){
                            intValue = 9;
                        }
                        else if (item=="Q"){
                            intValue = 10;
                        }
                        else if (item=="K"){
                            intValue = 11;
                        }
                        else if (item=="A"){
                            intValue = 12;
                        }
                        else{
                            intValue = Integer.parseInt(item)-2;
                        }
                        tempCard.setValue(intValue);
                        tempCard.setSuit(playerCardSuitCombo1.get(finalI).getSelectedIndex());
                        pList.get(roundNum).get(finalI).setCardsList(tempCard,0);
                        updatePlayerCard(GameName+String.valueOf(roundNum),pList.get(roundNum).get(finalI));
                        // 查看是否由取消选中选项触发
                    } else if (stateChange == ItemEvent.DESELECTED) {
                    } else {// 由其他原因触发
                    }
                }
            });
            playerCardValueCombo1.add(tempJ);
            tempJ2.addItem("club");
            tempJ2.addItem("diamond");
            tempJ2.addItem("heart");
            tempJ2.addItem("spades");
            tempJ2.addItemListener(new ItemListener() {// 添加选项事件监听器
                public void itemStateChanged(ItemEvent e) {
                    int stateChange = e.getStateChange();// 获得事件类型
                    String item = e.getItem().toString();// 获得触发此次事件的选项
                    if (stateChange == ItemEvent.SELECTED) {// 查看是否由选中选项触发
                        playerCardSuit1.get(finalI).setIcon(new ImageIcon("src/image/"+item+"s.png"));
                        cards tempCard = new cards();

                        int intSuit = 4;
                        if (item=="club"){
                            intSuit = 0;
                        }
                        else if (item=="diamond"){
                            intSuit = 1;
                        }
                        else if (item=="heart"){
                            intSuit = 2;
                        }
                        else if (item=="spades"){
                            intSuit = 3;
                        }
                        tempCard.setValue(playerCardValueCombo1.get(finalI).getSelectedIndex());
                        tempCard.setSuit(intSuit);
                        pList.get(roundNum).get(finalI).setCardsList(tempCard,0);
                        updatePlayerCard(GameName+String.valueOf(roundNum),pList.get(roundNum).get(finalI));

                        // 查看是否由取消选中选项触发
                    } else if (stateChange == ItemEvent.DESELECTED) {
                    } else {// 由其他原因触发
                    }
                }
            });
            playerCardSuitCombo1.add(tempJ2);
            JLabel tempL = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempL.setBounds(1460,20+i*20,60,20);
            playerCardValue1.add(tempL);
            JLabel tempLV = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempLV.setBounds(1580,20+i*20,60,20);
            playerCardSuit1.add(tempLV);



            JComboBox tempJL = new JComboBox<>();
            JComboBox tempJL2 = new JComboBox<>();
            tempJL.setBounds(1640,20+i*20,60,20);
            tempJL2.setBounds(1760,20+i*20,60,20);
            for (int v = 2; v <= 10; v++){
                tempJL.addItem(String.valueOf(v));
            }
            tempJL.addItem("J");
            tempJL.addItem("Q");
            tempJL.addItem("K");
            tempJL.addItem("A");
            tempJL.addItemListener(new ItemListener() {// 添加选项事件监听器
                public void itemStateChanged(ItemEvent e) {
                    int stateChange = e.getStateChange();// 获得事件类型
                    String item = e.getItem().toString();// 获得触发此次事件的选项
                    if (stateChange == ItemEvent.SELECTED) {// 查看是否由选中选项触发
                        playerCardValue2.get(finalI).setIcon(new ImageIcon("src/image/"+item+"s.png"));
                        cards tempCard = new cards();
                        int intValue = 13;

                        if (item=="J"){
                            intValue = 9;
                        }
                        else if (item=="Q"){
                            intValue = 10;
                        }
                        else if (item=="K"){
                            intValue = 11;
                        }
                        else if (item=="A"){
                            intValue = 12;
                        }
                        else{
                            intValue = Integer.parseInt(item)-2;
                        }
                        tempCard.setValue(intValue);
                        tempCard.setSuit(playerCardSuitCombo2.get(finalI).getSelectedIndex());
                        pList.get(roundNum).get(finalI).setCardsList(tempCard,1);
                        updatePlayerCard(GameName+String.valueOf(roundNum),pList.get(roundNum).get(finalI));

                        // 查看是否由取消选中选项触发
                    } else if (stateChange == ItemEvent.DESELECTED) {
                    } else {// 由其他原因触发
                    }
                }
            });
            playerCardValueCombo2.add(tempJL);
            tempJL2.addItem("club");
            tempJL2.addItem("diamond");
            tempJL2.addItem("heart");
            tempJL2.addItem("spades");
            tempJL2.addItemListener(new ItemListener() {// 添加选项事件监听器
                public void itemStateChanged(ItemEvent e) {
                    int stateChange = e.getStateChange();// 获得事件类型
                    String item = e.getItem().toString();// 获得触发此次事件的选项
                    if (stateChange == ItemEvent.SELECTED) {// 查看是否由选中选项触发
                        playerCardSuit2.get(finalI).setIcon(new ImageIcon("src/image/"+item+"s.png"));
                        cards tempCard = new cards();

                        int intSuit = 4;
                        if (item=="club"){
                            intSuit = 0;
                        }
                        else if (item=="diamond"){
                            intSuit = 1;
                        }
                        else if (item=="heart"){
                            intSuit = 2;
                        }
                        else if (item=="spades"){
                            intSuit = 3;
                        }
                        tempCard.setValue(playerCardValueCombo2.get(finalI).getSelectedIndex());
                        tempCard.setSuit(intSuit);
                        pList.get(roundNum).get(finalI).setCardsList(tempCard,1);
                        updatePlayerCard(GameName+String.valueOf(roundNum),pList.get(roundNum).get(finalI));

                        // 查看是否由取消选中选项触发
                    } else if (stateChange == ItemEvent.DESELECTED) {
                    } else {// 由其他原因触发
                    }
                }
            });
            playerCardSuitCombo2.add(tempJL2);
            JLabel tempL2 = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempL2.setBounds(1700,20+i*20,60,20);
            playerCardValue2.add(tempL2);
            JLabel tempLV2 = new JLabel(new ImageIcon("src/image/defaults.png"));
            tempLV2.setBounds(1820,20+i*20,60,20);
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
        JLabel turnBetL = new JLabel("player's bet amount");
        turnBetL.setBounds(600,0,200,20);
        jp.add(turnBetL);
        JLabel actionL = new JLabel("player's bet action");
        actionL.setBounds(800,0,200,20);
        jp.add(actionL);
        JLabel bettingB = new JLabel("perform a bet");
        bettingB.setBounds(1000,0,800,20);
        jp.add(bettingB);
        JLabel cardCombo = new JLabel("card selector");
        cardCombo.setBounds(1400,0,800,20);
        jp.add(cardCombo);
        playerIsBlind.get(0).setText("dealer");
        playerIsBlind.get(1).setText("small blind");
        playerIsBlind.get(2).setText("big blind");
        pList.get(roundNum).get(blindPos).setBlind(true);
        for (int j = 0; j < numofplayer; j++){
            jp.add(playerName.get(j));
            jp.add(playerChip.get(j));
            jp.add(playerChipChange.get(j));
            jp.add(playerIsBlind.get(j));
            jp.add(playerTurnBet.get(j));
            jp.add(roundWinner.get(j));
            jp.add(playerAction.get(j));
            jp.add(playerIsCheck.get(j));
            jp.add(playerIsFold.get(j));
            jp.add(playerIsCall.get(j));
            jp.add(playerIsRaise.get(j));
            jp.add(playerCardSuitCombo1.get(j));
            jp.add(playerCardValueCombo1.get(j));
            jp.add(playerCardSuit1.get(j));
            jp.add(playerCardValue1.get(j));
            jp.add(playerCardSuitCombo2.get(j));
            jp.add(playerCardValueCombo2.get(j));
            jp.add(playerCardSuit2.get(j));
            jp.add(playerCardValue2.get(j));
        }
        for (int j = 0; j < 5; j++){
            JComboBox tempJ = new JComboBox<>();
            JComboBox tempJ2 = new JComboBox<>();
            JLabel tempI = new JLabel(new ImageIcon("src/image/defaultSuit.png"));
            JLabel tempS = new JLabel(new ImageIcon("src/image/defaultValue.png"));
            tempJ.setBounds(j*384,20,384,20);
            tempI.setBounds(j*384,110,384,384);
            tempS.setBounds(j*384,60,50,50);
            cardImage.add(tempI);
            cardImageS.add(tempS);
            for (int v = 2; v <= 10; v++){
                tempJ.addItem(String.valueOf(v));
            }
            tempJ.addItem("J");
            tempJ.addItem("Q");
            tempJ.addItem("K");
            tempJ.addItem("A");
            int finalJ = j;
            tempJ.addItemListener(new ItemListener() {// 添加选项事件监听器
                public void itemStateChanged(ItemEvent e) {
                    int stateChange = e.getStateChange();// 获得事件类型
                    String item = e.getItem().toString();// 获得触发此次事件的选项
                    if (stateChange == ItemEvent.SELECTED) {// 查看是否由选中选项触发
                        cardImageS.get(finalJ).setIcon(new ImageIcon("src/image/"+item+".png"));
                        cards tempCard = new cards();
                        int intValue = 13;

                        if (item=="J"){
                            intValue = 9;
                        }
                        else if (item=="Q"){
                            intValue = 10;
                        }
                        else if (item=="K"){
                            intValue = 11;
                        }
                        else if (item=="A"){
                            intValue = 12;
                        }
                        else{
                            intValue = Integer.parseInt(item)-2;
                        }
                        tempCard.setValue(intValue);
                        tempCard.setSuit(cardSuit.get(finalJ).getSelectedIndex());
                        r.get(roundNum).setCardsList(tempCard,finalJ);
                        updateRoundCard(GameName+String.valueOf(roundNum),r.get(roundNum).getCardsList());
                        // 查看是否由取消选中选项触发
                    } else if (stateChange == ItemEvent.DESELECTED) {
                    } else {// 由其他原因触发
                    }
                }
            });
            cardValue.add(tempJ);
            tempJ2.setBounds(j*384,40,384,20);
            tempJ2.addItem("club");
            tempJ2.addItem("diamond");
            tempJ2.addItem("heart");
            tempJ2.addItem("spades");
            tempJ2.addItemListener(new ItemListener() {// 添加选项事件监听器
                public void itemStateChanged(ItemEvent e) {
                    int stateChange = e.getStateChange();// 获得事件类型
                    String item = e.getItem().toString();// 获得触发此次事件的选项
                    if (stateChange == ItemEvent.SELECTED) {// 查看是否由选中选项触发
                        cardImage.get(finalJ).setIcon(new ImageIcon("src/image/"+item+".png"));
                        cards tempCard = new cards();

                        int intSuit = 4;
                        if (item=="club"){
                            intSuit = 0;
                        }
                        else if (item=="diamond"){
                            intSuit = 1;
                        }
                        else if (item=="heart"){
                            intSuit = 2;
                        }
                        else if (item=="spades"){
                            intSuit = 3;
                        }
                        tempCard.setValue(cardValue.get(finalJ).getSelectedIndex());
                        tempCard.setSuit(intSuit);
                        r.get(roundNum).setCardsList(tempCard,finalJ);
                        updateRoundCard(GameName+String.valueOf(roundNum),r.get(roundNum).getCardsList());
                        // 查看是否由取消选中选项触发
                    } else if (stateChange == ItemEvent.DESELECTED) {
                    } else {// 由其他原因触发
                    }
                }
            });
            cardSuit.add(tempJ2);
        }
        for (int j = 0; j < 5; j++){
            jp2.add(cardSuit.get(j));
            jp2.add(cardValue.get(j));
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
//                p.get(blindPos).setBlind(false);
//                if (blindPos==numOfPlayer-1){
//                    blindPos = 0;
//                }
//                else{
//                    blindPos++;
//                }
//                p.get(blindPos).setBlind(true);
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
        }
        for (int i = 0; i < numOfPlayer; i++){
            if (e.getSource() == playerIsCheck.get(i)){
                System.out.println("player"+String.valueOf(i)+" check");

                switch (st){
                    case hole:
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0))+"check",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        break;
                    case flop:
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1))+"check",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        break;
                    case turn:
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2))+"check",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        break;
                    case river:
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3))+"check");

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        break;
                }

                u.updatePlayerData(GameName+String.valueOf(roundNum), pList.get(roundNum).get(i));
            }
            else if (e.getSource() == playerIsFold.get(i)){
                System.out.println("player"+String.valueOf(i)+" fold");
                switch (st){
                    case hole:
//                        if (pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).size()!=0){
//                            pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).size()-1)));
//                            playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
//                            turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).size()-1))));
//                            roundPot.setText(turnPot.getText());
//                        }
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0))+"fold",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText())+h.sumHelper(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        break;
                    case flop:
//                        if (pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).size()!=0){
//                            pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).size()-1)));
//                            playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
//                            turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).size()-1))));
//                            roundPot.setText(turnPot.getText());
//                        }
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1))+"fold",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText())+h.sumHelper(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        break;
                    case turn:

//                        if (pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).size()!=0){
//                            pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).size()-1)));
//                            playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
//                            turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).size()-1))));
//                            roundPot.setText(turnPot.getText());
//                        }
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2))+"fold",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText())+h.sumHelper(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        break;
                    case river:
//                        if (pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).size()!=0){
//                            pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).size()-1)));
//                            playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
//                            turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).size()-1))));
//                            roundPot.setText(turnPot.getText());
//                        }
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3))+"fold");

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText())+h.sumHelper(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        break;
                }
                u.updatePlayerData(GameName+String.valueOf(roundNum), pList.get(roundNum).get(i));
            }
            else if (e.getSource() == playerIsCall.get(i)){
                System.out.println("player"+String.valueOf(i)+" call");
                switch (st){
                    case hole:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).size()-1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).size()-1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0))+"call",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        break;
                    case flop:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).size()-1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).size()-1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1))+"call",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        break;
                    case turn:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).size()-1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).size()-1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2))+"call",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        break;
                    case river:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()-Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).size()-1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText())+Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).get(pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).size()-1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3))+"call");

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()),"is"+st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        break;
                }

                u.updatePlayerData(GameName+String.valueOf(roundNum), pList.get(roundNum).get(i));
            }
            else if (e.getSource() == playerIsRaise.get(i)) {
                System.out.println("player" + String.valueOf(i) + " raise");
                switch (st) {
                    case hole:
                        pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip() - Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0).size() - 1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText()) + Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0).size() - 1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)) + "raise",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        break;
                    case flop:
                        pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip() - Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1).size() - 1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText()) + Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1).size() - 1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)) + "raise",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        break;
                    case turn:
                        pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip() - Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2).size() - 1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText()) + Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2).size() - 1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)) + "raise",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)));

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        break;
                    case river:
                        pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3).add(String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip() - Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3).size() - 1)));
                        playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));
                        turnPot.setText(String.valueOf(Integer.parseInt(turnPot.getText()) + Integer.parseInt(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3).get(pList.get(roundNum).get(i).getChipTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3).size() - 1))));
                        roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())+Integer.parseInt(playerTurnBet.get(i).getText())));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)) + "raise");

                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        break;
                }
                u.updatePlayerData(GameName+String.valueOf(roundNum), pList.get(roundNum).get(i));
            }
            else if (e.getSource() == roundWinner.get(i)){
                pList.get(roundNum).get(i).setChip(pList.get(roundNum).get(i).getChip()+Integer.parseInt(playerTurnBet.get(i).getText()));
                turnPot.setText(String.valueOf(0));
                roundPot.setText(String.valueOf(Integer.parseInt(roundPot.getText())-Integer.parseInt(playerTurnBet.get(i).getText())));
                switch (st){
                    case hole:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(0).add("+"+String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)) + "win",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)));
                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(0)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(0)));

                        break;
                    case flop:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(1).add("+"+String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)) + "win",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)));
                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(1)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(1)));

                        break;
                    case turn:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(2).add("+"+String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)) + "win",
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)));
                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(2)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(2)));

                        break;
                    case river:
                        pList.get(roundNum).get(i).getChipTrans("player"+GameName+String.valueOf(roundNum), GameName).get(3).add("+"+String.valueOf(playerTurnBet.get(i).getText()));
                        pList.get(roundNum).get(i).setActionTrans(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(0)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(1)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(2)),
                                pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans("player" + GameName+String.valueOf(roundNum), GameName).get(3)) + "win");
                        u.updateRoundData(GameName+String.valueOf(roundNum), Integer.parseInt(turnPot.getText()), "is" + st);
                        playerAction.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getActionTrans().get(3)));
                        playerChipChange.get(i).setText(pList.get(roundNum).get(i).ListToString(pList.get(roundNum).get(i).getChipTrans().get(3)));

                        break;
                }
                playerChip.get(i).setText(String.valueOf(pList.get(roundNum).get(i).getChip()));
                u.updatePlayerData(GameName+String.valueOf(roundNum), pList.get(roundNum).get(i));
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
        }


    }
    public void main(String[] args) {
        //gamePanel gamePanel=new gamePanel();
        createNewRecord createNewRecord = new createNewRecord(this.numOfPlayer,this.numOfRound,this.GameName);
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


}
