/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

import gameoflife.components.Cell.statusTypes;
import static gameoflife.components.Cell.statusTypes;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import gameoflife.control.Algorithm;
/**
 *
 * @author toshiba
 */
public class Board extends JPanel implements MouseListener, Runnable{
    private Algorithm al;
    private int boardSize;
    private Thread golthread;
    
    public Board(int boardSize){
       this.al =new Algorithm(boardSize);
       this.boardSize = boardSize;
       addMouseListener(this);
       fill();
    }
    
    private void fill(){
        int sides = boardSize/Cell.sideValue;
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                addCell(new Cell(i, j));
            }
        }
    }
    
    public int getAliveCells(){
        return al.getAliveCells();
    }
    
    public void createnextGen(){
        al.createnextGen();
        repaint();
    }
   
    public void addCell(Cell cell) {
        al.addCell(cell);
    }
   
    private void selectCell(MouseEvent e) {
        int x = e.getPoint().x/Cell.sideValue;
        int y = e.getPoint().y/Cell.sideValue;
        Cell cell = al.getCells().get(Cell.genKey(x, y));
        if(cell != null){
            toggleCell(cell);
        }
        repaint();
    }
   
    private void toggleCell(Cell cell){
        al.toggleCell(cell);
    }
    
    public void startGame(){
        golthread  = new Thread(this);
        golthread.start();
    }
    
    public void stopGame(){
        golthread.interrupt();
    }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(boardSize, boardSize);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      Iterator i = al.getCells().entrySet().iterator();
      while(i.hasNext()) {
         Map.Entry<String, Cell> pairs = (Map.Entry)i.next();
         Cell cell = pairs.getValue();
         Rectangle rect = new Rectangle(cell.getX()*Cell.sideValue, cell.getY()*Cell.sideValue, Cell.sideValue, Cell.sideValue);
         g2.draw(rect);
         if(cell.getStatus() == statusTypes.ALIVE){
             g.fillRect(cell.getX()*Cell.sideValue, cell.getY()*Cell.sideValue, Cell.sideValue, Cell.sideValue);
         }
      }
   }

    @Override
    public void mouseClicked(MouseEvent e) {
        selectCell(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
         
    }

    @Override
    public void mouseReleased(MouseEvent e) {
         
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void run() {
        if(al.getAliveCells() > 0){
            createnextGen();
            try {
                Thread.sleep(1000);
                run();
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, "execution stoped");
            }
        }
        else{
            stopGame();
            JOptionPane.showMessageDialog(this, "No more cells alive");
        }
    }

}