package com.wst;

import com.wst.DBCases.implement.addDataImpl;
import com.wst.DBCases.implement.createTableImpl;
import com.wst.DBCases.implement.deleteDataImpl;
import com.wst.DBCases.implement.selectDataImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class viewReplay extends JFrame implements ActionListener {
    private List<JButton> replay = new ArrayList<>();
    private List<JButton> delete = new ArrayList<>();
    private List<JTextField> gameName = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private JButton back = new JButton("back");
    public viewReplay() {
        setLayout(null);
        this.setTitle("review game");    //set title
        this.setSize(1920,1080);    //set size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //set closeable
        setVisible(true);    //set visiable
        nameList = selectGameList();
        for(int i = 0; i < nameList.size(); i++){
            JTextField tempJT = new JTextField();
            tempJT.setBounds(0,i*20,200,20);
            tempJT.setText(nameList.get(i));
            tempJT.setEditable(false);
            gameName.add(tempJT);
            JButton tempJB = new JButton("go to replay");
            tempJB.setBounds(200,i*20,200,20);
            tempJB.addActionListener(this);
            replay.add(tempJB);

            JButton tempJBD = new JButton("delete");
            tempJBD.setBounds(400,i*20,200,20);
            tempJBD.addActionListener(this);
            delete.add(tempJBD);
        }
        back.setBounds(0,nameList.size()*20,200,20);
        back.addActionListener(this);

        for (int i = 0; i < nameList.size(); i++){
            this.add(gameName.get(i));
            this.add(replay.get(i));
            this.add(delete.get(i));
        }
        this.add(back);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        SQLiteJDBC conn = new SQLiteJDBC();
        conn.connect();
        deleteDataImpl d = new deleteDataImpl();
        selectDataImpl s = new selectDataImpl();
        for (int i = 0; i < nameList.size(); i++){
            if (e.getSource() == replay.get(i)){
                new replayDetail(s.countPlayers(nameList.get(i)),s.countRounds(nameList.get(i)),nameList.get(i));
            }
            else if (e.getSource() == delete.get(i)){
                d.deleteGame(nameList.get(i));
                new viewReplay();
                this.dispose();
            }
        }
        if (e.getSource()==back){
            new gamePanel();
            this.dispose();
        }
    }
    public static void main(String[] args) {
        viewReplay viewReplay=new viewReplay();
    }
    public List<String> selectGameList(){
        selectDataImpl s = new selectDataImpl();
        return s.selectGameData();
    }
}
