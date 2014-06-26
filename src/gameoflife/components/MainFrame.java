/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

import javax.swing.JFrame;

/**
 *
 * @author toshiba
 */
public class MainFrame extends JFrame {
   private Board board;
   private int boardSize = 500;
    
   public MainFrame() {
    super("Game Of Life");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    board = new Board(boardSize);
    getContentPane().add(board);
    fill();
    pack();
    setLocationRelativeTo(null);
    setVisible(true);
   }
   
   private void fill(){
    int sides = boardSize/Cell.sideValue;
    for (int i = 0; i < sides; i++) {
        for (int j = 0; j < sides; j++) {
            board.addCell(new Cell(j*10, i*10));
        }
    }
   }

}
