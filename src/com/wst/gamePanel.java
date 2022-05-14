package com.wst;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gamePanel extends JFrame implements ActionListener
{

    private JButton newgame = new JButton("new game");
    private JButton viewReplay = new JButton("replays");
    private JLabel title = new JLabel("Poker game replay system");
    public gamePanel()
    {
        setLayout(null);
        this.setTitle("Poker game replay system");    //set title
        this.setSize(1920,1080);    //set size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //set closeable
        newgame.addActionListener(this);
        viewReplay.addActionListener(this);
        Font fnt1 = new Font("Serief", Font.ITALIC + Font.BOLD, 60);
        Font fnt2 = new Font("Serief", Font.ITALIC + Font.BOLD, 40);
        newgame.setFont(fnt2);
        viewReplay.setFont(fnt2);
        title.setFont(fnt1);
        title.setBounds(600,0,1000,200);
        newgame.setBounds(850,200,250,200);
        viewReplay.setBounds(850,400,250,200);
        this.add(title);
        this.add(newgame);
        this.add(viewReplay);
        setVisible(true);    //set visiable
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newgame) {

            this.dispose();

            new createNewGame();
        }
        else if (e.getSource() == viewReplay){
            this.dispose();

            new viewReplay();
        }
    }
    public static void main(String[] args) {
        gamePanel gamePanel=new gamePanel();
    }

}

