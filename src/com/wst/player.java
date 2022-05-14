package com.wst;

import com.wst.DBCases.implement.selectDataImpl;

import java.util.ArrayList;
import java.util.List;

public class player {
    private int id = 0;
    private String name = "";
    private int chip = 0;
    private boolean isBlind = false;
    private List<cards> cardsList = new ArrayList<>();
    private List<List<String>> chipTrans = new ArrayList<>();
    private List<List<String>>actionTrans = new ArrayList<>();

    public List<cards> getCardsList() {
        return cardsList;
    }

    public void setCardsList(cards c, int pos) {
        this.cardsList.get(pos).setSuit(c.getSuit());
        this.cardsList.get(pos).setValue(c.getValue());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChip() {
        return chip;
    }

    public void setChip(int chip) {
        this.chip = chip;
    }

    public void initList(){
        for (int i = 0; i < 4; i++){
            List<String> temp1 = new ArrayList<>();
            List<String> temp2 = new ArrayList<>();
            this.chipTrans.add(temp1);
            this.actionTrans.add(temp2);
        }
        for (int i = 0; i < 2; i++){
            cards temp1 = new cards();
            temp1.setSuit(4);
            temp1.setValue(13);
            this.cardsList.add(temp1);
        }


    }
    public boolean isBlind() {
        return isBlind;
    }

    public void setBlind(boolean blind) {
        isBlind = blind;
    }

    public List<List<String>> getChipTrans(String tablename, String playerName) {
        selectDataImpl s = new selectDataImpl();
        List<player> p = new ArrayList<>();
        p = s.selectPlayerData(tablename);
        for (int i = 0; i < p.size(); i++){
            if (p.get(i).getName() == playerName){
                return p.get(i).chipTrans;
            }
        }
        return this.getChipTrans();
    }

    public void setChipTrans(String holeChip, String flopChip, String turnChip, String riverChip) {
        setListHelper(holeChip,true,0);
        setListHelper(flopChip,true,1);
        setListHelper(turnChip,true,2);
        setListHelper(riverChip,true,3);
    }

    public List<List<String>> getActionTrans(String tablename, String playerName) {
        selectDataImpl s = new selectDataImpl();
        List<player> p = new ArrayList<>();
        p = s.selectPlayerData(tablename);
        for (int i = 0; i < p.size(); i++){
            if (p.get(i).getName() == playerName){
                return p.get(i).actionTrans;
            }
        }
        return this.getActionTrans();
    }

    public void setActionTrans(String holeAction, String flopAction, String turnAction, String riverAction) {
        setListHelper(holeAction,false,0);
        setListHelper(flopAction,false,1);
        setListHelper(turnAction,false,2);
        setListHelper(riverAction,false,3);
    }

    public void setListHelper(String sth, boolean s, int pos){
        List<String> temp = new ArrayList<>();
        for (String retval: sth.split(";")){
            temp.add(retval);
        }
        if (s){
            this.chipTrans.get(pos).clear();
            //this.chipTrans.get(pos).add(temp.get(temp.size()-1));
            for (String t: temp){
                if (t.length()!=0){
                    this.chipTrans.get(pos).add(t);
                }
            }
        }
        else {
            this.actionTrans.get(pos).clear();
            //this.actionTrans.get(pos).add(temp.get(temp.size()-1));
            for (String t: temp){
                if (t.length()!=0) {
                    this.actionTrans.get(pos).add(t);
                }
            }
        }
    }


    public String ListToString(List<String> temp){
        String res = new String();
        for (int i = 0; i < temp.size(); i++){
            res += temp.get(i);
            if (res.length()!=0){
                res += ";";
            }
        }
        return res;
    }

    public List<List<String>> getChipTrans() {
        return chipTrans;
    }

    public void setChipTrans(List<List<String>> chipTrans) {
        this.chipTrans = chipTrans;
    }

    public List<List<String>> getActionTrans() {
        return actionTrans;
    }

    public void setActionTrans(List<List<String>> actionTrans) {
        this.actionTrans = actionTrans;
    }
}
