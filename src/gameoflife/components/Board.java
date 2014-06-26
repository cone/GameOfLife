/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gameoflife.components;

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
import static gameoflife.components.Cell.statusTypes;

import javax.swing.*;

/**
 *
 * @author toshiba
 */
public class Board extends JPanel implements MouseListener{
   private HashMap<String, Cell> cells = new HashMap();
   private HashMap<String, Cell> newcells = new HashMap();
   private int panelSideValue;
   
   public Board(int panelSideValue){
       this.panelSideValue = panelSideValue;
       addMouseListener(this);
   }

   public void addCell(int x, int y) {
      doAddCell(new Cell(x,y));
   }
   
   public void addCell(Cell cell) {
      doAddCell(cell);
   }
   
   private void doAddCell(Cell cell){
        if(!cells.containsValue(cell)){
            cells.put(cell.getKey(),cell);
        }
    }
   
   public void selectCell(MouseEvent e) {
      int x = e.getPoint().x/Cell.sideValue;
      int y = e.getPoint().y/Cell.sideValue;
      Cell cell = cells.get(Cell.genKey(x, y));
      if(cell != null){
          cell.setStatus(statusTypes.ALIVE);
      }
      repaint();
   }
   
   public void createnextGen(){
       int sides = panelSideValue/Cell.sideValue;
        int[] xy;
        Cell cell;
        Cell aux;
        for (int i = 0; i < sides; i++) {
            for (int j = 0; j < sides; j++) {
                cell = cells.get(Cell.genKey(i, j));
                for(int i1=0; i1<Cell.surroundingCoords.length; i1++){
                    xy = Cell.surroundingCoords[i1];
                    aux = cells.get(Cell.genKey(i+xy[0], j+xy[1]));
                    if(aux != null && aux.getStatus() == statusTypes.ALIVE){
                        cell.addNeighbor();
                    }
                }
            }
        }
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(panelSideValue, panelSideValue);
   }

   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      Iterator i = cells.entrySet().iterator();
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