package com.wst.DBCases.implement;

import com.wst.*;
import com.wst.DBCases.selectData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class selectDataImpl implements selectData {
    @Override
    public List<player> selectPlayerData(String DBname) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        List<player> res = new ArrayList<>();
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM "+DBname+";" );
            while ( rs.next() ) {
                player p = new player();
                cards cd = new cards();
                p.initList();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setChip(Integer.parseInt(rs.getString("chip")));
                p.setBlind(rs.getInt("isBlind") != 0);
                cd.setValue(rs.getInt("value1"));
                cd.setSuit(rs.getInt("suit1"));
                p.setCardsList(cd,0);
                cd.setValue(rs.getInt("value2"));
                cd.setSuit(rs.getInt("suit2"));
                p.setCardsList(cd,1);
                p.setChipTrans(rs.getString("holeChip"), rs.getString("flopChip"), rs.getString("turnChip"), rs.getString("riverChip"));
                p.setActionTrans(rs.getString("holeAction"), rs.getString("flopAction"), rs.getString("turnAction"), rs.getString("riverAction"));
                res.add(p);
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return res;
    }

    @Override
    public round selectRoundData(String DBname, String pos) {
        System.out.println(DBname);
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        round r = new round();
        r.initList();
        cards cd = new cards();
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM "+DBname+" WHERE "+pos+" =1" );


            r.setRoundId(rs.getInt("id"));
            r.setChips(Integer.parseInt(rs.getString("chip")));
            r.setIshole(Integer.parseInt(rs.getString("ishole")) != 0);
            r.setIsflop(Integer.parseInt(rs.getString("isflop")) != 0);
            r.setIsturn(Integer.parseInt(rs.getString("isturn")) != 0);
            r.setIsriver(Integer.parseInt(rs.getString("isriver")) != 0);
            //if (pos=="isriver"){
                for (int i = 1; i < 6; i++){
                    cd.setSuit(Integer.parseInt(rs.getString("cardsuit"+String.valueOf(i))==null?"4":rs.getString("cardsuit"+String.valueOf(i))));
                    cd.setValue(Integer.parseInt(rs.getString("cardvalue"+String.valueOf(i))==null?"13":rs.getString("cardvalue"+String.valueOf(i))));
                    r.setCardsList(cd,i-1);
                }
            //}





            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return r;
    }

    @Override
    public List<String> selectGameData() {

        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        List<String> res = new ArrayList<>();
        try {
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM game;" );
            while ( rs.next() ) {
                res.add(rs.getString("name"));
            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Operation done successfully");
        return res;
    }

    @Override
    public int countRounds(String gameName) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        int cnt = 0;
        try {
            stmt = c.createStatement();
            while (true){
                try {
                    String sql1 = "SELECT * FROM round"+gameName+String.valueOf(cnt);
                    stmt.executeUpdate(sql1);
                }
                catch (Exception e){
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    break;
                }
                cnt++;
            }
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return cnt;
    }

    @Override
    public int countPlayers(String gameName) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        int cnt = 0;
        try {
            stmt = c.createStatement();
            while (true){
                try {
                    String sql1 = "SELECT * FROM player"+gameName+String.valueOf(cnt);
                    stmt.executeUpdate(sql1);
                }
                catch (Exception e){
                    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                    break;
                }
                cnt++;
            }
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return cnt;
    }
}
