package com.wst.DBCases.implement;

import com.wst.DBCases.deleteData;
import com.wst.SQLiteJDBC;

import java.sql.Connection;
import java.sql.Statement;

public class deleteDataImpl implements deleteData {
    @Override
    public void deleteGame(String gameName) {
        SQLiteJDBC conn = new SQLiteJDBC();
        Connection c = conn.connect();
        Statement stmt = null;
        selectDataImpl s = new selectDataImpl();
        int cnt = s.countRounds(gameName);
        try {
            stmt = c.createStatement();
            String sql = "DELETE from game where name=\""+gameName+"\";";

            for (int i = 0; i < cnt; i++){
                String sql2 = "DROP TABLE player"+ gameName +String.valueOf(i);
                String sql3 = "DROP TABLE round"+ gameName +String.valueOf(i);
                System.out.println(sql2+"\n"+sql3);
                stmt.executeUpdate(sql2);
                stmt.executeUpdate(sql3);
            }
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

        }
        System.out.println("Table created successfully");
    }
}
