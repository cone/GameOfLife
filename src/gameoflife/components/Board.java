/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

import gameoflife.components.Cell.statusTypes;
import static gameoflife.components.Cell.statusTypes;
import gameoflife.control.Algorithm;
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
import java.util.Observable;

import javax.swing.*;
/**
 *
 * @author toshiba
 */
public class Board extends Observable implements Runnable{
    private Algorithm al;
    private int boardSize;
    private Thread golthread;
    private GameZone gameZone;
    private String message;
    public enum GameState{
        STARTED,
        STOPPED
    }
    private GameState state; 
    
    public Board(int boardSize){
       this.al =new Algorithm(boardSize);
       this.boardSize = boardSize;
       gameZone = new GameZone();
       state = GameState.STOPPED;
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
    
    public boolean createnextGen(){
        boolean hasMoreMoves = al.createnextGen();
        gameZone.repaint();
        return hasMoreMoves;
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
        gameZone.repaint();
    }
   
    private void toggleCell(Cell cell){
        al.toggleCell(cell);
    }
    
    public void startGame(){
        state = GameState.STARTED;
        golthread  = new Thread(this);
        golthread.start();
        changed("Game started!");
    }
    
    public void stopGame(){
        doStop();
        changed("Game stopped!");
    }
    
    public void stopGame(String message){
        doStop();
        changed(message);
    }
    
    private void doStop(){
        state = GameState.STOPPED;
        golthread.interrupt();
    }

    public JPanel getPanel(){
        return gameZone;
    }
    
    private void changed(String message){
        setChanged();
        notifyObservers(message);
    }
    
    public GameState getGameState(){
        return state;
    }
    
    public void reset(){
        Iterator i = al.getCells().entrySet().iterator();
        while(i.hasNext()) {
           Map.Entry<String, Cell> pairs = (Map.Entry)i.next();
           pairs.getValue().setStatus(statusTypes.DEAD);
        }
        gameZone.repaint();
    }

    @Override
    public void run() {
        if(createnextGen()){
            if(al.getAliveCells() > 0){
                try {
                    Thread.sleep(1000);
                    run();
                } catch(Exception ex) {
                    stopGame();
                }
            }
            else{
                stopGame("No more cells alive!");
            }
        }
        else{
            stopGame("No more moves!");
        }
    }
    
    class GameZone extends JPanel implements MouseListener{
        
        public GameZone(){
            addMouseListener(this);
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
    }

}