/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import gameoflife.control.Algorithm;

/**
 *
 * @author toshiba
 */
public class MainFrame extends JFrame {
   private Board board;
   private int boardSize = 500;
   private JMenuBar menubar;
   private Thread golthread;
    
   public MainFrame() {
    super("Game Of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BorderLayout bord = new BorderLayout();
    setLayout(bord);
    addOptions();
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
   }

   private void addMenu(){
        menubar = new JMenuBar();
        JMenu ops = new JMenu("Options");
        menubar.add(ops);
   }
   
   private void addOptions(){
        board = new Board(boardSize);
        JButton startbtn = new JButton("start");
        JButton stopbtn = new JButton("stop");
        startbtn.addActionListener(e->{
            board.startGame();
        });
        stopbtn.addActionListener(e->{
            board.stopGame();
        });
        JToolBar bar = new JToolBar();
        bar.add(startbtn);
        bar.add(stopbtn);
        add("North", bar);
        add("Center", board);
   }
}
