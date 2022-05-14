package com.wst.DBCases.implement;

import com.wst.DBCases.createTable;
import com.wst.SQLiteJDBC;

import java.sql.Connection;
import java.sql.Statement;

public class createTableImpl implements createTable {
    @Override
    public void createGameTable() {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            stmt = c.createStatement();

            String sql = "CREATE TABLE \"game\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"name\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"playerTableId\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"roundTableId\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Table created successfully");

    }

    @Override
    public void createPlayerTable(String playerTableId) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            stmt = c.createStatement();

            String sql = "CREATE TABLE \"player"+playerTableId+"\" (\n" +
                    "\t\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"name\"\tTEXT NOT NULL UNIQUE,\n" +
                    "\t\"chip\"\tTEXT,\n" +
                    "\t\"isBlind\"\tINTEGER NOT NULL,\n" +
                    "\t\"holeChip\"\tTEXT,\n" +
                    "\t\"flopChip\"\tTEXT,\n" +
                    "\t\"turnChip\"\tTEXT,\n" +
                    "\t\"riverChip\"\tTEXT,\n" +
                    "\t\"holeAction\"\tTEXT,\n" +
                    "\t\"flopAction\"\tTEXT,\n" +
                    "\t\"turnAction\"\tTEXT,\n" +
                    "\t\"riverAction\"\tTEXT,\n" +
                    "\t\"value1\"\tINTEGER,\n" +
                    "\t\"suit1\"\tINTEGER,\n" +
                    "\t\"value2\"\tINTEGER,\n" +
                    "\t\"suit2\"\tINTEGER,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Table created successfully");
    }

    @Override
    public void createRoundTable(String roundTableId) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            stmt = c.createStatement();

            String sql = "CREATE TABLE \"round"+roundTableId+"\" (\n" +
                    "\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"chip\"\tTEXT NOT NULL,\n" +
                    "\t\"ishole\"\tTEXT NOT NULL,\n" +
                    "\t\"isflop\"\tTEXT NOT NULL,\n" +
                    "\t\"isturn\"\tTEXT NOT NULL,\n" +
                    "\t\"isriver\"\tTEXT NOT NULL,\n" +
                    "\t\"cardvalue1\"\tTEXT,\n" +
                    "\t\"cardsuit1\"\tTEXT,\n" +
                    "\t\"cardvalue2\"\tTEXT,\n" +
                    "\t\"cardsuit2\"\tTEXT,\n" +
                    "\t\"cardvalue3\"\tTEXT,\n" +
                    "\t\"cardsuit3\"\tTEXT,\n" +
                    "\t\"cardvalue4\"\tTEXT,\n" +
                    "\t\"cardsuit4\"\tTEXT,\n" +
                    "\t\"cardvalue5\"\tTEXT,\n" +
                    "\t\"cardsuit5\"\tTEXT,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            String sql2 = "INSERT INTO round"+roundTableId+" (chip,ishole,isflop,isturn,isriver) " +
                    "VALUES (0,\"1\",0,0,0)";
            String sql3 = "INSERT INTO round"+roundTableId+" (chip,ishole,isflop,isturn,isriver) " +
                    "VALUES (0,0,\"1\",0,0)";
            String sql4 = "INSERT INTO round"+roundTableId+" (chip,ishole,isflop,isturn,isriver) " +
                    "VALUES (0,0,0,\"1\",0)";
            String sql5 = "INSERT INTO round"+roundTableId+" (chip,ishole,isflop,isturn,isriver) " +
                    "VALUES (0,0,0,0,\"1\")";
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);
            stmt.executeUpdate(sql5);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Table created successfully");
    }

    @Override
    public void createCardTable(String cardTableId) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        try {
            stmt = c.createStatement();

            String sql = "CREATE TABLE \"card"+cardTableId+"\" (\n" +
                    "\"id\"\tINTEGER NOT NULL UNIQUE,\n" +
                    "\t\"value\"\tINTEGER NOT NULL,\n" +
                    "\t\"suit\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                    ");";
            String sql0 = "INSERT INTO card"+cardTableId+"(id,value,suit)" + "VALUES (0,4,\"13\")";
            String sql1 = "INSERT INTO card"+cardTableId+"(id,value,suit)" + "VALUES (1,4,\"13\")";
            String sql2 = "INSERT INTO card"+cardTableId+"(id,value,suit)" + "VALUES (2,4,\"13\")";
            String sql3 = "INSERT INTO card"+cardTableId+"(id,value,suit)" + "VALUES (3,4,\"13\")";
            String sql4 = "INSERT INTO card"+cardTableId+"(id,value,suit)" + "VALUES (4,4,\"13\")";
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql0);
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);
            stmt.executeUpdate(sql4);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Table created successfully");
    }
}
