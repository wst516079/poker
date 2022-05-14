package com.wst.DBCases.implement;

import com.wst.DBCases.addData;
import com.wst.SQLiteJDBC;
import com.wst.helper;
import com.wst.player;

import java.sql.Connection;
import java.sql.Statement;

public class addDataImpl implements addData {
    @Override
    public String addGameTable(String name, String playerTableId, String roundTableId) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO game (NAME,playerTableId,roundTableId) " +
                    "VALUES ('" + name +
                    "', '"+ playerTableId +
                    "', '"+ roundTableId +
                    "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return e.getMessage();
        }
        return "Records created successfully";
    }

    @Override
    public void addPlayerTable(String tableName, player p) {
        String holeChip = new String();
        String flopChip = new String();
        String turnChip = new String();
        String riverChip = new String();
        String holeAction = new String();
        String flopAction = new String();
        String turnAction = new String();
        String riverAction = new String();
        helper h = new helper();
        h.StringHelper(p.getChipTrans(),holeChip,0);
        h.StringHelper(p.getChipTrans(),flopChip,1);
        h.StringHelper(p.getChipTrans(),turnChip,2);
        h.StringHelper(p.getChipTrans(),riverChip,3);
        h.StringHelper(p.getActionTrans(),holeAction,0);
        h.StringHelper(p.getActionTrans(),flopAction,1);
        h.StringHelper(p.getActionTrans(),turnAction,2);
        h.StringHelper(p.getActionTrans(),riverAction,3);
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "INSERT INTO player" + tableName + "(name,chip,isBlind,holeChip,flopChip,turnChip,riverChip,holeAction,flopAction,turnAction,riverAction,value1,suit1,value2,suit2) " +
                    "VALUES ('" + p.getName() +
                    "', '"+ p.getChip() +
                    "', '"+ p.isBlind() +
                    "', '"+ holeChip +
                    "', '"+ flopChip +
                    "', '"+ turnChip +
                    "', '"+ riverChip +
                    "', '"+ holeAction +
                    "', '"+ flopAction +
                    "', '"+ turnAction +
                    "', '"+ riverAction +
                    "', '"+ p.getCardsList().get(0).getValue() +
                    "', '"+ p.getCardsList().get(0).getSuit() +
                    "', '"+ p.getCardsList().get(1).getValue() +
                    "', '"+ p.getCardsList().get(1).getSuit()+
                    "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    @Override
    public void addRoundTable(String tableName, String chip) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO round" + tableName + "(chip) " +
                    "VALUES ('"+ chip +
                    "', '" +
                    "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    @Override
    public void addCardTable(String tableName, Integer value, String suit) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO card" + tableName + "(value,suit) " +
                    "VALUES ('" + value +
                    "', '"+ suit +
                    "')";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }


}
