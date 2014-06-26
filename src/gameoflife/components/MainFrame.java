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

/**
 *
 * @author toshiba
 */
public class MainFrame extends JFrame {
   private Board board;
   private int boardSize = 500;
   private JMenuBar menubar;
    
   public MainFrame() {
    super("Game Of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //setResizable(false);
    menubar = new JMenuBar();
    //getContentPane().add(menubar);
    //setJMenuBar(menubar);
    JMenu ops = new JMenu("Options");
    menubar.add(ops);
    
    
    BorderLayout bord = new BorderLayout();
    setLayout(bord);
    board = new Board(boardSize);
    JButton button = new JButton("start");
    button.addActionListener(e->{board.createnextGen();});
    JToolBar bar = new JToolBar();
    bar.add(button);
    add("North", bar);
    add("Center", board);
    //getContentPane().add(board);
    //add(board);
    fill();
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
   }
   
   private void fill(){
    int sides = boardSize/Cell.sideValue;
    for (int i = 0; i < sides; i++) {
        for (int j = 0; j < sides; j++) {
            board.addCell(new Cell(i, j));
        }
    }
   }

}
