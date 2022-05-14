package com.wst.DBCases.implement;

import com.wst.DBCases.updateData;
import com.wst.SQLiteJDBC;
import com.wst.cards;
import com.wst.helper;
import com.wst.player;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class updateDataImpl implements updateData {
    @Override
    public void updateRoundData(String tableName, Integer chip, String turn) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE  round" + tableName + " SET " + " chip = \"" + String.valueOf(chip)+"\" WHERE "+ turn + "=1";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    @Override
    public void updateRoundCardsData(String tableName, List<cards> cardsList) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE  round" + tableName + " SET "+

                    "cardvalue1 = "+ cardsList.get(0).getValue()+", "+
                    "cardsuit1 = \""+ cardsList.get(0).getSuit()+"\", "+
                    "cardvalue2 = \""+ cardsList.get(1).getValue()+"\", "+
                    "cardsuit2 = \""+ cardsList.get(1).getSuit()+"\", "+
                    "cardvalue3 = \""+ cardsList.get(2).getValue()+"\", "+
                    "cardsuit3 = \""+ cardsList.get(2).getSuit()+"\", "+
                    "cardvalue4 = \""+ cardsList.get(3).getValue()+"\", "+
                    "cardsuit4 = \""+ cardsList.get(3).getSuit()+"\", "+
                    "cardvalue5 = \""+ cardsList.get(4).getValue()+"\", "+
                    "cardsuit5 = \""+ cardsList.get(4).getSuit()+"\""+
                    " WHERE isriver" + "=1";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    @Override
    public void updatePlayerData(String tableName, player p) {
        String holeChip = new String();
        String flopChip = new String();
        String turnChip = new String();
        String riverChip = new String();
        String holeAction = new String();
        String flopAction = new String();
        String turnAction = new String();
        String riverAction = new String();
        helper h = new helper();
        holeChip=h.StringHelper(p.getChipTrans(),holeChip,0);
        flopChip=h.StringHelper(p.getChipTrans(),flopChip,1);
        turnChip=h.StringHelper(p.getChipTrans(),turnChip,2);
        riverChip=h.StringHelper(p.getChipTrans(),riverChip,3);
        holeAction=h.StringHelper(p.getActionTrans(),holeAction,0);
        flopAction=h.StringHelper(p.getActionTrans(),flopAction,1);
        turnAction=h.StringHelper(p.getActionTrans(),turnAction,2);
        riverAction=h.StringHelper(p.getActionTrans(),riverAction,3);
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "UPDATE  player" + tableName + " set "+
            "chip = "+ p.getChip()+", "+
            "isBlind = \""+ p.isBlind()+"\", "+
            "holeChip = \""+ holeChip+"\", "+
            "flopChip = \""+ flopChip+"\", "+
            "turnChip = \""+ turnChip+"\", "+
            "riverChip = \""+ riverChip+"\", "+
            "holeAction = \""+ holeAction+"\", "+
            "flopAction = \""+ flopAction+"\", "+
            "turnAction = \""+ turnAction+"\", "+
            "riverAction = \""+ riverAction+"\", "+
            "value1 = \""+ p.getCardsList().get(0).getValue()+"\", "+
            "suit1 = \""+ p.getCardsList().get(0).getSuit()+"\", "+
            "value2 = \""+ p.getCardsList().get(1).getValue()+"\", "+
            "suit2 = \""+ p.getCardsList().get(1).getSuit()+"\" "+
             "WHERE name=\""+ p.getName()+"\"";
             stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
